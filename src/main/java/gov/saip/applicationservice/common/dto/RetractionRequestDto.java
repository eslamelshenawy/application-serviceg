package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Setter
@Getter
public class RetractionRequestDto extends BaseDto<Long> {

    private Long applicationId;
    private LKSupportServiceType lkSupportServiceType;
    private DocumentLightDto RetractionReasonDocument;
    private String requestNumber;
    private LKSupportServiceRequestStatus requestStatus;

}
