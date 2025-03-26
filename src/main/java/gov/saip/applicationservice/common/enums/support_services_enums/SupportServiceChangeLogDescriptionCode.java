package gov.saip.applicationservice.common.enums.support_services_enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SupportServiceChangeLogDescriptionCode {

    PETITION_ACCEPTANCE("Petition Request Acceptance", "قبول طلب الألتماس"),
    PETITION_REJECTION("Petition Request Rejection", "رفض طلب الألتماس"),
    PETITION_CONDITIONAL_REJECTION("Petition Request Conditional Rejection", "رفض مرتقب لطلب الألتماس"),
    PETITION_CONDITIONAL_REJECTION_TIME_OUT("Petition Request Conditional Rejection Time Out", "انتهاء المدة المسموحه للرد على الرفض المرتقب"),
    EXP_TIME_OUT("Expiration Time Out", "انتهاء المدة المسموحه للرد");
    
    private String descriptionEn;
    private String descriptionAr;
}
