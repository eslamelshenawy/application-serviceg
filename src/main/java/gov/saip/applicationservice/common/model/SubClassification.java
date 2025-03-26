package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "sub_classifications")
public class SubClassification extends BaseEntity<Long> {


    private String code;

    private String nameEn;

    private String nameAr;
    private String descriptionEn;
    private String descriptionAr;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "classification_id")
    private Classification classification;
    private boolean isShortcut;
    private boolean isVisible;
    private boolean enabled;
    private int niceVersion;
    private String serialNumberEn;
    private String serialNumberAr;
    private Long basicNumber;
}
