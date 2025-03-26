package gov.saip.applicationservice.common.service.agency.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.UserManageClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.agency.LkAgentTypeDto;
import gov.saip.applicationservice.common.dto.agency.TMAgencyRequestDataDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestListDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.dto.notifications.AppNotificationDto;
import gov.saip.applicationservice.common.dto.notifications.NotificationLanguage;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.enums.agency.AgentType;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyStatusCode;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.mapper.agency.TrademarkAgencyRequestMapper;
import gov.saip.applicationservice.common.model.ApplicationCustomer;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.model.agency.LKTrademarkAgencyRequestStatus;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyStatusChangeLog;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.repository.agency.TrademarkAgencyRequestRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.activityLog.ActivityLogService;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyChangeLogService;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.report.dto.UserLightDto;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode.TM_AGENCY_REPLY_TO_APPLICANT;
import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.TRADEMARK;
import static gov.saip.applicationservice.common.enums.RequestTypeEnum.TRADEMARK_AGENCY_REQUEST;
import static gov.saip.applicationservice.common.enums.SupportServiceType.OWNERSHIP_CHANGE;
import static gov.saip.applicationservice.common.enums.agency.TrademarkAgencyStatusCode.ACCEPTED;
import static gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType.INVESTIGATION;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TrademarkAgencyRequestImpl extends BaseServiceImpl<TrademarkAgencyRequest, Long> implements TrademarkAgencyRequestService {
    public static final int MAX_NUM_OF_YEARS_TO_AGENCY = 5;
    private final TrademarkAgencyRequestRepository trademarkAgencyRequestRepository;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final SupportServiceProcess supportServiceProcess;
    private final BPMCallerServiceImpl bpmCallerService;
    private final UserManageClient userManageClient;
    private final TrademarkAgencyRequestMapper trademarkAgencyRequestMapper;
    private final ApplicationCustomerService applicationCustomerService;
	private final CustomerServiceCaller customerServiceCaller;
    private final LKSupportServicesService lkSupportServicesService;
    private final ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    private final TrademarkAgencyChangeLogService trademarkAgencyChangeLogService;
    private final NotificationCaller notificationCaller;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    @Lazy
    @Autowired
    private ActivityLogService activityLogService;
    @Lazy
    @Autowired
    private ApplicationInfoService applicationInfoService;
    @Lazy
    @Autowired
    private ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    @Lazy
    @Autowired
    private LicenceRequestService licenceRequestservice;
    @Override
    protected BaseRepository<TrademarkAgencyRequest, Long> getRepository() {
        return trademarkAgencyRequestRepository;
    }

    @Override
    public TrademarkAgencyRequest insert(TrademarkAgencyRequest entity) {
        validateNewAgencyRequest(entity);
        entity.setRequestStatus(new LKTrademarkAgencyRequestStatus(TrademarkAgencyStatusCode.UNDER_PROCEDURE.getId()));
        entity.setRequestNumber(generateRequestNumber());
        if(TrademarkAgencyType.CHANGE_OWNERSHIP.equals(entity.getAgencyType())){
            List<LKSupportServices> services = lkSupportServicesService.findByCodeAndCategory(OWNERSHIP_CHANGE, TRADEMARK.name());
            entity.setSupportServices(services);

        }
        TrademarkAgencyRequest insert = super.insert(entity);
        StartProcessResponseDto startProcessResponseDto = startTrademarkAgencyRequestProcess(entity);
        activityLogService.insertTrademarkAgencyActivityLogStatus(startProcessResponseDto.getTaskHistoryUIDto(), entity.getId());
        return insert;
    }

    private void validateNewAgencyRequest(TrademarkAgencyRequest entity) {

        agencyAddOrUpdateCommonValidation(entity);

        if (isRegisterationOrOwnershipType(entity)) {
            checkIfThereIsRequestUnderProcedure(entity);
            checkIfThereIsAcceptedRequestToClient(entity);
        }

        if (TrademarkAgencyType.SUPPORT_SERVICES.equals(entity.getAgencyType())) {
            validateApplicationInfoHasValidStatus(entity.getApplication().getId());
            checkIfSupportServicesAllowedForClient(entity);
        } else if (TrademarkAgencyType.CHANGE_OWNERSHIP.equals(entity.getAgencyType())) {
            validateApplicationInfoHasValidStatus(entity.getApplication().getId());
            validateEnteredCustomerCodeIsNotAppOwner(entity);
        }

        LkAgentTypeDto dto = customerServiceFeignClient.getAgentTypeById(entity.getAgentTypeId().intValue()).getPayload();
        if(AgentType.LEGAL_AGENT.equals(dto.getCode())) {
            validateNumberOfRequestsThisYear(entity,dto.getAllowedApplicationsCountYearly());
        }
    }

    private void agencyAddOrUpdateCommonValidation(TrademarkAgencyRequest entity) {
        validateStartAndEndDateRanges(entity);
        entity.setClientCustomerCode(entity.getClientCustomerCode().toUpperCase());
        isValidCustomerCode(entity);
    }

    private void isValidCustomerCode(TrademarkAgencyRequest entity) {
        customerServiceFeignClient.validateCustomerCode(entity.getClientCustomerCode());
    }

    private void validateApplicationInfoHasValidStatus(Long applicationId) {
        List<String> ApplicationStatus = Arrays.asList(ApplicationStatusEnum.REVOKED_PURSUANT_TO_COURT_RULING.name(), ApplicationStatusEnum.ABANDONED.name());
        boolean applicationHasValidStatus = applicationInfoService.checkApplicationStatusIsNotInListOfStatus(applicationId, ApplicationStatus);
        if(!applicationHasValidStatus)
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_STATUS_IS_NOT_VALID, HttpStatus.BAD_REQUEST, null);


    }

    private static boolean isRegisterationOrOwnershipType(TrademarkAgencyRequest entity) {
        return TrademarkAgencyType.TRADEMARK_REGISTRATION.equals(entity.getAgencyType()) || TrademarkAgencyType.CHANGE_OWNERSHIP.equals(entity.getAgencyType());
    }

    private void validateNumberOfRequestsThisYear(TrademarkAgencyRequest entity,Integer allowedApplicationsCountYearly) {
        Long numberOfRequestsThisYear = trademarkAgencyRequestRepository.getRequestsCountThisYearPerAgent(entity.getAgentCustomerCode());

        if(numberOfRequestsThisYear >= allowedApplicationsCountYearly) {
            throw new BusinessException(Constants.ErrorKeys.AGENCY_EXCEEDED_REQUESTS_THIS_YEAR,HttpStatus.BAD_REQUEST,null);
        }
    }



    private void checkIfSupportServicesAllowedForClient(TrademarkAgencyRequest entity) {
        List<ApplicationCustomer> customers = applicationCustomerService.getAppCustomersByTypeOrCodeOrCustomerId(ApplicationCustomerType.MAIN_OWNER, entity.getApplication().getId(), null, entity.getClientCustomerCode());
        if (customers == null || customers.isEmpty()) {
            isRequestSupportServicesAllowedForNonOwner(entity);
//            throw new BusinessException(Constants.ErrorKeys.AGENCY_CLIENT_NOT_OWNER, HttpStatus.BAD_REQUEST, null);
        } else {
            isRequestSupportServicesNotAllowedForOwner(entity);
        }
    }

    private void isRequestSupportServicesAllowedForNonOwner(TrademarkAgencyRequest entity) {
        List<LKSupportServices> services = lkSupportServicesService.getServicesByIds(entity.getSupportServices().stream().map(ser -> ser.getId()).toList());
        if (!SupportServiceType.AGENT_SUBSTITUTION.isSupportServicesCanRequestedByNonOwner(services.stream().map(ser -> ser.getCode()).toList())) {
            throw new BusinessException(Constants.ErrorKeys.AGENCY_SERVICES_CONTAINS_SERVICES_NOT_ALLOWED_FOR_NON_OWNER, HttpStatus.BAD_REQUEST, null);
        }
    }

    private void isRequestSupportServicesNotAllowedForOwner(TrademarkAgencyRequest entity) {
        List<LKSupportServices> services = lkSupportServicesService.getServicesByIds(entity.getSupportServices().stream().map(ser -> ser.getId()).toList());
        if (!SupportServiceType.REVOKE_BY_COURT_ORDER.isSupportServicesCannotRequestedByOwner(services.stream().map(ser -> ser.getCode()).toList())) {
            throw new BusinessException(Constants.ErrorKeys.AGENCY_SERVICES_CONTAINS_SERVICES_NOT_ALLOWED_FOR_OWNER, HttpStatus.BAD_REQUEST, null);
        }
    }

    private void validateEnteredCustomerCodeIsNotAppOwner(TrademarkAgencyRequest entity) {
        List<ApplicationCustomer> customers = applicationCustomerService.getAppCustomersByTypeOrCodeOrCustomerId(ApplicationCustomerType.MAIN_OWNER, entity.getApplication().getId(), null, entity.getClientCustomerCode());
        if (customers != null && !customers.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.SERVICE_NOT_AVAILABLE_TO_APP_OWNER, HttpStatus.BAD_REQUEST, null);
        }
    }

    private void validateStartAndEndDateRanges(TrademarkAgencyRequest entity) {

        if (Utilities.isStartDateEqualEndDate(entity.getStartAgency(), entity.getEndAgency())) {
            throw new BusinessException(Constants.ErrorKeys.COMMON_START_DATE_EQUAL_END_DATE, HttpStatus.BAD_REQUEST, null);
        }

        if (Utilities.isEndDateBeforeStartDate(entity.getStartAgency(), entity.getEndAgency())) {
            throw new BusinessException(Constants.ErrorKeys.COMMON_END_DATE_BEFORE_START_DATE, HttpStatus.BAD_REQUEST, null);
        }

        if (!LocalDate.now().minusYears(MAX_NUM_OF_YEARS_TO_AGENCY).isBefore(entity.getStartAgency())) {
            throw new BusinessException(Constants.ErrorKeys.COMMON_START_DATE_EXCEEDED_MAX_YEARS_IN_PAST, HttpStatus.BAD_REQUEST, new String[]{String.valueOf(MAX_NUM_OF_YEARS_TO_AGENCY)});
        }

        if (entity.getStartAgency().plusYears(MAX_NUM_OF_YEARS_TO_AGENCY).isBefore(entity.getEndAgency())) {
            throw new BusinessException(Constants.ErrorKeys.AGENCY_MAX_EXCEEDED_MAX_YEARS, HttpStatus.BAD_REQUEST, new String[]{String.valueOf(MAX_NUM_OF_YEARS_TO_AGENCY)});
        }
    }

    private void checkIfThereIsAcceptedRequestToClient(TrademarkAgencyRequest entity) {
        List<String> status = List.of(ACCEPTED.name());
        List<TrademarkAgencyRequest> trademarkAgencyRequests = getRequestByStatusAndAppAndClientAndType(entity, status, List.of(entity.getAgencyType()));
        if (trademarkAgencyRequests != null && !trademarkAgencyRequests.isEmpty()) {
            trademarkAgencyRequests.forEach(req -> handleExpiredAgencyInDatesButNotHasStatusExpired(req));
//            if (handleExpiredAgencyInDatesButNotHasStatusExpired(trademarkAgencyRequests.get(0))) {
//                return;
//            }
            //throw new BusinessException(Constants.ErrorKeys.AGENCY_REQUEST_NOT_EXPIRED, HttpStatus.BAD_REQUEST, null);
        }
    }

    public boolean handleExpiredAgencyInDatesButNotHasStatusExpired(TrademarkAgencyRequest trademarkAgencyRequest) {
        if (LocalDate.now().isAfter(trademarkAgencyRequest.getEndAgency())) {
            trademarkAgencyRequest.setRequestStatus(new LKTrademarkAgencyRequestStatus(TrademarkAgencyStatusCode.EXPIRED.getId()));
            trademarkAgencyRequestRepository.save(trademarkAgencyRequest);
            return true;
        }
        return false;
    }

    private void checkIfThereIsRequestUnderProcedure(TrademarkAgencyRequest entity) {
        List<String> status = List.of(TrademarkAgencyStatusCode.NEW.name(), TrademarkAgencyStatusCode.UNDER_PROCEDURE.name(), TrademarkAgencyStatusCode.REQUEST_CORRECTION.name());
        List<TrademarkAgencyRequest> trademarkAgencyRequests = getRequestByStatusAndAppAndClientAndType(entity, status, List.of(entity.getAgencyType()));
        if (trademarkAgencyRequests != null && !trademarkAgencyRequests.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.APPLICANT_APPLY_FOR_NEW_REQUEST_AND_HAS_UNDER_PROCEDURE_REQUEST, HttpStatus.BAD_REQUEST, null);
        }
    }

    private List<TrademarkAgencyRequest> getRequestByStatusAndAppAndClientAndType(TrademarkAgencyRequest entity, List<String> status, List<TrademarkAgencyType> types) {
        Long applicationId = entity.getApplication() == null || entity.getApplication().getId() == null ? null : entity.getApplication().getId();
        return trademarkAgencyRequestRepository.getByClientCodeAndStatusAndAgencyType(entity.getClientCustomerCode(), entity.getAgentCustomerCode(), applicationId, status, types);
    }

    public List<TrademarkAgencyRequestDto> getRequestByStatusAndAppAndClientAndTypeForInvestigation(String clientCustomerCode, String agentCustomerCode) {
        List<TrademarkAgencyRequest> trademarkAgencyRequestList = trademarkAgencyRequestRepository.getByClientCodeAndStatusAndAgencyTypeForInvestigation(clientCustomerCode, agentCustomerCode, ACCEPTED.name(), INVESTIGATION);
        return trademarkAgencyRequestMapper.map(trademarkAgencyRequestList);
    }

    @Override
    public TrademarkAgencyRequest update(TrademarkAgencyRequest entity) {
        agencyAddOrUpdateCommonValidation(entity);

        Optional<TrademarkAgencyRequest> requestIdOpt = trademarkAgencyRequestRepository.findById(entity.getId());
        TrademarkAgencyRequest dbEntity = requestIdOpt.orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, null));
        entity.setReturnedRequestNum(requestIdOpt.get().getReturnedRequestNum());
        if (dbEntity.getProcessRequestId() != null) {
            entity.setRequestStatus(new LKTrademarkAgencyRequestStatus(TrademarkAgencyStatusCode.UNDER_PROCEDURE.getId()));
            entity.setApplication(dbEntity.getApplication());
            RequestTasksDto requestTasksDto = bpmCallerService.getCurrentTaskByRequestId(dbEntity.getProcessRequestId());
            insertStatusChangeLog(dbEntity, requestTasksDto.getTaskId(), requestTasksDto.getProcessDefinitionId(), TrademarkAgencyStatusCode.UNDER_PROCEDURE);
            completeAgentUpdateRequestTask(entity, requestTasksDto);
            buildNotificationRequest(dbEntity , TM_AGENCY_REPLY_TO_APPLICANT);
        }
        return super.update(entity);
    }

    private void insertStatusChangeLog(TrademarkAgencyRequest dbEntity, String taskId, String definitionId, TrademarkAgencyStatusCode newStatus) {
        TrademarkAgencyStatusChangeLog trademarkAgencyStatusChangeLog = new TrademarkAgencyStatusChangeLog();

        trademarkAgencyStatusChangeLog.setTrademarkAgencyRequest(dbEntity);

        trademarkAgencyStatusChangeLog.setNewStatus(new LKTrademarkAgencyRequestStatus(newStatus.getId()));
        trademarkAgencyStatusChangeLog.setPreviousStatus(dbEntity.getRequestStatus());

        trademarkAgencyStatusChangeLog.setTaskDefinitionKey(definitionId);
        trademarkAgencyStatusChangeLog.setTaskInstanceId(taskId);

        trademarkAgencyChangeLogService.insert(trademarkAgencyStatusChangeLog);
    }

    private StartProcessResponseDto startTrademarkAgencyRequestProcess(TrademarkAgencyRequest entity){
        // start process
        List<CustomerSampleInfoDto> customerList = customerServiceFeignClient.getCustomerByListOfCode(new CustomerCodeListDto(List.of(entity.getAgentCustomerCode()))).getPayload();
        if (customerList == null || customerList.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        CustomerSampleInfoDto customer = customerList.get(0);
        StartProcessDto startProcessDto =  StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(customer.getNameAr())
                .fullNameEn(customer.getNameEn())
                .mobile(customer.getMobile())
                .email(customer.getEmail())
                .applicationCategory("TRADEMARK")
                .processName("tm_agency_request_process")
                .requestTypeCode(RequestTypeEnum.TRADEMARK_AGENCY_REQUEST.name())
                .applicationIdColumn(entity.getApplication() == null || entity.getApplication().getId() == null ? null : entity.getApplication().getId().toString())
                .identifier(customer.getIdentifier())
                .requestNumber(entity.getRequestNumber())
                .build();
        startProcessDto.addVariable("APPLICANT_CUSTOMER_CODE",entity.getAgentCustomerCode());
        StartProcessResponseDto startProcessResponseDto = supportServiceProcess.startProcess(startProcessDto);
        entity.setProcessRequestId(startProcessResponseDto.getBusinessKey());
        return startProcessResponseDto;
    }

    private void completeAgentUpdateRequestTask(TrademarkAgencyRequest entity, RequestTasksDto requestTasksDto) {
       CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(new HashMap<>());
        bpmCallerService.completeTaskToUser(requestTasksDto.getTaskId(), completeTaskRequestDto);



    }

    private String generateRequestNumber() {
        String lastRequestNumber = trademarkAgencyRequestRepository.getLastRequestNumber();
        if (lastRequestNumber == null)
            return generateRequestNumber(0L);

        String strNum = lastRequestNumber.split("-")[1];
        return generateRequestNumber(Long.valueOf(strNum));
    }

    private String generateRequestNumber(Long num) {
        return "TMA-" + String.format("%06d", num + 1);
    }

    @Override
    public void updateAgencyCheckerDecision(TrademarkAgencyRequestDto dto) {
        TrademarkAgencyRequest requestReference = trademarkAgencyRequestRepository.findById(dto.getId()).orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, null));
        requestReference.setRequestStatus(new LKTrademarkAgencyRequestStatus(dto.getStatus().getId()));
        insertStatusChangeLog(requestReference, dto.getTaskId(), dto.getTaskDefinitionKey(), dto.getStatus());
        if (dto.getAgencyCheckerNotes() != null && !dto.getAgencyCheckerNotes().isEmpty()) {
            requestReference.setAgencyCheckerNotes(dto.getAgencyCheckerNotes());
        }

        if (requestReference.getRequestStatus().getId().equals(3)) {
            requestReference.setReturnedRequestNum(requestReference.getReturnedRequestNum() + 1);
        }

        trademarkAgencyRequestRepository.save(requestReference);
    }

    @Override
    public TrademarkAgencyRequestDto getRequestDetailsById(Long id) {
        TrademarkAgencyRequest trademarkAgencyRequest = this.findById(id);
        TrademarkAgencyRequestDto trademarkAgencyRequestDto = trademarkAgencyRequestMapper.map(trademarkAgencyRequest);

        LkAgentTypeDto lkAgentTypeDto = customerServiceFeignClient.getAgentTypeById(trademarkAgencyRequest.getAgentTypeId().intValue()).getPayload();
        trademarkAgencyRequestDto.setAgentType(lkAgentTypeDto);

        CustomerSampleInfoDto agentInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(trademarkAgencyRequest.getAgentCustomerCode()).getPayload();
        trademarkAgencyRequestDto.setAgentInfo(agentInfoDto);

        CustomerSampleInfoDto clientInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(trademarkAgencyRequest.getClientCustomerCode()).getPayload();
        trademarkAgencyRequestDto.setClientInfo(clientInfoDto);
        return trademarkAgencyRequestDto;
    }
    public PaginationDto<List<TrademarkAgencyRequestListDto>> filterAndListAgenciesRequests(boolean prev, String query, Integer statusId,
                                                                                      String customerCode, Pageable pageable) {
        List<Integer> statusList = getFilterStatusList(prev, statusId);

        Page<TrademarkAgencyRequest> page = trademarkAgencyRequestRepository.filterAndListAgenciesRequests(statusList, query, customerCode, pageable);

        if (!page.hasContent()) {
            return buildPaginationResponse(page, null);
        }

        List<TrademarkAgencyApplicationMainOwnerDto> content = buildTrademarkAgencyApplicationMainOwnerDtoList(page.getContent());
        Map<String, CustomerSampleInfoDto> codesMap = getStringCustomerSampleInfoDtoMap(content);
        List<TrademarkAgencyRequestListDto> trademarkAgencyRequestListDtos = mapToDtoAndAddClientInfo(content, codesMap);
        if(!prev){
            addCurrentTask(trademarkAgencyRequestListDtos);
        }


        return buildPaginationResponse(page, trademarkAgencyRequestListDtos);
    }

    private List<TrademarkAgencyApplicationMainOwnerDto> buildTrademarkAgencyApplicationMainOwnerDtoList(List<TrademarkAgencyRequest> trademarkAgencyRequestList) {
        List<Long>  trademarkAgencyRequestIdList = trademarkAgencyRequestList.stream().map(tmAgencyRequest -> tmAgencyRequest.getId()).collect(Collectors.toList());
        List<TrademarkAgencyIdApplicationMainOwnerNameDto> trademarkAgencyIdApplicationMainOwnerNameList = trademarkAgencyRequestRepository.getTMAgenciesApplicationMainOwner(trademarkAgencyRequestIdList);
        return trademarkAgencyRequestList.stream().map( tmAgencyRequest -> new TrademarkAgencyApplicationMainOwnerDto(tmAgencyRequest, getApplicationMainOwnerNameFromListWithId(trademarkAgencyIdApplicationMainOwnerNameList, tmAgencyRequest.getId()))).collect(Collectors.toList());
    }

    private String getApplicationMainOwnerNameFromListWithId(List<TrademarkAgencyIdApplicationMainOwnerNameDto> trademarkAgencyIdApplicationMainOwnerNameList, Long id){
        return trademarkAgencyIdApplicationMainOwnerNameList.stream().filter(dto -> dto.getId().equals(id)).map(dto -> dto.getApplicationMainOwner()).findAny().orElse(null);
    }

    private void addCurrentTask(List<TrademarkAgencyRequestListDto> trademarkAgencyRequestListDtos) {
        for(TrademarkAgencyRequestListDto agencyDto : trademarkAgencyRequestListDtos){
            RequestTasksDto taskDto = bpmCallerFeignClient.getTaskByRowIdAndType(TRADEMARK_AGENCY_REQUEST, agencyDto.getId()).getPayload();
            setTaskAssigneeInfo(agencyDto, taskDto);
            agencyDto.setTask(taskDto);
        }
    }

    private void setTaskAssigneeInfo(TrademarkAgencyRequestListDto agencyDto, RequestTasksDto taskDto) {
        if(taskDto != null){
            UserLightDto assigneeInfo = userManageClient.getUserDetails(taskDto.getAssignee()).getPayload();
            agencyDto.setAssigneeInfo(assigneeInfo);
        }
    }

    private static List<Integer> getFilterStatusList(boolean prev, Integer statusId) {
        List<Integer> statusList;
        if (statusId != null) {
            statusList = List.of(statusId);
        } else {
            statusList = prev ? TrademarkAgencyStatusCode.getPreviousRequestsIdsList() : TrademarkAgencyStatusCode.getCurrentRequestsIdsList();
        }
        return statusList;
    }

    private List<TrademarkAgencyRequestListDto> mapToDtoAndAddClientInfo(List<TrademarkAgencyApplicationMainOwnerDto> content, Map<String, CustomerSampleInfoDto> codesMap) {
        List<TrademarkAgencyRequest> TrademarkAgencyRequestList =  content.stream().map(trademarkAgencyApplicationMainOwnerDto -> trademarkAgencyApplicationMainOwnerDto.getTrademarkAgencyRequest()).collect(Collectors.toList());
        List<TrademarkAgencyRequestListDto> trademarkAgencyRequestListDtos = trademarkAgencyRequestMapper.mapEntityToTrademarkAgencyRequestListDto(TrademarkAgencyRequestList);
        Map<Long, String> mainApplicationOwneAndAgencyRequestIdMap = getMainApplicationOwneAndAgencyRequestIdMap(content);
        trademarkAgencyRequestListDtos.forEach(item -> item.setClientInfo(codesMap.get(mainApplicationOwneAndAgencyRequestIdMap.get(item.getId()))));
        return trademarkAgencyRequestListDtos;
    }

    private Map<Long, String> getMainApplicationOwneAndAgencyRequestIdMap(List<TrademarkAgencyApplicationMainOwnerDto> TrademarkAgencyApplicationMainOwnerDtoList){
        return  TrademarkAgencyApplicationMainOwnerDtoList.stream()
                .collect(Collectors.toMap(
                        dto -> dto.getTrademarkAgencyRequest().getId(),
                        dto -> dto.getApplicationMainOwner() != null ? dto.getApplicationMainOwner() : dto.getTrademarkAgencyRequest().getClientCustomerCode()
                ));
    }

    private Map<String, CustomerSampleInfoDto> getStringCustomerSampleInfoDtoMap(List<TrademarkAgencyApplicationMainOwnerDto> content) {
        List<String> codes = content.stream().map(req -> req.getApplicationMainOwner() != null ? req.getApplicationMainOwner() : req.getTrademarkAgencyRequest().getClientCustomerCode()).toList();
        Map<String, CustomerSampleInfoDto> codesMap = customerServiceFeignClient.getCustomersByCodes(ListBodyDto.<String>builder().data(codes).build()).getPayload();
        return codesMap;
    }

    private static PaginationDto<List<TrademarkAgencyRequestListDto>> buildPaginationResponse(Page<TrademarkAgencyRequest> page, List<TrademarkAgencyRequestListDto> trademarkAgencyRequestListDtos) {
        return PaginationDto.<List<TrademarkAgencyRequestListDto>>builder().
                totalElements(page.getTotalElements()).
                content(trademarkAgencyRequestListDtos).
                totalPages(page.getTotalPages()).
                build();
    }

    @Transactional
    public void updateCheckerUsername(Long id, String checkerUsername) {
        trademarkAgencyRequestRepository.updateCheckerUsername(id, checkerUsername);
    }
  
    @Override
    public void validateAgencySearchResult(ApplicationInfo application, TrademarkAgencyType trademarkAgencyType) {
        validateApplicationCategory(application);
        validateApplicationStatus(trademarkAgencyType, application);
        validateRequestApplicant(application);
    }

    private static void validateApplicationStatus(TrademarkAgencyType trademarkAgencyType, ApplicationInfo application) {
        if (trademarkAgencyType.getStatus() != null && !trademarkAgencyType.getStatus().contains(ApplicationStatusEnum.valueOf(application.getApplicationStatus().getCode()))) {
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_STATUS_IS_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
    }

    private static void validateApplicationCategory(ApplicationInfo application) {
        if (!ApplicationCategoryEnum.TRADEMARK.name().equals(application.getCategory().getSaipCode())) {
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_CATEGORY_IS_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateRequestApplicant(ApplicationInfo applicationInfo) {
        String mainApplicationApplicantCustomerCode = Utilities.extractCustomerCode(applicationInfo);
        String agencyApplicant = Utilities.getCustomerCodeFromHeaders();
        if (mainApplicationApplicantCustomerCode.equalsIgnoreCase(agencyApplicant)) {
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_SUPPORT_SERVICE_ACTOR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CustomerSampleInfoDto getAgentCustomerSampleInfoDtoByRequestNumberOfChangeOwnershipRequest(String requestNumber) {
        String customerCode = trademarkAgencyRequestRepository.getAgentCustomerCodeByRequestNumber(requestNumber);
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        customerCodeListDto.setCustomerCode(Arrays.asList(customerCode));
        List<CustomerSampleInfoDto> customerSampleInfoDto = customerServiceCaller.getCustomerByListOfCode(customerCodeListDto).getPayload();
        return customerSampleInfoDto != null &&  !customerSampleInfoDto.isEmpty() ? customerSampleInfoDto.get(0): null;
    }

    @Override
    public String getAgentCustomerCodeByRequestNumber(String requestNumber) {
        return trademarkAgencyRequestRepository.getAgentCustomerCodeByRequestNumber(requestNumber);
    }

        @Override
    public List<CustomerSearchResultDto> searchInActiveRegistrationAgenciesByAgentAndClientCodes(String customerCode) {
        List<String> codes = trademarkAgencyRequestRepository.getActiveAgenciesClientCodesByAgentCode(Utilities.getCustomerCodeFromHeaders(), null, ACCEPTED.getId(), TrademarkAgencyType.TRADEMARK_REGISTRATION);
        if (codes == null || codes.isEmpty() || customerCode == null || customerCode.isBlank()) {
            return Collections.emptyList();
        }
        CustomerCodeListDto codesDto = CustomerCodeListDto.builder().customerCode(codes).build();
        return customerServiceFeignClient.searchInListOfCodesStartWith(customerCode, codesDto).getPayload();
    }

    @Override
    public PaginationDto<List<TrademarkAgencyRequestListDto>> searchChangeOwnershipAccepted(Pageable pageable) {
        try {
            log.info(" ############ searchChangeOwnershipAccepted: " + Utilities.getCustomerCodeFromHeaders());
            Page<TrademarkAgencyRequest> page = trademarkAgencyRequestRepository.getChangeOwnerByAgentCustomerCode(Utilities.getCustomerCodeFromHeaders(), TrademarkAgencyType.CHANGE_OWNERSHIP, ACCEPTED.getId(), pageable);
            if (!page.hasContent()) {
                return buildPaginationResponse(page, null);
            }

            List<TrademarkAgencyApplicationMainOwnerDto> content = buildTrademarkAgencyApplicationMainOwnerDtoList(page.getContent());

            List<TrademarkAgencyRequest> TrademarkAgencyRequestList =  content.stream().map(trademarkAgencyApplicationMainOwnerDto -> trademarkAgencyApplicationMainOwnerDto.getTrademarkAgencyRequest()).collect(Collectors.toList());
            List<TrademarkAgencyRequestListDto> trademarkAgencyRequestListDtos = trademarkAgencyRequestMapper.mapEntityToTrademarkAgencyRequestListDto(TrademarkAgencyRequestList);
            trademarkAgencyRequestListDtos.forEach(item -> item.setClientInfo(customerServiceFeignClient.getAnyCustomerByCustomerCode(item.getClientCustomerCode()).getPayload()));
            return buildPaginationResponse(page, trademarkAgencyRequestListDtos);
        } catch (Exception e) {
            log.info(" ############ searchChangeOwnershipAccepted EXCEPTION: " + e.getMessage());
            e.printStackTrace();
            throw new BusinessException(Constants.ErrorKeys.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void applyAgencyAfterRequestApproval(Long id) {
//         Optional<TrademarkAgencyRequest> agency = trademarkAgencyRequestRepository.findById(id);
//         if (agency.isEmpty()) {
//             return;
//         }
//         TrademarkAgencyRequest agencyRequest = agency.get();
//        if (TrademarkAgencyType.CHANGE_OWNERSHIP.equals(agencyRequest.getAgencyType()) || TrademarkAgencyType.SUPPORT_SERVICES.equals(agencyRequest.getAgencyType())) {
//            CustomerSampleInfoDto agentInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(agencyRequest.getAgentCustomerCode()).getPayload();
//            ApplicationCustomerData data = ApplicationCustomerData.builder()
//                    .applicationInfo(agencyRequest.getApplication())
//                    .trademarkAgencyRequest(agencyRequest)
//                    .customerCode(agencyRequest.getAgentCustomerCode())
//                    .customerType(ApplicationCustomerType.AGENT)
//                    .customerAccessLevel(CustomerApplicationAccessLevel.VIEW_ONLY)
//                    .customerId(agentInfoDto.getId())
//                    .build();
//            applicationCustomerService.addCustomerToApplicationIfNotExistsOrIgnore(data);
//         }
            
    }

    @Override
    public List<TrademarkAgencyRequest> getActiveAgenciesByAgentAndClientCodes(String agentCode, String clientCode,  TrademarkAgencyType agencyType) {
        List<TrademarkAgencyRequest> requests = trademarkAgencyRequestRepository.getActiveAgenciesByAgentCode(agentCode, clientCode, ACCEPTED.getId(), agencyType);
        if (requests == null || requests.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_NO_AGENCY_VALID_TO_REGISTER);
        }
        return requests;
    }

  	public boolean isAgentHasPermissionForApplicationSupportService(String agentCode, String customerCode, Long appId, SupportServiceType serviceCode, Long parentServiceId) {

        if (SupportServiceType.TRADEMARK_APPEAL_REQUEST.equals(serviceCode)) {
            boolean isCurrentAgentHasAccess = isCurrentLoggedInAgentTheApplicantToApplicationOrSupportService(agentCode, appId, parentServiceId);
            if (isCurrentAgentHasAccess) { // if not true we will check if the agent has agency: to handle if agent has agency to appeal but not the applicant
                return true;
            }
        }

        return trademarkAgencyRequestRepository.isAgentHasPermissionForApplicationSupportService(agentCode, customerCode, ACCEPTED.getId(), serviceCode, appId, LocalDate.now());
    }

    private boolean isCurrentLoggedInAgentTheApplicantToApplicationOrSupportService(String customerCode, Long appId, Long parentServiceId) {
        if (parentServiceId != null) {
            return applicationSupportServicesTypeService.isCustomerServiceApplicant(parentServiceId, customerCode);
        } else {
            return applicationInfoService.isCustomerApplicationApplicant(appId, Utilities.getCustomerIdFromHeadersAsLong());
        }
    }

//    @Override
//    public TrademarkAgencyRequest getAgencyWithPermissionForTrademarkAgencyType(String agentCode, String customerCode, TrademarkAgencyType type) {
//        TrademarkAgencyRequest trademarkAgency = trademarkAgencyRequestRepository.getActiveAgenciesByAgentCode(agentCode, customerCode, TrademarkAgencyStatusCode.ACCEPTED.getId(), type).get(0);
//        if(trademarkAgency == null)
//            throw new BusinessException(Constants.ErrorKeys.VALIDATION_NO_AGENCY_VALID_TO_REGISTER, HttpStatus.INTERNAL_SERVER_ERROR);
//        return trademarkAgency;
//    }

    @Override
    public TrademarkAgencyRequest getAgencyForServicesByAgentAndAppAndServiceType(String agentCode, String customerCode, Long appId, SupportServiceType serviceCode) {
        List<TrademarkAgencyRequest> agencyForApplicationSupportService = trademarkAgencyRequestRepository.getAgencyForApplicationSupportService(agentCode, customerCode, ACCEPTED.getId(), serviceCode, appId, LocalDate.now());
        if (agencyForApplicationSupportService == null || agencyForApplicationSupportService.isEmpty()) {
            return null;
        }
        return agencyForApplicationSupportService.get(0);
    }

    @Override
    public void validateAgencyExistAndActiveForSupportServiceBySupportServiceParentId(String agencyNumber, SupportServiceType serviceCode, ApplicantType applicantType, Long parentSupportServiceId) {
        CustomerCodeAndApplicationIdDTO customerCodeAndApplicationIdDTO = licenceRequestservice.getLicenceRequestCustomerCodeAndApplicationId(parentSupportServiceId);
        this.validateAgencyExistAndActiveForSupportServiceWithGettingMainOwnerIfNeeded(customerCodeAndApplicationIdDTO.getCustomerCode(), agencyNumber, serviceCode, applicantType, customerCodeAndApplicationIdDTO.getApplicationId());
    }

    private void validateAgencyExistAndActiveForSupportServiceWithGettingMainOwnerIfNeeded(String customerCode, String agencyRequestNumber, SupportServiceType serviceCode, ApplicantType applicantType, Long applicationId) {
        String agentCode = Utilities.getCustomerCodeFromHeaders();
        if(applicantType.equals(ApplicantType.OWNERS_AGENT))
            customerCode = applicationRelevantTypeRepository.getMainApplicantCustomerCodeByApplicationInfoId(applicationId);
        this.validateAgencyExistAndActiveForSupportService(agentCode, customerCode, agencyRequestNumber, serviceCode);
    }

    @Override
    public void validateAgencyExistAndActiveForSupportServiceByAgencyRequestNumberAndAgentCodeWithGettingMainOwnerIfNeeded( String agencyRequestNumber, SupportServiceType serviceCode, ApplicantType applicantType, Long applicationId) {
        String agentCode = Utilities.getCustomerCodeFromHeaders();
        if (applicantType.equals(ApplicantType.OWNERS_AGENT)){
            String customerCode = applicationRelevantTypeRepository.getMainApplicantCustomerCodeByApplicationInfoId(applicationId);
            this.validateAgencyExistAndActiveForSupportService(agentCode, customerCode, agencyRequestNumber, serviceCode);
        } else if (applicantType.equals(ApplicantType.LICENSED_CUSTOMER_AGENT)) {
            validateAgencyExistAndActiveForSupportServiceByAgencyRequestNumberAndAgentCode(agentCode, agencyRequestNumber, serviceCode);
        }
    }


    private void validateAgencyExistAndActiveForSupportServiceByAgencyRequestNumberAndAgentCode(String agentCode, String agencyRequestNumber, SupportServiceType serviceCode) {
        boolean agencyExists = serviceCode.equals(SupportServiceType.OWNERSHIP_CHANGE) ? trademarkAgencyRequestRepository.agencyExistAndActiveForChangeOwnership(agentCode, agencyRequestNumber) :
                trademarkAgencyRequestRepository.agencyExistAndActiveForSupportService(agentCode, agencyRequestNumber, serviceCode);
        if (!agencyExists)
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_NO_AGENCY_VALID_TO_REGISTER, HttpStatus.BAD_REQUEST);
    }

    @Override
    public void validateAgencyExistAndActiveForSupportService(String agentCode, String customerCode, String agencyRequestNumber, SupportServiceType serviceCode) {
        boolean agencyExists = serviceCode.equals(SupportServiceType.OWNERSHIP_CHANGE) ? trademarkAgencyRequestRepository.agencyExistAndActiveForChangeOwnership(agentCode, customerCode, agencyRequestNumber) :
                trademarkAgencyRequestRepository.agencyExistAndActiveForSupportService(agentCode, customerCode, agencyRequestNumber, serviceCode);
        if (!agencyExists)
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_NO_AGENCY_VALID_TO_REGISTER, HttpStatus.BAD_REQUEST);
    }

	@Override
    public TrademarkAgencyRequest getActiveAgentAgnecyRequestOnApplication(String agentCode, String agencyRequestNumber, Long applicationId, TrademarkAgencyType agencyType) {
        List<TrademarkAgencyRequest> activeAgentAgencyRequestOnApplication = trademarkAgencyRequestRepository.getActiveAgentAgencyRequestOnApplication(agentCode, agencyRequestNumber, applicationId, ACCEPTED.getId(), agencyType);
        if (activeAgentAgencyRequestOnApplication == null || activeAgentAgencyRequestOnApplication.isEmpty()) {
            return null;
        }
        return activeAgentAgencyRequestOnApplication.get(0);
    }

    @Override
    public String getAgencyCustomerCodeByRequestNumber(String agencyRequestNumber) {
        String customerCode = trademarkAgencyRequestRepository.getAgencyCustomerCodeByRequestNumber(agencyRequestNumber);
        if(customerCode == null)
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_NO_AGENCY_VALID_TO_REGISTER, HttpStatus.BAD_REQUEST);
        return customerCode;
    }

    @Override
    public List<Long> getProcessRequestIdsByAgencyRequestNumber(String agencyRequestNumber, LocalDate fromFilingDate, LocalDate toFilingDate) {
        List<Long> agencies = trademarkAgencyRequestRepository.getProcessRequestIdByAgencyRequestNumber(agencyRequestNumber, fromFilingDate, toFilingDate);
        if(agencies == null)
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_NO_AGENCY_VALID_TO_REGISTER, HttpStatus.BAD_REQUEST);
        return agencies;
    }

    @Override
    public CustomerSampleInfoDto getAgentInfo(SupportServiceType serviceCode, String customerCode, Long applicationId) {
        String agentCode = trademarkAgencyRequestRepository.getAgentCodeForAgency(serviceCode, customerCode, applicationId);
        return agentCode == null? null :customerServiceCaller.getCustomerInfoByCustomerCode(agentCode);
    }
    @Override
    public List<String> getAgentCodesByApplicationIdAndSupportServiceCode(SupportServiceType serviceCode, Long applicationId) {
        return trademarkAgencyRequestRepository.getAgentCodesByApplicationIdAndSupportServiceCode(applicationId, serviceCode);
    }

    @Override
    public List<BaseStatusChangeLogDto> getByTrademarkAgencyStatusChangeLogById(Long id) {
        List<TrademarkAgencyStatusChangeLog> statusLog = trademarkAgencyChangeLogService.getByTrademarkAgencyId(id);
        List<BaseStatusChangeLogDto> baseStatusChangeLogDtos = trademarkAgencyRequestMapper.mapToBaseStatusChangeLogDto(statusLog);
        return baseStatusChangeLogDtos;
    }

    @Override
    public String getTrademarkAgencyRequestNumberById(Long id) {
        return trademarkAgencyRequestRepository.getTrademarkAgencyRequestNumberById(id);
    }

    @Override
    public TMAgencyRequestDataDto getTMAgencyRequestDataById(Long id) {
        log.info("getTMAgencyRequestDataById id {} ", id);
        TMAgencyRequestDataDto tmAgencyRequestDataDto =  trademarkAgencyRequestRepository.getTMAgencyRequestDataById(id);
        if(tmAgencyRequestDataDto != null) {
            if (TrademarkAgencyType.CHANGE_OWNERSHIP.equals(tmAgencyRequestDataDto.getAgencyType())) {
                tmAgencyRequestDataDto.setAgencyTypeNameEn("Ownership Change Agency Request");
                tmAgencyRequestDataDto.setAgencyTypeNameAr("طلب وكالة نقل ملكية");
            } else if (TrademarkAgencyType.TRADEMARK_REGISTRATION.equals(tmAgencyRequestDataDto.getAgencyType())) {
                tmAgencyRequestDataDto.setAgencyTypeNameEn("Trademark Registration Agency Request");
                tmAgencyRequestDataDto.setAgencyTypeNameAr("طلب وكالة تسجيل علامة");
            } else if (TrademarkAgencyType.SUPPORT_SERVICES.equals(tmAgencyRequestDataDto.getAgencyType())) {
                tmAgencyRequestDataDto.setAgencyTypeNameEn("Support Service Agency Request");
                tmAgencyRequestDataDto.setAgencyTypeNameAr("طلب وكالة خدمات مساندة");
            }
            return tmAgencyRequestDataDto;
        }else{
            return null;
        }
    }


    private void buildNotificationRequest(TrademarkAgencyRequest request, NotificationTemplateCode notificationTemplateCode){
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(request.getAgentCustomerCode()).getPayload();
        Map<String , Object> notificationParams = new HashMap<>();
        notificationParams.put("requestNumber",request.getRequestNumber());
        NotificationDto emailDto = NotificationDto
                .builder()
                .to(customerSampleInfoDto.getEmail())
                .messageType(Constants.MessageType.EMAIL_TYPE_MESSAGE)
                .build();
        NotificationDto smsDto = NotificationDto
                .builder()
                .to(customerSampleInfoDto.getMobile())
                .build();
        AppNotificationDto appDto = AppNotificationDto.builder()
                .requestId(String.valueOf(request.getProcessRequestId()))
                .serviceId(String.valueOf(request.getId()))
                .serviceCode(TRADEMARK_AGENCY_REQUEST.name())
                .routing(ApplicationCategoryEnum.TRADEMARK.name()).date(LocalDateTime.now())
                .userNames(List.of((String)request.getAgentCustomerCode())).build();

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .lang(NotificationLanguage.AR)
                .code(notificationTemplateCode)
                .templateParams(notificationParams)
                .email(emailDto)
                .sms(smsDto)
                .app(appDto)
                .build();
        notificationCaller.sendAllToSpecificUser(notificationRequest);
    }

    @Override
    public void updateTrademarkAgencyToExpiredStatus() {
        trademarkAgencyRequestRepository.updateTrademarkAgencyToExpiredStatus();
    }
    
    @Override
    public List<Long> getExpiredTrademarkAgencyProcessRequestIds() {
        return trademarkAgencyRequestRepository.getExpiredTrademarkAgencyProcessRequestIds();
    }


}
