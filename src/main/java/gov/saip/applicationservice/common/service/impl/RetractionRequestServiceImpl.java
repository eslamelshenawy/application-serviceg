package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.ProcessInstanceDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.RetractionRequest;
import gov.saip.applicationservice.common.repository.RetractionRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.util.SupportServiceActivityLogHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.WAIVED;
import static gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum.COMPLETED;


@Service
@RequiredArgsConstructor
@Transactional
public class RetractionRequestServiceImpl extends SupportServiceRequestServiceImpl<RetractionRequest> implements RetractionRequestService {

    private final RetractionRequestRepository retractionRequestRepository;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final SupportServiceActivityLogHelper supportServiceActivityLogHelper;
    public static final String RETRACTION_LOG_TASK_NAME_AR = "انسحاب من الطلب";
    public static final String RETRACTION_LOG_TASK_NAME_EN = "ُApplication Retraction";
    private final CustomerServiceCaller customerServiceCaller;
    private final ApplicationInfoService applicationInfoService;
    private final LKSupportServiceRequestStatusService lkSupportServiceRequestStatusService;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return retractionRequestRepository;
    }

    @Override
    public RetractionRequest insert(RetractionRequest entity) {
        RetractionRequest retractionRequest = super.insert(SupportServiceType.RETRACTION, entity);
        processPaymentAndCallback(entity);
        return retractionRequest;
    }

    private void processPaymentAndCallback(RetractionRequest retractionRequest) {
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceCaller.getCustomerInfoFromRequest();
        ApplicationInfoBaseDto applicationInfoBaseDto = applicationInfoService.getAppBasicInfo(retractionRequest.getApplicationInfo().getId());
        Double cost = applicationInfoService.calculateServiceCost(customerSampleInfoDto, ApplicationPaymentMainRequestTypesEnum.WITHDRAW_APPLICATION,
                ApplicationCategoryEnum.valueOf(applicationInfoBaseDto.getApplicationCategory().getSaipCode()));

        if (cost == 0) {
            handleZeroCostInvoice(retractionRequest);
        }
    }

    private void handleZeroCostInvoice(RetractionRequest retractionRequest) {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setApplicationPaymentMainRequestTypesEnum(ApplicationPaymentMainRequestTypesEnum.WITHDRAW_APPLICATION);
        LKSupportServiceRequestStatus lkSupportServiceRequestStatus = lkSupportServiceRequestStatusService.getStatusByCode(COMPLETED.name());
        retractionRequest.setPaymentStatus(SupportServicePaymentStatus.PAID);
        retractionRequest.setRequestStatus(lkSupportServiceRequestStatus);
        paymentCallBackHandler(retractionRequest.getApplicationInfo().getId(), applicationNumberGenerationDto);
    }

    @Override
    public RetractionRequest update(RetractionRequest entity){
        RetractionRequest retractionRequest = findById(entity.getId());
        retractionRequest.setRetractionReasonDocument(entity.getRetractionReasonDocument() != null ? entity.getRetractionReasonDocument() : retractionRequest.getRetractionReasonDocument());
        retractionRequest.setLkSupportServiceType(entity.getLkSupportServiceType() != null ? entity.getLkSupportServiceType() : retractionRequest.getLkSupportServiceType());
        return super.update(entity);
    }

    @Override
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        ApplicationInfo applicationInfo = applicationInfoService.findById(id);
        stopRetractedApplicationProcess(applicationInfo);
        String taskId = supportServiceActivityLogHelper.addActivityLogForSupportService(id, RequestActivityLogEnum.RETRACTION, "DONE", RETRACTION_LOG_TASK_NAME_AR, RETRACTION_LOG_TASK_NAME_EN, applicationInfo.getId());
        applicationStatusChangeLogService.changeApplicationStatusAndLog(WAIVED.name(), null, applicationInfo.getId(), RequestActivityLogEnum.valueOf(SupportServiceType.RETRACTION.name()).name() ,taskId);

        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void stopRetractedApplicationProcess(ApplicationInfo applicationInfo) {
        ProcessInstanceDto dto = new ProcessInstanceDto();
        dto.setSuspended(true);
        bpmCallerFeignClient.suspendApplicationProcessByType(dto, applicationInfo.getId(), applicationInfo.getCategory().getSaipCode());

    }

    @Override
    public void paymentCallBackHandlerPriority(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return COMPLETED;
    }
}