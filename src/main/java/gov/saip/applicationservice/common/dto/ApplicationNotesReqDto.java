package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationNotesReqDto implements Serializable {
    private Long id;
    private Long applicationId;
    private List<Long> sectionNotesIds;
    private String description;
    private String sectionCode;
    private String attributeCode;
    private String taskDefinitionKey;
    private String stageKey;
    private Integer stepId;
    private Long priorityId;
    private NotesTypeEnum notesType;
    private List<ApplicationSectionNotesDto> applicationSectionNotesDtos;
}
