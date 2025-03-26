package gov.saip.applicationservice.common.validators;

import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@Slf4j
public class FileValidator {

    private final String[] validExtensions = {"application/pdf", "image/jpeg", "image/png"};

    private final Integer validFileSizeInBytes = 10485760;

    @Autowired
    public FileValidator() {
    }

    public void validateFileExtension(List<MultipartFile> files) {
        for (MultipartFile file:
                files) {
            if(file.getSize() > validFileSizeInBytes){
                throw new BusinessException(Constants.ErrorKeys.FILE_SIZE_EXCEEDED, HttpStatus.BAD_REQUEST, null);
            }
            else if(!validateExtension(file)){
                throw new BusinessException(Constants.ErrorKeys.FILE_EXTENSION_NOT_VALID, HttpStatus.BAD_REQUEST, null);
            }
        }
    }

    public boolean validateExtension(MultipartFile file){
        for (String validExtension : validExtensions) {
            if (file.getContentType().equals(validExtension)) {
                return true;
            }
        }
        return false;
    }

}


