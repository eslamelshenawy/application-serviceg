package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.NotesStepEnum;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.util.Constants;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lk_notes")
@Where(clause = "is_deleted = 0")
public class LkNotes extends BaseEntity<Long> {

    private String code;
    private String nameEn;
    private String nameAr;
    private String descriptionEn;
    private String descriptionAr;
    private boolean enabled;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private LkApplicationCategory category;
    @ManyToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "section_id")
    private LkSection section;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attribute_id")
    private LkAttribute attribute;
    @ManyToOne
    @JoinColumn(name = "step_id")
    private LkStep step;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_category_id")
    private LkNoteCategory noteCategory;
    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    @Enumerated(EnumType.STRING)
    private NotesTypeEnum notesTypeEnum;
    @Enumerated(EnumType.STRING)
    private NotesStepEnum notesStep;
    public LkNotes(Long id) {
        this.setId(id);
    }

}
