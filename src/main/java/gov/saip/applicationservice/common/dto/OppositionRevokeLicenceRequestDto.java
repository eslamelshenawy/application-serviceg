package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.ApplicantType;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OppositionRevokeLicenceRequestDto extends BaseDto<Long> {
    private RevokeLicenceRequestDto revokeLicenceRequest;
    private String objectionReason;
    private List<DocumentDto> documents;
    private List<Long> documentIds;
    private String createdByUser;
    private LocalDateTime createdDate;
    private String applicantNameAr;
    private String applicantCustomerCode;
    private String applicantNameEn;
    private String requestNumber;
    private LKSupportServiceRequestStatus requestStatus;
    private Long applicationId;
    private String courtDocumentNotes;
    private List<DocumentDto> courtDocuments;
}
