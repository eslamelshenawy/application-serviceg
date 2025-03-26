package gov.saip.applicationservice.modules.plantvarieties.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "plant_varieties_details")
@Setter
@Getter
@Where(clause = "is_deleted = 0")
@NoArgsConstructor
public class PlantVarietyDetails extends BaseEntity<Long> {
    @OneToOne
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    private String descriptionAr;
    private String descriptionEn;
    private String otherDetails;

    private String commercialNameAr;
    private String commercialNameEn;
    private boolean developmentGeneticEngineering;
    private String developmentGeneticEngineeringNote;
    private boolean plantHomogeneous;
    private String plantHomogeneousNote;
    private boolean specificPlant;
    private String specificPlantNote;
    private boolean variablesDuringReproductive;
    private String variablesDuringReproductiveNote;
    private String detailedSurvey;
    private String descriptionVarietyDevelopment;

    private String parentNameDescription;
    private LocalDateTime discoveryDate;
    private String discoveryPlace;
    private String plantOrigination;
    private boolean hybridizationTypeFlag;
    private boolean leapFlag;
    private boolean discoveryFlag;
    private boolean otherFlag;


    private String protectionOtherDiseases;
    private String otherDescription;

    private boolean additionalFeatureDifferentiateVariety;
    private String additionalFeatureDifferentiateVarietyNote;
    private boolean plantConditionalTesting;
    private String plantConditionalTestingNote;


    private boolean microbiology;
    private String microbiologyNote;
    private boolean chemicalEdit;
    private String chemicalEditNote;
    private boolean tissueCulture;
    private String tissueCultureNote;
    private boolean otherFactors;
    private String otherFactorsNote;


    private boolean marketingInKsa;
    private LocalDateTime firstFillingDateInKsa;
    private String marketingInKsaNote;

    private boolean marketingOutKsa;
    private LocalDateTime firstFillingDateOutKsa;
    private String marketingOutKsaNote;

    @ManyToOne
    @JoinColumn(name = "lk_vegetarian_types_id")
    private LkVegetarianTypes lkVegetarianTypes;
    @OneToOne
    @JoinColumn(name = "hybridization_type_id")
    private LKPlantDetails hybridizationType;
    @OneToOne
    @JoinColumn(name = "reproduction_method_id")
    private LKPlantDetails reproductionMethod;
    @OneToOne
    @JoinColumn(name = "vegetarian_type_use_id")
    private LKPlantDetails vegetarianTypeUse;
    @OneToOne
    @JoinColumn(name = "illness_result_id")
    private LKPlantDetails illnessResult;

    private String hybridizationFatherName;
    private String hybridizationMotherName;
    private String reproductionMethodClarify;
    private String vegetarianTypeUseClarify;
    private String itemProducedBy;
    private LocalDateTime firstAssignationDate;
}
