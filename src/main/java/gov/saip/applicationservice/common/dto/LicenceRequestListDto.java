package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class LicenceRequestListDto {

    private Long id;
    private String requestNumber;
    private LocalDateTime filingDate;
    private String titleEn;
    private String titleAr;
    private LKSupportServiceRequestStatus requestStatus;
    private String notes;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long applicationId;
    private Integer licenceValidityNumber;

    public LicenceRequestListDto(Long id, String requestNumber, LocalDateTime filingDate, String titleEn, String titleAr, LKSupportServiceRequestStatus requestStatus, String notes, LocalDateTime fromDate, LocalDateTime toDate, Long applicationId) {
        this.id = id;
        this.requestNumber = requestNumber;
        this.filingDate = filingDate;
        this.titleEn = titleEn;
        this.titleAr = titleAr;
        this.requestStatus = requestStatus;
        this.notes = notes;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.applicationId = applicationId;
    }
    public LicenceRequestListDto(Long id, String requestNumber, LocalDateTime filingDate, String titleEn, String titleAr, LKSupportServiceRequestStatus requestStatus, String notes, LocalDateTime fromDate, LocalDateTime toDate, Long applicationId,Integer licenceValidityNumber) {
        this.id = id;
        this.requestNumber = requestNumber;
        this.filingDate = filingDate;
        this.titleEn = titleEn;
        this.titleAr = titleAr;
        this.requestStatus = requestStatus;
        this.notes = notes;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.applicationId = applicationId;
        this.licenceValidityNumber=licenceValidityNumber;
    }
}
