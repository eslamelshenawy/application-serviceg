package gov.saip.applicationservice.common.dto.trademark;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.ApplicationPublicationSummaryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class TradeMarkThirdPartyDto {
    private long id;
    private DocumentDto poaDocument;
    private DocumentDto tmImage;
    private RequestTasksDto task;
    private List<RequestTasksDto> allTasks;
    private String requestNotes;
    private String requestId;

    private DocumentDto legalDocuments;
    private List<ApplicationCertificateDocumentDto> applicationCertificateDocumentDtos;

    private List<DocumentDto> applicationDocuments;


}
