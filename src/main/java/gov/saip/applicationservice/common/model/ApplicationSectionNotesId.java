package gov.saip.applicationservice.common.model;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationSectionNotesId implements Serializable {

    private Long applicationNotes;
    private Long note;

}