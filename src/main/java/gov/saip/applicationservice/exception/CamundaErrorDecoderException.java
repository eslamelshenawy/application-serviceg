package gov.saip.applicationservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CamundaErrorDecoderException extends ErrorDecoderException {

    private CamundaErrorResponse response;

    public CamundaErrorDecoderException(String message, HttpStatus httpStatus, String [] params) {
        super(message, httpStatus, params);
    }

    public CamundaErrorDecoderException(CamundaErrorResponse response, HttpStatus httpStatus) {
        super(httpStatus);
        this.response=response;
    }

    public CamundaErrorDecoderException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public CamundaErrorDecoderException(String message) {
        super(message);
    }

}
