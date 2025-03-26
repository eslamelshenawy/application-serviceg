package gov.saip.applicationservice.common.dto.supportService;


import gov.saip.applicationservice.common.dto.BaseSupportServiceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationPriorityModifyRequestDto extends BaseSupportServiceDto {

    List<ApplicationPriorityModifyRequestDetailsDto> applicationPriorityDtoList;

    private boolean isRequestUpdated;
}
