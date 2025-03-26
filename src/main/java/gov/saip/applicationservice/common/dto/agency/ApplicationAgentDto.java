package gov.saip.applicationservice.common.dto.agency;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.enums.ApplicationAgentStatus;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ApplicationAgentDto extends BaseDto<Long> {
    private Long applicationId;
    private Long customerId;
    private ApplicationAgentStatus status;
    private LocalDate expirationDate;
    private List<DocumentDto> applicationAgentDocuments = new ArrayList<>();
}
