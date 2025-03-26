package gov.saip.applicationservice.common.dto.veena;

import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import gov.saip.applicationservice.common.model.veena.LKVeenaDepartment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LKVeenaAssistantDepartmentDto extends BaseLkpEntityDto<Long> {

    private String nameVeenaDepartmentEn;
    private String nameVeenaDepartmentAr;
    private String nameVeenaClassificationEn;
    private String nameVeenaClassificationAr;
    private String nameVeenaClassificationCode;
    private LKVeenaDepartmentDto veenaDepartment;



}
