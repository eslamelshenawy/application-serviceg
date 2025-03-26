package gov.saip.applicationservice.modules.plantvarieties.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "plant_details_properties_options")
public class PlantDetailsPropertiesOptions extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "plant_variety_details_id")
    private PlantVarietyDetails plantVarietyDetails;

    @ManyToOne
    @JoinColumn(name = "lk_pv_property_id")
    private LKPVProperty lkpvProperty;

    @ManyToOne
    @JoinColumn(name = "lk_pv_property_options_id")
    private LKPVPropertyOptions lkpvPropertyOptions;

    @Column
    private String note;

    @Column
    private String example;
}
