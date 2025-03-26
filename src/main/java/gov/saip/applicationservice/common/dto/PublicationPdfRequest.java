package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PublicationPdfRequest {
    private Long publicationIssueId;

    public void setStartDate(String startDate) {
        this.startDate = startDate + " ه ";
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate + " ه ";
    }

    private String startDate;
    private String endDate;
    List<PublishedPatentApplicationDto> publicationPdfDTOS;
    List<PublishedPatentApplicationDto> rejectedPublishedPatentApplicationDtos;
    List<GrantedPublishedPatentApplicationDto> grantedPublishedPatentApplicationDtos;
    List<ChangeOwnerShipReportDTO> changeOwnerShipReportDTOS;
    List<ChangeAgentReportDto> changeAgentReportDtos;
}

