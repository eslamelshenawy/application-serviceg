package gov.saip.applicationservice.exception;

import gov.saip.applicationservice.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionDto {
    private String message;
    private String arMessage;
    private HttpStatus httpStatus;
    private String zonedDateTime;
    private String cause;
    private String source;
    private String requestId = MDC.get(Constants.REQUEST_CORRELATION_ID);

    public ApiExceptionDto(String message, String arMessage, HttpStatus httpStatus, String zonedDateTime, String cause, String source) {
        this.message = message;
        this.arMessage = arMessage;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
        this.cause = cause;
        this.source = source;
    }

    public ApiExceptionDto(String message, String arMessage, HttpStatus httpStatus, String zonedDateTime){
        this.message= message;
        this.arMessage = arMessage;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }

    public ApiExceptionDto(String message, String arMessage, HttpStatus httpStatus, String zonedDateTime, String source){
        this.message= message;
        this.arMessage = arMessage;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
        this.source =source;
    }

}
