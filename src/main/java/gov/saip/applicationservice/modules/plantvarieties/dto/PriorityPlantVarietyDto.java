package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PriorityPlantVarietyDto extends PlantVarietyDto {
    private Long id;
    private DocumentDto vegetarianDocumentPerviousTest;
    private List<DUSTestingDocumentListDto> dusTestingDocumentListDto;
    private List<FillingRequestInOtherCountryDto> fillingRequestInOtherCountryDto;
    private List<PriorityDataSectionPlantVarietyDto> priorityDataSectionPlantVarietyDto;
}



