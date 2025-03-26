package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.AgentSubstitutionRequestDto;
import gov.saip.applicationservice.common.dto.agency.AgentSubstitutionServiceDto;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;

import java.util.List;

public interface AgentSubstitutionRequestService extends SupportServiceRequestService<AgentSubstitutionRequest> {

    public List<AgentSubstitutionRequest> getAllByApplicationId(Long appId);

    AgentSubstitutionRequest getPendingRequestByApplicationId(Long appId);

    AgentSubstitutionRequest insertAddNewAgentRequest(AgentSubstitutionRequest agentSubstitutionRequest);
    AgentSubstitutionRequest insertSubstituteAgentRequest(AgentSubstitutionRequest agentSubstitutionRequest);
    AgentSubstitutionRequest insertSubstituteAgentRequestToAllAppsByAgentId(AgentSubstitutionRequest agentSubstitutionRequest, String customerCode);
    AgentSubstitutionRequest insertDeleteAgentRequest(AgentSubstitutionRequest agentSubstitutionRequest);

    AgentSubstitutionServiceDto getDetailsBySupportServiceId(Long serviceId);

    AgentSubstitutionServiceDto getDetailsByAgentSubstitutionId(Long agentSubstituteId);
}
