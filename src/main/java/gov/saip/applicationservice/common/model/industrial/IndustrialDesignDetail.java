package gov.saip.applicationservice.common.model.industrial;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "industrial_design_details")
public class IndustrialDesignDetail extends BaseEntity<Long> {
    @NotNull
    private Long applicationId;
    private String explanationAr;
    private String explanationEn;
    private String usageAr;
    private String usageEn;
//    private boolean secret;
    private boolean haveExhibition;
    private String  exhibitionInfo;
    private LocalDate exhibitionDate;
    private boolean haveRevealedToPublic;
    private boolean viaMyself ;
    private LocalDate detectionDate;
    @Enumerated(EnumType.STRING)
    private RequestTypeEnum requestType;

    @OneToMany(mappedBy = "industrialDesignId",fetch = FetchType.LAZY)
    private Set<DesignSample> designSamples;

}
