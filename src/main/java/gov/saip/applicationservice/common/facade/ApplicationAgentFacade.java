package gov.saip.applicationservice.common.facade;

import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;

import java.util.List;

public interface ApplicationAgentFacade {

    AgentSubstitutionRequest insertAddAgentRequest(AgentSubstitutionRequest entity);
    AgentSubstitutionRequest deleteAgentsByAgentAndCustomerCode(Long agentId, String customerCode);

    AgentSubstitutionRequest deleteAgentsByAppIds(List<Long> applications);


    AgentSubstitutionRequest insertSubstituteAgentRequest(AgentSubstitutionRequest agentSubstitutionRequest);
    AgentSubstitutionRequest insertSubstituteAgentRequestToAllAppsByAgentId(AgentSubstitutionRequest agentSubstitutionRequest, String customerCode, Long agentId);

    void processSubsituteAgentRequest(Long requestId);
    void startSupportServiceProcess(Long requestId, SupportServicePaymentStatus status, AgentSubstitutionRequest entity);
    void processAddAgentRequest(Long requestId);
    void processDeleteAgentRequest(Long requestId);
}
