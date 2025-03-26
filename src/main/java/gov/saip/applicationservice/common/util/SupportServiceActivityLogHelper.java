package gov.saip.applicationservice.common.util;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.RequestActivityLogsDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceHelperInfoDto;
import gov.saip.applicationservice.common.enums.RequestActivityLogEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.LKSupportServicesService;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class SupportServiceActivityLogHelper {
    private final BPMCallerFeignClient bPMCallerFeignClient;
    private final SupportServiceRequestRepository supportServiceRequestRepository;
    private final ApplicationInfoService applicationInfoService;
    private final LKSupportServicesService lKSupportServicesService;

    public String addActivityLogForSupportService(Long serviceId, RequestActivityLogEnum requestActivityLogEnum, SupportServiceType supportServiceType) {
        SupportServiceHelperInfoDto supportServiceHelperInfoDto = supportServiceRequestRepository.getCreatedByCustomerCodeAndApplicationIdById(serviceId);
        String applicationStatus = applicationInfoService.getApplicationStatus(supportServiceHelperInfoDto.getApplicationId());
        LKSupportServices lkSupportServices = lKSupportServicesService.findByCode(supportServiceType);
        return buildActivityLogAndInsert(requestActivityLogEnum, applicationStatus, supportServiceHelperInfoDto.getCreatedByCustomerCode(), supportServiceHelperInfoDto.getApplicationId(), lkSupportServices.getNameAr(), lkSupportServices.getNameEn());
    }


    public String addActivityLogForSupportService(Long serviceId, RequestActivityLogEnum requestActivityLogEnum, String status, String taskNameAr, String taskNameEn, Long appId) {
        SupportServiceHelperInfoDto supportServiceHelperInfoDto = supportServiceRequestRepository.getCreatedByCustomerCodeAndApplicationIdById(serviceId);
        String customerCode = Objects.nonNull(supportServiceHelperInfoDto) ? supportServiceHelperInfoDto.getCreatedByCustomerCode() : Utilities.getCustomerCodeFromHeaders();
        Long applicationId = Objects.nonNull(supportServiceHelperInfoDto) ? supportServiceHelperInfoDto.getApplicationId() : appId;
        return buildActivityLogAndInsert(requestActivityLogEnum, status, customerCode, applicationId, taskNameAr, taskNameEn);
    }

    public String buildActivityLogAndInsert(RequestActivityLogEnum requestActivityLogEnum, String applicationStatus, String createdByCustomerCode, Long applicationId, String taskNameAr, String taskNameEn) {
        RequestActivityLogsDto requestActivityLogsDto = RequestActivityLogsDto.builder()
                .taskDefinitionKey(requestActivityLogEnum)
                .taskNameAr(taskNameAr)
                .taskNameEn(taskNameEn)
                .statusCode(applicationStatus)
                .assignee(createdByCustomerCode)
                .applicationId(applicationId).build();
        return bPMCallerFeignClient.addActivityLog(requestActivityLogsDto).getPayload().toString();
    }
}
