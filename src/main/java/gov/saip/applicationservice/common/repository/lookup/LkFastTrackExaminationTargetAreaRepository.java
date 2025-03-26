package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LkFastTrackExaminationTargetArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LkFastTrackExaminationTargetAreaRepository extends BaseRepository<LkFastTrackExaminationTargetArea, Long> {



    @Query( """
            select fatr from LkFastTrackExaminationTargetArea fatr
            WHERE (:search IS NULL OR :search = ''
            OR UPPER(fatr.descriptionAr) LIKE UPPER(concat('%', :search, '%'))
            OR UPPER(fatr.descriptionEn) LIKE UPPER(concat('%', :search, '%')))
            """)
    Page<LkFastTrackExaminationTargetArea> getAllPaginatedFastTrackExaminationAreaWithSearch(@Param("search")String search , Pageable pageable);

}
