package gov.saip.applicationservice.common.controllers.veena;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.veena.LKVeenaAssistantDepartmentDto;
import gov.saip.applicationservice.common.model.veena.LKVeenaAssistantDepartment;
import gov.saip.applicationservice.common.service.veena.LKVeenaAssistantDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/veena-assistant-department", "/internal-calling/veena-assistant-department"})
@RequiredArgsConstructor
@Slf4j
public class LKVeenaAssistantDepartmentController extends BaseLkpController<LKVeenaAssistantDepartment, LKVeenaAssistantDepartmentDto, Long> {

    private final LKVeenaAssistantDepartmentService lkVeenaAssistantDepartmentService;

    @GetMapping("/{departmentId}/department")
    public ApiResponse<List<LKVeenaAssistantDepartmentDto>> getByVeenaDepartmentId(@PathVariable("departmentId") Long departmentId) {
        return ApiResponse.ok(lkVeenaAssistantDepartmentService.getByVeenaDepartmentId(departmentId));
    }

    @GetMapping("/search")
    public ApiResponse<List<LKVeenaAssistantDepartmentDto>> searchByVeenaAssistantDepartment(@RequestParam(value = "search",required = false)String search) {
        return ApiResponse.ok(lkVeenaAssistantDepartmentService.searchByVeenaAssistantDepartment(search));
    }




}
