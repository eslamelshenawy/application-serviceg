package gov.saip.applicationservice.common.controllers.agency;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.agency.ApplicationAgentDto;
import gov.saip.applicationservice.common.dto.customer.AgentListDto;
import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.mapper.agency.ApplicationAgentMapper;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
    @RequestMapping( value = {"/kc/application-agent", "/internal-calling/application-agent"})
@RequiredArgsConstructor
public class ApplicationAgentController extends BaseController<ApplicationAgent, ApplicationAgentDto, Long> {

    private final ApplicationAgentService applicationAgentService;
    private final ApplicationAgentMapper applicationAgentMapper;
    @Override
    protected BaseService<ApplicationAgent, Long> getService() {
        return  applicationAgentService;
    }

    @Override
    protected BaseMapper<ApplicationAgent, ApplicationAgentDto> getMapper() {
        return applicationAgentMapper;
    }

    @GetMapping("/application/{appId}/agent")
    public ApiResponse<Long>  getCurrentApplicationAgent(@PathVariable("appId") Long appId) {
        return ApiResponse.ok(applicationAgentService.getCurrentApplicationAgent(appId));
    }
    @GetMapping("/application/{appId}/agents")
    public ApiResponse<List<Long>> getAllApplicationAgents(@PathVariable("appId") Long appId) {
        return ApiResponse.ok(applicationAgentService.getAllApplicationAgents(appId));
    }

    @GetMapping("/customer/{customerCode}/agents")
    public ApiResponse<List<Long>> getAllCustomerAgentsByCustomerCode(@PathVariable("customerCode") String customerCode) {
        return ApiResponse.ok(applicationAgentService.getAllCustomerAgentsByCustomerCode(customerCode));
    }


    @GetMapping("/customer/{customerCode}/agent-app-count")
    public ApiResponse<Map<Long, Long>> getCustomerAgentsAndCounts(@PathVariable("customerCode") String customerCode) {
        return ApiResponse.ok(applicationAgentService.getCustomerAgentsAndCounts(customerCode));
    }

    @GetMapping(value = "/agent-summary/{applicationId}")
    public ApiResponse<ApplicationAgentSummaryDto> getCurrentApplicationAgentSummary(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(applicationAgentService.getCurrentApplicationAgentSummary(applicationId));
    }


    @GetMapping(value = "/customer/{customerCode}/paginated-agents")
    public ApiResponse<PaginationDto<List<AgentListDto>>> getCurrentAgentsByCustomerCode(
            @PathVariable("customerCode") String customerCode,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return ApiResponse.ok(applicationAgentService.getCurrentAgentsByCustomerCode(customerCode, name, page, limit));
    }

}
