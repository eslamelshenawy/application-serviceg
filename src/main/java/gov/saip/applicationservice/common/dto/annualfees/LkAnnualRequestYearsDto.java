package gov.saip.applicationservice.common.dto.annualfees;

import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LkAnnualRequestYearsDto extends BaseLkpEntityDto<Long> {
    private List<String> costsCodes;

}
