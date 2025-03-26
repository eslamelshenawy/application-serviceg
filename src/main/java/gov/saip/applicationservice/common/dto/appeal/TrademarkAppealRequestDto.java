package gov.saip.applicationservice.common.dto.appeal;

import gov.saip.applicationservice.common.dto.ApplicationSupportServicesTypeDto;
import gov.saip.applicationservice.common.dto.BaseSupportServiceDto;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.enums.support_services_enums.TrademarkAppealRequestType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TrademarkAppealRequestDto extends BaseSupportServiceDto {
    private TrademarkAppealRequestType appealRequestType;
    private String appealReason;
    private String departmentReply;
    private ApplicationSupportServicesTypeDto supportServicesType;
    private List<DocumentLightDto> documents;
    private String finalDecisionNotes;
    private List<Long> documentIds;
    private Long supportServiceId;
}
