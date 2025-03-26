package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.EvictionRequestDto;
import gov.saip.applicationservice.common.model.EvictionRequest;

public interface EvictionRequestService extends SupportServiceRequestService<EvictionRequest> {
    EvictionRequest createEvictionRequest(EvictionRequestDto evictionRequestDto);
}
