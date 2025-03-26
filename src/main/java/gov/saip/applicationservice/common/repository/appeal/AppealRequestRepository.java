package gov.saip.applicationservice.common.repository.appeal;

import gov.saip.applicationservice.common.enums.appeal.AppealCheckerDecision;
import gov.saip.applicationservice.common.enums.appeal.AppealCommitteeDecision;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.appeal.AppealRequest;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.util.DocumentsTypesConstants;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppealRequestRepository extends SupportServiceRequestRepository<AppealRequest> {

    @Modifying
    @Query("""
        update AppealRequest app
        set app.checkerDecision = :checkerDecision,
        app.checkerFinalNotes = :checkerFinalNotes,
        app.requestStatus = :status
        where app.id = :id
    """)
    void addCheckerDecision(@Param("id")Long id, @Param("checkerDecision")AppealCheckerDecision checkerDecision,
                            @Param("checkerFinalNotes")String checkerFinalNotes, @Param("status") LKSupportServiceRequestStatus status);

    @Modifying
    @Query(value = "INSERT INTO application.appeal_request_documents (appeal_request_id, document_id) VALUES(:id, :documentId)", nativeQuery = true)
    void addAppealDocument(@Param("id")Long id, @Param("documentId")Long documentId);

    @Modifying
    @Query("update AppealRequest app set app.officialLetterComment = :officialLetterComment where app.id = :id")
    void addOfficialLetter(@Param("id")Long id, @Param("officialLetterComment")String officialLetterComment);

    @Modifying
    @Query("""
    update AppealRequest app set app.appealCommitteeDecision = :appealCommitteeDecision,
     app.appealCommitteeDecisionComment = :appealCommitteeDecisionComment,
     app.requestStatus.id = :statusId where app.id = :id
     """)
    void addAppealCommitteeDecision(@Param("id")Long id,
                                    @Param("appealCommitteeDecision")AppealCommitteeDecision appealCommitteeDecision,
                                    @Param("appealCommitteeDecisionComment") String appealCommitteeDecisionComment,
                                    @Param("statusId")Integer statusId);


    @Query("SELECT DISTINCT docs.id FROM AppealRequest appeal JOIN appeal.documents docs JOIN docs.documentType docType "
            + "WHERE docType.code IN ('" + DocumentsTypesConstants.appealDocumentStatement + "', '" + DocumentsTypesConstants.appealDocumentPoa + "') "
            + "AND appeal.id = :id")
    List<Document> getRequiredModifiedDocuments(@Param("id")Long id);

}
