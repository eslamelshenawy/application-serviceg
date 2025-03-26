package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LkTaskEqmTypeDto;
import gov.saip.applicationservice.common.mapper.LkTaskEqmTypeMapper;
import gov.saip.applicationservice.common.model.LkTaskEqmType;
import gov.saip.applicationservice.common.service.LkTaskEqmTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/task-eqm-type", "/internal-calling/task-eqm-type"})
@RequiredArgsConstructor
@Slf4j
public class LkTaskEqmTypeController extends BaseLkpController<LkTaskEqmType, LkTaskEqmTypeDto, Integer> {

    private final LkTaskEqmTypeService taskEqmTypeService;
    private final LkTaskEqmTypeMapper taskEqmTypeMapper;


    @GetMapping("/code")
    public ApiResponse<LkTaskEqmTypeDto> findByCode(
            @RequestParam(value = "code", required = false) String code) {
        LkTaskEqmType res = taskEqmTypeService.findByCode(code);
        return ApiResponse.ok(taskEqmTypeMapper.map(res));
    }
}
