package gov.saip.applicationservice.common.dto.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillResponseModel {
    private String billerId;
    private String batchId;
    private String billIban;
    private String requestUniversallyUniqueId;
    private String serviceProviderReceivedDateTime;
    private String clientDateTime;
    private String clientMessageId;
    private String statusCode;
    private String statusDescription;
    private String billingGatewayTrackingId;
    private String billNumber;
    private Date expirationDate;
    private Double totalCost;
    private Double totalPenaltyCost;
    private Double totalTaxCost;
}
