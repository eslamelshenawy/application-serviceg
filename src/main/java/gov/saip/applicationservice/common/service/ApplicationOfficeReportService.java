package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationOfficeReportDto;
import gov.saip.applicationservice.common.model.ApplicationOfficeReport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApplicationOfficeReportService extends BaseService<ApplicationOfficeReport, Long> {

    public List<ApplicationOfficeReport> getAllByApplicationId(Long appId);
}
