package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApplicationNumberGenerationDto {
    private LocalDateTime paymentDate  ;
    private String serviceCode;
    private String mainRequestType;
    private String billNumber;
    private Double totalCost;
    private Double totalPenaltyCost;
    private Double totalTaxCost;
    private Double totalCostWithTax;
    private ApplicationPaymentMainRequestTypesEnum applicationPaymentMainRequestTypesEnum;
}
