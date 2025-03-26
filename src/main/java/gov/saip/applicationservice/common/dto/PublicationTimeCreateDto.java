package gov.saip.applicationservice.common.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationTimeCreateDto {
    private DayOfWeek dayOfWeekCode;
    @NotNull
    private LocalDateTime time;
    @Range(min = 1, max = 28)
    private Integer dayOfMonth;
}
