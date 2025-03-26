package gov.saip.applicationservice.common.repository.supportService;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.support_services_enums.SupportServiceReviewStatus;
import gov.saip.applicationservice.common.model.supportService.SupportServiceReview;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportServiceReviewRepository extends BaseRepository<SupportServiceReview, Long> {
    @Query("""
                select sr from SupportServiceReview sr
                join sr.supportServicesType sst
                where sst.id = :serviceId 
                order by case
                                when sr.reviewStatus = 'APPROVED' then 1
                                when sr.reviewStatus = 'NEW' then 2
                                when sr.reviewStatus = 'REJECTED' then 3
                           end
            """)
    List<SupportServiceReview> findBySupportServicesTypeId(@Param("serviceId") Long serviceId);


    @Query("""
            select sr from SupportServiceReview sr
            join sr.supportServicesType sst
            where sst.id = :serviceId and sr.createdByUser = :username
        """)
    SupportServiceReview findBySupportServicesTypeIdAAndCreatedByUser(@Param("serviceId") Long serviceId, @Param("username") String username);

    @Modifying
    @Query("""
        update SupportServiceReview rev set rev.reviewStatus = :reviewStatus where rev.id = :id
    """)
    void updateReviewStatus(@Param("id")Long id, @Param("reviewStatus")SupportServiceReviewStatus reviewStatus);
}
