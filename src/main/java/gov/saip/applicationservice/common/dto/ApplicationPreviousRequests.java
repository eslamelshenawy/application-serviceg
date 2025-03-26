package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.CustomerApplicationAccessLevel;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ApplicationPreviousRequests {

    private Long id;
    private Long applicationId;
    private String requestNumber;
    private String titleAr;
    private String titleEn;
    private LocalDateTime createdDate;
    private LkApplicationCategory applicationCategory;
    private LKSupportServices supportServices;
    private SupportServicePaymentStatus supportServicePaymentStatus;
    private LKSupportServiceRequestStatus requestStatus;
    private CustomerApplicationAccessLevel accessLevel;
    private Long licenseRequestId;
    private Long revokeLicenseId;
    private Long oppositionId;
    public ApplicationPreviousRequests(Long id) {
        this.id = id;
    }

    public ApplicationPreviousRequests(Long id, Long applicationId, String requestNumber, String titleAr, String titleEn,
                                       LocalDateTime createdDate, LkApplicationCategory applicationCategory,
                                       LKSupportServices supportServices, SupportServicePaymentStatus supportServicePaymentStatus,
                                       LKSupportServiceRequestStatus requestStatus) {
        this.id = id;
        this.applicationId = applicationId;
        this.requestNumber = requestNumber;
        this.titleAr = titleAr;
        this.titleEn = titleEn;
        this.createdDate = createdDate;
        this.applicationCategory = applicationCategory;
        this.supportServices = supportServices;
        this.supportServicePaymentStatus = supportServicePaymentStatus;
        this.requestStatus = requestStatus;
    }
    public ApplicationPreviousRequests(Long id, Long applicationId, String requestNumber, String titleAr, String titleEn,
                                       LocalDateTime createdDate, LkApplicationCategory applicationCategory,
                                       LKSupportServices supportServices, SupportServicePaymentStatus supportServicePaymentStatus,
                                       LKSupportServiceRequestStatus requestStatus, CustomerApplicationAccessLevel accessLevel) {
        this.id = id;
        this.applicationId = applicationId;
        this.requestNumber = requestNumber;
        this.titleAr = titleAr;
        this.titleEn = titleEn;
        this.createdDate = createdDate;
        this.applicationCategory = applicationCategory;
        this.supportServices = supportServices;
        this.supportServicePaymentStatus = supportServicePaymentStatus;
        this.requestStatus = requestStatus;
        this.accessLevel = accessLevel;
    }

    public ApplicationPreviousRequests(Long id, Long applicationId, String requestNumber, String titleAr, String titleEn,
                                       LocalDateTime createdDate, LkApplicationCategory applicationCategory,
                                       LKSupportServices supportServices, SupportServicePaymentStatus supportServicePaymentStatus,
                                       LKSupportServiceRequestStatus requestStatus, CustomerApplicationAccessLevel accessLevel, Long licenseRequestId) {
        this.id = id;
        this.applicationId = applicationId;
        this.requestNumber = requestNumber;
        this.titleAr = titleAr;
        this.titleEn = titleEn;
        this.createdDate = createdDate;
        this.applicationCategory = applicationCategory;
        this.supportServices = supportServices;
        this.supportServicePaymentStatus = supportServicePaymentStatus;
        this.requestStatus = requestStatus;
        this.accessLevel = accessLevel;
        this.licenseRequestId= licenseRequestId;
    }

    public ApplicationPreviousRequests(Long id, Long applicationId, String requestNumber, String titleAr, String titleEn,
                                       LocalDateTime createdDate, LkApplicationCategory applicationCategory,
                                       LKSupportServices supportServices, SupportServicePaymentStatus supportServicePaymentStatus,
                                       LKSupportServiceRequestStatus requestStatus,Long licenseRequestId, Long revokeLicenseId) {
        this.id = id;
        this.applicationId = applicationId;
        this.requestNumber = requestNumber;
        this.titleAr = titleAr;
        this.titleEn = titleEn;
        this.createdDate = createdDate;
        this.applicationCategory = applicationCategory;
        this.supportServices = supportServices;
        this.supportServicePaymentStatus = supportServicePaymentStatus;
        this.requestStatus = requestStatus;
        this.licenseRequestId= licenseRequestId;
        this.revokeLicenseId = revokeLicenseId;
    }


    public ApplicationPreviousRequests(Long id, Long applicationId, String requestNumber, String titleAr, String titleEn,
                                       LocalDateTime createdDate, LkApplicationCategory applicationCategory,
                                       LKSupportServices supportServices, SupportServicePaymentStatus supportServicePaymentStatus,
                                       LKSupportServiceRequestStatus requestStatus, CustomerApplicationAccessLevel accessLevel, Long licenseRequestId, Long revokeLicenseId) {
        this.id = id;
        this.applicationId = applicationId;
        this.requestNumber = requestNumber;
        this.titleAr = titleAr;
        this.titleEn = titleEn;
        this.createdDate = createdDate;
        this.applicationCategory = applicationCategory;
        this.supportServices = supportServices;
        this.supportServicePaymentStatus = supportServicePaymentStatus;
        this.requestStatus = requestStatus;
        this.accessLevel = accessLevel;
        this.licenseRequestId= licenseRequestId;
        this.revokeLicenseId = revokeLicenseId;
    }
}
