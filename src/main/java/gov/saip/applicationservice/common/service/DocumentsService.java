package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface DocumentsService {

    public List<DocumentDto> addDocuments(List<MultipartFile> files, String docTypeName, String applicationType, Long applicationId) ;

    DocumentDto findDocumentById(Long id);


    Long softDeleteDocumentById(Long id);

    List<DocumentDto> findDocumentByIds(List<Long> ids);
    List<DocumentDto> findDeletedAndUndeletedDocumentByIds(List<Long> ids);

    Map<Long, DocumentDto> findDocumentMapByIds(List<Long> ids);

    DocumentDto findDocumentByApplicationIdAndDocumentType(Long id, String typeName);
    
    DocumentDto findLatestDocumentByApplicationIdAndDocumentType(Long id, String typeName);
    
    List<DocumentWithCommentDto> findDocumentByApplicationID(DocumentApplicationTypesDto documentApplicationTypesDto);
    
    public List<ApplicationDocumentLightDto> getDocumentsByAppIdsAndType(List<Long> appIds, String typeName);
    
    ApplicationDocumentLightDto getDocumentByApplicationIdAndType(Long appId);
    
    void linkApplicationToDocument(Long id, Long appId);
    DocumentDto getTradeMarkApplicationDocument(Long appId,String typeName);
    List<DocumentWithCommentDto> findDocumentsWithCommentsByApplicationIdAndCategory(Long appId, String category);
    List<ApplicationDocumentLightDto> findLightDocumentByIds(List<Long> ids);

    void hardDeleteDocumentById(Long id);

    void softDeleteById(Long id);

    List<Document> findNexuoIdByApplicationId(Long applicationId);

    String getNexuoIdsByApplicationIdAndTagIdAndDocumentTypeCode(Integer tagId, Long applicationId, String documentTypeCode);

    List<DocumentDto> getDocumentsByApplicationId(Long id);
    
    ApplicationDocumentLightDto findTrademarkLatestDocumentByApplicationIdAndDocumentType(Long applicationId);

    DocumentDto getDocumentByNexuoId (String nexuoId);
    
    void updateDocumentType(Long id, String newDocType);

    void softDeleteDocumentByAppIdANDDocumentType(Long appId, String... documentTypeCode);

    Long getIsDeletedByDocumentId(Long id);

    int updateIsDeleted(Long id, int isDeleted);

    List<DocumentWithTypeDto> getApplicationDocsByApplicationIdAndTypes(Long id, List<String> type);

    List<String> getNexuoIdsByApplicationIdAndTypes(Long applicationId, List<String> docTypes);
    void deleteDocumentByApplicationIdAndDocType(Long appId, String documentType);
}
