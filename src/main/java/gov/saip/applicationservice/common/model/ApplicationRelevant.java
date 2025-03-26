package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.IdentifierTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "application_relevant")
@Setter
@Getter
public class ApplicationRelevant extends BaseEntity<Long> {

    @Column
    private String fullNameAr;

    @Column
    private String fullNameEn;
    @Column
    @Enumerated(EnumType.STRING)
    private IdentifierTypeEnum identifierType;

    @Column
    private String identifier;

    @Column
    private String gender;

    @Column
    private Long nationalCountryId;

    @Column
    private String address;

    @Column
    private Long countryId;

    @Column
    private String city;

    @Column
    private String pobox;


}
