package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LkClassificationVersionDto  extends BaseLkpEntityDto<Integer> {


    private Long categoryId;


}
