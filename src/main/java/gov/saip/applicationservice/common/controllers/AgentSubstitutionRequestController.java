package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.AgentSubstitutionRequestDto;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.agency.AgentSubstitutionServiceDto;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.facade.ApplicationAgentFacade;
import gov.saip.applicationservice.common.mapper.AgentSubstitutionRequestMapper;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.service.AgentSubstitutionRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = {"/kc/support-service/agent-substitution", "/internal-calling/support-service/agent-substitution"})
@RequiredArgsConstructor
@Slf4j
public class AgentSubstitutionRequestController extends BaseController<AgentSubstitutionRequest, AgentSubstitutionRequestDto, Long> {

    private final AgentSubstitutionRequestService agentSubstitutionRequestService;
    private final AgentSubstitutionRequestMapper agentSubstitutionRequestMapper;
    private final ApplicationAgentFacade applicationAgentFacade;

    @Override
    protected BaseService<AgentSubstitutionRequest, Long> getService() {
        return agentSubstitutionRequestService;
    }

    @Override
    protected BaseMapper<AgentSubstitutionRequest, AgentSubstitutionRequestDto>  getMapper() {
        return agentSubstitutionRequestMapper;
    }

    @GetMapping("/{appId}/application")
    public ApiResponse<List<AgentSubstitutionRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(agentSubstitutionRequestMapper.map(agentSubstitutionRequestService.getAllByApplicationId(appId)));
    }

    @PostMapping("/add-agent")
    public ApiResponse<Long> insertAddNewAgentRequest(@RequestBody AgentSubstitutionRequestDto dto) {
        AgentSubstitutionRequest entity = agentSubstitutionRequestMapper.unMap(dto);
        AgentSubstitutionRequest result = applicationAgentFacade.insertAddAgentRequest(entity);
        return ApiResponse.ok(result.getId());
    }

    @PostMapping("/substitute-agent")
    public ApiResponse<Long> insertSubstituteAgentRequest(@RequestBody AgentSubstitutionRequestDto dto,
                                                          @RequestParam(name = "substituteAll", defaultValue = "false", required = false) boolean substituteAll,
                                                          @RequestParam(name = "customerCode", required = false) String customerCode,
                                                          @RequestParam(name = "agentId", required = false) Long agentId) {
        AgentSubstitutionRequest entity = agentSubstitutionRequestMapper.unMap(dto);
        AgentSubstitutionRequest result;
        if (substituteAll) {
            result = applicationAgentFacade.insertSubstituteAgentRequestToAllAppsByAgentId(entity, customerCode, agentId);
            return ApiResponse.ok(result.getId());
        }
        result = applicationAgentFacade.insertSubstituteAgentRequest(entity);
        return ApiResponse.ok(result.getId());
    }


    @DeleteMapping("/delete/application-agent")
    public ApiResponse<Long> deleteAgent(@RequestParam(required = false, name = "applicationIds") List<Long> applicationIds,
                                         @RequestParam(required = false, name = "agentId") Long agentId,
                                         @RequestParam(required = false, name = "customerCode") String customerCode
    ) {
        AgentSubstitutionRequest req = null;
        if (Objects.nonNull(applicationIds)) {
            req = applicationAgentFacade.deleteAgentsByAppIds(applicationIds);
            return ApiResponse.ok(req.getId());
        }

        req = applicationAgentFacade.deleteAgentsByAgentAndCustomerCode(agentId, customerCode);
        return ApiResponse.ok(req.getId());
    }


//    @GetMapping(value = "/customer/{customerCode}/agents")
//    public ApiResponse<PaginationDto<List<AgentListDto>>> getCurrentAgentsByCustomerCode(
//            @PathVariable("customerCode") String customerCode,
//            @RequestParam(value = "name", required = false, defaultValue = "") String name,
//            @RequestParam(value = "page", defaultValue = "0") Integer page,
//            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
//        return ApiResponse.ok(organizationService.getCurrentAgentsByCustomerCode(customerCode, name, page, limit));
//    }


    @GetMapping("/service/{serviceId}")
    public ApiResponse<AgentSubstitutionServiceDto> getDetailsBySupportServiceId(@PathVariable(name="serviceId")Long serviceId){
        return ApiResponse.ok(agentSubstitutionRequestService.getDetailsBySupportServiceId(serviceId));
    }

    @GetMapping("agent/{agentSubstituteId}")
    public ApiResponse<AgentSubstitutionServiceDto> getDetailsByAgentSubstitutionId(@PathVariable(name = "agentSubstituteId") Long agentSubstituteId){
        return ApiResponse.ok(agentSubstitutionRequestService.getDetailsByAgentSubstitutionId(agentSubstituteId));
    }


    @PostMapping("/process/{id}/{serviceTypeCode}")
    public ApiResponse<Void> processAgentRequest(
            @PathVariable(name="id")Long id, @PathVariable("serviceTypeCode") SupportedServiceCode serviceTypeCode) {

        switch (serviceTypeCode) {
            case ADD_AGENT -> applicationAgentFacade.processAddAgentRequest(id);
            case DELETE_AGENT -> applicationAgentFacade.processDeleteAgentRequest(id);
            case SUBSTITUTE_AGENT -> applicationAgentFacade.processSubsituteAgentRequest(id);
        }

        return ApiResponse.noContent();
    }

}
