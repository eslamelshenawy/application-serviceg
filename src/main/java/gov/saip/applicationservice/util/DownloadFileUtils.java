package gov.saip.applicationservice.util;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class DownloadFileUtils {

    private static final String DEFAULT_FILE_NAME = "file";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.OK;
    private static final MediaType DEFAULT_CONTENT_TYPE = MediaType.APPLICATION_OCTET_STREAM;


    public static class Builder {

        private ByteArrayResource file;
        private String downloadedFileName;
        private HttpStatus httpStatus;
        private MediaType contentType;

        public Builder file(ByteArrayResource file) {
            this.file = file;
            return this;
        }

        public Builder downloadedFileName(String downloadedFileName) {
            this.downloadedFileName = downloadedFileName;
            return this;
        }

        public Builder httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder contentType(MediaType contentType) {
            this.contentType = contentType;
            return this;
        }

        public ResponseEntity<ByteArrayResource> build() {
            file = file == null ? new ByteArrayResource(new byte[]{}) : file;
            downloadedFileName = downloadedFileName == null ? DEFAULT_FILE_NAME : downloadedFileName;
            httpStatus = httpStatus == null ? DEFAULT_HTTP_STATUS : httpStatus;
            contentType = contentType == null ? DEFAULT_CONTENT_TYPE : contentType;
            return buildResponseEntityUsingFile(file, downloadedFileName, httpStatus, contentType);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private static ResponseEntity<ByteArrayResource> buildResponseEntityUsingFile(ByteArrayResource file, String downloadedFileName, HttpStatus httpStatus, MediaType contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=" + downloadedFileName);
        return ResponseEntity.status(httpStatus)
                .contentType(contentType)
                .contentLength(file.contentLength())
                .headers(headers)
                .body(file);
    }

}
