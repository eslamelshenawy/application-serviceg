package gov.saip.applicationservice.modules.plantvarieties.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.plantvarieties.dto.PlantDetailsPropertiesOptionsDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.PlantDetailsPropertiesOptionsSelectionsDto;
import gov.saip.applicationservice.modules.plantvarieties.model.PlantDetailsPropertiesOptions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlantDetailsPropertiesOptionsRepository extends BaseRepository<PlantDetailsPropertiesOptions,Long> {


    List<PlantDetailsPropertiesOptions> findByPlantVarietyDetailsId(@Param("plantVarietyDetailsId")Long plantVarietyDetailsId);




    @Query("""
            select new gov.saip.applicationservice.modules.plantvarieties.dto.PlantDetailsPropertiesOptionsSelectionsDto(
            pdpo.id as id,
            pdpo.lkpvProperty.id as pvPropertyId,
            pdpo.lkpvProperty.nameAr as pvPropertyNameAr,
            pdpo.lkpvProperty.nameEn as pvPropertyNameEn,
            pdpo.lkpvPropertyOptions.id as pvPropertyOptionId,
            pdpo.lkpvPropertyOptions.nameAr as pvPropertyOptionNameAr ,
            pdpo.lkpvPropertyOptions.nameEn as pvPropertyOptionNameEn,
            pdpo.note as note,
            pdpo.example as example,
            pdpo.lkpvProperty.type)
            from PlantDetailsPropertiesOptions pdpo where pdpo.plantVarietyDetails.id = :plantDetailsId
            AND  (pdpo.lkpvProperty.isDeleted=1 OR pdpo.lkpvProperty.isDeleted=0)
            """)
    List<PlantDetailsPropertiesOptionsSelectionsDto> findByPlantDetailsId(@Param("plantDetailsId")Long plantDetailsId);
}
