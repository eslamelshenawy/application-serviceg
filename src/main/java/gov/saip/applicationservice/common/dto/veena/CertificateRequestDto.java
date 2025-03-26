package gov.saip.applicationservice.common.dto.veena;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.dto.LkCertificateTypeDto;
import gov.saip.applicationservice.common.model.LkCertificateStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CertificateRequestDto extends BaseDto<Long> {
    private Long applicationInfoId;
    private LkCertificateTypeDto certificateType;
    private LkCertificateStatus certificateStatus;
    private DocumentLightDto document;
}
