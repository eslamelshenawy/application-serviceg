package gov.saip.applicationservice.modules.plantvarieties.repository;

import feign.Param;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.model.LkVegetarianTypes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface LkVegetarianTypeRepository extends BaseRepository<LkVegetarianTypes, Long> {


    @Query("""
    select lkvt from LkVegetarianTypes lkvt
    WHERE (:search is null
    OR UPPER(lkvt.code) LIKE UPPER(concat('%', :search, '%'))
    OR UPPER(lkvt.nameAr) LIKE UPPER(concat('%', :search, '%'))
    OR UPPER(lkvt.nameEn) LIKE UPPER(concat('%', :search, '%'))
    OR UPPER(lkvt.scientificName) LIKE UPPER(concat('%', :search, '%'))
    OR CAST(lkvt.protectionPeriod AS string) LIKE concat('%', :search, '%')
    OR CAST(lkvt.marketingPeriodInKsa AS string) LIKE concat('%', :search, '%')
    OR CAST(lkvt.marketingPeriodOutKsa AS string) LIKE concat('%', :search, '%')
    OR CAST(lkvt.codeNumber AS string) LIKE concat('%', :search, '%'))
    AND lkvt.isDeleted = 0
    AND (:isActive is null OR lkvt.isActive = :isActive)
    ORDER BY lkvt.id DESC
    """)
    Page<LkVegetarianTypes> getAllPaginatedVegetarianTypes(@Param("search") String search,@Param("isActive") Boolean isActive,Pageable pageable);



    @Query("select lkvt from LkVegetarianTypes lkvt where lkvt.isDeleted = 0")
    List<LkVegetarianTypes> getAllWithNoPaging();



    @Query("""
            SELECT DISTINCT vt
            FROM LkVegetarianTypes vt
            JOIN LKPVProperty prop on prop.lkVegetarianType.id = vt.id
            JOIN LKPVPropertyOptions opt on opt.LKPVProperty.id = prop.id
            WHERE vt.isActive = true
            AND prop.isActive = true
            AND opt.isActive = true
            AND prop.isDeleted = 0
            AND opt.isDeleted = 0
            AND (:excellence is null Or prop.excellence= :excellence)
            """)
    List<LkVegetarianTypes> getAllVegetarianTypesThatHaveOnlyPropertiesAndOptions(@RequestParam("excellence") PVExcellence excellence);



}
