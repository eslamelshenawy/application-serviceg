package gov.saip.applicationservice.modules.plantvarieties.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "pv_prove_excellence")
@Setter
@Getter
@Where(clause = "is_deleted = 0")
@NoArgsConstructor
public class ProveExcellence extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "plant_variety_details_id")
    private PlantVarietyDetails plantVarietyDetails;
    private String plantNameSimilarYourPlant;
    @OneToOne
    @JoinColumn(name = "lk_pv_property_id")
    private LKPVProperty lkpvProperty;
    @OneToOne
    @JoinColumn(name = "lk_pv_property_options_id")
    private LKPVPropertyOptions lkpvPropertyOptions;
    @OneToOne
    @JoinColumn(name = "lk_pv_property_options_second_id")
    private LKPVPropertyOptions lkpvPropertyOptionsSecond;

    private String attributePlantDescription;
    private String explainDifference;
    private String descriptionTraitSimilarCategory;
    @ManyToOne
    @JoinColumn(name = "lk_vegetarian_types_id")
    private LkVegetarianTypes lkVegetarianTypes;

}
