package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.agency.AgentSubstitutionServiceDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.mapper.AgentSubstitutionRequestMapper;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.repository.AgentSubstitutionRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class AgentSubstitutionRequestServiceImpl extends SupportServiceRequestServiceImpl<AgentSubstitutionRequest> implements AgentSubstitutionRequestService {

    private final AgentSubstitutionRequestRepository agentSubstitutionRequestRepository;
    private final LKSupportServiceTypeService lkSupportServiceTypeService;
    private final CustomerServiceCaller customerServiceCaller;
    private final AgentSubstitutionRequestMapper agentSubstitutionRequestMapper;
    private final LKSupportServiceRequestStatusService lkSupportServiceRequestStatusService;
    private ApplicationInfoService applicationInfoService;

    @Autowired
    public void setApplicationInstallmentService(@Lazy ApplicationInfoService applicationInfoService) {
        this.applicationInfoService = applicationInfoService;
    }

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return agentSubstitutionRequestRepository;
    }

    @Autowired
    public void setApplicationInfoService(@Lazy ApplicationInfoService applicationInfoService) {
        this.applicationInfoService = applicationInfoService;
    }

    @Override
    public List<AgentSubstitutionRequest> getAllByApplicationId(Long appId) {
        return  agentSubstitutionRequestRepository.findByApplicationInfoIdAndLkSupportServicesCode(appId, SupportServiceType.AGENT_SUBSTITUTION);
    }
    @Override
    public AgentSubstitutionRequest insertAddNewAgentRequest(AgentSubstitutionRequest entity){
        entity.setLkSupportServiceType(lkSupportServiceTypeService.findByCode(SupportedServiceCode.ADD_AGENT));
        return insert(entity);
    }


    @Override
    public AgentSubstitutionRequest insertSubstituteAgentRequest(AgentSubstitutionRequest entity){
        entity.setLkSupportServiceType(lkSupportServiceTypeService.findByCode(SupportedServiceCode.SUBSTITUTE_AGENT));
        AgentSubstitutionRequest insert = insert(SupportServiceType.AGENT_SUBSTITUTION, entity);
        return insert;
    }

    @Override
    public AgentSubstitutionRequest insertSubstituteAgentRequestToAllAppsByAgentId(AgentSubstitutionRequest entity, String customerCode){
        List<ApplicationInfo> allAgentApplications = applicationInfoService.getAllApplicationsByCustomerCodeAndAgentId(customerCode, entity.getCustomerId());
        entity.setApplications(allAgentApplications);
        entity.setLkSupportServiceType(lkSupportServiceTypeService.findByCode(SupportedServiceCode.SUBSTITUTE_AGENT));
        AgentSubstitutionRequest agentSubstitutionRequest = insert(SupportServiceType.AGENT_SUBSTITUTION, entity);
        return agentSubstitutionRequest;
    }
    @Override
    public AgentSubstitutionRequest insertDeleteAgentRequest(AgentSubstitutionRequest entity) {
        entity.setLkSupportServiceType(lkSupportServiceTypeService.findByCode(SupportedServiceCode.DELETE_AGENT));
        return insert(entity);
    }

    @Override
    public AgentSubstitutionRequest update(AgentSubstitutionRequest entity){
        Optional<AgentSubstitutionRequest> agentSubstitutionRequest = agentSubstitutionRequestRepository.findById(entity.getId());
        LKSupportServiceRequestStatus newRequestStatus = lkSupportServiceRequestStatusService.getStatusByCodeAndNameEn(SupportServiceRequestStatusEnum.UNDER_PROCEDURE.name(), "Under Procedure");
        AgentSubstitutionRequest agent = agentSubstitutionRequest.orElseThrow(() -> new BusinessException("EXCEPTION_RECORD_NOT_FOUND"));
        agent.setApplications(entity.getApplications());
        agent.setApplicationInfo(entity.getApplications().get(0));
        agent.setCustomerId(entity.getCustomerId());
        agent.setRequestStatus(newRequestStatus);
        agent.setDelegationDocument(entity.getDelegationDocument());
        agent.setEvictionDocument(entity.getEvictionDocument());
        return super.update(agent);
    }

    @Override
    public AgentSubstitutionRequest getPendingRequestByApplicationId(Long appId) {
        return agentSubstitutionRequestRepository.getPendingRequestByApplicationId(appId, SupportServiceType.AGENT_SUBSTITUTION, SupportServicePaymentStatus.UNPAID);
    }

    @Override
    public AgentSubstitutionServiceDto getDetailsBySupportServiceId(Long serviceId) {
        Optional<AgentSubstitutionRequest> detailsBySupportServiceId = agentSubstitutionRequestRepository.findById(serviceId);
        AgentSubstitutionRequest agentSubstitutionRequestDto = detailsBySupportServiceId.orElseThrow(() -> new BusinessException("EXCEPTION_RECORD_NOT_FOUND"));
        AgentSubstitutionServiceDto agent = agentSubstitutionRequestMapper.mapToAgentSubstitutionServiceDto(agentSubstitutionRequestDto);
        CustomerSampleInfoDto anyCustomerDetails = customerServiceCaller.getAnyCustomerDetails(agentSubstitutionRequestDto.getCustomerId());
        agent.setAgent(anyCustomerDetails);
        List<ApplicationInfo> applications = agentSubstitutionRequestDto.getApplications();
        ApplicantsDto applicantsDto = applicationInfoService.listMainApplicant(applications.get(0).getId());
        agent.setApplicant(applicantsDto);
        return agent;
    }



    @Override
    public AgentSubstitutionServiceDto getDetailsByAgentSubstitutionId(Long agentSubstituteId){
        Optional<AgentSubstitutionRequest> detailsBySubstitutionId = agentSubstitutionRequestRepository.findById(agentSubstituteId);
        AgentSubstitutionRequest agentSubstitutionRequestDto = detailsBySubstitutionId.orElseThrow(() -> new BusinessException("EXCEPTION_RECORD_NOT_FOUND"));
        AgentSubstitutionServiceDto agent = agentSubstitutionRequestMapper.mapToAgentSubstitutionServiceDto(agentSubstitutionRequestDto);
        CustomerSampleInfoDto anyCustomerDetails = customerServiceCaller.getAnyCustomerDetails(agentSubstitutionRequestDto.getCustomerId());
        agent.setAgent(anyCustomerDetails);
        List<ApplicationInfo> applications = agentSubstitutionRequestDto.getApplications();
        ApplicantsDto applicantsDto = applicationInfoService.listMainApplicant(applications.get(0).getId());
        agent.setApplicant(applicantsDto);
        return agent;
    }

}
