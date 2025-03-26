package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.ApplicationInfoDto;
import gov.saip.applicationservice.common.model.PetitionRecoveryRequest;

public interface PetitionRecoveryRequestService extends SupportServiceRequestService<PetitionRecoveryRequest> {
    ApplicationInfoDto getMainApplicationInfo(Long applicationId);

}
