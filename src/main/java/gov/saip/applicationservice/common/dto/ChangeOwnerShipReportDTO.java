package gov.saip.applicationservice.common.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ChangeOwnerShipReportDTO {
    private int rowNumber;
    private Long customerId;
    private LocalDateTime modifiedDate;
    private String applicationNumber;
    private String newCustomerNameAr;
    private String oldCustomerNameAr;
    private Long id;
    private String modifiedDateHigri;

    public void setModifiedDateHigri(String modifiedDateHigri) {
        this.modifiedDateHigri = modifiedDateHigri + " ه ";
    }

    public ChangeOwnerShipReportDTO(Long id , Long customerId, LocalDateTime modifiedDate, String applicationNumber) {
        this.id = id;
        this.customerId = customerId;
        this.modifiedDate = modifiedDate;
        this.applicationNumber = applicationNumber;
    }
}
