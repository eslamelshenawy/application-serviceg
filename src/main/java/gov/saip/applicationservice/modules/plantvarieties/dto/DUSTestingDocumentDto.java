package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.common.dto.DocumentLightDto;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class DUSTestingDocumentDto extends PlantVarietyDto {
    private Long countryId;
    private DocumentLightDto document;
}
