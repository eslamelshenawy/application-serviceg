package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application_notes")
public class    ApplicationNotes extends BaseEntity<Long> {


    @OneToMany(mappedBy = "applicationNotes", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ApplicationSectionNotes> applicationSectionNotes;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;
    @ManyToOne
    @JoinColumn(name = "section_id")
    private LkSection section;
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private LkAttribute attribute;
    @ManyToOne
    @JoinColumn(name = "step_id")
    private LkStep step;
    private String taskDefinitionKey;
    private String description;
    private boolean isDone;
    private String stageKey;
    private Long priorityId;

}
