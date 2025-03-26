package gov.saip.applicationservice.common.controllers.veena;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.veena.LKVeenaDepartmentDto;
import gov.saip.applicationservice.common.model.veena.LKVeenaDepartment;
import gov.saip.applicationservice.common.service.veena.LKVeenaDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/veena-department", "/internal-calling/veena-department"})
@RequiredArgsConstructor
@Slf4j
public class LKVeenaDepartmentController extends BaseLkpController<LKVeenaDepartment, LKVeenaDepartmentDto, Long> {

    private final LKVeenaDepartmentService lkVeenaDepartmentService;

    @GetMapping("/{classificationId}/classification")
    public ApiResponse<List<LKVeenaDepartmentDto>> getClassificationById(@PathVariable("classificationId") Long classificationId) {
        return ApiResponse.ok(lkVeenaDepartmentService.getByVeenaClassificationId(classificationId));
    }


}
