package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantVarietyDto extends BaseDto<Long> {
    private ApplicationInfo application;
}
