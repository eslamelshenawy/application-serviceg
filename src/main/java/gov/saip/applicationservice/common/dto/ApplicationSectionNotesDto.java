package gov.saip.applicationservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSectionNotesDto implements Serializable {
    private Long applicationNotes;
    private Long lkNotes;
    private String description;
    private int isDeleted;

}
