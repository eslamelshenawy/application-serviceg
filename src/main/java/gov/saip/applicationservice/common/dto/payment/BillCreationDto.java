package gov.saip.applicationservice.common.dto.payment;

import gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
public class BillCreationDto {
	private List<FeeCostDto>  cost;
	@NotNull
	private Long applicationId;
	private Double totoalCost;
	private Double totalCostwithTaxes;
	private Double taxes;
	@NotNull
	private String serviceCode;
	@NotNull
	@Enumerated(EnumType.STRING)
	private ApplicationPaymentMainRequestTypesEnum mainRequestType;
	private Long serviceId;
	private Integer expirePeriod;
	private boolean forceCreateBill;
}
