package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationOfficeReportDto;
import gov.saip.applicationservice.common.mapper.ApplicationOfficeReportMapper;
import gov.saip.applicationservice.common.model.ApplicationOfficeReport;
import gov.saip.applicationservice.common.service.ApplicationOfficeReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/app-office-report", "/internal-calling/app-office-report"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationOfficeReportController extends BaseController<ApplicationOfficeReport, ApplicationOfficeReportDto, Long> {

    private final ApplicationOfficeReportService applicationOfficeReportService;
    private final ApplicationOfficeReportMapper applicationOfficeReportMapper;
    @Override
    protected BaseService<ApplicationOfficeReport, Long> getService() {
        return applicationOfficeReportService;
    }

    @Override
    protected BaseMapper<ApplicationOfficeReport, ApplicationOfficeReportDto> getMapper() {
        return applicationOfficeReportMapper;
    }

    @GetMapping("/{appId}/application")
    public ApiResponse<List<ApplicationOfficeReportDto>> getAllApplicationWords(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationOfficeReportMapper.map(applicationOfficeReportService.getAllByApplicationId(appId)));
    }

}
