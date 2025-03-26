package gov.saip.applicationservice.common.dto.certs;

import gov.saip.applicationservice.common.model.LkCertificateType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CertificateRequestVerificationDto {
    
    Long applicationId;
    
    String applicationNumber;
    
    String applicationTitleAr;
    
    String applicationTitleEn;
    
    LkCertificateType certificateType;
    
    LocalDate depositDate;
    
    String requestNumber;
    String billNumber;
    private String applicationRequestNumber;
    String categoryCode;
    String categoryNameAr;
    String categoryNameEn;

    public CertificateRequestVerificationDto(Long applicationId, String applicationNumber,
                                             String applicationTitleAr, String applicationTitleEn,
                                             LkCertificateType certificateType, LocalDateTime depositDate, String requestNumber,
                                             String applicationRequestNumber, String categoryCode, String categoryNameAr, String categoryNameEn) {
        this.applicationId = applicationId;
        this.applicationNumber = applicationNumber;
        this.applicationTitleAr = applicationTitleAr;
        this.applicationTitleEn = applicationTitleEn;
        this.certificateType = certificateType;
        this.depositDate = depositDate.toLocalDate();
        this.requestNumber = requestNumber;
        this.applicationRequestNumber = applicationRequestNumber;
        this.categoryCode = categoryCode;
        this.categoryNameAr = categoryNameAr;
        this.categoryNameEn = categoryNameEn;
    }
}
