package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.RetractionTypesDto;
import gov.saip.applicationservice.common.model.RetractionRequest;

public interface RetractionRequestService extends SupportServiceRequestService<RetractionRequest> {

    public void paymentCallBackHandlerPriority(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto);
}
