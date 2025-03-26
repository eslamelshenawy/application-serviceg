package gov.saip.applicationservice.common.service.agency.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ChangeAgentReportDto;
import gov.saip.applicationservice.common.dto.KeyValueDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.customer.AgentListDto;
import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.enums.ApplicationAgentStatus;
import gov.saip.applicationservice.common.enums.ApplicationStatusPreconditions;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import gov.saip.applicationservice.common.repository.agency.ApplicationAgentRepository;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.impl.ApplicationAgentFacadeService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationAgentServiceImpl extends BaseServiceImpl<ApplicationAgent, Long> implements ApplicationAgentService {

    private final ApplicationAgentRepository applicationAgentRepository;
    private final CustomerServiceCaller customerServiceCaller;
    private final ApplicationAgentFacadeService applicationAgentFacadeService;
    @Override
    protected BaseRepository<ApplicationAgent, Long> getRepository() {
        return applicationAgentRepository;
    }
    @Override
    public Long getCurrentApplicationAgent(Long appId) {
        return applicationAgentRepository.getAgentIdByAppIdAndStatus(appId, ApplicationAgentStatus.ACTIVE);
    }

    @Override
    public List<Long> getAllApplicationAgents(Long appId) {
        return applicationAgentRepository.getAllApplicationAgentsByAppId(appId);
    }

    @Override
    public List<Long> getAllCustomerAgentsByCustomerCode(String customerCode) {
        return applicationAgentRepository.getAllCustomerAgentsByCustomerCode(customerCode);
    }

    public List<Long> getAllAgentApplications(Long agentId, String customerCode) {
        return applicationAgentRepository.getApplicationIdsByAgentAndUserIds(agentId, customerCode, ApplicationAgentStatus.ACTIVE);
    }


    @Override
    public void deActivateApplicationAgentsByAgentAndApps(List<Long> appIds, List<Long> newApplicationAgentIds) {
        applicationAgentRepository.deActivateApplicationAgentsByAgentAndApps(appIds, newApplicationAgentIds, ApplicationAgentStatus.CHANGED, ApplicationAgentStatus.ACTIVE);
    }


    @Override
    public ApplicationAgent insert(ApplicationAgent entity) {
        insertValidation(entity);
        entity.setStatus(ApplicationAgentStatus.ACTIVE);
        return super.insert(entity);
    }

    private void insertValidation(ApplicationAgent entity) {
        if (entity.getApplication() == null || entity.getApplication().getId() == null || entity.getCustomerId() == null)
            throw new BusinessException(Constants.ErrorKeys.AGENT_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, null);

        boolean agentExists = applicationAgentRepository.existsByApplicationIdAndCustomerId(entity.getApplication().getId(), entity.getCustomerId());
        if (agentExists)
            throw new BusinessException(Constants.ErrorKeys.AGENT_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, null);


    }

    @Override
    public Map<Long, Long> getCustomerAgentsAndCounts(String customerCode) { // TOTOTO
        List<KeyValueDto<Long, Long>> data = applicationAgentRepository.getCustomerAgentsAndCounts(customerCode, ApplicationAgentStatus.ACTIVE, ApplicationStatusPreconditions.AGENT_PRE_CONDITIONS.getStatusList());
        Map<Long, Long> map = data.stream().collect(Collectors.toMap(KeyValueDto::getKey, KeyValueDto::getValue));
        return map;
    }

    @Override
    public void deleteAgentsByAgentAndUserIds(Long agentId, String customerCode) {
        List<Long> ids = applicationAgentRepository.getApplicationAgentIdsByAgentAndUserIdAndStatus(agentId, customerCode, ApplicationAgentStatus.ACTIVE);
        applicationAgentRepository.delteAgentsByIds(ids, ApplicationAgentStatus.DELETED, ApplicationAgentStatus.ACTIVE);
    }

    @Override
    public void deleteAgentsByAppIds(List<Long> ids) {
        applicationAgentRepository.deleteAgentsByAppIds(ids, ApplicationAgentStatus.DELETED, ApplicationAgentStatus.ACTIVE);
    }

    @Override
    public ApplicationAgentSummaryDto getCurrentApplicationAgentSummary(Long applicationId) {
        return applicationAgentFacadeService.getApplicationCurrentAgentSummary(applicationId);
    }

    @Override
    @Transactional
    public void assignCreatedApplicationToAgent(ApplicationAgent applicationAgent) {
        applicationAgentRepository.save(applicationAgent);
    }

    @Override
    public ApplicationAgent getCurrentApplicationAgentEntity(Long appId) {
        return applicationAgentRepository.getCurrentActiveApplicationAgent(appId, ApplicationAgentStatus.ACTIVE);
    }

    @Override
    @Transactional
    public void deleteApplicationAgent(ApplicationAgent agent) {
        applicationAgentRepository.delete(agent);
    }

    @Override
    public PaginationDto<List<AgentListDto>> getCurrentAgentsByCustomerCode(String customerCode, String name, Integer page, Integer limit) {
        Map<Long, Long> customerAgentsAndCounts = getCustomerAgentsAndCounts(customerCode);
        PaginationDto<List<AgentListDto>> agentsPage = customerServiceCaller.getCurrentAgentsByCustomerCode(customerAgentsAndCounts.keySet().stream().toList(), name, page, limit);
        agentsPage.getContent().forEach(agent -> agent.setCount(customerAgentsAndCounts.get(agent.getId())));
        return agentsPage;
    }

    @Override
    public List<ChangeAgentReportDto> findLatestChangedAgents() {
        return applicationAgentRepository.findChangedAgentsWithApplicationNumber();
    }

    public ApplicationAgent findActiveAgentsByApplicationId(Long applicationId) {
        return applicationAgentRepository.findActiveAgentsByApplicationId(applicationId);
    }
    
    public List<ApplicationAgent> getApplicationsCustomersIdsByAppIdsAndStatus(List<Long> appIds, ApplicationAgentStatus status){
        return applicationAgentRepository.getApplicationsCustomersIdsByAppIdsAndStatus(appIds, status);
    }
    
}
