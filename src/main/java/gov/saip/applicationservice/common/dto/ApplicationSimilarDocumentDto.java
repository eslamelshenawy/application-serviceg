package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ApplicationSimilarDocumentDto extends BaseDto<Long> {

    private Long applicationId;
    private Long countryId;
    private String publicationNumber;
    private String documentNumber;
    private LocalDate documentDate;
    private String documentLink;
    private DocumentLightDto document;


}
