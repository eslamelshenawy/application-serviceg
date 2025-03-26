package gov.saip.applicationservice.modules.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicantsRequestDto;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.StartProcessResponseDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BaseApplicationInfoService extends BaseService<ApplicationInfo, Long> {

    Map<ApplicationCategoryEnum,  BaseApplicationInfoService> getServicesMap();
    default void registerService(ApplicationCategoryEnum code, BaseApplicationInfoService service) {
        getServicesMap().put(code, service);
    }

    default BaseApplicationInfoService getServiceByEnumCode(ApplicationCategoryEnum code) {
        return getServicesMap().get(code);
    }

    default BaseApplicationInfoService getServiceByStringCode(String code) {
        return getServicesMap().get(ApplicationCategoryEnum.valueOf(code));
    }

    void init();
    void paymentCallBackHandler(ApplicationNumberGenerationDto applicationNumberGenerationDto, ApplicationInfo applicationInfo, ApplicationInfo partialApplicationInfo);
    ApplicationCategoryEnum getApplicationCategoryEnum();
    Long saveApplication(ApplicantsRequestDto applicantsRequestDto);
    void updateApplicationWithProcessRequestId(StartProcessResponseDto startProcessResponseDto, Long id);

    StartProcessResponseDto startProcessConfig(ApplicationInfo applicationInfo);

    void generateApplicationNumberAndUpdateApplicationInfoAfterPaymentCallback(ApplicationNumberGenerationDto applicationNumberGenerationDto, ApplicationInfo applicationInfo, ApplicationInfo partialApplicationInfo);

    String getApplicationNumberWithUniqueSequence(LocalDateTime paymentDate, String serviceCode, ApplicationInfo applicationInfo);

    void updateApplicationStatusByIdAndStatusCode(Long applicationId, String statusCode, String categoryCode);

    void updateApplicationsStatusByIdsAndStatusCode(List<Long> applicationIds, String statusCode, String categoryCode);

    void setApplicantInfo(Long applicationId, ApplicationInfo applicationInfo);
}
