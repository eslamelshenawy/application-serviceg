package gov.saip.applicationservice.common.dto.supportService;


import gov.saip.applicationservice.common.dto.BaseSupportServiceDto;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.enums.support_services_enums.ModificationTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationPriorityRequestDto extends BaseSupportServiceDto {


    @Enumerated(EnumType.STRING)
    private ModificationTypeEnum modifyType;

    private String reason;

    private DocumentLightDto document;

    private boolean isRequestUpdated;
}
