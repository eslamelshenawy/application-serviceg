package gov.saip.applicationservice.common.dto.agency;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.model.agency.ClientType;
import lombok.Data;

@Data
public class LkClientTypeDto extends BaseDto<Integer> {
    private String typeEn;
    private String typeAr;
    private ClientType code;
}
