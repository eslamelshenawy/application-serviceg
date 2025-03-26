package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DUSTestingDocumentListDto {
    private Long id;
    private DocumentLightDto document;
    private Long countryId;
    private CountryDto countryDto;
}
