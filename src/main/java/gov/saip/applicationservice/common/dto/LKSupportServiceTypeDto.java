package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Setter
@Getter
public class LKSupportServiceTypeDto extends BaseDto<Long> {

    private String descAr;
    private String descEn;

    private String code;

    @Enumerated(EnumType.STRING)
    private SupportServiceType type;

}
