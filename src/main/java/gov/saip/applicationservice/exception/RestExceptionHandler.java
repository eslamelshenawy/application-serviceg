package gov.saip.applicationservice.exception;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Locale;


@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Value("${spring.application.name}")
    private String applicationName;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleApiRequestException(BusinessException businessException) {

        String message = messageSource.getMessage(businessException.getMessage(),
                businessException.getParams(), new Locale("en"));
        String arMessage = messageSource.getMessage(businessException.getMessage(),
                businessException.getParams(), new Locale("ar"));

        ApiExceptionDto apiException = new ApiExceptionDto(
                message,
                arMessage,
                businessException.getHttpStatus(),
                ZonedDateTime.now(ZoneId.of("Z")).toString(),
                applicationName
        );
        //
        return new ResponseEntity<>(ApiResponse.builder().error(apiException).success(false)
                .code(businessException.getHttpStatus().value()).build(), businessException.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        String fieldError = ex.getFieldError().getField();
        String messageKey = ex.getFieldError(fieldError).getDefaultMessage();
        //
        String message = messageSource.getMessage(messageKey,
                null, new Locale("en"));
        String arMessage = messageSource.getMessage(messageKey,
                null, new Locale("ar"));
        //
        ApiExceptionDto apiException = new ApiExceptionDto(
                message,
                arMessage,
                status,
                ZonedDateTime.now(ZoneId.of("Z")).toString()
        );
        //
        return new ResponseEntity<>(ApiResponse.builder().error(apiException).success(false)
                .code(status.value()).build(), status);
    }

    @ExceptionHandler(CamundaErrorDecoderException.class)
    public ResponseEntity<Object> handleFeignStatusException(CamundaErrorDecoderException ex) {
        log.error("exception details  == >> {} ", ex.getMessage());
        ApiExceptionDto apiException = new ApiExceptionDto(
                ex.getResponse().getMessage(),
                ex.getResponse().getMessage(),
                ex.getHttpStatus(),
                ZonedDateTime.now(ZoneId.of("Z")).toString()
        );
        return new ResponseEntity<>(ApiResponse.builder().error(apiException).success(false)
                .code(ex.getHttpStatus().value()).build(), ex.getHttpStatus());
    }



    @ExceptionHandler(AppErrorDecoderException.class)
    public ResponseEntity<Object> handleFeignStatusExceptionApp(AppErrorDecoderException ex) {
        log.error("exception details  == >> {} ", ex);
        return new ResponseEntity<>(ex.getApiResponse(), ex.getApiResponse().getError().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleApiRequestException(Exception exception) {
        log.error("internal server error happened  == >> {} ", exception.getMessage());
        log.error("internal server error happened exception details  == >> {} ", exception);
        String message = messageSource.getMessage(Constants.ErrorKeys.INTERNAL_SERVER_ERROR,
                null, new Locale("en"));
        String arMessage = messageSource.getMessage(Constants.ErrorKeys.INTERNAL_SERVER_ERROR,
                null, new Locale("ar"));

        ApiExceptionDto apiException = new ApiExceptionDto(
                message,
                arMessage,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z")).toString(),
                exception.getMessage(),
                applicationName

        );

        return new ResponseEntity<>(ApiResponse.builder().error(apiException).success(false)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataException(DataIntegrityViolationException ex) {
        log.error("DataIntegrityViolationException: {}", ex.getSuppressed());
        ex.printStackTrace();
        ApiExceptionDto apiException = new ApiExceptionDto(
                "internal server error",
                "حدث خطأ ما",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z")).toString(),
                "internal server error",
                applicationName

        );
        return new ResponseEntity<>(ApiResponse.builder().error(apiException).success(false)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}