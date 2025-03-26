package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.ExtensionRequest;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ExtensionRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.ExtensionRequestService;
import gov.saip.applicationservice.common.util.SupportServiceActivityLogHelper;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.ApplicationConstants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum.COMPLETED;
import static gov.saip.applicationservice.util.Constants.ErrorKeys.EXTENSION_APPLIED;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExtensionRequestServiceImpl extends SupportServiceRequestServiceImpl<ExtensionRequest> implements ExtensionRequestService {

    private final ExtensionRequestRepository extensionRequestRepository;
    private final ApplicationInfoService applicationService;
    private final BPMCallerServiceImpl bpmCallerService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationConstants applicationConstants;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final SupportServiceActivityLogHelper supportServiceActivityLogHelper;
    private static final String EXTENSION_LOG_TASK_NAME_AR = "طلب تمديد مهلة";
    private static final String EXTENSION_LOG_TASK_NAME_EN = "Application Extension Request";
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return extensionRequestRepository;
    }

    @Override
    public ExtensionRequest insert(ExtensionRequest entity) {
        ApplicationInfoBaseDto appBasicInfo = getAppBasicInfo(entity);
        RequestTasksDto requestTasksDto = getCurrentTask(appBasicInfo);

        if (isExistingRequestInSamePhase(appBasicInfo, requestTasksDto, entity)) {
            throw new BusinessException(EXTENSION_APPLIED, HttpStatus.BAD_REQUEST, null);
        }

        return createNewExtensionRequest(entity, requestTasksDto);
    }

    private ApplicationInfoBaseDto getAppBasicInfo(ExtensionRequest entity) {
        return applicationService.getAppBasicInfo(entity.getApplicationInfo().getId());
    }

    private RequestTasksDto getCurrentTask(ApplicationInfoBaseDto appBasicInfo) {
        return bpmCallerFeignClient.getTaskByRowId(
                appBasicInfo.getApplicationId(),
                appBasicInfo.getCategoryCode()
        );
    }

    private boolean isExistingRequestInSamePhase(ApplicationInfoBaseDto appBasicInfo, RequestTasksDto requestTasksDto, ExtensionRequest extensionRequest) {
        List<ApplicationSupportServicesType> servicesTypes = applicationSupportServicesTypeService.getAllByApplicationId(
                appBasicInfo.getApplicationId(), SupportServiceType.EXTENSION);

        for (ApplicationSupportServicesType serviceType : servicesTypes) {
            if (isExtensionServiceWithCompletedStatus(serviceType)) {
                if (isMatchingExistingRequest(serviceType, requestTasksDto, extensionRequest)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isExtensionServiceWithCompletedStatus(ApplicationSupportServicesType serviceType) {
        return SupportServiceType.EXTENSION.equals(serviceType.getLkSupportServices().getCode())
                && COMPLETED.name().equals(serviceType.getRequestStatus().getCode());
    }

    private boolean isMatchingExistingRequest(ApplicationSupportServicesType serviceType, RequestTasksDto requestTasksDto, ExtensionRequest extensionRequest) {
        Long serviceId = serviceType.getId();
        if (serviceId == null) {
            return false;
        }

        ExtensionRequest existingRequest = findById(serviceId);
        return existingRequest != null
                && existingRequest.getExtensionPhase() != null
                && isSupportedServiceTypeIdProvided(extensionRequest, existingRequest)
                && isSameTaskPhase(existingRequest, requestTasksDto);
    }

    private boolean isSameTaskPhase(ExtensionRequest existingRequest, RequestTasksDto requestTasksDto) {
        return existingRequest.getExtensionPhase().equalsIgnoreCase(requestTasksDto.getTaskDefinitionKey());
    }

    private boolean isSupportedServiceTypeIdProvided(ExtensionRequest extensionRequest, ExtensionRequest existingRequest) {
        if (Objects.isNull(extensionRequest.getLkSupportServiceType()) || Objects.isNull(existingRequest.getLkSupportServiceType())) {
            return true;
        }

        return existingRequest.getLkSupportServiceType().getId()
                .equals(extensionRequest.getLkSupportServiceType().getId());
    }

    private ExtensionRequest createNewExtensionRequest(ExtensionRequest entity, RequestTasksDto requestTasksDto) {
        entity.setExtensionPhase(requestTasksDto.getTaskDefinitionKey());
        return super.insert(SupportServiceType.EXTENSION, entity);
    }


    @Override
    public ExtensionRequest update(ExtensionRequest entity){
        ExtensionRequest extensionRequest = findById(entity.getId());
//        entity.setApplicationSupportServicesType(extensionRequest.getApplicationSupportServicesType());
        return super.update(extensionRequest);
    }
    private RequestTypeEnum getRequestTypeEnum(String code){

        if(code.equals("PATENT"))
            return RequestTypeEnum.PATENT;
        if(code.equals("TRADEMARK"))
            return RequestTypeEnum.TRADEMARK;
        if(code.equals("INDUSTRIAL_DESIGN"))
            return RequestTypeEnum.INDUSTRIAL_DESIGN;
        return null;
    }
    private LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    @Override
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        log.info(" line 102  extend test   {} ------ --- {}   ",id , applicationNumberGenerationDto);
        extendProcessInstanceDuration(id);
        log.info(" line 103  extend test   {} ------ --- {}   ",id , applicationNumberGenerationDto);
        supportServiceActivityLogHelper.addActivityLogForSupportService(id, RequestActivityLogEnum.EXTENSION, "DONE", EXTENSION_LOG_TASK_NAME_AR, EXTENSION_LOG_TASK_NAME_EN, null );
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return COMPLETED;
    }

    private void extendProcessInstanceDuration(Long id) {
        Long applicationId = super.findById(id).getApplicationInfo().getId();

        try {
            String processRequestTypeCode = ApplicationCategoryEnum.valueOf(applicationInfoRepository.findCategoryByApplicationId(applicationId)).getProcessTypeCode();

            RequestTasksDto taskByRowId = bpmCallerService.getTaskByRowId(applicationId,processRequestTypeCode);
            log.info(" line 120  fn extendProcessInstanceDuration {} ", id, taskByRowId.getRowId());
            String processInstanceId = taskByRowId.getProcessInstanceId();
            String dateString = taskByRowId.getDue();
            log.info(" task due date before changes  {}", dateString);

            if (Objects.isNull(dateString)) {
                log.info("due date is null for application with ID - " + applicationId);
                return;
            }
            OffsetDateTime newDueDate = Utilities.convertStringIntoOffsetDateTime(dateString, applicationConstants.camundaDateTimeFormat);
            newDueDate = newDueDate.plusDays(60);
            taskByRowId.setDue(Utilities.convertOffsetDateTimeIntoString(newDueDate, applicationConstants.camundaDateTimeFormat));
            log.info("due date extend to  - " + taskByRowId.getDue());
            bpmCallerService.extendDueDate(taskByRowId);
            String taskTimerId = null;
            OffsetDateTime taskTimerDueDate = null;
            List<JobDto> timers = bpmCallerFeignClient.getAllJobs(null,null);
            for (JobDto timer : timers) {
                if (Objects.nonNull(timer.getProcessInstanceId())) {
                    if (timer.getProcessInstanceId().equals(processInstanceId)) {
                        taskTimerId = timer.getId();
                        taskTimerDueDate = timer.getDueDate();
                    }
                }
            }
            if (Objects.isNull(taskTimerDueDate) || Objects.isNull(taskTimerId)) {
                log.info("there is no timer event on process" + processInstanceId);
                return;
            }
            LocalDateTime localDateTime = taskTimerDueDate.plusDays(60).toLocalDateTime();
            ZoneOffset zoneOffset = ZoneOffset.ofHoursMinutes(2, 0);
            OffsetDateTime outputOffsetDateTime = localDateTime.atOffset(zoneOffset);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String outputDateString = outputOffsetDateTime.format(formatter);
            DueDateDto dueDateDto = new DueDateDto();
            dueDateDto.setDuedate(outputDateString);
            dueDateDto.setCascade(false);
            bpmCallerFeignClient.setJobDueDate(taskTimerId, dueDateDto);


        } catch (Exception e) {
            log.error("Exception occurred While  extend application with id ==> {}  - " + applicationId);
        }

    }



}