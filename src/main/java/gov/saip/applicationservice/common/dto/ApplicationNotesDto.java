package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LkNotesDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationNotesDto implements Serializable {
    private Long id;
    private List<LkNotesDto> sectionNotes;
    private String description;
    private boolean isDone;
    private LkSectionDto section;
    private LkAttributeDto attribute;
    private Long priorityId;
}
