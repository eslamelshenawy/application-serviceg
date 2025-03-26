package gov.saip.applicationservice.common.dto.lookup;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Setter
@Getter
public class LkApplicationPriorityStatusDto extends BaseDto<Long> implements Serializable {

    private String ipsStatusDescEn;

    private String ipsStatusDescAr;
    private String code;
}
