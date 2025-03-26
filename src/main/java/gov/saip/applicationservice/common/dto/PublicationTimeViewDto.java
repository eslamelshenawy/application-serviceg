package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.lookup.LkDayOfWeekDto;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationTimeViewDto extends BaseDto<Long> {
    private LkDayOfWeekDto dayOfWeek;
    @NotNull
    private LocalDateTime time;
    @Range(min = 1, max = 28)
    private Integer dayOfMonth;
}
