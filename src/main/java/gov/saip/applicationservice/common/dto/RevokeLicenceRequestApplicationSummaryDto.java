package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RevokeLicenceRequestApplicationSummaryDto extends BaseDto<Long> {

    private ApplicationInfoSummaryDto applicationInfoSummaryDto;
    private RevokeLicenceRequestDto revokeLicenceRequestDto;

}
