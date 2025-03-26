package gov.saip.applicationservice.report.service;

import gov.saip.applicationservice.common.dto.trademark.PublicationApplicationTrademarkDetailDto;
import gov.saip.applicationservice.report.dto.IndustrialDesignJasperReportDto;
import org.springframework.core.io.ByteArrayResource;

public interface ApplicationReportsService {

    ByteArrayResource getPatentLicenseByApplicationId(Long applicationId);
    
    IndustrialDesignJasperReportDto getIndustrialDesignJasperReportDetails(Long applicationId,boolean performance);
    
    PublicationApplicationTrademarkDetailDto getTradeMarkDetailsReport(Long appId);
    
}
