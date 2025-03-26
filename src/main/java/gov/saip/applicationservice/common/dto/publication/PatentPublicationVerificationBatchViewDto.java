package gov.saip.applicationservice.common.dto.publication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusDto;
import gov.saip.applicationservice.common.dto.lookup.LkStatusLightDto;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatentPublicationVerificationBatchViewDto extends PublicationBatchViewDto {
    
    @JsonIgnore
    private String ipStatusDescAr;
    
    @JsonIgnore
    private String ipStatusDescEn;
    
    @JsonIgnore
    private String statusCode;
    
    private LocalDate PublicationDate;
    
//    private LkStatusLightDto applicationStatus;
    
}
