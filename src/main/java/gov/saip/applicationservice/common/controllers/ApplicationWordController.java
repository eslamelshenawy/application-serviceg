package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationWordDto;
import gov.saip.applicationservice.common.mapper.ApplicationWordMapper;
import gov.saip.applicationservice.common.model.ApplicationWord;
import gov.saip.applicationservice.common.service.ApplicationWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController()
@RequestMapping(value = {"/kc/app-word", "/internal-calling/app-word"})
@RequiredArgsConstructor
public class ApplicationWordController extends BaseController<ApplicationWord, ApplicationWordDto, Long> {

    private final ApplicationWordService applicationWordService;
    private final ApplicationWordMapper applicationWordMapper;
    @Override
    protected BaseService<ApplicationWord, Long> getService() {
        return applicationWordService;
    }

    @Override
    protected BaseMapper<ApplicationWord, ApplicationWordDto> getMapper() {
        return applicationWordMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<ApplicationWordDto>> getAllApplicationWords(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationWordMapper.map(applicationWordService.getAllAppWordsByApplicationId(appId)));
    }

}
