package gov.saip.applicationservice.common.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PetitionRequestNationalStageDto extends BaseSupportServiceDto {

    private String globalApplicationNumber;
    private List<DocumentDto> petitionRequestNationalStageDocuments;
    private String reason;
    private List<Long> documentIds;

}
