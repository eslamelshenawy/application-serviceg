package gov.saip.applicationservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDetail {

    private String error;
    private Integer status;
    private String pointer;
    private String details;
}