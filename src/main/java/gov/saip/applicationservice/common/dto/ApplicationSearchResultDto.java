package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.model.Document;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationSearchResultDto extends BaseDto<Long> {

    private Long applicationId;
    private LocalDate resultDate;
    private String relationOfProtectionElements;
    private Long countryId;
    private String resultLink;
    private DocumentLightDto document ;
    private String sameDocument;
    private String result;
}
