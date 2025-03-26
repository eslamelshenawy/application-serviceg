package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.ProtectionExtendTypeEnum;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class  ProtectionExtendRequestDto extends BaseDto<Long> {
    private Long applicationId;
    private ProtectionExtendTypeEnum type;
    private int claimCount;
    private String claimNumber;
    private DocumentLightDto supportDocument;
    private DocumentLightDto poaDocument;
    private DocumentLightDto waiveDocument;
    private String requestNumber;
    private LKSupportServiceRequestStatus requestStatus;
}
