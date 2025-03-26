package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApplicationCustomerDto {
    private Long customerId;
    private ApplicationCustomerType customerType;

}
