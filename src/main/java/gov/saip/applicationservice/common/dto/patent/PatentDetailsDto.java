package gov.saip.applicationservice.common.dto.patent;

import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PatentDetailsDto implements Serializable {
    private LocalDateTime createdDate;
    private String createdByUser;
    private LocalDateTime modifiedDate;
    private String modifiedByUser;
    private Long id;
    private int isDeleted;
    private Long applicationId;
    private String ipdSummaryAr;
    private String ipdSummaryEn;
    private Long specificationsDocId;
    private Boolean collaborativeResearch;
    private DocumentDto documentDto;
    private Boolean patentOpposition=Boolean.FALSE;
    private LkApplicationCollaborativeResearchDto collaborativeResearchId;
}
