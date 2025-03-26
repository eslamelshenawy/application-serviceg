package gov.saip.applicationservice.modules.plantvarieties.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.plantvarieties.dto.*;
import gov.saip.applicationservice.modules.plantvarieties.model.PlantVarietyDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlantVarietyRepository extends BaseRepository<PlantVarietyDetails, Long> {

    @Query(" select case when (count(pv) > 0)  then true else false end from PlantVarietyDetails pv where pv.application.id = :appId ")
    boolean checkApplicationHasPlantVariety(@Param("appId") Long appId);

    @Query(" select pv from PlantVarietyDetails pv where pv.application.id = :appId ")
    Optional<PlantVarietyDetails> getPlantVarietyByApplicationId(@Param("appId") Long appId);

    @Query("""
            select new gov.saip.applicationservice.modules.plantvarieties.dto.SummaryPlantVarietyDto(pv.id,pv.descriptionAr,pv.descriptionEn,pv.otherDetails) 
            from PlantVarietyDetails pv where pv.application.id = :appId
            """)
    List<SummaryPlantVarietyDto> getSummaryWithApplicationId(@Param("appId") Long appId);
    @Query("""
            select new gov.saip.applicationservice.modules.plantvarieties.dto.TechnicalSurveyPlantVarietyDto
            (        pv.id,ai.titleAr, ai.titleEn,
                     pv.commercialNameAr, pv.commercialNameEn,
                     pv.developmentGeneticEngineering, pv.developmentGeneticEngineeringNote,
                     pv.plantHomogeneous,pv.plantHomogeneousNote,pv.specificPlant,pv.specificPlantNote,
                     pv.variablesDuringReproductive,pv.variablesDuringReproductiveNote,pv.detailedSurvey,
                     pv.descriptionVarietyDevelopment,pv.hybridizationTypeFlag,pv.leapFlag,pv.discoveryFlag,pv.otherFlag,
                     pv.discoveryDate,pv.plantOrigination,pv.hybridizationFatherName,pv.hybridizationMotherName,
                     pv.reproductionMethodClarify, pv.vegetarianTypeUseClarify,rm.nameAr,
                     vtu.nameAr,rm.nameEn,vtu.nameEn,
                     pv.lkVegetarianTypes.scientificName,pv.discoveryPlace,pv.lkVegetarianTypes.id,
                     pv.lkVegetarianTypes.nameAr,pv.lkVegetarianTypes.nameEn,hy.nameAr,hy.nameEn,
                     hy.id,rm.id,vtu.id,
                     hy.code, rm.code,vtu.code,pv.itemProducedBy)
            from PlantVarietyDetails pv
            left join ApplicationInfo ai on ai.id = pv.application.id
            left join LKPlantDetails hy on hy.id = pv.hybridizationType.id
            left join LKPlantDetails rm on rm.id = pv.reproductionMethod.id
            left join LKPlantDetails vtu on vtu.id = pv.vegetarianTypeUse.id
              where pv.application.id = :appId
            """)
    List<TechnicalSurveyPlantVarietyDto> getTechnicalSurveyByApplicationId(@Param("appId") Long appId);

                 @Query("""
            select new gov.saip.applicationservice.modules.plantvarieties.dto.ExaminationDataPlantVarietyDto
            (pv.id,pv.microbiology,pv.microbiologyNote,pv.chemicalEdit,pv.chemicalEditNote,
            pv.tissueCulture,pv.tissueCultureNote,pv.otherFactors,pv.otherFactorsNote)
            from PlantVarietyDetails pv  where pv.application.id = :appId
            """)
    List<ExaminationDataPlantVarietyDto>getExaminationDataByApplicationId(@Param("appId") Long appId);

    @Query("""
            select new gov.saip.applicationservice.modules.plantvarieties.dto.PriorityDataSectionPlantVarietyDto
            (pv.id,pv.marketingInKsa,pv.firstFillingDateInKsa,pv.marketingInKsaNote,
            pv.marketingOutKsa,pv.firstFillingDateOutKsa,pv.marketingOutKsaNote)
            from PlantVarietyDetails pv  where pv.application.id = :appId
            """)
    List<PriorityDataSectionPlantVarietyDto> getPriorityDataSectionWithApplication(@Param("appId") Long appId);
        @Query("""
            select
            new gov.saip.applicationservice.modules.plantvarieties.dto.PlantVarietyRequestDto(pvd.id,ai.applicationNumber,
            ai.id,pvd.otherDetails,ai.applicationStatus.ipsStatusDescEn,ai.applicationStatus.ipsStatusDescAr,
                ai.applicationStatus.code,ai.titleAr,ai.titleEn,
                ai.createdDate,ai.createdByCustomerType,ai.ownerNameAr,ai.ownerNameEn,ai.applicationStatus.ipsStatusDescEnExternal,
                ai.applicationStatus.ipsStatusDescArExternal,ai.modifiedDate)
            from PlantVarietyDetails pvd
            left join ApplicationInfo ai on ai.id = pvd.application.id
            where ai.id = :appId
            """)
    PlantVarietyRequestDto getPlantVarietiesAppInfo(@Param("appId") Long appId);
    @Query("""
            select
            new gov.saip.applicationservice.modules.plantvarieties.dto.PlantProveExcellenceVariablesDto(
            pvd.id,
            pvd.additionalFeatureDifferentiateVariety,pvd.additionalFeatureDifferentiateVarietyNote,
            pvd.plantConditionalTesting,pvd.plantConditionalTestingNote
          )
            from PlantVarietyDetails pvd
            left join ApplicationInfo ai on ai.id = pvd.application.id
            where ai.id = :appId
            """)
    List <PlantProveExcellenceVariablesDto>getProveExcellenceVariablesWithApplication(@Param("appId") Long appId);


    @Query(value = """
        SELECT  distinct new gov.saip.applicationservice.modules.plantvarieties.dto.PlantVarietiesListDto(
        pv.id,
        ai.id,
        ai.titleAr,
        ai.titleEn,
        ai.applicationNumber , 
        ai.applicationRequestNumber,  
        ai.filingDate, 
        ai.applicationStatus.ipsStatusDescEn,
        ai.applicationStatus.ipsStatusDescAr,
        ai.applicationStatus.ipsStatusDescArExternal, 
        ai.applicationStatus.ipsStatusDescEnExternal, 
        pv.createdDate,
        ai.applicationStatus.code)
        FROM PlantVarietyDetails pv
        join pv.application ai
        JOIN ai.applicationCustomers ac 
        JOIN ai.applicationStatus stat
        WHERE (:customerId is null or ac.customerId = :customerId or :customerCode = (SELECT a.customerCode FROM ApplicationRelevantType a 
        join a.applicationInfo info
        where info.id = ai.id AND a.type = 'Applicant_MAIN'))
        AND (:query is null
        OR UPPER(ai.applicationNumber) LIKE CONCAT('%', UPPER(:query), '%')
        OR UPPER(ai.titleAr) LIKE CONCAT('%', UPPER(:query), '%')
        OR UPPER(ai.titleEn) LIKE CONCAT('%', UPPER(:query), '%'))
        AND (:statusCode is null or stat.code = :statusCode)
        ORDER BY pv.createdDate DESC
    """
    )
    Page<PlantVarietiesListDto> getPaginatedPlantVarietiesApplicationsFiltering(@Param("query") String query,
                                                                                @Param("statusCode") String statusCode,
                                                                                @Param("customerCode") String customerCode,
                                                                                @Param("customerId") Long customerId, Pageable pageable);


    @Modifying
    @Query("""
            update PlantVarietyDetails pv set pv.isDeleted='1' where pv.application.id = :appId
           """)
    void deleteByAppId(@Param("appId") Long appId);



    @Query("""
            select pvd.lkVegetarianTypes.id from PlantVarietyDetails pvd where pvd.id= :plantId
            """)
    Long findVegetarianTypeIdByPlantDetailsId(@Param("plantId") Long plantId);

    @Modifying
    @Query("""
            update PlantVarietyDetails pvd
            set pvd.firstAssignationDate = :firstAssignationDate
            where pvd.application.id = :appId
    """)
    void updateFirstAssignationDateByApplicationId(@Param("appId") Long appId, @Param("firstAssignationDate") LocalDateTime firstAssignationDate);
    @Query("""
            select pvd.application.id from PlantVarietyDetails pvd where pvd.id= :plantVarietyDetailsId
            """)
    Long getAppIdByPlantVarietyId(@Param("plantVarietyDetailsId") Long plantVarietyDetailsId);

}
