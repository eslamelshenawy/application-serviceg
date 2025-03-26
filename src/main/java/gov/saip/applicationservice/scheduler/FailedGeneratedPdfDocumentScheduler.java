package gov.saip.applicationservice.scheduler;

import gov.saip.applicationservice.common.model.patent.ApplicationCertificateDocument;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.patent.ApplicationCertificateDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class FailedGeneratedPdfDocumentScheduler {

    private final ApplicationCertificateDocumentService applicationCertificateDocumentService;
    private final ApplicationInfoService applicationInfoService;

    @Scheduled(cron = "${retry.sending.failedPdfDocument.scheduler}")
    public void retryGeneratingAllFailedPdfDocument() {
        log.info(" Scheduler to retry regenerating all failed Pdf documents starts");


        log.info("  regenerateAllFailedDocuments  Scheduler to retry regenerating all failed Pdf documents starts");
        try {
            regenerateAllFailedDocuments();
        }catch (Exception ex){
            log.error( " regenerateAllFailedDocuments error " , ex);
        }

            log.info("  generateDocumentForAllPatentApplicationsWithNoDocument Scheduler to retry regenerating all failed Pdf documents starts");
          try {
              generateDocumentForAllPatentApplicationsWithNoDocument();
          }catch (Exception ex){
              log.error( " generateDocumentForAllPatentApplicationsWithNoDocument error " , ex);
          }



        log.info(" Scheduler to retry regenerating all failed Pdf documents ended ");
    }


    public  void generateDocumentForAllPatentApplicationsWithNoDocument() {
        try {
        List<Long> applicationIdsWithNoDocument = applicationInfoService.findPatentApplicationWithNoPdfDocument();
        log.info(" count  generateDocumentForAllPatentApplicationsWithNoDocument   " + applicationIdsWithNoDocument.size());
        applicationIdsWithNoDocument.forEach(applicationId -> applicationCertificateDocumentService.generateAndSaveDocument(applicationId));
        }catch (Exception ex){
            log.error( " regenerateAllFailedDocumentsEXCEPTION " , ex);
        }
    }

    public void regenerateAllFailedDocuments() {
        try {
            List<ApplicationCertificateDocument> applicationCertificateDocuments = applicationCertificateDocumentService.getAllFailedDocument();
            if (applicationCertificateDocuments != null)
                log.info(" count  regenerateAllFailedDocuments  " + applicationCertificateDocuments.size());
            applicationCertificateDocuments.forEach(applicationCertificateDocument -> applicationCertificateDocumentService.reGenerateAndSaveDocument(applicationCertificateDocument));
        }catch (Exception ex){
            log.error( " regenerateAllFailedDocumentsEXCEPTION " , ex);
        }
    }

}
