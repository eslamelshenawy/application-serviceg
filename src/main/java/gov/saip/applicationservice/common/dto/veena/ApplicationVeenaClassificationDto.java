package gov.saip.applicationservice.common.dto.veena;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationVeenaClassificationDto extends BaseDto<Long> {
    private LKVeenaClassificationDto veenaClassification;
    private LKVeenaDepartmentDto veenaDepartment;
    private LKVeenaAssistantDepartmentDto veenaAssistantDepartment;
}
