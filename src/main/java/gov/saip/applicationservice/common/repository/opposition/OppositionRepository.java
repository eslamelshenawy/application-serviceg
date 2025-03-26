package gov.saip.applicationservice.common.repository.opposition;

import gov.saip.applicationservice.common.enums.opposition.OppositionFinalDecision;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.opposition.Opposition;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OppositionRepository extends SupportServiceRequestRepository<Opposition> {

    @Modifying
    @Query("update Opposition opp set opp.hearingSession.isPaid = true where opp.id = :id")
    void updateComplainerHearingSessionPaymentStatus(@Param("id") Long id);

    @Modifying
    @Query("update Opposition opp set opp.applicantHearingSession.isPaid = true where opp.id = :id")
    void updateApplicantPaymentStatus(@Param("id") Long id);

    @Modifying
    @Query("update Opposition opp set opp.hearingSession.date = :date, opp.hearingSession.time = :time, opp.hearingSession.isHearingSessionScheduled = :isHearingSessionScheduled where opp.id = :id ")
    void updateComplainerHaringSessionDate(@Param("id") Long id, @Param("date")LocalDate date, @Param("time")String time, @Param("isHearingSessionScheduled") boolean isHearingSessionScheduled);

    @Modifying
    @Query("update Opposition opp set opp.applicantHearingSession.date = :date, opp.applicantHearingSession.time = :time, opp.applicantHearingSession.isHearingSessionScheduled = :isHearingSessionScheduled where opp.id = :id ")
    void updateApplicantHaringSessionDate(@Param("id") Long id, @Param("date")LocalDate date, @Param("time")String time, @Param("isHearingSessionScheduled") boolean isHearingSessionScheduled);

    @Modifying
    @Query("update Opposition opp set opp.hearingSession.result = :result, opp.hearingSession.fileURL = :fileURL where opp.id = :id ")
    void updateComplainerHaringSessionResult(@Param("id") Long id, @Param("result")String result, @Param("fileURL")String fileURL);

    @Modifying
    @Query("update Opposition opp set opp.applicantHearingSession.result = :result, opp.applicantHearingSession.fileURL = :fileURL where opp.id = :id ")
    void updateApplicantHaringSessionResult(@Param("id") Long id, @Param("result")String result, @Param("fileURL")String fileURL);


    @Modifying
    @Query("update Opposition opp set opp.applicantExaminerNotes = :applicantExaminerNotes where opp.id = :id ")
    void updateApplicantExaminerNotes(@Param("id") Long id, @Param("applicantExaminerNotes")String applicantExaminerNotes);

    @Modifying
    @Query("update Opposition opp set opp.finalDecision = :oppositionFinalDecision, opp.finalNotes = :finalNotes where opp.id = :id ")
    void examinerFinalDecision(@Param("id") Long id, @Param("oppositionFinalDecision") OppositionFinalDecision oppositionFinalDecision, @Param("finalNotes") String finalNotes);

    @Modifying
    @Query("update Opposition opp set opp.headExaminerNotesToExaminer = :headExaminerNotesToExaminer where opp.id = :id ")
    void headExaminerNotesToExaminer(@Param("id")Long id, @Param("headExaminerNotesToExaminer") String headExaminerNotesToExaminer);

    @Modifying
    @Query(value = "INSERT INTO application.opposition_documents (opposition_id, document_Id) VALUES(:oppositionId, :documentId)", nativeQuery = true)
    void addOppositionDocument(@Param("oppositionId")Long oppositionId, @Param("documentId") Long documentId);

    @Modifying
    @Query(value = "update Opposition set isHeadExaminerConfirmed = true where id = :id")
    void updateHeadExaminerConfirmation(@Param("id")Long id);

    @Query("select opp.finalDecision from Opposition opp where opp.id = :id")
    OppositionFinalDecision getFinalDecisionById(@Param("id") Long id);

    @Query("select count(1) from Opposition opp where opp.application = (select opp2.application from Opposition opp2 where opp2.id = :id) and (opp.isHeadExaminerConfirmed is null or opp.isHeadExaminerConfirmed = false) and opp.id != :id")
    Integer getOtherOpenedOppositionsCount(@Param("id") Long id);

    @Modifying
    @Query("update ApplicationInfo ai set ai.applicationStatus = :applicationStatus where ai.id = (select opp.application.id from Opposition opp where opp.id = :id)")
    void updateApplicationStatus(@Param("id") Long id, @Param("applicationStatus")LkApplicationStatus applicationStatus);

//    @Query("select oppo from Opposition oppo where oppo.applicationSupportServicesType.id = :serviceId")
//    Optional<Opposition> getDetailsBySupportServiceId(@Param("serviceId") Long serviceId);
}
