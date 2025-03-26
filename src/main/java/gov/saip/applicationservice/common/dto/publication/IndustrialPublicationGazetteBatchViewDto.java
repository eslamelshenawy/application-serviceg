package gov.saip.applicationservice.common.dto.publication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class IndustrialPublicationGazetteBatchViewDto extends PublicationBatchViewDto{
    
    private String ipStatusDescAr;
    
    private String ipStatusDescEn;
    
    LocalDate PublicationDate;
    
    String designerNameAr;
    
    String designerNameEn;
    
    private LocalDate publicationDate;
    
    private String publicationTypeEn;
    
    private String publicationTypeAr;
    
    private String publicationCode;
    
    @JsonIgnore
    private List<ApplicationRelevantTypeLightDto> applicationRelevantTypes;
    
    private Long applicationPublicationId;
    
    private String agentNameEn;
    
    private String agentNameAr;

}
