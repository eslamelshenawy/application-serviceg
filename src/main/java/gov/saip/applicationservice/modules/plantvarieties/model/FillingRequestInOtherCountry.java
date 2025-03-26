package gov.saip.applicationservice.modules.plantvarieties.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "filling_request_other_country")
@Where(clause = "is_deleted = 0")
@Setter
@Getter
public class FillingRequestInOtherCountry extends BaseEntity<Long> {
    @Column(name = "plantVarietyDetails_id")
    private Long plantVarietyDetailsId;
    private Long countryId;
    private String registrationRequestOtherCountryNumber;
    private LocalDateTime fillingDateRequest;
    private String classification;


}
