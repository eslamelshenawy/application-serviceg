package gov.saip.applicationservice.common.dto.publication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.saip.applicationservice.common.dto.ApplicationDocumentLightDto;
import lombok.Data;

@Data
public class TrademarkPublicationVerificationBatchViewDto extends PublicationBatchViewDto {
    
    @JsonIgnore
    private String ipStatusDescAr;
    
    @JsonIgnore
    private String ipStatusDescEn;
    
    @JsonIgnore
    private String statusCode;
    
    private ApplicationDocumentLightDto document;
    
    private String tmTypeAr;
    
    private String tmTypeEn;
    
    private String tmTagCode;
    
    
}
