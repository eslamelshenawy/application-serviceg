package gov.saip.applicationservice.common.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationBillLightDTO {

    private Date expiryDate;
    private String paymentStatus;
    private Date paymentDate;
}
