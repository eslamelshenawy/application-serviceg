package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class PetitionRecoveryRequestDto extends BaseSupportServiceDto  {
    private Long applicationId;
    private LKSupportServiceType lkSupportServiceType;
    private DocumentLightDto evictionDocument;
    private DocumentLightDto recoveryDocument;
    private String justification;
    private List<DocumentLightDto> justificationDocuments;
    private boolean hideConditionalRejectionButton;
}
