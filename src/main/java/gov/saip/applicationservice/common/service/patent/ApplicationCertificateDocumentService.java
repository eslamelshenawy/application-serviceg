package gov.saip.applicationservice.common.service.patent;


import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationCertificateDocumentDto;
import gov.saip.applicationservice.common.model.patent.ApplicationCertificateDocument;

import java.util.List;
import java.util.Optional;

public interface ApplicationCertificateDocumentService extends BaseService<ApplicationCertificateDocument, Long> {

    Optional<ApplicationCertificateDocument> getMostRecentCertificateDocumentByApplicationId(Long applicationId);
    Integer getMaxVersionByApplicationId(Long applicationId);

    Integer getMinVersionByApplicationId(Long applicationId);

    void generateAndSaveDocument(Long applicationId);

    void reGenerateAndSaveDocument(ApplicationCertificateDocument certificateDocument);

    void updateGeneratedDocument(Long applicationId);

    List<ApplicationCertificateDocumentDto> findByApplicationId(Long applicationId);

    List<ApplicationCertificateDocument> getAllFailedDocument();

    List<ApplicationCertificateDocument> getDocumentsByApplicationId(Long applicationId);
}
