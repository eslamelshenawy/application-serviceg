package gov.saip.applicationservice.common.dto.publication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatentPublicationGazetteBatchViewDto extends PublicationBatchViewDto {
    
    private LocalDate PublicationDate;
    
    @JsonIgnore
    private List<ApplicationRelevantTypeLightDto> applicationRelevantTypes;
    
    private String agentNameEn;
    
    private String agentNameAr;
    
    private ApplicationRelevantTypeLightDto inventorName;
    
    private ApplicationRelevantTypeLightDto relevantName;
    
    private LocalDate publicationDate;
    
    private String publicationTypeEn;
    
    private String publicationTypeAr;
    
    private String publicationCode;
    
    private Long applicationPublicationId;
    
    @JsonIgnore
    private String customerCode;
    
    private ApplicationRelevantTypeLightDto agentName;

}