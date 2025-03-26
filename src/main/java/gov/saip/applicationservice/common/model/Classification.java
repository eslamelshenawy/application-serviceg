package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "classifications")
public class Classification extends BaseEntity<Long> {
    @NotEmpty
    private String code;
    private String nameEn;
    @NotEmpty
    private String nameAr;
    private String descriptionEn;
    @NotEmpty
    private String descriptionAr;
    private String notesEn;
    private String notesAr;
    private boolean enabled;
    private int niceVersion;
    private Long idOld;
    @ManyToOne()
    @JoinColumn(name = "version_id")
    private LkClassificationVersion version;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id")
    private LkClassificationUnit unit;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private LkApplicationCategory category;


    @OneToMany(mappedBy = "classification",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ApplicationNiceClassification> niceClassifications = new ArrayList<>();

    @OneToMany(mappedBy = "classification",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SubClassification> subClassifications = new ArrayList<>();

    public Classification() {}

    public Classification(Long id) {
        super(id);
    }
}
