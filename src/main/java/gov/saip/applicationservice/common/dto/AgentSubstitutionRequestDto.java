package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class AgentSubstitutionRequestDto extends BaseDto<Long> {

    private LKSupportServiceType lkSupportServiceType;
    private DocumentLightDto delegationDocument;
    private DocumentLightDto evictionDocument;
    @NotNull
    private Long customerId;
    @NotNull @NotEmpty
    private List<Long> applicationIds;

}
