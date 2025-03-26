package gov.saip.applicationservice.common.dto.publication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IndustrialPublicationVerificationBatchViewDto extends PublicationBatchViewDto {
    
    @JsonIgnore
    private String ipStatusDescAr;
    
    @JsonIgnore
    private String ipStatusDescEn;
    
    @JsonIgnore
    private String statusCode;
    
    LocalDate PublicationDate;
    
    
}
