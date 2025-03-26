package gov.saip.applicationservice.report;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * The {@code JasperReportUtils} class provides methods to construct Jasper reports
 */
public class JasperReportUtils {

    private static final Logger logger = LoggerFactory.getLogger(JasperReportUtils.class);


    public static ByteArrayResource generateReport(String reportFilePath, Collection<?> dataSource) {
        try {
            // Create Jasper report
            JasperReport patentLicenseReport = JasperCompileManager
                    .compileReport(new ClassPathResource(reportFilePath).getInputStream());
            JasperPrint patentLicenseReportPrint = JasperFillManager.fillReport(patentLicenseReport, new HashMap<>(),
                    new JRBeanCollectionDataSource(dataSource));
            // Export Jasper report to PDF format
            byte[] report = JasperExportManager.exportReportToPdf(patentLicenseReportPrint);
            return new ByteArrayResource(report);
        } catch (IOException e) {
            logger.error().exception("exception", e).message(e.getMessage()).log();
            throw new BusinessException(Constants.ErrorKeys.READING_DISK_FILE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JRException e) {
            logger.error().exception("exception", e).message(e.getMessage()).log();
            throw new BusinessException(Constants.ErrorKeys.GENERATING_JASPER_REPORT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
