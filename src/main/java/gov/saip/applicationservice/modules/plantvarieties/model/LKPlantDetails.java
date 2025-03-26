package gov.saip.applicationservice.modules.plantvarieties.model;

import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "lk_plant_details")
@Getter
@Setter
public class LKPlantDetails extends BaseLkEntity<Integer> {

    @Column
    private String mainCode;
    public LKPlantDetails( ) {

    }
    public LKPlantDetails(Integer hybridizationTypeId) {
     super(hybridizationTypeId);
    }
}
