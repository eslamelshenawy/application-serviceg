package gov.saip.applicationservice.common.service.impl;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.common.clients.rest.callers.FileServiceCaller;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.mapper.DocumentCommentMapper;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LkDocumentType;
import gov.saip.applicationservice.common.model.LkNexuoUser;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.lookup.LKDocumentTypeService;
import gov.saip.applicationservice.common.service.lookup.LKNexuoUserService;
import gov.saip.applicationservice.common.util.FileExtentionValidation;
import gov.saip.applicationservice.common.validators.FileValidator;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static gov.saip.applicationservice.util.Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentsServiceImpl implements DocumentsService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentsServiceImpl.class);

    private final ApplicationInfoRepository applicationInfoRepository;

    private final FileServiceCaller fileServiceCaller;

    private final FileValidator fileValidator;

    private final LKDocumentTypeService lkDocumentTypeService;

    private final LKNexuoUserService lkNexuoUserService;

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;
    private final DocumentCommentMapper documentCommentMapper;
    private final ApplicationInfoMapper applicationInfoMapper;


    @Override
    @Transactional
    public List<DocumentDto> addDocuments(List<MultipartFile> files, String docTypeName, String applicationType, Long applicationId)   {
        try {
            log.info("addDocuments  To Nexuo applicationId {}",  applicationId);
             validateDoc(files  , applicationId  );
            LkNexuoUser lkNexuoUser = lkNexuoUserService.getNexuoUserTypeByType(applicationType);
            Long currentTime = System.currentTimeMillis();
            List<String> nexuoIds = fileServiceCaller.addFiles(files, lkNexuoUser.getName());
            Long endTime = System.currentTimeMillis();
            log.info("Integration With Nexuo Took {}", endTime - currentTime);
            log.info("document uploaded in nexuo with nexuoIds " + nexuoIds.toString());
            return addDocuments(files, nexuoIds, docTypeName, applicationId, lkNexuoUser);
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw  exception;
        }
    }

    private void validateDoc(List<MultipartFile> files , Long applicationId)   {
            for (MultipartFile file : files) {
                    if ( !FileExtentionValidation.isValidFileExtension(file.getOriginalFilename())){
                        log.error("validateDoc files With applicationId {} and  file {} is not valide ",  applicationId , file.getOriginalFilename());
                        throw new BusinessException("Extension not allowed");
                    }
            }
        log.info("validateDoc files With applicationId {} and  All files are Valid ",  applicationId  );
    }

    @Override
    public DocumentDto findDocumentById(Long id) {
        try {
            Optional<Document> document = documentRepository.findById(id);
            if (document.isEmpty() || document.get().getIsDeleted() == 1) {
                throw new BusinessException(Constants.ErrorKeys.DOCUMENT_ID_NOT_FOUND, HttpStatus.NOT_FOUND, null);
            }
            Document document1 = document.get();
            return documentMapper.mapToDto(document1);
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }


    @Override
    public Long softDeleteDocumentById(Long id) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.DOCUMENT_ID_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        Document document1 = document.get();
        document1.setIsDeleted(1);
        documentRepository.save(document1);
        return documentRepository.save(document1).getId();
    }

    @Override
    public void softDeleteDocumentByAppIdANDDocumentType(Long appId, String... documentTypeCodeList) {
        Arrays.stream(documentTypeCodeList)
                .flatMap(documentType -> documentRepository.findDocumentByApplicationId(appId, documentType).stream())
                .map(Document::getId)
                .forEach(this::softDeleteById);
    }

    @Override
    public List<DocumentDto> findDocumentByIds(List<Long> ids) {
        if (ids.isEmpty())
            return new ArrayList<>();
        List<Document> docs = documentRepository.findByIdIn(ids);
        return documentMapper.mapToDtoss(docs);
    }

    @Override
    public List<DocumentDto> findDeletedAndUndeletedDocumentByIds(List<Long> ids) {
        if (ids.isEmpty())
            return new ArrayList<>();
        List<Document> docs = documentRepository.findByIdIn(ids);
        List<Document> deletedDocs = documentRepository.findDeletedDocuments(ids);
        List<Document> allDocs = new ArrayList<>(docs);
        allDocs.addAll(deletedDocs);
        return documentMapper.mapToDtoss(allDocs);
    }

        @Override
    public List<ApplicationDocumentLightDto> findLightDocumentByIds(List<Long> ids) {
        if (ids.isEmpty())
            return new ArrayList<>();
        List<Document> docs = documentRepository.findByIdIn(ids);
        return documentMapper.mapToAppDocLightDto(docs);
    }

    @Override
    public Map<Long, DocumentDto> findDocumentMapByIds(List<Long> ids) {
        Map<Long, DocumentDto> map = new LinkedHashMap<>();
        if (CollectionUtils.isEmpty(ids)) {
            return map;
        }
        List<DocumentDto> docs = findDocumentByIds(ids);
        for (DocumentDto doc : docs) {
            map.put(doc.getId(), doc);
        }
        return map;
    }

    public List<DocumentDto> addDocuments(List<MultipartFile> files, List<String> nexuoIds, String docTypeName, Long applicationId, LkNexuoUser lkNexuoUser) {
        log.info("startaddDocuments applicationId+" + applicationId);
        List<DocumentDto> result = new LinkedList<>();
        Document document = null;
        LkDocumentType docType = lkDocumentTypeService.getDocumentTypeByName(docTypeName);
        for (int i = 0; i < files.size(); i++) {
            document = new Document();
            if (Objects.nonNull(applicationId))
                document.setApplicationInfo(new ApplicationInfo(applicationId));

            document.setDocumentType(docType);
            document.setUploadedDate(new Date());
            document.setNexuoId(nexuoIds.get(i));
            document.setLkNexuoUser(lkNexuoUser);
            document.setFileName(files.get(i).getOriginalFilename());
            document.setNoOfDocumentPages(calculateNumberOfPages(files.get(i)));
            document.setNoOfDocumentPages(20);
            result.add(documentMapper.mapToDto(documentRepository.save(document)));
            log.info("document saved in db appId" + applicationId);

        }
        log.info("endDocuments applicationId+" + applicationId);
        return result;
    }

    public Integer calculateNumberOfPages(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        Integer numberOfPages = null;
        if (fileExtension.equalsIgnoreCase("pdf")) {
            try {
                PDDocument pdfDocument = PDDocument.load(file.getBytes());
                numberOfPages = pdfDocument.getNumberOfPages();
            } catch (Exception e) {
                logger.error().exception("IOException", e).message(e.getMessage()).log();
            }
        } else if (fileExtension.equalsIgnoreCase("docx")) {
            try {
                XWPFDocument wordDocxDocument = new XWPFDocument(file.getInputStream());
                numberOfPages = wordDocxDocument.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
            } catch (Exception e) {
                logger.error().exception("IOException", e).message(e.getMessage()).log();
            }
        } else if (fileExtension.equalsIgnoreCase("doc")) {
            try {
                HWPFDocument wordDocDocument = new HWPFDocument(file.getInputStream());
                numberOfPages = wordDocDocument.getSummaryInformation().getPageCount();
            } catch (Exception e) {
                logger.error().exception("IOException", e).message(e.getMessage()).log();
            }
        } else {
            numberOfPages = 1;
        }

        return numberOfPages;
    }

    public DocumentDto findDocumentByApplicationIdAndDocumentType(Long id, String typeName) {

        List<Document> document = documentRepository.findDocumentByApplicationId(id, typeName);
        if (document.isEmpty()) {
            return null;
        }
        return documentMapper.mapToDto(document.get(0));
    }

    public DocumentDto findLatestDocumentByApplicationIdAndDocumentType(Long id, String typeName) {

        List<Document> document = documentRepository.findLatestDocumentByApplicationId(id, typeName);
        if (document.isEmpty()) {
            return null;
        }
        return documentMapper.mapToDto(document.get(0));
    }

    @Override
    public List<DocumentWithCommentDto> findDocumentByApplicationID(DocumentApplicationTypesDto documentApplicationTypesDto) {
        ApplicationInfo application = applicationInfoRepository.getReferenceById(documentApplicationTypesDto.getId());
        if (application == null) {
            String[] param = {};
            throw new BusinessException(APPLICATION_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, param);
        }

        List<Document> docs = documentRepository.getApplictionDocumentsTypes(application.getId(), "APPLICATION", documentApplicationTypesDto.getTypes(), documentApplicationTypesDto.getExtypes());
        return documentCommentMapper.map(docs);
    }

    @Override
    public List<ApplicationDocumentLightDto> getDocumentsByAppIdsAndType(List<Long> appIds, String typeName) {
        List<Document> docs = documentRepository.findLatestDocumentsByApplicationIds(appIds, typeName);
        return documentMapper.mapToAppDocLightDto(docs);
    }

    @Override
    public ApplicationDocumentLightDto getDocumentByApplicationIdAndType(Long appId) {
        String typeName = "Trademark Image/voice";
        List<Document> document = documentRepository.findDocumentByApplicationId(appId, typeName);
        if (document.isEmpty()) {
            return null;
        }

        return documentMapper.mapToAppDocLightDto(document.get(0));
    }

    @Override
    public void linkApplicationToDocument(Long id, Long appId) {
        documentRepository.linkApplicationToDocument(id, appId);
    }

    public DocumentDto getTradeMarkApplicationDocument(Long appId, String typeName) {
        List<Document> document = documentRepository.findDocumentByApplicationId(appId, typeName);
        if (document.isEmpty()) {
            return null;
        }

        return documentMapper.mapToDto(document.get(0));
    }

    public List<DocumentWithCommentDto> findDocumentsWithCommentsByApplicationIdAndCategory(Long appId, String category) {
        ApplicationInfo application = applicationInfoRepository.getReferenceById(appId);
        if (application == null) {
            String[] param = {};
            throw new BusinessException(APPLICATION_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, param);
        }

        List<Document> docs = documentRepository.getApplicationDocumentsWithComments(application.getId(), category);
        return documentCommentMapper.map(docs);
    }

    @Override
    public List<Document> findNexuoIdByApplicationId(Long applicationId) {
        return documentRepository.findNexuoIdByApplicationId(applicationId);
    }


    @Override
    public void hardDeleteDocumentById(Long id) {
        documentRepository.hardDeleteById(id);
    }

    @Override
    public void softDeleteById(Long id) {
        documentRepository.updateIsDeleted(id, 1);
    }


    @Override
    public String getNexuoIdsByApplicationIdAndTagIdAndDocumentTypeCode(Integer tagId, Long applicationId, String documentTypeCode) {
        return documentRepository.getNexuoIdsByApplicationIdAndTagIdAndDocumentTypeCode(tagId, applicationId, documentTypeCode);
    }

    @Override
    public List<DocumentDto> getDocumentsByApplicationId(Long applicationId) {
        List<Document> documents = documentRepository.getByApplicationInfoId(applicationId);
        return documentCommentMapper.mapToDocumentDto(documents);
    }

    @Override
    public ApplicationDocumentLightDto findTrademarkLatestDocumentByApplicationIdAndDocumentType(Long applicationId) {
        String typeName = "Trademark Image/voice";
        List<Document> document = documentRepository.findLatestDocumentByApplicationId(applicationId, typeName);
        if (document.isEmpty()) {
            return null;
        }

        return documentMapper.mapToAppDocLightDto(document.get(0));
    }

    public DocumentDto getDocumentByNexuoId(String nexuoId) {
        Document document = documentRepository.getDocumentByNexuoId(nexuoId).orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, new String[]{nexuoId})
        );

        return documentMapper.mapToDto(document);

    }

    public void updateDocumentType(Long id, String newDocType) {
        Optional<Document> document = documentRepository.findById(id);
        LkDocumentType documentType = lkDocumentTypeService.getDocumentTypeByName(newDocType);
        document.ifPresent(doc -> document.get().setDocumentType(documentType));
    }

    @Override
    public Long getIsDeletedByDocumentId(Long id) {
        return documentRepository.getIsDeletedByDocumentId(id);
    }

    @Override
    public int updateIsDeleted(Long id, int isDeleted) {
        return documentRepository.updateIsDeleted(id, isDeleted);
    }

    @Override
    public List<DocumentWithTypeDto> getApplicationDocsByApplicationIdAndTypes(Long id, List<String> type) {
        List<Document> docs = documentRepository.getApplicationDocsByApplicationIdAndTypes(id, type);
        return documentCommentMapper.mapToDocumentWithTypeDto(docs);
    }

    @Override
    public List<String> getNexuoIdsByApplicationIdAndTypes(Long applicationId, List<String> docTypes) {
        return documentRepository.getNexuoIdsByApplicationIdAndTypes(applicationId, docTypes);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteDocumentByApplicationIdAndDocType(Long appId, String documentType) {
        documentRepository.deleteDocumentByApplicationIdAndDocType(appId, documentType);
    }
}
