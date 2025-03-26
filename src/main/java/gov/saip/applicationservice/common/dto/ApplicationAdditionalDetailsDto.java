package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.model.ApplicationPublicationSummaryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ApplicationAdditionalDetailsDto {
    private ApplicationPublicationSummaryProjection lastPublicationSummary;
    private String lastProtectionDocumentNumber;
}
