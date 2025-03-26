package gov.saip.applicationservice.modules.plantvarieties.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyOptionsLightDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVPropertyOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LKPVPropertyOptionsRepository extends BaseRepository<LKPVPropertyOptions,Long> {

    @Query("""
        select pvpo from LKPVPropertyOptions pvpo
        WHERE (:search IS NULL OR :search = ''
        OR UPPER(pvpo.nameAr) LIKE UPPER(concat('%', :search, '%'))
        OR UPPER(pvpo.nameEn) LIKE UPPER(concat('%', :search, '%'))
        OR UPPER(pvpo.note) LIKE UPPER(concat('%', :search, '%'))
        OR UPPER(pvpo.example) LIKE UPPER(concat('%', :search, '%'))
        OR CAST(pvpo.isActive as string) LIKE UPPER(concat('%', :search, '%'))
        OR (UPPER(pvpo.LKPVProperty.nameAr) LIKE UPPER(concat('%', :search, '%')) AND :language = 'AR')
        OR (UPPER(pvpo.LKPVProperty.nameEn) LIKE UPPER(concat('%', :search, '%')) AND :language = 'EN')
        OR (UPPER(pvpo.LKPVProperty.lkVegetarianType.nameAr) LIKE UPPER(concat('%', :search, '%')) AND :language = 'AR')
        OR (UPPER(pvpo.LKPVProperty.lkVegetarianType.nameEn) LIKE UPPER(concat('%', :search, '%')) AND :language = 'EN'))
        AND pvpo.isDeleted = 0
        AND pvpo.LKPVProperty.lkVegetarianType.isDeleted = 0
        AND (:lkVegetarianTypeId IS NULL OR pvpo.LKPVProperty.lkVegetarianType.id = :lkVegetarianTypeId)
        AND (:lkPVPropertyId IS NULL OR pvpo.LKPVProperty.id = :lkPVPropertyId)
        AND (:type IS NULL OR pvpo.LKPVProperty.type = :type)
        AND (:excellence IS NULL OR pvpo.LKPVProperty.excellence = :excellence)
        AND (:isActive IS NULL OR pvpo.isActive = :isActive)
        AND pvpo.LKPVProperty.isDeleted = 0
        ORDER BY pvpo.id DESC
        """)
    Page<LKPVPropertyOptions> getAllPaginatedPvPropertyOptions(Pageable pageable,
                                                               @Param("lkPVPropertyId")Long lkPVPropertyId,
                                                               @Param("type") PVPropertyType type,
                                                               @Param("excellence") PVExcellence excellence,
                                                               @Param("lkVegetarianTypeId") Long lkVegetarianTypeId,
                                                               @Param("isActive") Boolean isActive,
                                                               @Param("language") String language,
                                                               @Param("search")String search);



    @Query( """
            select pvpo from LKPVPropertyOptions pvpo
            WHERE (:search IS NULL OR :search = ''
            OR UPPER(pvpo.code) LIKE UPPER(concat('%', :search, '%'))
            OR UPPER(pvpo.nameAr) LIKE UPPER(concat('%', :search, '%'))
            OR UPPER(pvpo.nameEn) LIKE UPPER(concat('%', :search, '%')))
            AND pvpo.isDeleted = 0
            AND (:lkPVPropertyId IS NULL OR pvpo.LKPVProperty.id= :lkPVPropertyId)
            AND (:type IS NULL OR pvpo.LKPVProperty.type= :type)
            AND (:excellence IS NULL OR pvpo.LKPVProperty.excellence= :excellence)
            AND pvpo.LKPVProperty.isDeleted=0
            """)
    List<LKPVPropertyOptions> getAllPvPropertyOptions(@Param("lkPVPropertyId")Long lkPVPropertyId,
                                                      @Param("type") PVPropertyType type,
                                                      @Param("excellence") PVExcellence excellence,
                                                      @Param("search")String search);





    @Query("""
        select new gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyOptionsLightDto(
        pvpo.id,
        pvpo.isDeleted,
        pvpo.code,
        pvpo.nameAr,
        pvpo.nameEn,
        pvpo.example,
        pvpo.note,
        pvpo.isActive
        ) from LKPVPropertyOptions pvpo where pvpo.isDeleted = 0
        AND (:lkPVPropertyId IS NULL OR pvpo.LKPVProperty.id = :lkPVPropertyId)
        AND pvpo.isActive is true
        """)
    List<LKPVPropertyOptionsLightDto> getAllPvPropertiesOptionsLightDto(@Param("lkPVPropertyId") Long lkPVPropertyId);


    List<LKPVPropertyOptions> findByLKPVPropertyId(@Param("id")Long id);

}
