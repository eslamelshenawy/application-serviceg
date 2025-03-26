package gov.saip.applicationservice.common.dto.certs;

import gov.saip.applicationservice.common.enums.SupportServiceType;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CertificateRequestSearchFilterDto {

    private String searchField;
    private Long applicationId;
    private String applicationNumber;
    private String applicationTitle;
    private String certificateTypeName;
    private String certificateStatusName;
    private LocalDate depositDate;
    private String customerCode;
    private String customerName;
    private String applicantName;
    private Long requestType;
    private SupportServiceType serviceType;
    private String requestNumber;
}
