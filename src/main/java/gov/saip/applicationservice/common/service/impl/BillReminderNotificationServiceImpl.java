package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationNotificationData;
import gov.saip.applicationservice.common.dto.BillReminderNotificationDetailsDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.enums.GeneralCategories;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSearch;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.CertificateRequest;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.BillReminderNotificationService;
import gov.saip.applicationservice.common.service.CertificateRequestService;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static gov.saip.applicationservice.common.enums.GeneralCategories.APPLICATION_CERTIFICATE;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillReminderNotificationServiceImpl implements BillReminderNotificationService {
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationCustomerServiceImpl applicationCustomerService;
    private final ApplicationInstallmentService applicationInstallmentService;
    private final CertificateRequestService certificateRequestService;




    @Override
    public BillReminderNotificationDetailsDto getBillReminderDetails(Long id, String type) {
        switch (GeneralCategories.valueOf(type)) {
            case SUPPORT_SERVICE -> {
                return supportServiceFlow(id);
            }
            case MAIN_APPLICATION -> {
                return mainApplicationFlow(id);
            }
            case APPLICATION_INSTALLMENT -> {
                return applicationInstallmentFlow(id);
            }
            case APPLICATION_CERTIFICATE -> {
                return applicationCertificateFlow(id);
            }
        }
        return null;
    }

    private BillReminderNotificationDetailsDto applicationCertificateFlow(Long id) {
        CertificateRequest certificateRequest = certificateRequestService.findById(id);
        Long applicationId = certificateRequest.getApplicationInfo().getId();
        ApplicationInfo applicationInfo = applicationInfoService.findById(applicationId);
        ApplicationNotificationData mainOwnerNotificationData = applicationCustomerService.findMainOwnerNotificationData(applicationId);

        return new BillReminderNotificationDetailsDto(id,
                mainOwnerNotificationData.getEmail(),
                mainOwnerNotificationData.getMobileNumber(),
                mainOwnerNotificationData.getCustomerCode(),
                applicationInfo.getApplicationNumber() == null ? applicationInfo.getApplicationRequestNumber() : applicationInfo.getApplicationNumber(),
                String.valueOf(applicationInfo.getProcessRequestId()),
                APPLICATION_CERTIFICATE.name(),
                applicationInfo.getCategory().getSaipCode(),
                applicationInfo.getCategory().getApplicationCategoryDescEn(),
                applicationInfo.getCategory().getApplicationCategoryDescAr(),
                applicationInfo.getTitleAr(), applicationInfo.getTitleEn());
    }

    private BillReminderNotificationDetailsDto applicationInstallmentFlow(Long id) {
        ApplicationInstallment applicationInstallment = applicationInstallmentService.findById(id);
        Long applicationId = applicationInstallment.getApplication().getId();
        ApplicationInfo applicationInfo = applicationInfoService.findById(applicationId);
        ApplicationNotificationData mainOwnerNotificationData = applicationCustomerService.findMainOwnerNotificationData(applicationId);

        return new BillReminderNotificationDetailsDto(id,
                mainOwnerNotificationData.getEmail(),
                mainOwnerNotificationData.getMobileNumber(),
                mainOwnerNotificationData.getCustomerCode(),
                applicationInfo.getApplicationNumber() == null ? applicationInfo.getApplicationRequestNumber() : applicationInfo.getApplicationNumber(),
                String.valueOf(applicationInfo.getProcessRequestId()),
                null,
                applicationInfo.getCategory().getSaipCode(),
                applicationInfo.getCategory().getApplicationCategoryDescEn(),
                applicationInfo.getCategory().getApplicationCategoryDescAr(),
                applicationInfo.getTitleAr(), applicationInfo.getTitleEn());
    }

    private BillReminderNotificationDetailsDto mainApplicationFlow(Long id) {
        ApplicationInfo mainApplication = applicationInfoService.findById(id);
        CustomerSampleInfoDto applicantInfo = customerServiceFeignClient.getAnyCustomerById(mainApplication.getCreatedByCustomerId()).getPayload();

        return new BillReminderNotificationDetailsDto(id,
                applicantInfo.getEmail(),
                applicantInfo.getMobile(),
                applicantInfo.getCode(),
                mainApplication.getApplicationNumber() == null ? mainApplication.getApplicationRequestNumber() : mainApplication.getApplicationNumber(),
                String.valueOf(mainApplication.getProcessRequestId()),
                null,
                mainApplication.getCategory().getSaipCode(),
                mainApplication.getCategory().getApplicationCategoryDescEn(),
                mainApplication.getCategory().getApplicationCategoryDescAr(),
                mainApplication.getTitleAr(), mainApplication.getTitleEn());
    }

    private BillReminderNotificationDetailsDto supportServiceFlow(Long id) {
        ApplicationSupportServicesType supportService = applicationSupportServicesTypeService.findById(id);
        CustomerSampleInfoDto supportServiceApplicant = customerServiceFeignClient.getAnyCustomerByCustomerCode(supportService.getCreatedByCustomerCode()).getPayload();

//        String title = getTrademarkApplicationSearchName(supportService);
        return new BillReminderNotificationDetailsDto(id,
                supportServiceApplicant.getEmail(),
                supportServiceApplicant.getMobile(),
                supportServiceApplicant.getCode(),
                getRequestNumber(supportService),
                String.valueOf(supportService.getProcessRequestId()),
                supportService.getLkSupportServices().getCode().name(),
                supportService.getApplicationInfo() != null ? supportService.getApplicationInfo().getCategory().getSaipCode(): getTrademarkApplicationSearchCategory(supportService),
                supportService.getApplicationInfo() != null ? supportService.getApplicationInfo().getCategory().getApplicationCategoryDescEn() : null,
                supportService.getApplicationInfo() != null ? supportService.getApplicationInfo().getCategory().getApplicationCategoryDescAr(): null,
                supportService.getApplicationInfo() != null ? supportService.getApplicationInfo().getTitleAr(): null,
                supportService.getApplicationInfo() != null ? supportService.getApplicationInfo().getTitleEn() : null);
    }

        private String getTrademarkApplicationSearchCategory(ApplicationSupportServicesType supportServicesType) {
            if (supportServicesType != null && supportServicesType.getLkSupportServices().getCode().equals(SupportServiceType.TRADEMARK_APPLICATION_SEARCH)){
                return "TRADEMARK";
            }else return null;
        }


    private String getRequestNumber(ApplicationSupportServicesType supportServicesType) {
        if (supportServicesType != null && supportServicesType.getLkSupportServices().getCode().equals(SupportServiceType.LICENSING_REGISTRATION)){
            return supportServicesType.getRequestNumber();
        }else if (supportServicesType.getApplicationInfo() != null){
            return supportServicesType.getApplicationInfo().getApplicationNumber();
        }else return null;
    }

//    private String getTrademarkApplicationSearchName(ApplicationSupportServicesType supportServicesType){
//        if (supportServicesType != null && supportServicesType.getLkSupportServices().getCode().equals(SupportServiceType.TRADEMARK_APPLICATION_SEARCH)){
//            ApplicationSearch applicationSearch = (ApplicationSearch) supportServicesType;
//            return applicationSearch.getTitle();
//        }else return null;
//    }
}