package gov.saip.applicationservice.util.objectmother;

import gov.saip.applicationservice.common.dto.PublicationTimeCreateDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class PublicationTimeTestFactory {
    public static final LocalDateTime TIME = LocalDateTime.of(2020, 1, 1, 15, 0);
    public static final Integer DAY_OF_MONTH = 5;

    public static PublicationTimeCreateDto.PublicationTimeCreateDtoBuilder aDefaultWeeklyPublicationTimeCreateDto(DayOfWeek dayOfWeek) {
        return PublicationTimeCreateDto.builder()
                .dayOfWeekCode(dayOfWeek)
                .time(TIME)
                .dayOfMonth(null);
    }

    public static PublicationTimeCreateDto.PublicationTimeCreateDtoBuilder aDefaultMonthlyPublicationTimeCreateDto() {
        return PublicationTimeCreateDto.builder()
                .dayOfWeekCode(null)
                .time(TIME)
                .dayOfMonth(DAY_OF_MONTH);
    }
}
