package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RevokeByCourtOrderRequestDto extends BaseDto<Long> {

    private Long applicationId;
    private String notes;
    private List<Long> documentIds;
    private String requestNumber;
    private LKSupportServiceRequestStatus requestStatus;
    private List<DocumentDto> documents;
    private String createdByUser;
    private LocalDateTime createdDate;
    private String applicantNameAr;
    private String applicantNameEn;

}
