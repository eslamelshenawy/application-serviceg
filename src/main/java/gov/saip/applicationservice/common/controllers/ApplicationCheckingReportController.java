package gov.saip.applicationservice.common.controllers;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationCheckingReportDto;
import gov.saip.applicationservice.common.enums.ReportsType;
import gov.saip.applicationservice.common.mapper.ApplicationCheckingReportMapper;
import gov.saip.applicationservice.common.model.ApplicationCheckingReport;
import gov.saip.applicationservice.common.service.ApplicationCheckingReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/application-checking-report", "/internal-calling/application-checking-report"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationCheckingReportController extends BaseController<ApplicationCheckingReport, ApplicationCheckingReportDto, Long> {

    private final ApplicationCheckingReportService reportService;
    private final ApplicationCheckingReportMapper applicationCheckingReportMapper;

    @Override
    protected BaseService<ApplicationCheckingReport, Long> getService() {
        return reportService;
    }

    @Override
    protected BaseMapper<ApplicationCheckingReport, ApplicationCheckingReportDto> getMapper() {
        return applicationCheckingReportMapper;
    }

    @GetMapping("/last-report")
    public ApplicationCheckingReportDto getLastReportByReportType(@RequestParam("applicationId")Long applicationId,
                                     @RequestParam("reportsType")ReportsType reportsType){
            return reportService.getLastReportByReportType(applicationId,reportsType);
    }
}
