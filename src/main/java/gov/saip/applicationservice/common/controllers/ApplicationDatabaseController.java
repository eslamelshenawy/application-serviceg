package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationDatabaseDto;
import gov.saip.applicationservice.common.mapper.ApplicationDatabaseMapper;
import gov.saip.applicationservice.common.model.ApplicationDatabase;
import gov.saip.applicationservice.common.service.ApplicationDatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/app-database", "/internal-calling/app-database"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationDatabaseController extends BaseController<ApplicationDatabase, ApplicationDatabaseDto, Long> {

    private final ApplicationDatabaseService applicationDatabaseService;
    private final ApplicationDatabaseMapper applicationDatabaseMapper;
    @Override
    protected BaseService<ApplicationDatabase, Long> getService() {
        return applicationDatabaseService;
    }

    @Override
    protected BaseMapper<ApplicationDatabase, ApplicationDatabaseDto> getMapper() {
        return applicationDatabaseMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<ApplicationDatabaseDto>> getAllApplicationWords(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationDatabaseMapper.map(applicationDatabaseService.getAllByApplicationId(appId)));
    }
}
