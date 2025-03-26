package gov.saip.applicationservice.common.dto.payment;

import gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FeeCostDto {
	@NotNull
	private String applicationCategoryKey;
	@NotNull
	private String  applicantCategoryKey;
	@NotNull
    private String requestKey;
	@NotNull
	private Integer numberOfElement = 1;
	private String orgSizeCode;
	private Long applicationCategoryId;
	private Long applicantCategoryId;
	private Long requestId;
	private Double cost;
	private boolean addPenalty;

}
