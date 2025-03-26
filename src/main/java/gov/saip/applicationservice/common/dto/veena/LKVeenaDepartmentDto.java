package gov.saip.applicationservice.common.dto.veena;

import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import gov.saip.applicationservice.common.model.veena.LKVeenaClassification;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class LKVeenaDepartmentDto extends BaseLkpEntityDto<Long> {
    private String nameVeenaClassificationAr;
    private String nameVeenaClassificationEn;
    private LKVeenaClassificationDto veenaClassification;

}
