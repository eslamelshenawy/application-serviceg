package gov.saip.applicationservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.*;

@AllArgsConstructor
@Getter
public enum ApplicationStatusGroupEnum {
    UNDER_PROCESS(List.of(
            UNDER_REVIEW_BY_AN_CHECKER_AUDITOR.name(),
            UNDER_STUDY_BY_THE_CLASSIFICATIONS_OFFICIAL.name(),
            UNDER_REVIEW_BY_AN_OBJECTIVE_AUDITOR.name(),
            RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL.name(),
            UNDER_FORMAL_PROCESS.name(),
            UNDER_OBJECTIVE_PROCESS.name(),
            RETURN_TO_THE_FORMAL_EXAMINER.name(),
            RESPOND_TO_THE_FORMAL_EXAMINATION_REPORT.name(),
            RETURNED_TO_THE_CLASSIFICATION_OFFICER.name(),
            UNDER_ACCELERATED_TRACK_STUDY.name()

    )),

    RETURNED_TO_APPLICANT(List.of(RETURNED_TO_THE_APPLICANT.name(),INVITATION_FOR_OBJECTIVE_CORRECTION.name()
    )),

    FORMAL_REJECT(List.of(APPEAL_FORMAL_REJECTION.name(),FORMAL_REJECTION.name()));



    private List<String> codes;


}



