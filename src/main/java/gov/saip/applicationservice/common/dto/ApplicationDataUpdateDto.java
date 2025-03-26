package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplicationDataUpdateDto {
    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    private String titleEn;
    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    private String titleAr;
    private String ipcNumber;
    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    private Boolean partialApplication = Boolean.FALSE;
    private String partialApplicationNumber;

    @NotNull(message = Constants.ErrorKeys.GENERAL_ERROR_MESSAGE)
    private Boolean nationalSecurity;
}
