package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAcceleratedResponseDto implements Serializable {
    private Long id;
    private LocalDateTime createdDate;

    private Boolean acceleratedExamination;

    private Boolean fastTrackExamination;

    private Boolean pphExamination;

    private DocumentDto latestPatentableClaimsFile;

    private DocumentDto closestPriorArtDocumentsRelatedToCitedReferencesFile;

    private DocumentDto comparative;

    private Long applicationInfoId;
    private String decision;

    private LkFastTrackExaminationTargetAreaDto fastTrackExaminationTargetArea;
}
