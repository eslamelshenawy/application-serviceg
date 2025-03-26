package gov.saip.applicationservice.common.dto.lookup;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.ApplicationTypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LKNexuoUserDto extends BaseDto<Long> implements Serializable {


    private String name;
    private ApplicationTypeEnum type;

}
