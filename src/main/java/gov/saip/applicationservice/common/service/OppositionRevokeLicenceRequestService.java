package gov.saip.applicationservice.common.service;


import gov.saip.applicationservice.common.dto.OppositionRevokeLicenceCourtDocumentsDto;
import gov.saip.applicationservice.common.dto.OppositionRevokeLicenceRequestApplicationSummaryDto;
import gov.saip.applicationservice.common.dto.OppositionRevokeLicenceRequestDto;
import gov.saip.applicationservice.common.model.OppositionRevokeLicenceRequest;

public interface OppositionRevokeLicenceRequestService extends SupportServiceRequestService<OppositionRevokeLicenceRequest> {

     Long getUnderProcedureOppositionIdByRevokeLicenceRequest(Long revokeLicenceRequestId);

    OppositionRevokeLicenceRequestDto findByServiceId(Long id);

    OppositionRevokeLicenceRequestApplicationSummaryDto getApplicationSummaryByOppositionRequestLicenseId(Long id);

    void withdrawOppositionRevokeLicenseRequest(Long id);

    boolean revokeLicenceRequestHasUnderProcedureOpposition(Long revokeLicenseId);

    OppositionRevokeLicenceRequestDto getUnderProcedureOppositionRevokeLicenceRequestByRevokeLicenseId(Long revokeLicenseId);
    String getUnderProcedureOppositionRevokeLicenceRequestNumberByRevokeLicenseId(Long revokeLicenseId);

    boolean checkRevokeLicenseRequestHasUnderProcedureOppositionRevokeLicenseRequest(Long revokeLicenseRequestId);

    void updateOppositionRevokeLicenseRequestWithCourtDocuments(OppositionRevokeLicenceCourtDocumentsDto oppositionRevokeLicenceCourtDocumentsDto);
}
