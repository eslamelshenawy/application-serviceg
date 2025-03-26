package gov.saip.applicationservice.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TestUtils {
    public static final String WEDNESDAY_2023_06_14_AT_01_00 = "2023-06-14T01:00:00.00Z";
    public static final Clock WEDNESDAY_2023_06_14_AT_01_00_CLOCK = createFixedClock(WEDNESDAY_2023_06_14_AT_01_00);
    public static final String WEDNESDAY_2023_06_14_AT_01_00_HIJRI = "1444-11-25";
    /**
     * <p>This clock will cause any call to {@link LocalDateTime#now(Clock)} to return the same time as the one parsed from the input string ISO datetime string.</p>
     *
     * @param isoDateTime The datetime that should always be returned in ISO format, e.g. "2023-06-14T01:00:00.00Z".
     */
    public static Clock createFixedClock(String isoDateTime) {
        return Clock.fixed(Instant.parse(isoDateTime), ZoneId.systemDefault());
    }

}
