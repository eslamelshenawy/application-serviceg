package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.util.Constants;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto implements Serializable {


    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    @NotBlank(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    private String templateType;

    private String appName;
    private String emailSubject;
    private String message;
    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    @NotBlank(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    private String to;
    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    @NotBlank(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    private String messageType;
    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    @NotBlank(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    private Map<String, Object> templateParams;


}
