package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.util.IPRMultipartFile;
import gov.saip.applicationservice.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/kc/documents", "/internal-calling/documents","/pb/documents"})
@Slf4j
public class DocumentsController {

    private final DocumentsService documentsService;

    @Autowired
    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }


    @PostMapping(consumes = {"multipart/form-data"})
    public ApiResponse<List<DocumentDto>> addDocuments
            (@RequestPart(value = "files") List<MultipartFile> files,
             @RequestHeader("Doc-Type-Name") String docTypeName,
             @RequestHeader("App-Type") String applicationType,
             @RequestHeader("Application-Id") String applicationId
            ) throws Exception {
        log.info("starting upload document from application service with Application-Id= " + applicationId );
        Long serviceTime = System.currentTimeMillis();
        List<DocumentDto> result = documentsService.addDocuments(files, docTypeName, applicationType, Long.valueOf(applicationId));
        log.info("done with upload document from application service with Application-Id= " + applicationId );
        return ApiResponse.ok(result, String.valueOf(System.currentTimeMillis() - serviceTime));
    }

    @PostMapping(value = "without-app-id", consumes = {"multipart/form-data"})
    public ApiResponse<List<DocumentDto>> addDocumentsWithoutApp
            (@RequestPart(value = "files") List<MultipartFile> files,
             @RequestHeader("Doc-Type-Name") String docTypeName,
             @RequestHeader("App-Type") String applicationType,
             @RequestHeader(value = "extension", required = false) String extension
            ) {
        files = getFilesToUpload(files, extension);
        Long serviceTime = System.currentTimeMillis();
        List<DocumentDto> result = documentsService.addDocuments(files, docTypeName, applicationType, null);
        return ApiResponse.ok(result, String.valueOf(System.currentTimeMillis() - serviceTime));
    }

    private static List<MultipartFile> getFilesToUpload(List<MultipartFile> files, String extension) {
        if (extension == null || extension.isBlank()) {
            return files;
        }

        List<MultipartFile> multiPartFiles = new ArrayList<>();

        for(MultipartFile file : files) {
            IPRMultipartFile iprMultipartFile = new IPRMultipartFile(file.getName(), Utilities.replaceFileExtension(file.getOriginalFilename(), extension), file);
            multiPartFiles.add(iprMultipartFile);
        }

       return multiPartFiles;
    }

    @GetMapping("/{id}")
    public ApiResponse<DocumentDto> findDocumentById(@PathVariable Long id) {
        return ApiResponse.ok(documentsService.findDocumentById(id));

    }

    @PostMapping("/findByIds")
    public ApiResponse<List<DocumentDto>> findDocumentByIds(@RequestBody List<Long> ids) {
        return ApiResponse.ok(documentsService.findDocumentByIds(ids));

    }

    @DeleteMapping("/{id}")
    public ApiResponse<Long> softDeleteDocumentById(@PathVariable Long id) {
        return ApiResponse.ok(documentsService.softDeleteDocumentById(id));
    }
    @GetMapping("/by-id/{appId}")
    ApiResponse<ApplicationDocumentLightDto> getTradeMarkImageByApplicationId(
            @PathVariable(name="appId") Long appId){
        return ApiResponse.ok(documentsService.getDocumentByApplicationIdAndType(appId));
    }
    
    @GetMapping()
    ApiResponse<List<ApplicationDocumentLightDto>> getDocumentsByApplicationIdsAndTypeName(
            @RequestParam(value="appIds") List<Long> appIds,
            @RequestParam(value = "typeName") String typeName){
        return ApiResponse.ok(documentsService.getDocumentsByAppIdsAndType(appIds, typeName));
    }
    
    @DeleteMapping("/{id}/hard-deleted")
    public String hardDeleteDocumentById(@PathVariable Long id) {
        documentsService.hardDeleteDocumentById(id);
        return "SUCCESS";
    }

    @DeleteMapping("/{id}/soft-deleted")
        public ApiResponse<?> softDeleteById(@PathVariable Long id) {
        documentsService.softDeleteById(id);
        return ApiResponse.ok("SUCCESS");
    }

    @GetMapping("/nexuo/{nexuoId}")
    public ApiResponse<DocumentDto> getDocumentByNexuoId(@PathVariable String nexuoId) {
        return ApiResponse.ok(documentsService.getDocumentByNexuoId(nexuoId));

    }

    @GetMapping("/application/{id}")
    public ApiResponse<List<DocumentWithTypeDto>> getDocumentByApplicationIdAndType(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "type", required = false) List<String> type) {
        List<DocumentWithTypeDto> documentByApplicationIdAndType = documentsService.getApplicationDocsByApplicationIdAndTypes(id, type);
        return ApiResponse.ok(documentByApplicationIdAndType);
    }

    @GetMapping("/all-documents-application/{id}")
    public ApiResponse<List<DocumentDto>> getDocumentByApplicationIdAndType(
            @PathVariable(name = "id") Long id) {
        List<DocumentDto> documents = documentsService.getDocumentsByApplicationId(id);
        return ApiResponse.ok(documents);
    }

    @GetMapping("/document/applications/{id}")
    public ApiResponse<DocumentDto> findDocumentByApplicationIdAndDocumentType(@PathVariable Long id
            , @RequestParam(value = "typeName",required = true) String typeName ) {
        return ApiResponse.ok(documentsService.findDocumentByApplicationIdAndDocumentType(id, typeName));
    }

    @GetMapping("/findDeletedAndUndeleted")
    public ApiResponse<List<DocumentDto>> findDeletedAndUndeletedDocumentByIds(@RequestParam(value = "ids") List<Long> ids) {
        return ApiResponse.ok(documentsService.findDeletedAndUndeletedDocumentByIds(ids));

    }

}