package gov.saip.applicationservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static gov.saip.applicationservice.common.enums.ReportsType.*;

@AllArgsConstructor
@Getter
public enum PetitopnRecoveryTaskEnum {

    PENDING_FOR_REQUEST_1_PAT("لعدم الرد خلال المهلة النظامية على اشعار تقرير الفحص الشكلي",DroppedRequestReport_For_Timing_Checker.name())
    ,PENDING_FOR_REQUEST_2_PAT("لعدم الرد خلال المهلة النظامية على اشعار تقرير الفحص الشكلي",DroppedRequestReport_For_Timing_Checker.name()),
    PENDING_FOR_REQUEST_3_PAT("سبق أن أرسل إليكم إشعار بالمقابل المالي للنشر والنفقات اللازمة لتقرير الفحص الموضوعي ", DroppedRequestReportForExaminerBill.name())
    ,PENDING_FOR_REQUEST_7_PAT("عدم سداد رسوم النشر(أ) في الفترة النظامية", DroppedRequestReportPublicationA.name()),
    PENDING_FOR_REQUEST_9_PAT("عدم سداد رسوم النشر(أ) في الفترة النظامية", DroppedRequestReportPublicationA.name()),
    PENDING_FOR_REQUEST_4_PAT("لعدم الرد خلال المهلة النظامية على اشعار تقرير الفحص موضوعي",DroppedRequestReportSubstantive.name()),
    PENDING_FOR_REQUEST_5_PAT("لعدم الرد خلال المهلة النظامية على اشعار تقرير الفحص موضوعي",DroppedRequestReportSubstantive.name()),
    PENDING_FOR_REQUEST_6_PAT("عدم سداد رسوم المنح في الفترة النظامية",DroppedRequestReportPublicationB.name())
    ;


    private String reason;
    private String reportsType;

}
