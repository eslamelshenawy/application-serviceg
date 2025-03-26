package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationCheckingReportDto;
import gov.saip.applicationservice.common.enums.ReportsType;
import gov.saip.applicationservice.common.model.ApplicationCheckingReport;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


public interface ApplicationCheckingReportService extends BaseService<ApplicationCheckingReport, Long> {
    ApplicationCheckingReportDto getLastReportByReportType(Long applicationId, ReportsType reportsType);

    ApplicationCheckingReport findByApplicationInfoIdAndReportType(Long applicationId, ReportsType reportsType);

    ApplicationCheckingReport findBySupportServicesTypeIdAndReportType(Long serviceId ,ReportsType reportsType);


}