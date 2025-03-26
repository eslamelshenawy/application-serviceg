package gov.saip.applicationservice.common.service.agency;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ChangeAgentReportDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.customer.AgentListDto;
import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.enums.ApplicationAgentStatus;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;

import java.util.List;
import java.util.Map;

public interface ApplicationAgentService extends BaseService<ApplicationAgent, Long> {

    Long getCurrentApplicationAgent(Long appId);
    ApplicationAgent getCurrentApplicationAgentEntity(Long appId);
    List<Long> getAllApplicationAgents(Long appId);
    public List<Long> getAllCustomerAgentsByCustomerCode(String customerCode);

    List<Long> getAllAgentApplications(Long agentId, String customerCode);

    Map<Long, Long> getCustomerAgentsAndCounts(String customerCode);

    void deleteAgentsByAgentAndUserIds(Long agentId, String customerCode);

    void deleteAgentsByAppIds(List<Long> applications);

    void deActivateApplicationAgentsByAgentAndApps(List<Long> appIds, List<Long> newApplicationAgentIds);

    ApplicationAgentSummaryDto getCurrentApplicationAgentSummary(Long applicationId);

    void assignCreatedApplicationToAgent(ApplicationAgent applicationAgent);
    void deleteApplicationAgent(ApplicationAgent agent);

    PaginationDto<List<AgentListDto>> getCurrentAgentsByCustomerCode(String customerCode, String name, Integer page, Integer limit);

    List<ChangeAgentReportDto> findLatestChangedAgents();

    ApplicationAgent findActiveAgentsByApplicationId(Long applicationId);
    
    List<ApplicationAgent> getApplicationsCustomersIdsByAppIdsAndStatus(List<Long> appIds, ApplicationAgentStatus applicationAgentStatus);
}
