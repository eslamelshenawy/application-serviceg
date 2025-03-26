package gov.saip.applicationservice.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppStatusChangeLogDescriptionCode {
    APPEAL_REQUEST_REQUESTED("Appeal Request Requested", "إيداع طلب تظلم"),
    APPEAL_REQUEST_PAID("Appeal Request Payment", "دفع فاتوره طلب التظلم"),
    APPEAL_REQUEST_REJECTED("Appeal Request Rejected", "رفض طلب التظلم"),
    APPEAL_REQUEST_ACCEPTED("Appeal Request Accepted", "قبول طلب التظلم"),
    APPEAL_REQUEST_TIMEOUT("Appeal Request Timeout", "انتهاء مهلة طلب التظلم"),

    OPPOSITION_REQUEST_REQUESTED("Opposition Request Requested", "إيداع طلب إعتراض"),
    OPPOSITION_REQUEST_REJECTED("Opposition Request Rejected", "رفض طلب الإعتراض"),
    OPPOSITION_REQUEST_ACCEPTED("Opposition Request Accepted", "قبول طلب الإعتراض"),
    OPPOSITION_REQUEST_TIME_OUT("Opposition Reply Time Out", "تجاوز مده الرد على الاعتراض"),

    REGISTRATION_INVOICE_PAID("Registration Invoice Paid", "دفع فاتورة التسجيل"),
    TIME_OUT_NOT_COMPLETED_FILING_REQUIREMENT("Time Out Not Completed Filing Requirement", "انتهاء الوقت لمتطلبات الإيداع غير المكتملة"),

    FAST_TRACK_REJECTED("Fast Track Rejected", "رفض المسار السريع"),
    FAST_TRACK_APPROVED_AND_IS_NATIONAL_SECURITY("Fast Track Approved and is National Security", "الموافقة على المسار السريع وهو أمن قومي"),
    FAST_TRACK_APPROVED("Fast Track Approved", "الموافقة على المسار السريع"),
    FAST_TRACK_TOOK_DECISION_AND_NOT_NATIONAL_SECURITY("Fast Track Took Decision and Not National Security", "اتخذ المسار السريع قرارًا وليس أمن قومي"),

    APPROVED_PETITION_RECOVERY_REQUEST("Approved Petition Recovery Request", "قبول طلب استعادة العريضة"),
    PAID_SUBSTANTIVE_EXAM_AND_HAVE_FAST_TRACK("Paid Substantive Exam and Have Fast Track", "دفع رسوم الفحص الموضوعي ولديه مسار سريع"),
    PAID_SUBSTANTIVE_EXAM_AND_NOT_HAVE_FAST_TRACK("Paid Substantive Exam and Not Have Fast Track", "دفع رسوم الفحص الموضوعي وليس لديه مسار سريع"),
    PUBLICATION_A_FEES_ARE_NOT_PAID("Publication A Fees Are Not Paid", "رسوم النشر أ لم تُدفع"),
    PAID_PUB_A_AND_NOT_FAST_TRACK("Paid Publication A and Not Fast Track", "دفع رسوم النشر أ وليس مسار سريع"),

    PETITION_RECOVERY_REQUEST_APPROVED("Petition Recovery Request Approved", "الموافقة على طلب استعادة العريضة"),
    PETITION_RECOVERY_REQUEST_REJECTED("Petition Recovery Request Rejected", "رفض طلب استعادة العريضة"),

    REVOKE_VOLUNTARY_REQUEST_ACCEPTED("Revoke Voluntary Request Accepted", "قبول طلب الإلغاء الطوعي"),
    REVOKE_BY_COURT_ORDER_REQUEST_ACCEPTED("Revoke by Court Order Request Accepted", "قبول طلب الإلغاء بأمر من المحكمة"),

    WAIVER_DOCUMENTS_NOT_PROVIDED("Waiver Documents Not Provided", "لم يتم تقديم وثائق التنازل"),
    REGISTRATION_COMPLETED("Registration Completed", "اكتمل التسجيل"),
    PATENT_GRANTED("Patent Granted", "تم منح براءة اختراع"),
    PAID_PUB_INVOICE_AND_AWAITING_VERIFICATION("Paid Publication Invoice and Awaiting Verification", "دفع فاتورة النشر وفي انتظار التحقق"),
//    PUBLISHED_ELECTRONICALLY("Published Electronically", "نشر إلكترونيًا"),
    PUBLISHED_ELECTRONICALLY_AND_NOT_CROSSED_OUT("Published Electronically and Not Crossed Out", "نشر إلكترونيًا ولم يُشطب"),
    GRANT_FEES_PAID("Grant Fees Paid", "دفع رسوم المنح"),
    APPLIED_INITIAL_MODIFICATION("Applied Initial Modification", "تطبيق التعديل الأولي"),
    EXAMINATION_INITIATED("Examination Initiated", "بدأ الفحص"),

    DATA_COMPLETION_TIMEOUT("Data Completion Timeout", "انتهاء مهلة استكمال البيانات"),
    APPLICATION_RECOVER_TIMEOUT("Application Recover Timeout", "انتهاء مهلة استعادة الطلب"),
    APPLICATION_UPDATE_TIMEOUT("Application Update Timeout", "انتهاء مهلة تحديث الطلب"),
    CORRECTION_TIMEOUT("Correction Timeout", "انتهاء مهلة التصحيح"),
    ACCEPT_WITH_CONDITION_TIMEOUT("Accept with Condition Timeout", "انتهاء مهلة القبول مع الشرط"),
    PUBLICATION_INVOICE_PAYMENT_TIMEOUT("Publication Invoice Payment Timeout", "انتهاء مهلة دفع فاتورة النشر"),
    GRANT_FEES_PAYMENT_TIMEOUT("Grant Fees Payment Timeout", "انتهاء مهلة دفع رسوم المنح"),
    CREATE_APPLICATION_PUBLICATION("Application is published","تم نشر الطلب"),
    APPLICATION_CREATED("Application is created", "بدء الطلب"),
    APPLICATION_FILLED("Application is filled", "تم ملء الطلب"),
    BILL_CREATED("Bill is created", "تم إصدار الفاتورة");

    private String descriptionEn;
    private String descriptionAr;
}
