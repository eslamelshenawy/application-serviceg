package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.PctIValidateDto;
import gov.saip.applicationservice.common.model.PetitionRequestNationalStage;

public interface PetitionRequestNationalStageService extends SupportServiceRequestService<PetitionRequestNationalStage>{



    boolean checkPetitionRequestNumberToUseInPct(String requestNumber);
    PctIValidateDto getPctForPetitionRequestNumber(String requestNumber);

    Long getPetitionRequestNationalStageSupportServicesProcessRequestId(Long applicationId);
}
