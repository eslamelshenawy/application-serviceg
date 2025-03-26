package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.PublicationFrequency;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

@Entity
@Table(name = "publication_time")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicationTime extends BaseEntity<Long> {
    // We store this as LocalDateTime, but we only care about the hour and minute
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn
    private LkDayOfWeek dayOfWeek;

    @Range(min = 1, max = 28)
    private Integer dayOfMonth;

    public static boolean isIssuingDateWithinCutOffPeriod(Clock clock, LocalDateTime issuingDate, int cutOffInDays) {
        LocalDateTime cutOffDate = issuingDate.minus(cutOffInDays, ChronoUnit.DAYS);
        return cutOffDate.isAfter(LocalDateTime.now(clock));
    }

    public LocalDateTime toIssuingDate(Clock clock, int issueCutOffInDays, PublicationFrequency publicationFrequency) {
        return switch (publicationFrequency) {
            case WEEKLY -> toWeeklyIssuingDate(clock, issueCutOffInDays);
            case MONTHLY -> toMonthlyIssuingDate(clock, issueCutOffInDays);
        };
    }

    private LocalDateTime toWeeklyIssuingDate(Clock clock, int issueCutOffInDays) {
        LocalDateTime now = nowWithIssuingHourAndMinute(clock);

        DayOfWeek dayOfWeekEnum = toEnum(dayOfWeek);
        TemporalAdjuster nextDayOfWeek = TemporalAdjusters.next(dayOfWeekEnum);
        // For example, get the next Sunday
        LocalDateTime issuingDate = now.with(nextDayOfWeek);
        if (isIssuingDateWithinCutOffPeriod(clock, issuingDate, issueCutOffInDays)) {
            return issuingDate;
        } else {
            // This Sunday's cutoff has passed, so get the Sunday after it
            return issuingDate.with(nextDayOfWeek);
        }
    }

    private DayOfWeek toEnum(LkDayOfWeek dayOfWeek) {
        return switch (dayOfWeek.getCode()) {
            case "SUNDAY" -> DayOfWeek.SUNDAY;
            case "MONDAY" -> DayOfWeek.MONDAY;
            case "TUESDAY" -> DayOfWeek.TUESDAY;
            case "WEDNESDAY" -> DayOfWeek.WEDNESDAY;
            case "THURSDAY" -> DayOfWeek.THURSDAY;
            case "FRIDAY" -> DayOfWeek.FRIDAY;
            case "SATURDAY" -> DayOfWeek.SATURDAY;
            default -> throw new IllegalStateException("Unexpected value: " + dayOfWeek.getCode());
        };
    }

    private LocalDateTime toMonthlyIssuingDate(Clock clock, int issueCutOffInDays) {
        LocalDateTime now = nowWithIssuingHourAndMinute(clock);

        boolean issuingDayOfMonthHasNotPassedInCurrentMonth = now.getDayOfMonth() <= dayOfMonth;

        LocalDateTime issuingDate;
        if (issuingDayOfMonthHasNotPassedInCurrentMonth) {
            issuingDate = now.withDayOfMonth(dayOfMonth);
        } else {
            issuingDate = now.plusMonths(1).withDayOfMonth(dayOfMonth);
        }

        if (isIssuingDateWithinCutOffPeriod(clock, issuingDate, issueCutOffInDays)) {
            return issuingDate;
        } else {
            // This day of the month's cutoff period has passed so add one month
            return issuingDate.plusMonths(1);
        }
    }

    private LocalDateTime nowWithIssuingHourAndMinute(Clock clock) {
        return LocalDateTime
                .now(clock)
                .withHour(time.getHour())
                .withMinute(time.getMinute());
    }
}
