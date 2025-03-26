package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.client.JasperReportsClient;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.ApplicationTypeEnum;
import gov.saip.applicationservice.common.enums.DocumentTypeEnum;
import gov.saip.applicationservice.common.enums.ReportsType;
import gov.saip.applicationservice.common.model.ApplicationCheckingReport;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.service.trademark.impl.CustomMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static gov.saip.applicationservice.common.enums.ApplicationTypeEnum.IPRS_INDUSTRIAL_DESIGN;

@Component
public class IndustrialAndIntegratedCircuitReportGenerationHelper {

    @Autowired
    private ApplicationCheckingReportService applicationCheckingReportService;

    @Autowired
    private JasperReportsClient jasperReportsClient;

    @Autowired
    private DocumentsService documentsService;

    public List<DocumentDto> generateJasperPdf(ReportRequestDto dto, String reportName, ApplicationTypeEnum applicationTypeEnum, DocumentTypeEnum documentType) throws IOException {
        // need call dashboard api
        Long applicationId = Long.valueOf(dto.getParams().get("APPLICATION_ID").toString());
        ApplicationCheckingReport reportEntity = applicationCheckingReportService.findByApplicationInfoIdAndReportType(applicationId, ReportsType.valueOf(reportName));
        if (Objects.isNull(reportEntity)){
            reportEntity=new ApplicationCheckingReport();
            reportEntity.setReportType(ReportsType.valueOf(reportName));
            reportEntity.setApplicationInfo(new ApplicationInfo(applicationId));
            reportEntity= applicationCheckingReportService.insert(reportEntity);
        }else{
            documentsService.softDeleteById(reportEntity.getDocumentId());
        }
        Map<String, Object> jasperParams = dto.getParams();
        jasperParams.put("REPORT_ID", reportEntity.getId());
        dto.setParams(jasperParams);
        ResponseEntity<ByteArrayResource> file=  jasperReportsClient.exportToPdf(dto, reportName);
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile multipartFile =
                new CustomMultipartFile(applicationId.toString(), reportName+".jrxml",
                        "application/pdf", false, file.getHeaders().getContentLength(), file.getBody());
        files.add(multipartFile);
        List<DocumentDto> documentDtos = documentsService.addDocuments(files, documentType.name(), applicationTypeEnum.name(), applicationId);
        reportEntity.setDocumentId(documentDtos.get(0).getId());
        applicationCheckingReportService.insert(reportEntity);
        return documentDtos;
    }
}
