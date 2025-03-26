package gov.saip.applicationservice.modules.plantvarieties.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceLightDto;
import gov.saip.applicationservice.modules.plantvarieties.model.ProveExcellence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveExcellenceRepository extends BaseRepository<ProveExcellence, Long> {
    @Query("""
            select new gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceLightDto(
            pe.id,pe.plantVarietyDetails.id,pe.plantNameSimilarYourPlant,
            pp.id as lkPropertyId,pp.nameAr as lkPropertyNameAr ,pp.nameEn as lkPropertyNameEn,
            optionFirst.id as lkpvPropertyOptionsId,optionFirst.nameAr as optionNameAr,optionFirst.nameEn as optionNameEn,
            optionSecond.id as lkpvPropertyOptionsSecondId,optionSecond.nameAr as optionSecondNameAr,optionSecond.nameEn as optionSecondNameEn,
            pe.attributePlantDescription,pe.explainDifference,pe.descriptionTraitSimilarCategory,lvt.id)
            from ProveExcellence pe
            left join pe.lkpvPropertyOptions optionFirst on optionFirst.id=pe.lkpvPropertyOptions.id
            left join pe.lkpvPropertyOptionsSecond optionSecond on optionSecond.id=pe.lkpvPropertyOptionsSecond.id
            left join pe.lkpvProperty pp
            left join pe.lkVegetarianTypes lvt
                        where pe.plantVarietyDetails.application.id = :appId
            """)
    List<ProveExcellenceLightDto> findApplicationProveExcellenceByApplicationId(@Param("appId") Long appId);

    @Query("""
            select new gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceLightDto(
            pe.id,
            pe.plantVarietyDetails.id,
            pe.plantNameSimilarYourPlant,
            pp.id,
            pp.nameAr,
            pp.nameEn,
            optionFirst.id,
            optionFirst.nameAr,
            optionFirst.nameEn,
            optionSecond.id,
            optionSecond.nameAr,
            optionSecond.nameEn,
            pe.attributePlantDescription,
            pe.explainDifference,
            pe.descriptionTraitSimilarCategory,
            lvt.id)
             from ProveExcellence pe
             left join pe.plantVarietyDetails pv
             left join pv.application ai
             left join ai.applicationCustomers ac
             left join pe.lkpvPropertyOptions optionFirst
             left join pe.lkpvPropertyOptionsSecond optionSecond
             left join pe.lkpvProperty pp
             left join pe.lkVegetarianTypes lvt
                         WHERE (:customerId is null or ac.customerId = :customerId or :customerCode = (SELECT a.customerCode FROM ApplicationRelevantType a
                                        join a.applicationInfo info
                                        where
                                        info.id = ai.id  AND a.type = 'Applicant_MAIN')) and ai.id= :appId and lvt.id= :lkVegetarianTypesId
            """)
    Page<ProveExcellenceLightDto> getProveExcellenceApplicationsFiltering(@Param("customerCode") String customerCode,
                                                                          @Param("customerId") Long customerId, @Param("appId") Long appId,
                                                                          @Param("lkVegetarianTypesId") Long lkVegetarianTypesId,
                                                                          Pageable pageable);


    @Query("""
            select new gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceLightDto(
            pe.id,pe.plantVarietyDetails.id,pe.plantNameSimilarYourPlant,
            pe.lkpvProperty.id,pe.lkpvProperty.nameAr,pe.lkpvProperty.nameEn,
            pe.lkpvPropertyOptions.id,pe.lkpvPropertyOptions.nameAr,pe.lkpvPropertyOptions.nameEn,
            pe.lkpvPropertyOptionsSecond.id,pe.lkpvPropertyOptionsSecond.nameAr,pe.lkpvPropertyOptionsSecond.nameEn,
            pe.attributePlantDescription,pe.explainDifference,pe.descriptionTraitSimilarCategory,lvt.id)
            from ProveExcellence pe 
            left join pe.lkpvPropertyOptions optionFirst on optionFirst.id=pe.lkpvPropertyOptions.id
            left join pe.lkpvPropertyOptionsSecond optionSecond on optionSecond.id=pe.lkpvPropertyOptionsSecond.id
            left join pe.lkpvProperty pp
            left join pe.lkVegetarianTypes lvt
            where pe.plantVarietyDetails.id = :plantDetailsId and lvt.id= :lkVegetarianTypesId
            """)
    List<ProveExcellenceLightDto> findProveExcellenceByPlantDetailsId(@Param("plantDetailsId") Long plantDetailsId,@Param("lkVegetarianTypesId") Long lkVegetarianTypesId);

    @Query(" select pe from ProveExcellence pe where pe.plantVarietyDetails.id = :plantVarietyDetailsId ")
    Optional<ProveExcellence> getProveExcellenceByPlantVarietyDetailsId(@Param("plantVarietyDetailsId") Long plantVarietyDetailsId);
}