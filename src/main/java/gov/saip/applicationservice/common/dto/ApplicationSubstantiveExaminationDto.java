package gov.saip.applicationservice.common.dto;


import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class ApplicationSubstantiveExaminationDto {
    private String problem;
    private String problemSolution;
    private Boolean nameApproved = Boolean.FALSE;
    private String nameNotes;
    private Boolean summeryApproved = Boolean.FALSE;
    private String summeryNotes;
    private Boolean fullSummeryApproved = Boolean.FALSE;
    private String fullSummeryNotes;
    private Boolean protectionElementsApproved = Boolean.FALSE;
    private String protectionElementsNotes;
    private Boolean gedaApproved = Boolean.FALSE;
    private String gedaNotes;
    private Boolean illustrationsApproved = Boolean.FALSE;
    private String illustrationsNotes;
    private Boolean innovativeStepApproved = Boolean.FALSE;
    private String innovativeStepNotes;
    private Boolean industrialApplicableApproved = Boolean.FALSE;
    private String industrialApplicableNotes;
    private List<ApplicationNotesReqDto> applicationNotes;
}
