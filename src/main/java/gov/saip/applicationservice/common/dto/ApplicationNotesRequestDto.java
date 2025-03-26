package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LkNotesDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationNotesRequestDto implements Serializable {
    private Long id;
    private ApplicationInfo applicationInfo;
    private List<LkNotesDto> sectionNotes;
    private String description;
    private boolean isDone;
    private LkSectionDto section;
    private LkStepDto step;
}
