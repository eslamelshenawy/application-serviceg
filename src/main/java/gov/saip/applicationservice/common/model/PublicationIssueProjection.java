package gov.saip.applicationservice.common.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;

public record PublicationIssueProjection(
        Long issueId,
        Long issueNumber,
        LocalDate issuingDateGregorian,
        String issuingDateHijri,
        Long numberOfApplications
) {

    // e.g. 1444-07-12
    public static final DateTimeFormatter yyyy_MM_dd_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PublicationIssueProjection(Long issueId, Long issueNumber, LocalDateTime issuingDateGregorian, Long numberOfApplications) {
        this(issueId,
                issueNumber,
                issuingDateGregorian.toLocalDate(),
                HijrahDate.from(issuingDateGregorian).format(yyyy_MM_dd_FORMATTER),
                numberOfApplications);
    }
}
