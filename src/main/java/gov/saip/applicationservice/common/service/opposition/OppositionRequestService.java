package gov.saip.applicationservice.common.service.opposition;

import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.opposition.OppositionDetailsProjection;
import gov.saip.applicationservice.common.dto.ApplicationInfoDto;
import gov.saip.applicationservice.common.dto.opposition.OppositionRequestDto;
import gov.saip.applicationservice.common.model.opposition.OppositionRequest;
import gov.saip.applicationservice.common.service.SupportServiceRequestService;

import java.time.LocalDate;
import java.util.List;

public interface OppositionRequestService extends SupportServiceRequestService<OppositionRequest> {

    Long updateComplainerOpposition(OppositionRequest oppositionRequest);

    OppositionRequestDto getRequestById(Long id);

    Long addApplicationOwnerReply(OppositionRequest oppositionRequest);

    Long updateComplainerHearingSession(OppositionRequest oppositionRequest);

    Long updateApplicationOwnerHearingSession(OppositionRequest oppositionRequest);
    PaginationDto<List<OppositionDetailsProjection>> getRequestsDetails(String requestNumber, LocalDate sessionDate, LocalDate from, LocalDate to, Integer limit, Integer page);

    Long addComplainerHearingSessionResult(OppositionRequest oppositionRequest);

    Long addApplicationOwnerHearingSessionResult(OppositionRequest oppositionRequest);

    LocalDate getMaxDateOfOpposition(Long applicationId);
    void changeOppositionRequestStatusAfterFinalDecision(Long id);
}
