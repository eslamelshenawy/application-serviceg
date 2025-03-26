package gov.saip.applicationservice.common.model;

import java.time.LocalDateTime;

public record ApplicationPublicationSummaryProjection(
        String publicationNumber,
        LocalDateTime publicationDate,
        String publicationType,
        LocalDateTime registrationDate,
        Long issueNumber
) {}
