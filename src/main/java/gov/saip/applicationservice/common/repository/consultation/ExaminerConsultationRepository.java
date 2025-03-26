package gov.saip.applicationservice.common.repository.consultation;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ExaminerConsultation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExaminerConsultationRepository extends BaseRepository<ExaminerConsultation, Long> {

    @Query("""
   SELECT cons
    FROM  ExaminerConsultation cons
   JOIN ApplicationInfo  app
    ON cons.application.id = app.id
   WHERE
   (:appId is null or cons.application = :appId)
   AND
   (:replayed is null or cons.replayed = :replayed)
   AND
   (:senderUserName is null or cons.userNameSender = :senderUserName)
   AND
   (:receiverUserName is null or cons.userNameReceiver = :receiverUserName)


""")
    Page<ExaminerConsultation> findConsultations(Pageable page,
                                                 @Param("appId") Long appId,
                                                 @Param("replayed") Boolean replayed
                                              , @Param("senderUserName") String senderUserName
                                              , @Param("receiverUserName") String receiverUserName);



  Optional<List<ExaminerConsultation>>  findByApplicationId(Long appId);
}
