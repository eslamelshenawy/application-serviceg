package gov.saip.applicationservice.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CamundaErrorResponse {

    private String type;

    private String message;

    private String code;
}
