package gov.saip.applicationservice.common.enums.statistic;

import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.THE_TRADEMARK_IS_REGISTERED;

@AllArgsConstructor
@Getter
public enum StatisticTypes {
    // all these enum need the actual status list, for now it's dummy status
    NEW(List.of(ApplicationStatusEnum.DRAFT, ApplicationStatusEnum.NEW, ApplicationStatusEnum.WAITING_FOR_APPLICATION_FEE_PAYMENT),"طلبات الايداع", "New Requests"),

    COMPLETED(List.of(ApplicationStatusEnum.OBJECTOR, ApplicationStatusEnum.WAIVED, ApplicationStatusEnum.PUBLISHED_ELECTRONICALLY, ApplicationStatusEnum.ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION, ApplicationStatusEnum.ACCEPTANCE, THE_TRADEMARK_IS_REGISTERED), "طلبات منتهيه", "Completed"),

    IN_PROGRESS(List.of(ApplicationStatusEnum.UNDER_OBJECTIVE_PROCESS, ApplicationStatusEnum.RETURNED_TO_THE_CLASSIFICATION_OFFICER), "طلبات قيد التنفيذ", "In Progress"),

    IN_REVIEW(List.of(ApplicationStatusEnum.UNDER_FORMAL_PROCESS, ApplicationStatusEnum.UNDER_OBJECTIVE_PROCESS), "طلبات قيدالمراجعه", "In Review"),

    WAITING_OWNER(List.of(ApplicationStatusEnum.RETURNED_TO_THE_APPLICANT, ApplicationStatusEnum.PUBLICATION_FEES_ARE_PENDING, ApplicationStatusEnum.PAYMENT_OF_MODIFICATION_FEES_IS_PENDING, ApplicationStatusEnum.INVITATION_FOR_OBJECTIVE_CORRECTION),"طلبات بإنتظار مالك العلامه", "Waiting Owner"),

    REFUSAL(List.of(ApplicationStatusEnum.CROSSED_OUT_MARK),"مرفوضه", "Refused"),

    REGISTERED(List.of(THE_TRADEMARK_IS_REGISTERED),"مسجله", "Registered"),

    EXPIRED(List.of(ApplicationStatusEnum.DRAFT),"منتهيه", "Expired"),

    FILED(List.of(ApplicationStatusEnum.DRAFT), "مودعه", "Filed"),




    // no status enum

    APPEAL(null, "طلبات التظلم", "Appeal Requests"),
    TOTAL_APPEAL(null, "التظلم الاجمالى", "Total Appeal"),
    COMPLETED_APPEAL(null, "التظلم المكتمل", "Completed Appeal");


    private List<ApplicationStatusEnum> statusList;
    private String nameAr;
    private String nameEn;

}
