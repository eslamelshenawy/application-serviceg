package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@ToString
public class BaseApplicantsRequestDto extends BaseDto<Long> {
    private Long appId;
    @NotNull
    private String customerCode;
    private String email;
    private String address;
    private String mobileCode;
    private String mobileNumber;
    private String categoryKey;
    @NotNull
    private Long createdByUserId;
    private Long createdByCustomerId;
    @Enumerated(EnumType.STRING)
    private ApplicationCustomerType createdByCustomerType;
    private List<ApplicationRelevantRequestsDto> relevants;
    private boolean byHimself;
    private String poaDocumentId;
    @NotNull
    private Long customerId;
}
