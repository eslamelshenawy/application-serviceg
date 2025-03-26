package gov.saip.applicationservice.common.util;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
@Slf4j
public class FileExtentionValidation {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png","PNG", "pdf" , "mp4", "mp3","docx","xml" , "jrxml");
    private static final Logger logger = LoggerFactory.getLogger(FileExtentionValidation.class);

    private  static String getFileExtension(String fileName) {
        log.info("getFileExtension  To fileName {}"+  fileName);

        int occurance = StringUtils.countOccurrencesOf(fileName, ".");

        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex == -1 || occurance > 1) {
            return "";
        }
        return fileName.substring(lastIndex + 1).toLowerCase();
    }

    public static  boolean isValidFileExtension(String fileName) {
        String extension  = getFileExtension(fileName);
        return ALLOWED_EXTENSIONS.contains(extension);
    }


}
