package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class OtherPlantVarietyDocumentsDto  extends BaseDto<Long> {
    private ApplicationInfo application;
    private String  fileName;
    private DocumentLightDto document;
}
