package gov.saip.applicationservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AssistiveSupportServiceSpecialistDecision {
    ACCEPTED("YES", SupportServiceRequestStatusEnum.APPROVED),
    REJECTED("NO", SupportServiceRequestStatusEnum.REJECTED),
    SEND_BACK("SEND_BACK", SupportServiceRequestStatusEnum.REQUEST_CORRECTION),
    HAVE_SIMILAR("HAVE_SIMILAR",SupportServiceRequestStatusEnum.COMPLETED),
    NOT_SIMILAR("NOT_SIMILAR",SupportServiceRequestStatusEnum.COMPLETED),
    SEND_BACK_DEPARTMENT("SEND_BACK_DEPARTMENT", null),
    FORMAL_APPROVAL("FORMAL_APPROVAL", null),
    OBJECTIVE_APPROVAL("OBJECTIVE_APPROVAL", null),
    APPROVE_MODIFICATION("APPROVE_MODIFICATION", null),
    EXAMINER_APPROVAL("EXAMINER_APPROVAL", null),
    SEND_BACK_TO_CHECKER("SEND_BACK_TO_CHECKER", null),
    EXAMINER_REJECTION("EXAMINER_REJECTION", null);


    private String value;
    private SupportServiceRequestStatusEnum status;

    public static AssistiveSupportServiceSpecialistDecision getDecisionByValue(String value) {
        return Arrays.stream(AssistiveSupportServiceSpecialistDecision.values()).filter(val -> val.value.equals(value)).findFirst().get();
    }
}
