package gov.saip.applicationservice.common.clients.rest.callers;


import gov.saip.applicationservice.common.clients.rest.feigns.FileServiceFienClient;
import gov.saip.applicationservice.common.dto.FileServiceResponseDto;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.IPRMultipartFile;
import gov.saip.applicationservice.util.Util;
import liquibase.util.file.FilenameUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

import static gov.saip.applicationservice.util.Constants.ErrorKeys.DOCUMENT_NOT_UPLOADED_TO_NEXUO;

@Component
@Slf4j
public class FileServiceCaller {

    @Value("${nuxeo.upload.url}")
    private String nuxeoUploadUrl;

    private final FileServiceFienClient fileServiceFienClient;
    private final Util util;

    @Autowired
    public FileServiceCaller(FileServiceFienClient fileServiceFienClient, Util util) {
        this.fileServiceFienClient = fileServiceFienClient;
        this.util = util;
    }

    public List<String> addFiles(List<MultipartFile> files, String nuxeoUserName) {

        List<String> filesIds = new LinkedList<>();
        FileServiceResponseDto fileServiceResponseDto;
        for (MultipartFile file : files) {

            fileServiceResponseDto = fileServiceFienClient.uploadFile(nuxeoUserName, getMultipartFileToUpload(file));

            if (fileServiceResponseDto == null || fileServiceResponseDto.getDocId() == null) {
                log.error("an exception occurred while uploading the file '{}' and the nexuo response is '{}' ", file.getOriginalFilename(), fileServiceResponseDto);
                throw new BusinessException(DOCUMENT_NOT_UPLOADED_TO_NEXUO, HttpStatus.BAD_REQUEST);
            }

            log.info("file with name '{}' has uploaded successfully ", file.getOriginalFilename());
            filesIds.add(fileServiceResponseDto.getDocId());
        }
        return filesIds;
    }

    private static MultipartFile getMultipartFileToUpload(MultipartFile file) {
        MultipartFile multipartFile;
        if (FilenameUtils.getBaseName(file.getOriginalFilename()).length() <= 10) {
            log.info("file length is short so it's name '{}' will be the same", file.getOriginalFilename());
            return file;
        }

        String fileName = file.getOriginalFilename().length() > 10 ? file.getOriginalFilename().substring(0, 9) : file.getOriginalFilename();
        multipartFile = renameMultipartFile(file, fileName);
        log.info("file with name has renamed from '{}' to '{}' ", file.getOriginalFilename(), multipartFile.getOriginalFilename());
        return multipartFile;
    }

    private static MultipartFile renameMultipartFile(MultipartFile file, String newName) {
        String newOriginalFileFromOriginalMultipartFile = getNewOriginalFileFromOriginalMultipartFile(file, newName);
        return new IPRMultipartFile(newName, newOriginalFileFromOriginalMultipartFile, file);
    }


    private static String getNewOriginalFileFromOriginalMultipartFile(MultipartFile file, String newName) {
        return newName + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    }
}
