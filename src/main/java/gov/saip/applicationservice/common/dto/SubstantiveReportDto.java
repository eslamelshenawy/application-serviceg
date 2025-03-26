package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.lookup.LkExaminationOfficeDto;
import gov.saip.applicationservice.common.dto.lookup.LkNotesDto;
import gov.saip.applicationservice.common.enums.ExaminerReportType;
import gov.saip.applicationservice.common.enums.ReportDecisionEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class SubstantiveReportDto extends BaseDto<Long> {

    private Long applicationId;
    @Enumerated(EnumType.STRING)
    private ExaminerReportType type;
    private List<String> links;
    private List<DocumentLightDto> documents;
    private List<Long> documentIds;
    private String examinerOpinion;
    private String examinerRecommendation;
    private String examinerReport;
    private String decision;
    private List<LkNotesDto> sectionNotes;
	private LocalDateTime createdDate;
}
