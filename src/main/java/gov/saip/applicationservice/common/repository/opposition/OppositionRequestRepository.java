package gov.saip.applicationservice.common.repository.opposition;

import gov.saip.applicationservice.common.dto.opposition.OppositionDetailsProjection;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.RevokeVoluntaryRequest;
import gov.saip.applicationservice.common.model.opposition.OppositionRequest;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface OppositionRequestRepository extends SupportServiceRequestRepository<OppositionRequest> {

    @Modifying
    @Query("update ApplicationInfo ai set ai.applicationStatus = :applicationStatus where ai.id = (select opp.application.id from Opposition opp where opp.id = :id)")
    void updateApplicationStatus(@Param("id") Long id, @Param("applicationStatus") LkApplicationStatus applicationStatus);


    @Query("""
            select or2.id as id , asst.applicationInfo.id as appId , asst.requestNumber as requestNumber , ai.applicationNumber as applicationNumber , or2.complainerHearingSession.complainerSessionDate as complainerSessionDate , or2.complainerHearingSession.complainerSessionTime as complainerSessionTime, 'COMPLAINER' as requesterType
            from ApplicationSupportServicesType asst inner join ApplicationInfo ai
            on  asst.applicationInfo.id  = ai.id
            inner join OppositionRequest or2 on or2.id = asst.id
            where (:requestNumber is null or asst.requestNumber  = :requestNumber)
            AND or2.complainerHearingSession.complainerSessionDate >= CURRENT_DATE
            AND (cast(:sessionDate as date) is null or or2.complainerHearingSession.complainerSessionDate = :sessionDate)
            AND (cast(:from as date) is null or or2.complainerHearingSession.complainerSessionDate >= :from)
            AND (cast(:to as date) is null or or2.complainerHearingSession.complainerSessionDate <= :to)
            """)
    List<OppositionDetailsProjection> getRequestsDetailsFirst(@Param("requestNumber") String requestNumber,@Param("sessionDate") LocalDate sessionDate,@Param("to") LocalDate to,@Param("from") LocalDate from);



    @Query("""
            select or2.id as id  , asst.applicationInfo.id as appId , asst.requestNumber as requestNumber, ai.applicationNumber as applicationNumber, or2.applicationOwnerHearingSession.complainerSessionDate as applicationOwnerSessionDate  , or2.applicationOwnerHearingSession.complainerSessionTime as applicationOwnerSessionTime , 'APPLICATION_OWNER' as requesterType
            from ApplicationSupportServicesType asst inner join ApplicationInfo ai
            on  asst.applicationInfo.id  = ai.id
            inner join OppositionRequest or2 on or2.id = asst.id
            where (:requestNumber is null or asst.requestNumber  = :requestNumber)
            AND or2.applicationOwnerHearingSession.complainerSessionDate >= CURRENT_DATE
            And (cast(:sessionDate as date) is null or or2.applicationOwnerHearingSession.complainerSessionDate = :sessionDate)
            AND (cast(:from as date) is null or or2.applicationOwnerHearingSession.complainerSessionDate >= :from)
            AND (cast(:to as date) is null or or2.applicationOwnerHearingSession.complainerSessionDate <= :to)
            """)
    List<OppositionDetailsProjection> getRequestsDetailsSecond(@Param("requestNumber") String requestNumber,@Param("sessionDate") LocalDate sessionDate,@Param("to") LocalDate to,@Param("from") LocalDate from);


    @Query("""
        select count(1) from OppositionRequest opp
        join opp.applicationInfo ai
        where
        ai.id = :appId and opp.requestStatus.code in (:statusCodes)
    """)
    Integer getOtherOpenedOppositionsCount(@Param("appId") Long appId, @Param("statusCodes") List<String> statusCodes);
}
