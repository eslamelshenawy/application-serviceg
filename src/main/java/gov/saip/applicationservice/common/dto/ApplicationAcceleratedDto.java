package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAcceleratedDto implements Serializable {
    private Long id;
    private LocalDateTime createdDate;
    private Boolean acceleratedExamination;
    private Boolean fastTrackExamination;//FTE
    private Boolean pphExamination;//PPH
    private Long latestPatentableClaimsFileId;
    private Long closestPriorArtDocumentsRelatedToCitedReferencesFileId;
    private Long comparativeId;
    private Long applicationInfoId;
    private Long fastTrackExaminationTargetAreaId;
    private String decision;
//    private Boolean pphPctExamination; // PPH-PCT
//    private String fastTrackType; // FTE or PPH or PPH-PCT or ...etc
//    private String pctNumber;
//    private Boolean matchingExplanationProtectionElements;
//    private Boolean allLastProtectionElementsSimilarOffice;
//    private String demandProtectionElements;
//    private String lastDemandProtectionElements;
//    private String matchingExplanation;
}



