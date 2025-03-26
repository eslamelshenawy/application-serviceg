
package gov.saip.applicationservice.common.service.patent.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ApplicationCertificateDocumentDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.mapper.ApplicationCertificateDocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.patent.ApplicationCertificateDocument;
import gov.saip.applicationservice.common.model.patent.PdfGenerationStatus;
import gov.saip.applicationservice.common.repository.patent.ApplicationCertificateDocumentRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.impl.ApplicationServiceImpl;
import gov.saip.applicationservice.common.service.patent.ApplicationCertificateDocumentService;
import gov.saip.applicationservice.common.service.pdf.PdfGenerationService;
import gov.saip.applicationservice.common.service.trademark.impl.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static gov.saip.applicationservice.common.enums.ApplicationTypeEnum.IPRS_PATENT;

/**
 * The {@code ApplicationCertificateDocumentServiceImpl} class provides an implementation of the {@link ApplicationCertificateDocumentService} interface
 * that manages ApplicationCertificateDocument.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ApplicationCertificateDocumentServiceImpl extends BaseServiceImpl<ApplicationCertificateDocument, Long> implements ApplicationCertificateDocumentService {

    private final ApplicationCertificateDocumentRepository applicationCertificateDocumentRepository;
    private final PdfGenerationService pdfGenerationService;
    private final DocumentsService documentsService;
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationCertificateDocumentMapper applicationCertificateDocumentMapper;
    private final ApplicationServiceImpl applicationServiceImpl;

    @Override
    protected BaseRepository<ApplicationCertificateDocument, Long> getRepository() {
        return applicationCertificateDocumentRepository;
    }
    @Override
    public Optional<ApplicationCertificateDocument> getMostRecentCertificateDocumentByApplicationId(Long applicationId) {
        return applicationCertificateDocumentRepository.getMostRecentCertificateDocumentByApplicationId(applicationId);
    }

    @Override
    public Integer getMaxVersionByApplicationId(Long applicationId) {
        return applicationCertificateDocumentRepository.getMaxVersionByApplicationId(applicationId);
    }

    @Override
    public Integer getMinVersionByApplicationId(Long applicationId) {
        return applicationCertificateDocumentRepository.getMinVersionByApplicationId(applicationId);
    }

    @Override
    public ApplicationCertificateDocument insert(ApplicationCertificateDocument entity) {
        return super.insert(entity);
    }

    public void generateAndSaveDocument(Long applicationId){
        log.info("startGenerateAndSaveDocument applicationId: "+applicationId );
        ApplicationCertificateDocument certificateDocument = new ApplicationCertificateDocument();
        try {
            ApplicationInfo applicationInfo = applicationInfoService.findById(applicationId);
            Integer maxVersion = getMaxVersionByApplicationId(applicationId);
            Integer minVersion = getMinVersionByApplicationId(applicationId);
            int nextVersion;
            nextVersion = (minVersion != null && minVersion != 1) ? 1 : (maxVersion == null ? 1 : maxVersion + 1);
            certificateDocument.setVersion(nextVersion);
            certificateDocument.setApplication(applicationInfo);
            generateDocumentAndSaveApplicationCertificateDocument(certificateDocument);
        } catch(Exception ex){
            try {
            String exceptionMessage = ex.getClass() + " : " + ex.getMessage();
            insertOrUpdateFailedCertificateDocument(certificateDocument,  exceptionMessage);
            }catch (Exception ex2){
                log.error("insertOrUpdateFailedCertificateDocumentException  " , ex2);
            }
        }

    }

    @Override
    public void reGenerateAndSaveDocument(ApplicationCertificateDocument certificateDocument){
        try {
            log.info(" start  reGenerateAndSaveDocument  " + certificateDocument.getApplication().getId() );
            generateDocumentAndSaveApplicationCertificateDocument(certificateDocument);
        } catch(Exception ex){
            log.error(" reGenerateAndSaveDocumentException  " , ex);
         try {
             insertOrUpdateFailedCertificateDocument(certificateDocument, ex.getMessage());
         }catch (Exception ex2){
             log.error(" insertOrUpdateFailedCertificateDocumentException  " , ex2);
         }
        }
    }

    @Override
    @Transactional
    public void updateGeneratedDocument(Long applicationId) {
        applicationServiceImpl.updateCertificateDocument(applicationId);
    }

    private void insertOrUpdateFailedCertificateDocument(ApplicationCertificateDocument certificateDocument, String exceptionMessage){
        certificateDocument.setFailureReason(exceptionMessage);
        certificateDocument.setGenerationStatus(PdfGenerationStatus.FAILED);
        insertOrUpdate(certificateDocument);
    }

    private void generateDocumentAndSaveApplicationCertificateDocument(ApplicationCertificateDocument certificateDocument){
            List<DocumentDto> documents = generatePatentDocument(certificateDocument.getApplication().getId(), certificateDocument.getApplication().getTitleAr(), certificateDocument.getApplication().getTitleEn());
           log.info("afterGenerateDocumentAndSaveApplicationCertificateDocument  certificateDocument: "+ certificateDocument.getApplication().getId());
            if (!documents.isEmpty())
                certificateDocument.setDocument(new Document(documents.get(0).getId()));
            certificateDocument.setGenerationStatus(PdfGenerationStatus.SUCCEED);
            insertOrUpdate(certificateDocument);
            System.out.println(documents.get(0).getFileReviewUrl());
    }

    private ApplicationCertificateDocument insertOrUpdate(ApplicationCertificateDocument certificateDocument){
        return certificateDocument.getId() == null ? insert(certificateDocument) : update(certificateDocument);
    }

    @Override
    public List<ApplicationCertificateDocumentDto> findByApplicationId(Long applicationId) {
        List<ApplicationCertificateDocument> applicationCertificateDocuments = applicationCertificateDocumentRepository.findByApplicationIdOrderByVersionDesc(applicationId);
        return applicationCertificateDocumentMapper.map(applicationCertificateDocuments);
    }

    @Override
    public List<ApplicationCertificateDocument> getDocumentsByApplicationId(Long applicationId) {
        return applicationCertificateDocumentRepository.findByApplicationIdOrderByVersionDesc(applicationId);
    }

    public List<DocumentDto> generatePatentDocument(Long applicationId,String titleAr,String titleEn){

        try {
            log.info(" start generatePatentDocument  applicationId: " + applicationId);
            String generationId = UUID.randomUUID().toString();
            log.info("   generatePatentDocument  generationId : " + generationId);
            ByteArrayResource file = null;
            ResponseEntity<ByteArrayResource> newFile = null;
            List<MultipartFile> files = null;
            log.info("generatePatentDocumentgetApplicationDocumentFilePath   applicationId: " + applicationId + " applicationId :" + applicationId + " titleAr : " + titleAr + " titleEn : " + titleEn);
            String filePath = pdfGenerationService.getApplicationDocumentFilePath(generationId, applicationId, titleAr, titleEn);
            log.info("generatePatentDocumentconvertFileToResource   filePath: " + filePath);
            file = pdfGenerationService.convertFileToResource(filePath);
            log.info("generatePatentDocumentgetCustomMultipartFiles  file: " + file.contentLength());
            files = pdfGenerationService.getCustomMultipartFiles(applicationId, file, "originFilename.pdf", "application/pdf");
            log.info("generatePatentDocumentPdfGenerationService   applicationId: " + applicationId);
            if (newFile != null) {
                files = new ArrayList<>();
                MultipartFile multipartFile =
                        new CustomMultipartFile(applicationId.toString(), "Patent Document" + ".jrxml",
                                "application/pdf", false, file.contentLength(), file);
                files.add(multipartFile);
                log.info(" generatePatentDocumentfilesfiles  applicationId: " + applicationId + " files:" + files.size());
            }
            log.info(" generatePatentDocumentfiles  applicationId: " + applicationId);
            List<DocumentDto> documents = documentsService.addDocuments(files, "Issue Certificate", IPRS_PATENT.name(), applicationId);
            log.info("generatePatentaddDocuments  applicationId: " + applicationId + " generationId:" + generationId);
             pdfGenerationService.deleteFilesWithPrefixAfterComplete(generationId);
            log.info("BEFOREgeneratePatentaddRETURNs  applicationId: " + applicationId + " generationId:" + generationId);
            return documents;
        }catch (Exception ex){
            log.info("generatePatentDocumentException   "  , ex );
            return null;
        }

    }

    @Override
    public List<ApplicationCertificateDocument> getAllFailedDocument() {
        return applicationCertificateDocumentRepository.findAllFailed();
    }


}
