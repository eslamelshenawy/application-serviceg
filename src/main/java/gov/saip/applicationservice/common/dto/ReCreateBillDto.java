package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ReCreateBillDto {
    private Long  applicationId  ;
    private String oldBill;
    private String newBill;
}
