package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApplicationDrawingDto extends BaseDto<Long> {

    private Long applicationId;
    private DocumentLightDto document;
    private String title;
    private String numbering;
    private boolean isDefault;
}
