package gov.saip.applicationservice.modules.plantvarieties.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LKPVPropertyRepository extends BaseRepository<LKPVProperty, Long> {


    @Query("""
    select pvp from LKPVProperty pvp
    WHERE (:search IS NULL OR :search = ''
    OR UPPER(pvp.code) LIKE UPPER(concat('%', :search, '%'))
    OR UPPER(pvp.nameAr) LIKE UPPER(concat('%', :search, '%')) 
    OR UPPER(pvp.nameEn) LIKE UPPER(concat('%', :search, '%'))
    OR UPPER(pvp.excellence) LIKE UPPER(concat('%', :search, '%'))
    OR ((UPPER(pvp.lkVegetarianType.nameAr) LIKE UPPER(concat('%', :search, '%')) AND :language = 'AR')
    OR (UPPER(pvp.lkVegetarianType.nameEn) LIKE UPPER(concat('%', :search, '%')) AND :language = 'EN')))
    AND pvp.isDeleted = 0
    AND pvp.lkVegetarianType.isDeleted = 0
    AND (:lkVegetarianTypeId IS NULL OR pvp.lkVegetarianType.id = :lkVegetarianTypeId)
    AND (:type IS NULL OR pvp.type = :type)
    AND (:excellence IS NULL OR pvp.excellence = :excellence)
    AND (:isActive IS NULL OR pvp.isActive = :isActive)
    ORDER BY pvp.id DESC
    """)
    Page<LKPVProperty> getAllPaginatedPvProperties(@Param("search")String search ,
                                                   @Param("lkVegetarianTypeId") Long lkVegetarianTypeId,
                                                   @Param("type") PVPropertyType type,
                                                   @Param("excellence") PVExcellence excellence,
                                                   @Param("isActive") Boolean isActive,
                                                   @Param("language") String language,
                                                   Pageable pageable);




    @Query( """
            select DISTINCT pvp from LKPVProperty pvp
            JOIN LKPVPropertyOptions po ON po.LKPVProperty.id = pvp.id
            WHERE pvp.isDeleted = 0
            AND pvp.lkVegetarianType.isDeleted=0
            AND (:lkVegetarianTypeId is NULL OR pvp.lkVegetarianType.id= :lkVegetarianTypeId)
            AND (:type is NULL OR pvp.type= :type)
            AND pvp.isActive is true
            ORDER BY pvp.id DESC
            """)
    Page<LKPVProperty> getAllPaginatedPvPropertiesThatHaveOptionsOnly(@Param("lkVegetarianTypeId") Long lkVegetarianTypeId,
                                                                      @Param("type") PVPropertyType type,
                                                                      Pageable pageable);



    @Query( """
            select DISTINCT pvp from LKPVProperty pvp
            JOIN LKPVPropertyOptions po ON po.LKPVProperty.id = pvp.id
            WHERE pvp.isDeleted = 0
            AND pvp.lkVegetarianType.isDeleted = 0
            AND (:lkVegetarianTypeId is NULL OR pvp.lkVegetarianType.id= :lkVegetarianTypeId)
            AND (:type is NULL OR pvp.type= :type) 
            AND pvp.isActive is true
            AND (:excellence is NULL OR pvp.excellence= :excellence)
            """)
    List<LKPVProperty> getAllPvPropertiesThatHaveOptionsOnlyWithoutPaging(@Param("lkVegetarianTypeId") Long lkVegetarianTypeId,
                                                                          @Param("excellence") PVExcellence excellence,
                                                                          @Param("type") PVPropertyType type);






    @Query( """
            select pvp from LKPVProperty pvp
            WHERE pvp.isDeleted = 0
            AND pvp.lkVegetarianType.isDeleted = 0
            AND (:lkVegetarianTypeId is NULL OR pvp.lkVegetarianType.id= :lkVegetarianTypeId)
            AND (:type is NULL OR pvp.type= :type)
            AND (:excellence is NULL OR pvp.excellence= :excellence) 
            """)
    List<LKPVProperty> getAllPvPropertiesWithoutPaging(@Param("lkVegetarianTypeId") Long lkVegetarianTypeId,
                                                       @Param("type") PVPropertyType type,
                                                       @Param("excellence") PVExcellence excellence);

}
