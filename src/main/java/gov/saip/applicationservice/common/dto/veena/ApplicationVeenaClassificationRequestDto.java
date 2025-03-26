package gov.saip.applicationservice.common.dto.veena;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
public class ApplicationVeenaClassificationRequestDto extends BaseDto<Long> {

    @NotNull
    @Positive
    private Long applicationId;
    @NotNull
    @Positive
    private Long veenaClassificationId;
    @NotNull
    @Positive
    private Long veenaDepartmentId;
    @NotNull
    @Positive
    private Long veenaAssistantDepartmentId;
}
