package gov.saip.applicationservice.common.service.installment;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentDatesDto;
import gov.saip.applicationservice.common.dto.installment.InstallmentNotificationProjectionDto;
import gov.saip.applicationservice.common.dto.installment.InstallmentStatusBannerDto;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.annual_fees.AnnualFeesRequest;
import gov.saip.applicationservice.common.model.annual_fees.LkPostRequestReasons;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationInstallmentService extends BaseService<ApplicationInstallment, Long> {

//    void protectionRenewalFeesPaymentCallBack(Long applicationInfoId, ApplicationNumberGenerationDto applicationNumberGenerationDto);

    PaginationDto<List<InstallmentNotificationProjectionDto>> filterApplicationInstallments(InstallmentNotificationStatus notificationStatus, InstallmentStatus installmentStatus, String appNum, Integer page, Integer limit);

    void postponedInstallment(ApplicationInfo applicationInfo, LkPostRequestReasons postRequestReasons);

    void annualFeesPaymentCallBackHandler(AnnualFeesRequest annualFeesRequest, ApplicationNumberGenerationDto applicationNumberGenerationDto);

    ApplicationInstallment getUnpaidInstallmentForGivenApplicationId(Long appId);
    LocalDateTime getLastDueDate(Long appId, InstallmentStatus installmentStatus);


    InstallmentStatusBannerDto getInstallmentConfigAndStatus(Long applicationId);

    void insertFirstInstallmentForGivenApplications(ApplicationInfo applications);

    Long createAndSaveFirstAnnualFees(Long id);

    ApplicationInstallment installmentPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto);

    Integer getLastIndexByAppAndStatus(Long appId, InstallmentStatus installmentStatus);

    void isRenewalOpenedForGivenApplication(ApplicationInfo applicationInfo);

    ApplicationInstallmentDatesDto getLastInstallmentInfoByAppId(Long applicationId);
    String resumeHoledCallBack(Long applicationId);
    String updateInstallmentDates(ApplicationInstallmentDatesDto dto);
    void ignoreAutomaticPaymentCallback(boolean ignore);
    void ignoreAnnualMonths(boolean ignore);
    void setInstallmentSupportService(ApplicationSupportServicesType supportServicesType, InstallmentType installmentType);
    void removeAnnualFeeInstallmentsAfterApplicationRejection(Long applicationId);
    void cancelAnnualFeePostponement(Long applicationId);
}
