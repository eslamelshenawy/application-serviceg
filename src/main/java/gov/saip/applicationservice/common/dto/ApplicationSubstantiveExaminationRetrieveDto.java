package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.enums.SubClassificationType;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApplicationSubstantiveExaminationRetrieveDto {
    private long id;

    private String email;
    private String applicationNumber;


    private String mobileCode;


    private String mobileNumber;


    private String titleEn;


    private String titleAr;


    private Boolean partialApplication;


    private String partialApplicationNumber;


    private Boolean nationalSecurity;


    private String ipcNumber;

    private Boolean substantiveExamination;

    private Boolean accelerated;
    private String address;
    private ApplicationStatusDto applicationStatus;
    private LKApplicationCategoryDto category;
    private SubClassificationType subClassificationType;
    private LkClassificationVersionDto version;
    private List<ClassificationDto> classifications;
    private List<ClassificationDto> niceClassifications;
    private boolean byHimself;
    private LocalDateTime filingDate;

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

    private List<ApplicantsDto> applicants ;

}
