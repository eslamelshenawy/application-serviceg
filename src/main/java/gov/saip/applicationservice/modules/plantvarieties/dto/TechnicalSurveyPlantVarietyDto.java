package gov.saip.applicationservice.modules.plantvarieties.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TechnicalSurveyPlantVarietyDto  extends PlantVarietyDto {
    private Long id;
    private String plantVarietyNameAr;
    private String plantVarietyNameEN;
    private String commercialNameAr;
    private String commercialNameEn;
    private Boolean developmentGeneticEngineering;
    private String developmentGeneticEngineeringNote;
    private Boolean plantHomogeneous;
    private String plantHomogeneousNote;
    private Boolean specificPlant;
    private String specificPlantNote;
    private Boolean variablesDuringReproductive;
    private String variablesDuringReproductiveNote;
    private String detailedSurvey;
    private String descriptionVarietyDevelopment;
    private Long lkVegetarianTypesId;

    private Boolean hybridizationTypeFlag;
    private Boolean leapFlag;
    private Boolean discoveryFlag;
    private Boolean otherFlag;
    private LocalDateTime discoveryDate;
    private String plantOrigination;
    private String discoveryPlace;

    private Integer hybridizationTypeId;
    private Integer reproductionMethodId;
    private Integer vegetarianTypeUseId;

    private String hybridizationFatherName;
    private String hybridizationMotherName;
    private String reproductionMethodClarify;
    private String vegetarianTypeUseClarify;

    private String reproductionMethodNameAr;
    private String vegetarianTypeUseNameAr;
    private String reproductionMethodNameEn;
    private String vegetarianTypeUseNameEn;

    private String hybridizationTypeCode;
    private String reproductionMethodCode;
    private String vegetarianTypeUseCode;

    private String lkVegetarianTypesScientificName;
    private String lkVegetarianTypesNameAr;
    private String lkVegetarianTypesNameEn;

    private String hybridizationTypeNameAr;
    private String hybridizationTypeNameEn;
    private String itemProducedBy;


    public TechnicalSurveyPlantVarietyDto(Long id, String plantVarietyNameAr, String plantVarietyNameEN,
                                          String commercialNameAr, String commercialNameEn,
                                          Boolean developmentGeneticEngineering, String developmentGeneticEngineeringNote,
                                          Boolean plantHomogeneous, String plantHomogeneousNote, Boolean specificPlant, String specificPlantNote,
                                          Boolean variablesDuringReproductive, String variablesDuringReproductiveNote, String detailedSurvey,
                                          String descriptionVarietyDevelopment, Boolean hybridizationTypeFlag, Boolean leapFlag, Boolean discoveryFlag, Boolean otherFlag,
                                          LocalDateTime discoveryDate, String plantOrigination, String hybridizationFatherName, String hybridizationMotherName,
                                          String reproductionMethodClarify, String vegetarianTypeUseClarify, String reproductionMethodNameAr,
                                          String vegetarianTypeUseNameAr, String reproductionMethodNameEn, String vegetarianTypeUseNameEn,
                                          String lkVegetarianTypesScientificName, String discoveryPlace,Long lkVegetarianTypesId,
                                          String lkVegetarianTypesNameAr, String lkVegetarianTypesNameEn,String hybridizationTypeNameAr,String hybridizationTypeNameEn,
                                          Integer hybridizationTypeId ,Integer reproductionMethodId,Integer vegetarianTypeUseId,
                                          String hybridizationTypeCode, String reproductionMethodCode,String vegetarianTypeUseCode,String itemProducedBy) {
        this.id = id;
        this.plantVarietyNameAr = plantVarietyNameAr;
        this.plantVarietyNameEN = plantVarietyNameEN;
        this.commercialNameAr = commercialNameAr;
        this.commercialNameEn = commercialNameEn;
        this.developmentGeneticEngineering = developmentGeneticEngineering;
        this.developmentGeneticEngineeringNote = developmentGeneticEngineeringNote;
        this.plantHomogeneous = plantHomogeneous;
        this.plantHomogeneousNote = plantHomogeneousNote;
        this.specificPlant = specificPlant;
        this.specificPlantNote = specificPlantNote;
        this.variablesDuringReproductive = variablesDuringReproductive;
        this.variablesDuringReproductiveNote = variablesDuringReproductiveNote;
        this.detailedSurvey = detailedSurvey;
        this.descriptionVarietyDevelopment = descriptionVarietyDevelopment;
        this.hybridizationTypeFlag = hybridizationTypeFlag;
        this.leapFlag = leapFlag;
        this.discoveryFlag = discoveryFlag;
        this.otherFlag = otherFlag;
        this.discoveryDate = discoveryDate;
        this.plantOrigination = plantOrigination;
        this.hybridizationFatherName = hybridizationFatherName;
        this.hybridizationMotherName = hybridizationMotherName;
        this.reproductionMethodClarify = reproductionMethodClarify;
        this.vegetarianTypeUseClarify = vegetarianTypeUseClarify;
        this.reproductionMethodNameAr = reproductionMethodNameAr;
        this.vegetarianTypeUseNameAr = vegetarianTypeUseNameAr;
        this.reproductionMethodNameEn = reproductionMethodNameEn;
        this.vegetarianTypeUseNameEn = vegetarianTypeUseNameEn;
        this.lkVegetarianTypesScientificName = lkVegetarianTypesScientificName;
        this.discoveryPlace = discoveryPlace;
        this.lkVegetarianTypesId = lkVegetarianTypesId;
        this.lkVegetarianTypesNameAr = lkVegetarianTypesNameAr;
        this.lkVegetarianTypesNameEn = lkVegetarianTypesNameEn;
        this.hybridizationTypeNameAr = hybridizationTypeNameAr;
        this.hybridizationTypeNameEn = hybridizationTypeNameEn;

        this.hybridizationTypeId = hybridizationTypeId;
        this.reproductionMethodId = reproductionMethodId;
        this.vegetarianTypeUseId = vegetarianTypeUseId;


        this.hybridizationTypeCode=hybridizationTypeCode;
        this.reproductionMethodCode= reproductionMethodCode;
        this.vegetarianTypeUseCode=vegetarianTypeUseCode;
        this.itemProducedBy=itemProducedBy;


    }
}



