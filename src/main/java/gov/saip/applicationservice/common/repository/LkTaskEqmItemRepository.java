package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.LkTaskEqmItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LkTaskEqmItemRepository extends BaseLkRepository<LkTaskEqmItem, Integer> {

    @Query("""
            select item from LkTaskEqmItem item
            join item.types type
            where type.code = :typeCode and item.shown = true
        """)
    Optional<List<LkTaskEqmItem>> getLkTaskEqmItemsByTypesCode(@Param("typeCode") String typeCode);

    @Query("""
            SELECT COUNT(ltei)>0 FROM LkTaskEqmItem ltei
            JOIN ltei.types ltet
            WHERE ltei.code = :code AND ltet.code = :type
            """)
    boolean checkHaveCodeAndType(@Param("code") String code,@Param("type") String type);


    @Query("""
            select item from LkTaskEqmItem item 
            WHERE (:search IS NULL OR :search = ''
            OR UPPER(item.nameAr) LIKE UPPER(concat('%', :search, '%'))
            OR UPPER(item.nameEn) LIKE UPPER(concat('%', :search, '%')))
            """)
    Page<LkTaskEqmItem> getTaskEqmBySearch(@Param("search")String search , Pageable pageable);

}
