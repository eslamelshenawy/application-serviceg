package gov.saip.applicationservice.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class DownloadFileUtilsTest {

    @Mock
    private ByteArrayResource file;

    private static final String DEFAULT_FILE_NAME = "file";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.OK;
    private static final MediaType DEFAULT_CONTENT_TYPE = MediaType.APPLICATION_OCTET_STREAM;


    @Test
    public void testBuilderDefaultValues() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=" + DEFAULT_FILE_NAME);
        ResponseEntity<ByteArrayResource> expectedResponse = ResponseEntity.status(DEFAULT_HTTP_STATUS)
                .contentType(DEFAULT_CONTENT_TYPE)
                .contentLength(0)
                .headers(headers)
                .body(new ByteArrayResource(new byte[]{}));
        // Act
        ResponseEntity<ByteArrayResource> actualResponse = DownloadFileUtils.builder().build();
        // Assert
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void testBuilderDefaultValues2() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        String downloadedFileName = "testFile.testExtension";
        headers.set("Content-Disposition", "attachment; filename=" + downloadedFileName);
        ResponseEntity<ByteArrayResource> expectedResponse = ResponseEntity.status(DEFAULT_HTTP_STATUS)
                .contentType(DEFAULT_CONTENT_TYPE)
                .contentLength(file.contentLength())
                .headers(headers)
                .body(file);
        // Act
        ResponseEntity<ByteArrayResource> actualResponse = DownloadFileUtils.builder()
                .downloadedFileName(downloadedFileName)
                .file(file)
                .build();
        // Assert
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void testBuilderNonDefaultValues() {
        // Arrange
        String downloadedFileName = "testFile.testExtension";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=" + downloadedFileName);
        ResponseEntity<ByteArrayResource> expectedResponse = ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.contentLength())
                .headers(headers)
                .body(file);
        // Act
        ResponseEntity<ByteArrayResource> actualResponse = DownloadFileUtils.builder()
                .httpStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .downloadedFileName(downloadedFileName)
                .file(file)
                .build();
        // Assert
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void testBuilderNonDefaultValues2() {
        // Arrange
        String downloadedFileName = "testFile.testExtension";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=" + downloadedFileName);
        ResponseEntity<ByteArrayResource> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.contentLength())
                .headers(headers)
                .body(file);
        // Act
        ResponseEntity<ByteArrayResource> actualResponse = DownloadFileUtils.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PDF)
                .downloadedFileName(downloadedFileName)
                .file(file)
                .build();
        // Assert
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

}
