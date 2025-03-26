package gov.saip.applicationservice.common.dto.patent;

import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
public class PctRequestDto {
    private Long id;
    private Long applicationId;
    private String pctApplicationNo;
    @NotNull(message = Constants.ErrorKeys.FILLING_DATE_GR_REQUIRED)
    private LocalDate filingDateGr;
    private String publishNo;
    private String wipoUrl;
    private String petitionNumber;
    private boolean active = Boolean.TRUE;
    private PatentDetailsRequestDto patentDetailsRequestDto;
    private LocalDate internationalPublicationDate;
    private DocumentLightDto pctCopyDocument;
}
