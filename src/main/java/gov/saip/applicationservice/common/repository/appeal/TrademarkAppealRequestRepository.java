package gov.saip.applicationservice.common.repository.appeal;

import gov.saip.applicationservice.common.model.appeal.TrademarkAppealRequest;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrademarkAppealRequestRepository extends SupportServiceRequestRepository<TrademarkAppealRequest> {


    @Modifying
    @Query("""
    update TrademarkAppealRequest tar set tar.departmentReply = :reply,
     tar.requestStatus = (select st.id from LKSupportServiceRequestStatus st where st.code = 'COMPLAINANT_TO_COMMITTEE')
     where tar.id = :id
     """)
    void updateDepartmentReply(@Param("id") Long id, @Param("reply") String reply);

    @Query("""
            select CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END from TrademarkAppealRequest tar
            where
            (:applicationId is null or tar.applicationInfo.id = :applicationId)
            and
            (:parentSupportServiceId is null or tar.supportServicesType.id = :parentSupportServiceId)
    """)
    boolean isApplicationOrSupportServiceAppealed(@Param("applicationId") Long applicationId, @Param("parentSupportServiceId") Long parentSupportServiceId);
}
