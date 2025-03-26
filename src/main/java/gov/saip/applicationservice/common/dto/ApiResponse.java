package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.exception.ApiExceptionDto;
import gov.saip.applicationservice.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    @Builder.Default
    private Boolean success = true;
    private ApiExceptionDto error;
    @Builder.Default
    private Integer code = HttpStatus.OK.value();
    private T payload;
    private String serviceTime;
        private String requestId = MDC.get(Constants.REQUEST_CORRELATION_ID);


        public ApiResponse(Boolean success, ApiExceptionDto error, Integer code, T payload, String serviceTime) {
            this.success = success;
            this.error = error;
            this.code = code;
            this.payload = payload;
            this.serviceTime = serviceTime;
        }

    public static <T> ApiResponse<T> ok(T payload) {
        return status(HttpStatus.OK, payload, null);
    }

    public static <T> ApiResponse<T> ok() {
        return status(HttpStatus.OK, null, null);
    }

    public static <T> ApiResponse<T> created(T payload) {
        return status(HttpStatus.CREATED, payload, null);
    }

    public static <T> ApiResponse<T> accepted(T payload) {
        return status(HttpStatus.ACCEPTED, payload, null);
    }

    public static <T> ApiResponse<T> ok(T payload, String serviceTime) {
        return status(HttpStatus.OK, payload, serviceTime);
    }

    public static <T> ApiResponse<T> created(T payload, String serviceTime) {
        return status(HttpStatus.CREATED, payload, serviceTime);
    }

    public static <T> ApiResponse<T> accepted(T payload, String serviceTime) {
        return status(HttpStatus.ACCEPTED, payload, serviceTime);
    }

    public static <T> ApiResponse<T> status(HttpStatus status, T payload, String serviceTime) {
        return new ApiResponse<>(true, null, status.value(), payload, serviceTime);
    }

    public static <T> ApiResponse<T> noContent() {
        return status(HttpStatus.NO_CONTENT, null, null);
    }

}