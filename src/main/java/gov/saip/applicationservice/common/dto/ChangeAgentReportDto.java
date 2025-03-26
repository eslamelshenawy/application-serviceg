package gov.saip.applicationservice.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ChangeAgentReportDto {
    private int rowNumber;
    private  String applicationNumber;
    private LocalDateTime modifiedDate;
    private Long id;
    private String newAgentName;
    private String oldAgentName;
    private Long customerId;

    public void setModifiedDateHigri(String modifiedDateHigri) {
        this.modifiedDateHigri = modifiedDateHigri + " ه ";
    }

    private String modifiedDateHigri;




    public ChangeAgentReportDto(Long id , String applicationNumber, LocalDateTime modifiedDate, Long customerId) {
        this.id = id;
        this.applicationNumber = applicationNumber;
        this.modifiedDate = modifiedDate;
        this.customerId = customerId;
    }
}
