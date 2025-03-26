package gov.saip.applicationservice.common.dto.publication;

import gov.saip.applicationservice.common.dto.ApplicationDocumentLightDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrademarkGazettePublicationListDto extends PublicationBatchViewDto {
    
    private ApplicationDocumentLightDto document;
    
    private String tmTypeAr;
    
    private String tmTypeEn;
    
    private String tmTagCode;
    
    private String publicationTypeEn;
    
    private String publicationTypeAr;
    
    private String publicationCode;
    
    private ApplicationRelevantTypeLightDto applicationRelevantType;
    
    private Long applicationPublicationId;
    
    private LocalDate publicationDate;
    
    private String ownerNameAr;
    
    private String ownerNameEn;
    
}
