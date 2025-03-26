package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "application_section_notes")
@Setter
@Where(clause = "is_deleted = 0")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationSectionNotes  extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "application_notes_id")
    private ApplicationNotes applicationNotes;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private LkNotes note;

    @Column(name = "description")
    private String description;

}

