package gov.saip.applicationservice.exception;

import gov.saip.applicationservice.common.dto.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ErrorDecoderException extends RuntimeException {

    private HttpStatus httpStatus;
    private  String [] params;
    private ApiResponse apiResponse;

    public ErrorDecoderException(String message, HttpStatus httpStatus, String [] params) {
        super(message);
        this.httpStatus = httpStatus;
        this.params = params;
    }

    public ErrorDecoderException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ErrorDecoderException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ErrorDecoderException(HttpStatus status) {
        this.httpStatus = status;
    }

}
