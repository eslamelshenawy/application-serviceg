package gov.saip.applicationservice.common.dto.opposition;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.DocumentWithTypeDto;
import gov.saip.applicationservice.common.enums.opposition.OppositionType;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OppositionSupportServiceDto extends BaseDto<Long> {
    private OppositionType type;
    private String oppositionReason;
    private List<DocumentWithTypeDto> documents;
    private List<ClassificationDto> classifications;
    private OppositionLegalRepresentativeDto oppositionLegalRepresentative;
    private HearingSessionDto hearingSession;
    private String requestNumber;
    private Long applicationId;
    private LKSupportServiceRequestStatus requestStatus;
}
