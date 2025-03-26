package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.model.OppositionRevokeLicenceRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OppositionRevokeLicenceRequestRepository extends SupportServiceRequestRepository<OppositionRevokeLicenceRequest>{
    @Query(" SELECT r.id FROM OppositionRevokeLicenceRequest r WHERE r.requestStatus.code = 'UNDER_PROCEDURE' AND r.revokeLicenceRequest.id = :revokeLicenceRequestId")
    Long getUnderProcedureOppositionIdByRevokeLicenceRequest(@Param("revokeLicenceRequestId") Long revokeLicenceRequestId);

    @Query(" SELECT r.revokeLicenceRequest.id FROM OppositionRevokeLicenceRequest r WHERE r.id = :id")
    Long getRevokeLicenceRequestIdByOppositionId(@Param("id") Long id);

    @Query(" SELECT r FROM OppositionRevokeLicenceRequest r WHERE r.requestStatus.code = 'UNDER_PROCEDURE' AND r.revokeLicenceRequest.id = :revokeLicenceRequestId")
    OppositionRevokeLicenceRequest getUnderProcedureOppositionRevokeLicenceRequest(@Param("revokeLicenceRequestId") Long revokeLicenceRequestId);

    @Query(" SELECT r FROM OppositionRevokeLicenceRequest r WHERE  r.revokeLicenceRequest.id = :revokeLicenceRequestId")
    OppositionRevokeLicenceRequest getOppositionRevokeLicenceRequestByRevokeLicenceId(@Param("revokeLicenceRequestId") Long revokeLicenceRequestId);

    @Query(" select case when (count(r) > 0)  then true else false end FROM OppositionRevokeLicenceRequest r WHERE r.requestStatus.code = 'UNDER_PROCEDURE' AND r.revokeLicenceRequest.id = :revokeLicenceRequestId")
    boolean revokeLicenceRequestHasUnderProcedureOpposition(@Param("revokeLicenceRequestId") Long revokeLicenceRequestId);

    @Query("""
           select case when (count(req) > 0)  then true else false end from OppositionRevokeLicenceRequest req where req.revokeLicenceRequest.id = :revokeLicenseRequestId 
           AND req.requestStatus.code in :supportServiceRequestStatuses 
           """)
    boolean checkRevokeLicenseRequestHasUnderProcedureOppositionRevokeLicenseRequest(@Param("revokeLicenseRequestId") Long revokeLicenseRequestId, @Param("supportServiceRequestStatuses") List<String> supportServiceRequestStatuses);

    @Query(" SELECT r.requestNumber FROM OppositionRevokeLicenceRequest r WHERE r.requestStatus.code = 'UNDER_PROCEDURE' AND r.revokeLicenceRequest.id = :revokeLicenceRequestId")
    String getUnderProcedureOppositionRevokeLicenceRequestNumber(@Param("revokeLicenceRequestId") Long revokeLicenceRequestId);


    @Query(" SELECT r FROM OppositionRevokeLicenceRequest r WHERE r.id = :revokeLicenceRequestId or r.revokeLicenceRequest.id = :revokeLicenceRequestId")
    OppositionRevokeLicenceRequest getOppositionRevokeLicenceRequestByIdOrRevokeLicenceId(@Param("revokeLicenceRequestId") Long revokeLicenceRequestId);
}
