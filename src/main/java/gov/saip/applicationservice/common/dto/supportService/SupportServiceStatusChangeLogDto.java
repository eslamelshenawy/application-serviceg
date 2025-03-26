package gov.saip.applicationservice.common.dto.supportService;

import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.enums.support_services_enums.SupportServiceChangeLogDescriptionCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupportServiceStatusChangeLogDto extends BaseStatusChangeLogDto {
    private SupportServiceChangeLogDescriptionCode descriptionCode;
    private Long supportServicesTypeId;
}
