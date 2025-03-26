package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.patent.PctRequestDto;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApplicantsRequestDto extends BaseApplicantsRequestDto {
    private Document pltDocument;
    private String pltDescription;
    private Boolean pltRegisteration;
    private PctRequestDto pctRequestDto;
}
