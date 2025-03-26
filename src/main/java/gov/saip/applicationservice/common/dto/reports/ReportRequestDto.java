package gov.saip.applicationservice.common.dto.reports;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto extends BaseDto<Long> {
    private String fileName;
    private Map<String, Object> params;
}
