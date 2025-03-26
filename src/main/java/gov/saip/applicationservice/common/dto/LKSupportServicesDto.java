package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Setter
@Getter
public class LKSupportServicesDto extends BaseDto<Long> {

    private String nameAr;
    private String nameEn;
    private String descAr;
    private String descEn;
    private int cost;
    @Enumerated(EnumType.STRING)
    private SupportServiceType code;

}
