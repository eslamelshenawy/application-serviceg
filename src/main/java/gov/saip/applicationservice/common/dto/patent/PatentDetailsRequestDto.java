package gov.saip.applicationservice.common.dto.patent;

import lombok.Data;

import java.util.List;


@Data
public class PatentDetailsRequestDto {
    private Long id;
    private String ipdSummaryAr;
    private String ipdSummaryEn;
    private Long specificationsDocId;
    private Boolean collaborativeResearch;
    private Boolean patentOpposition=Boolean.FALSE;
    private LkApplicationCollaborativeResearchDto collaborativeResearchId;
    private List<PatentAttributeChangeLogDto> attributeChangeLogs;
}
