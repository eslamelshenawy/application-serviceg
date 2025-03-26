package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.mapper.RevokeLicenceRequestMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LicenceRequest;
import gov.saip.applicationservice.common.model.RevokeLicenceRequest;
import gov.saip.applicationservice.common.repository.RevokeLicenceRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import gov.saip.applicationservice.common.service.supportService.SupportServiceStatusChangeLogService;
import gov.saip.applicationservice.util.LicenceRequestUtil;
import gov.saip.applicationservice.util.Utilities;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static gov.saip.applicationservice.common.enums.SupportServiceType.REVOKE_LICENSE_REQUEST;

@Service
@Transactional
@AllArgsConstructor
public class RevokeLicenceRequestServiceImpl extends SupportServiceRequestServiceImpl<RevokeLicenceRequest> implements RevokeLicenceRequestService {

    private final RevokeLicenceRequestRepository revokeLicenceRequestRepository;
    private final LicenceRequestService licenceRequestService;
    private final LicenceRequestUtil licenceRequestUtil;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationInfoService applicationInfoService;
    private final RevokeLicenceRequestMapper revokeLicenceRequestMapper;
    private final CustomerServiceCaller customerServiceCaller;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final SupportServiceStatusChangeLogService supportServiceStatusChangeLogService;
    private final ApplicationCustomerService applicationCustomerService;
    private final TrademarkAgencyRequestService trademarkAgencyRequestService;

    @Autowired
    @Lazy
    private OppositionRevokeLicenceRequestService oppositionRevokeLicenceRequestService;


    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return revokeLicenceRequestRepository;
    }


    @Override
    public RevokeLicenceRequest insert(RevokeLicenceRequest entity) {
        LicenceRequest licenceRequest = licenceRequestService.findById(entity.getLicenceRequest().getId());
        entity.setApplicationInfo(licenceRequest.getApplicationInfo());
        licenceRequestUtil.validateLicenceRequestOwner(entity.getApplicantType(), entity.getAgencyRequestNumber(), licenceRequest.getCustomerId(), entity.getApplicationInfo().getId(), REVOKE_LICENSE_REQUEST);
        return super.insert(REVOKE_LICENSE_REQUEST,entity, licenceRequest.getId());
    }

    @Override
    public RevokeLicenceRequest update(RevokeLicenceRequest entity) {
        RevokeLicenceRequest revokeLicenceRequest = findById(entity.getId());
        revokeLicenceRequest.setNotes(entity.getNotes());
        revokeLicenceRequest.setLicenceRequest(entity.getLicenceRequest());
        revokeLicenceRequest.setAgencyRequestNumber(entity.getAgencyRequestNumber());
        revokeLicenceRequest.setApplicantType(entity.getApplicantType() !=null ? entity.getApplicantType():revokeLicenceRequest.getApplicantType());
        revokeLicenceRequest.setDocuments(entity.getDocuments() != null ? entity.getDocuments() : revokeLicenceRequest.getDocuments());
        revokeLicenceRequest = super.update(revokeLicenceRequest);
        RequestTasksDto requestTasksDto = this.completeUserTask(revokeLicenceRequest);
        this.updateRequestStatus(revokeLicenceRequest.getId(), requestTasksDto);
        return revokeLicenceRequest;
    }

    private void updateRequestStatus(Long serviceId, RequestTasksDto requestTasksDto) {
        SupportServiceStatusChangeLogDto statusChangeLogDto = new SupportServiceStatusChangeLogDto();
        statusChangeLogDto.setSupportServicesTypeId(serviceId);
        statusChangeLogDto.setNewStatusCode(SupportServiceRequestStatusEnum.UNDER_PROCEDURE.name());
        statusChangeLogDto.setTaskDefinitionKey(requestTasksDto.getTaskDefinitionKey());
        statusChangeLogDto.setTaskInstanceId(requestTasksDto.getProcessInstanceId());
        supportServiceStatusChangeLogService.insert(statusChangeLogDto);
    }

    private RequestTasksDto completeUserTask(RevokeLicenceRequest revokeLicenceRequest){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.REVOKE_LICENSE_REQUEST, revokeLicenceRequest.getId()).getPayload();
        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", "YES");
        Map<String, Object> supportServiceId = new LinkedHashMap();
        supportServiceId.put("value", revokeLicenceRequest.getId().toString());
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        processVars.put("supportServiceId", supportServiceId);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);

        return requestTasksDto;
    }


    @Override
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        startRevokeLicenceProcess(id);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }
    // Start process Revoke Licence
    private void startRevokeLicenceProcess(Long id) {
        RevokeLicenceRequest entity = findById(id);
        CustomerSampleInfoDto clientCustomerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(entity.getCreatedByCustomerCode()).getPayload();
        Long licenceRequestId = entity.getLicenceRequest() == null ? null : entity.getLicenceRequest().getId();
        String customerCode = licenceRequestService.getLicensedCustomerCodeByLicenseId(licenceRequestId);
        CustomerSampleInfoDto oldCustomer = customerServiceFeignClient.getAnyCustomerByCustomerCode(customerCode).getPayload();
        Map<String, Object> variable = new HashMap<>();
        variable.put("OLD_OWNER_MAIL", oldCustomer == null ? null : oldCustomer.getEmail());
        variable.put("OLD_OWNER_MOBILE", oldCustomer == null ? null : oldCustomer.getMobile());
        variable.put("OLD_OWNER_NAME", oldCustomer == null ? null : oldCustomer.getCreatedByUser());
        variable.put("OLD_CUSTOMER_CODE", oldCustomer == null ? null : oldCustomer.getCode());
        // Start process
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(clientCustomerSampleInfoDto.getNameAr())
                .fullNameEn(clientCustomerSampleInfoDto.getNameEn())
                .mobile(clientCustomerSampleInfoDto.getMobile())
                .email(clientCustomerSampleInfoDto.getEmail())
                .applicationCategory(entity.getApplicationInfo().getCategory().getSaipCode())
                .processName("revoke_licensing_request_process") // name in camunda
                .applicationIdColumn(entity.getApplicationInfo().getId().toString())
                .requestTypeCode("REVOKE_LICENSE_REQUEST") // RevokeLicenceRequest enum code
                .supportServiceTypeCode(entity.getApplicantType().name())
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .identifier(id.toString())
                .requestNumber(entity.getRequestNumber())
                .variables(variable)
                .build();
        updateStartProcessDtoWithOpposedInfo(entity, startProcessDto);
        startSupportServiceProcess(entity, startProcessDto);
    }

    private void updateStartProcessDtoWithOpposedInfo(RevokeLicenceRequest revokeLicenceRequest, StartProcessDto startProcessDto) {
        CustomerSampleInfoDto OpposedCustomerInfo = getOpposedCustomerInfo(revokeLicenceRequest);
        startProcessDto.addVariable("OPPOSITION_CUSTOMER_CODE", OpposedCustomerInfo.getCode());
        startProcessDto.addVariable("OPPOSITION_USER_NAME", OpposedCustomerInfo.getIdentifier());
    }

    private CustomerSampleInfoDto getOpposedCustomerInfo(RevokeLicenceRequest revokeLicenceRequest) {
        CustomerSampleInfoDto OpposedCustomerCode = revokeLicenceRequest.getApplicantType().equals(ApplicantType.LICENSED_CUSTOMER) ?
                getRevokeLicenceMainApplicationApplicantCustomerInfo(revokeLicenceRequest.getLicenceRequest().getApplicationInfo())
                : getRevokeLicenceRequestCustomerInfo(revokeLicenceRequest.getLicenceRequest().getCustomerId());
        return OpposedCustomerCode;
    }

    private CustomerSampleInfoDto getRevokeLicenceMainApplicationApplicantCustomerInfo(ApplicationInfo applicationInfo){
        String mainApplicationApplicantCustomerCode = Utilities.extractCustomerCode(applicationInfo);
        return customerServiceCaller.getCustomerInfoByCustomerCode(mainApplicationApplicantCustomerCode);
    }
    private CustomerSampleInfoDto getRevokeLicenceRequestCustomerInfo(Long revokeLicenceRequestCustomerId){
       return customerServiceCaller.getAnyCustomerDetails(revokeLicenceRequestCustomerId);
    }

    @Override
    public List<CustomerSampleInfoDto> getLicenseRequestAllInvolvedUsersInfo(Long id) {
        RevokeLicenceRequest revokeLicenceRequest =  this.findById(id);
        List<String> codes = licenceRequestUtil.getLicenseOwnerAndAgentCodes(revokeLicenceRequest.getLicenceRequest());
        String agentCode = licenceRequestUtil.getAgentCodeByAgencyRequestNumber(revokeLicenceRequest.getAgencyRequestNumber(), revokeLicenceRequest.getApplicantType());
        if (agentCode != null)
            codes.add(agentCode);
        return licenceRequestUtil.getLicenseRequestAllInvolvedUsersInfo(revokeLicenceRequest.getLicenceRequest(), codes);
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public RevokeLicenceRequestApplicationSummaryDto getApplicationSummaryByRevokeLicenseRequestId(Long id) {
        RevokeLicenceRequestDto revokeLicenceRequestDto = findByServiceId(id);
        setCanCurrentCustomerOppose(revokeLicenceRequestDto);
        ApplicationInfoSummaryDto applicationInfoSummaryDto = (ApplicationInfoSummaryDto) applicationInfoService.getApplicationSummary(revokeLicenceRequestDto.getApplicationId(), null);
        RevokeLicenceRequestApplicationSummaryDto revokeLicenceRequestApplicationSummaryDto= new RevokeLicenceRequestApplicationSummaryDto(applicationInfoSummaryDto, revokeLicenceRequestDto);
        return revokeLicenceRequestApplicationSummaryDto;
    }

    private void setCanCurrentCustomerOppose(RevokeLicenceRequestDto revokeLicenceRequestDto) {
        String currentCustomerCode = Utilities.getCustomerCodeFromHeaders();
        String applicantCustomerCode = revokeLicenceRequestDto.getCreatedByCustomerCode();
        String mainOwnerCustomerCode = applicationCustomerService.findMainOwnerNotificationData(revokeLicenceRequestDto.getApplicationId()).getCustomerCode();
        List<String> agentsCustomerCodes = trademarkAgencyRequestService.getAgentCodesByApplicationIdAndSupportServiceCode(SupportServiceType.REVOKE_LICENSE_REQUEST, revokeLicenceRequestDto.getApplicationId());
        List<String> licencedCustomersCodes = Optional.ofNullable(licenceRequestService.getLicensedCustomersDetails(revokeLicenceRequestDto.getApplicationId()))
                .orElse(Collections.emptyList())
                .stream().map(CustomerSampleInfoDto::getCode).toList();

        // set default canCurrentCustomerOppose to true
        revokeLicenceRequestDto.setCanCurrentCustomerOppose(true);

        // Case 1: If the revoke is created by an agent
        if (agentsCustomerCodes.contains(applicantCustomerCode)) {
            // If the current customer is the mark owner or mark agent, set canCurrentCustomerOppose to false
            if (currentCustomerCode.equals(mainOwnerCustomerCode) || agentsCustomerCodes.contains(currentCustomerCode)) {
                revokeLicenceRequestDto.setCanCurrentCustomerOppose(false);
            }
        }
        // Case 2: If the revoke is created by the mark owner
        if (applicantCustomerCode.equals(mainOwnerCustomerCode)) {
            // If the current customer is the mark owner or mark agent, set canCurrentCustomerOppose to false
            if (currentCustomerCode.equals(mainOwnerCustomerCode) || agentsCustomerCodes.contains(currentCustomerCode)) {
                revokeLicenceRequestDto.setCanCurrentCustomerOppose(false);
            }
        }
        // Case 3: If the revoke is created by a licensed customer
        if (licencedCustomersCodes.contains(applicantCustomerCode)) {
            // If the current customer is the same licensed customer, set canCurrentCustomerOppose to false
            if (licencedCustomersCodes.contains(currentCustomerCode)) {
                revokeLicenceRequestDto.setCanCurrentCustomerOppose(false);
            }
        }
    }

    private void updateRevokeLicenceRequestDtoWithApplicantName(RevokeLicenceRequestDto revokeLicenceRequestDto, String applicantCode) {
        CustomerSampleInfoDto customerSampleInfoDto =  applicantCode == null ? null : customerServiceCaller.getCustomerInfoByCustomerCode(applicantCode);
        if(customerSampleInfoDto != null) {
            revokeLicenceRequestDto.setApplicantNameAr(customerSampleInfoDto.getNameAr());
            revokeLicenceRequestDto.setApplicantNameEn(customerSampleInfoDto.getNameEn());
        }
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public RevokeLicenceRequestDto findByServiceId(Long id){
        RevokeLicenceRequest revokeLicenceRequest =  this.findById(id);
        RevokeLicenceRequestDto revokeLicenceRequestDto = revokeLicenceRequestMapper.map(revokeLicenceRequest);
        updateRevokeLicenceRequestDtoWithApplicantName(revokeLicenceRequestDto, revokeLicenceRequest.getCreatedByCustomerCode());
        revokeLicenceRequestDto.setHasOpposition(oppositionRevokeLicenceRequestService.revokeLicenceRequestHasUnderProcedureOpposition(id));
        revokeLicenceRequestDto.setOppositionRevokeLicenceRequestDto(oppositionRevokeLicenceRequestService.getUnderProcedureOppositionRevokeLicenceRequestByRevokeLicenseId(id));
        return revokeLicenceRequestDto;
    }

    @Override
    public String getLicensedCustomerCodeByRevokeLicenseId(Long id) {
        Long licensedCustomerId = revokeLicenceRequestRepository.findLicenceCustomerId(id);
        return customerServiceCaller.getCustomerCodeByCustomerId(licensedCustomerId);
    }

    @Override
    public Long getLicenseRequestIdByRevokeLicenseId(Long id) {
        return revokeLicenceRequestRepository.findLicenceIdByRevokeLicenseId(id);
    }

}
