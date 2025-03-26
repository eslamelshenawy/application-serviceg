package gov.saip.applicationservice.exception;

import gov.saip.applicationservice.common.dto.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppErrorDecoderException extends ErrorDecoderException {

    private ApiResponse apiResponse;

    public AppErrorDecoderException(String message, HttpStatus httpStatus, String [] params) {
        super(message, httpStatus, params);
    }


    public AppErrorDecoderException(ApiResponse response, HttpStatus httpStatus) {
        super(httpStatus);
        this.apiResponse =response;
    }

    public AppErrorDecoderException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public AppErrorDecoderException(String message) {
        super(message);
    }

}
