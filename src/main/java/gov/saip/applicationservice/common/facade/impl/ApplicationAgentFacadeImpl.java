package gov.saip.applicationservice.common.facade.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.facade.ApplicationAgentFacade;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum.AGENT_DELETION_APPROVED;
import static gov.saip.applicationservice.common.enums.SupportedServiceCode.DELETE_AGENT;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApplicationAgentFacadeImpl implements ApplicationAgentFacade {

    private final ApplicationAgentService applicationAgentService;
    private final AgentSubstitutionRequestService agentSubstitutionRequestService;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final LKSupportServiceTypeService lkSupportServiceTypeService;
    private final ApplicationInfoService applicationInfoService;
    private final SupportServiceProcess supportServiceProcess;
    private final ApplicationCustomerService applicationCustomerService;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final CustomerServiceCaller customerServiceCaller;
    private final LKSupportServiceRequestStatusService lkSupportServiceRequestStatusService;
    private final BPMCallerFeignClient bPMCallerFeignClient;


    @Override
    public AgentSubstitutionRequest
    insertAddAgentRequest(final AgentSubstitutionRequest entity) {
        log.info("add agent request ");
        Long startTime = System.currentTimeMillis();
        LKSupportServiceType lkSupportServiceType = lkSupportServiceTypeService.findByCode(SupportedServiceCode.ADD_AGENT);
        Long endTime = System.currentTimeMillis();
        log.info("Call to getLKSupportServiceType took {} ms", (endTime - startTime));
        if (lkSupportServiceType.getIsFree() ||
                getServiceCost(entity, ApplicationPaymentMainRequestTypesEnum.ADD_AGENT_REQUEST) == 0) {
            log.info("add agent request is free so it will be processing immediately");
//            addNewApplicationAgentByAgentRequest(entity);
            entity.setPaymentStatus(SupportServicePaymentStatus.FREE);
        }
        Long startTime1 = System.currentTimeMillis();
        entity.setLkSupportServiceType(lkSupportServiceType);
        insertApplicationSupportServiceType(entity);
        Long endTime1 = System.currentTimeMillis();
        log.info("Call to setApplicationInfo took {} ms", (endTime1 - startTime1));
//        agentSubstitutionRequestService.insert(entity);

        Long startTime2 = System.currentTimeMillis();
        if (lkSupportServiceType.getIsFree()
                || getServiceCost(entity, ApplicationPaymentMainRequestTypesEnum.ADD_AGENT_REQUEST) == 0) {
            startSupportServiceProcess(entity.getId(), null, entity);
        }
        Long endTime2 = System.currentTimeMillis();
        log.info("Call to startProcess took {} ms", (endTime2 - startTime2));

        return  entity;
    }

    private Double getServiceCost(AgentSubstitutionRequest agentSubstitutionRequest, ApplicationPaymentMainRequestTypesEnum applicationPaymentMainRequestType) {
        List<Long> applicationIds = agentSubstitutionRequest.getApplications().stream()
                .map(ApplicationInfo::getId) // Extract IDs from each Application
                .filter(Objects::nonNull) // Optional: Filter out null IDs
                .collect(Collectors.toList());
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceCaller.getCustomerInfoFromRequest();
        ApplicationInfoBaseDto applicationInfoBaseDto = applicationInfoService.getAppBasicInfoIds(applicationIds);
        return applicationInfoService.calculateServiceCost(customerSampleInfoDto, applicationPaymentMainRequestType,
                ApplicationCategoryEnum.valueOf(applicationInfoBaseDto.getApplicationCategory().getSaipCode()));
    }

    private void addNewApplicationAgentByAgentRequest(AgentSubstitutionRequest entity) {
        List<ApplicationAgent> agents = transformAgentRequestToApplicationAgent(entity);
        applicationAgentService.saveAll(agents);
    }

    private static List<ApplicationAgent> transformAgentRequestToApplicationAgent(AgentSubstitutionRequest entity) {
        List<ApplicationAgent> agents = entity.getApplications().stream().map(app -> {
            ApplicationAgent agent = new ApplicationAgent();
            agent.setCustomerId(entity.getCustomerId());
            agent.setApplication(app);
            agent.setApplicationAgentDocuments(Arrays.asList(entity.getDelegationDocument(), entity.getEvictionDocument()));
            return agent;
        }).collect(Collectors.toList());
        return agents;
    }

    @Override
    public AgentSubstitutionRequest insertSubstituteAgentRequest(AgentSubstitutionRequest entity){

        if (Objects.isNull(entity.getApplications()) || entity.getApplications().isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.AGENT_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, null);
        }
        return substituteApplicationAgentByAgentSubstitutionRequest(entity);
    }

    @Override
    public AgentSubstitutionRequest insertSubstituteAgentRequestToAllAppsByAgentId(AgentSubstitutionRequest entity, String customerCode, Long agentId){
        List<ApplicationInfo> allAgentApplications = applicationInfoService.getAllApplicationsByCustomerCodeAndAgentId(customerCode, agentId);
        if (Objects.isNull(allAgentApplications) || allAgentApplications.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.AGENT_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, null);
        }

        entity.setApplications(allAgentApplications);
        return substituteApplicationAgentByAgentSubstitutionRequest(entity);
    }


    public AgentSubstitutionRequest substituteApplicationAgentByAgentSubstitutionRequest(AgentSubstitutionRequest entity) {
        LKSupportServiceType lkSupportServiceType = lkSupportServiceTypeService.findByCode(SupportedServiceCode.SUBSTITUTE_AGENT);

        if (lkSupportServiceType.getIsFree() ||
                getServiceCost(entity, ApplicationPaymentMainRequestTypesEnum.CHANGE_AGENT_REQUEST) == 0) {
            log.info("change agent request is free so it will be processing immediately");
            entity.setPaymentStatus(SupportServicePaymentStatus.FREE);
        }

        entity.setLkSupportServiceType(lkSupportServiceType);
        insertApplicationSupportServiceType(entity);
//        agentSubstitutionRequestService.insert(entity);

        if (lkSupportServiceType.getIsFree() ||
                getServiceCost(entity, ApplicationPaymentMainRequestTypesEnum.CHANGE_AGENT_REQUEST) == 0) {
            startSupportServiceProcess(entity.getId(), null, entity);
        }

        return  entity;
    }

    private void processSubstituteAgentRequest(AgentSubstitutionRequest entity) {
        List<ApplicationAgent> agents = transformAgentRequestToApplicationAgent(entity);
        List<ApplicationAgent> newApplicationAgens = applicationAgentService.saveAll(agents);

        List<Long> newApplicationAgenIds = newApplicationAgens.stream().map(ApplicationAgent::getId).toList();
        List<Long> appIds = mapApplicationListToIds(entity.getApplications());

        applicationAgentService.deActivateApplicationAgentsByAgentAndApps(appIds, newApplicationAgenIds);
    }


    @Override
    public AgentSubstitutionRequest deleteAgentsByAgentAndCustomerCode(Long agentId, String customerCode) {
        AgentSubstitutionRequest request = createAgentSubstitutionRequestByAgentAndCustomerCode(agentId, customerCode);

        insertApplicationSupportServiceType(request);
        processDeleteAgentRequest(request.getId());

        return request;
    }

    private AgentSubstitutionRequest createAgentSubstitutionRequestByAgentAndCustomerCode(Long agentId, String customerCode) {
        AgentSubstitutionRequest request = new AgentSubstitutionRequest();

        request.setCustomerId(agentId);
        request.setPaymentStatus(SupportServicePaymentStatus.FREE);

        LKSupportServiceType serviceType = getServiceType(DELETE_AGENT);
        LKSupportServiceRequestStatus lkSupportServiceRequestStatus = lkSupportServiceRequestStatusService.findByCode(AGENT_DELETION_APPROVED.name());
        request.setLkSupportServiceType(serviceType);
        request.setRequestStatus(lkSupportServiceRequestStatus);

        List<ApplicationInfo> applicationInfos = getApplicationInfosByAgentAndCustomer(agentId, customerCode);
        request.setApplications(applicationInfos);

        return request;
    }

    private List<ApplicationInfo> getApplicationInfosByAgentAndCustomer(Long agentId, String customerCode) {
        List<Long> appIds = applicationAgentService.getAllAgentApplications(agentId, customerCode);
        return appIds.stream()
                .map(ApplicationInfo::new)
                .collect(Collectors.toList());
    }

    @Override
    public AgentSubstitutionRequest deleteAgentsByAppIds(List<Long> applicationIds) {
        AgentSubstitutionRequest request = createAgentSubstitutionRequest(applicationIds);

        insertApplicationSupportServiceType(request);
        processDeleteAgentRequest(request.getId());

        return request;
    }

    private AgentSubstitutionRequest createAgentSubstitutionRequest(List<Long> applicationIds) {
        AgentSubstitutionRequest request = new AgentSubstitutionRequest();

        LKSupportServiceType serviceType = getServiceType(DELETE_AGENT);
        LKSupportServiceRequestStatus lkSupportServiceRequestStatus = lkSupportServiceRequestStatusService.findByCode(AGENT_DELETION_APPROVED.name());
        request.setLkSupportServiceType(serviceType);
        request.setPaymentStatus(SupportServicePaymentStatus.FREE);
        request.setRequestStatus(lkSupportServiceRequestStatus);

        Long agentId = getCurrentAgentForApplication(applicationIds.get(0));
        request.setCustomerId(agentId);

        List<ApplicationInfo> applicationInfos = convertToApplicationInfoList(applicationIds);
        request.setApplications(applicationInfos);

        return request;
    }

    private LKSupportServiceType getServiceType(SupportedServiceCode code) {
        return lkSupportServiceTypeService.findByCode(code);
    }

    private Long getCurrentAgentForApplication(Long applicationId) {
        return applicationAgentService.getCurrentApplicationAgent(applicationId);
    }

    private List<ApplicationInfo> convertToApplicationInfoList(List<Long> applicationIds) {
        return applicationIds.stream()
                .map(ApplicationInfo::new)
                .collect(Collectors.toList());
    }


    private void insertApplicationSupportServiceType(AgentSubstitutionRequest request) {
        request.setApplicationInfo(request.getApplications().get(0));
        agentSubstitutionRequestService.insert(SupportServiceType.AGENT_SUBSTITUTION, request);
    }



    // payment call back


    @Override
    public void startSupportServiceProcess(Long requestId, SupportServicePaymentStatus status, AgentSubstitutionRequest entity) {
        log.info("start change agent process " + requestId);

        if (entity == null) {
            Optional<AgentSubstitutionRequest> request = agentSubstitutionRequestService.getById(requestId);
            entity = request.orElseThrow(() -> new BusinessException("EXCEPTION_RECORD_NOT_FOUND"));
        }

        if (status != null) {// service is free so no need for this update
            agentSubstitutionRequestService.updatePaymentStatusAndRequestStatus(SupportServicePaymentStatus.PAID, SupportServiceRequestStatusEnum.UNDER_PROCEDURE, requestId);
        }


        // start process
        Long appId = entity.getApplications().get(0).getId();
        String applicationCategory = applicationInfoService.getApplicationCategoryById(appId);
        long startTime = System.currentTimeMillis();
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(entity.getCreatedByCustomerCode()).getPayload();
        long endTime = System.currentTimeMillis();
        log.info("Call to getApplicantInfo took {} ms", (endTime - startTime));
        StartProcessDto processDto = StartProcessDto.builder()
                .id(String.valueOf(entity.getId()))
                .fullNameAr(customerSampleInfoDto.getNameAr())
                .fullNameEn(customerSampleInfoDto.getNameEn())
                .email(customerSampleInfoDto.getEmail())
                .mobile(customerSampleInfoDto.getMobile())
                .applicantCustomerCode(customerSampleInfoDto.getCode())
                .requestType("AGENT_SUBSTITUTION_REQUEST")
                .requestTypeCode("AGENT_SUBSTITUTION_REQUEST")
                .identifier(String.valueOf(entity.getId()))
                .applicationCategory(applicationCategory) // for first app only
                .applicationIdColumn(String.valueOf(appId))
                .processName("agent_substitution_request_process")
                .applicantUserName(entity.getCreatedByUser())
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .supportServiceTypeCode(entity.getLkSupportServiceType().getCode().name())
                .requestNumber(entity.getRequestNumber())
                .build();

        supportServiceProcess.starSupportServiceProcess(processDto);
    }

    @Override
    @Transactional
    public void processSubsituteAgentRequest(Long requestId) {
        log.info("Processing agent substitution request with ID: {}", requestId);

        AgentSubstitutionRequest agentRequest = fetchAgentSubstitutionRequest(requestId);

        processSubstituteAgentRequest(agentRequest);
        updateApplicationData(agentRequest);
        assignTasksToNewCustomer(agentRequest, agentRequest.getCustomerId(), SupportedServiceCode.SUBSTITUTE_AGENT);

    }

    private AgentSubstitutionRequest fetchAgentSubstitutionRequest(Long requestId) {
        return agentSubstitutionRequestService.getById(requestId)
                .orElseThrow(() -> new BusinessException("EXCEPTION_RECORD_NOT_FOUND"));
    }

    private void updateApplicationData(AgentSubstitutionRequest agentRequest) {
        deleteApplicationAgents(agentRequest.getApplications());
        addApplicationCustomers(agentRequest.getCustomerId(), agentRequest.getApplications());
    }

    private void assignTasksToNewCustomer(AgentSubstitutionRequest agentRequest, Long customerId, SupportedServiceCode serviceCode) {
        agentRequest.getApplications().forEach(applicationInfo ->
                processApplication(applicationInfo, customerId, serviceCode)
        );
    }

    private void processApplication(ApplicationInfo applicationInfo, Long customerId, SupportedServiceCode serviceCode) {
        ApplicationInfo application = getApplicationInfo(applicationInfo.getId());
        RequestTasksDto requestTasksDto = getTaskByApplication(application);

        if (requestTasksDto != null) {
            String customerCode = determineCustomerCode(application, customerId, serviceCode);
            assignTaskToCustomer(requestTasksDto.getTaskId(), customerCode, application);
        }
    }

    private String determineCustomerCode(ApplicationInfo application, Long customerId, SupportedServiceCode serviceCode) {
        if (DELETE_AGENT.equals(serviceCode)) {
            return getCustomerCode(application.getCreatedByCustomerId());
        }
        return getCustomerCode(customerId);
    }


    private String getCustomerCode(Long customerId) {
        return customerServiceCaller.getCustomerCodeByCustomerId(customerId);
    }

    private ApplicationInfo getApplicationInfo(Long applicationId) {
        return applicationInfoService.findById(applicationId);
    }

    private RequestTasksDto getTaskByApplication(ApplicationInfo applicationInfo) {
        return bPMCallerFeignClient.getTaskByRowId(applicationInfo.getId(), applicationInfo.getCategory().getSaipCode());
    }

    private void assignTaskToCustomer(String taskId, String customerCode, ApplicationInfo applicationInfo) {
        AssigneeRequest assigneeRequest = new AssigneeRequest();
        assigneeRequest.setUserId(customerCode);
        assigneeRequest.setRequestType(applicationInfo.getCategory().getSaipCode());
        assigneeRequest.setAppId(applicationInfo.getId());
        bPMCallerFeignClient.assigneeTaskToUser(taskId, assigneeRequest);
    }

    @Override
    @Transactional
    public void processAddAgentRequest(Long requestId) {
        log.info("add agent has been approved, the number of request is ==>  " + requestId);
        Optional<AgentSubstitutionRequest> request = agentSubstitutionRequestService.getById(requestId);
        AgentSubstitutionRequest agentRequest = request.orElseThrow(() -> new BusinessException("EXCEPTION_RECORD_NOT_FOUND"));
        addNewApplicationAgentByAgentRequest(agentRequest);
        addApplicationCustomers(agentRequest.getCustomerId(), agentRequest.getApplications());
//        agentSubstitutionRequestService.updateRequestStatusByCode(requestId, SupportServiceRequestStatusEnum.APPROVED);
        applicationInfoService.updateApplicationByHimSelfAfterAddingAgent(agentRequest.getApplications().stream().map(agent->agent.getId()).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public void processDeleteAgentRequest(Long requestId) {
        log.info("delete agent has been approved, the number of request is ==>  " + requestId);
        Optional<AgentSubstitutionRequest> request = agentSubstitutionRequestService.getById(requestId);
        AgentSubstitutionRequest agentRequest = request.orElseThrow(() -> new BusinessException("EXCEPTION_RECORD_NOT_FOUND"));
        applicationAgentService.deleteAgentsByAppIds(mapApplicationListToIds(agentRequest.getApplications()));
        deleteApplicationAgents(agentRequest.getApplications());
        agentSubstitutionRequestService.updateRequestStatusByCode(requestId, AGENT_DELETION_APPROVED);
        assignTasksToNewCustomer(agentRequest, Utilities.getCustomerIdFromHeadersAsLong(), DELETE_AGENT);
    }

    private void deleteApplicationAgents(List<ApplicationInfo> applications) {
        applicationCustomerService.deleteApplicationCustomersByTypeAndAppIds(ApplicationCustomerType.AGENT, mapApplicationListToIds(applications));
    }

    private static List<Long> mapApplicationListToIds(List<ApplicationInfo> applications) {
        return applications.stream().map(ApplicationInfo::getId).toList();
    }

    private void addApplicationCustomers(final Long customerId, List<ApplicationInfo> applications) {
        String customerCode = getCustomerCodeById(customerId);
        List<ApplicationCustomer> applicationCustomers = mapCustomerAndApplicationsToApplicationCustomer(customerId, applications, customerCode);
        applicationCustomerService.saveAll(applicationCustomers);
    }

    private static List<ApplicationCustomer> mapCustomerAndApplicationsToApplicationCustomer(final Long customerId, List<ApplicationInfo> applications, final String customerCode) {
        return applications.stream()
                .map(app -> mapApplicationAndCustomerToApplicationCustomer(customerId, app, customerCode))
                .toList();
    }

    private static ApplicationCustomer mapApplicationAndCustomerToApplicationCustomer(final Long customerId, ApplicationInfo app, final String customerCode) {
        ApplicationCustomer applicationCustomer = new ApplicationCustomer();
        applicationCustomer.setCustomerId(customerId);
        applicationCustomer.setApplication(app);
        applicationCustomer.setCustomerType(ApplicationCustomerType.AGENT);
        applicationCustomer.setCustomerCode(customerCode);
        return applicationCustomer;
    }

    private String getCustomerCodeById(Long id) {
        return customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(id)).getPayload();
    }

}
