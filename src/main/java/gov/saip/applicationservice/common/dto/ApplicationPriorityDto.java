package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.util.Constants;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ApplicationPriorityDto {

    private Long id;

    private String priorityApplicationNumber;
    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    private Long countryId;
    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    private LocalDate filingDate;

    private String applicationClass;

    private Boolean provideDocLater;

    private Long priorityDocument;

    private Long translatedDocument;

    private String dasCode;
    private Long applicationId ;

}
