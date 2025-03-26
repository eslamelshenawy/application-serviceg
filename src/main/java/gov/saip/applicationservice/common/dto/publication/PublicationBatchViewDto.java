package gov.saip.applicationservice.common.dto.publication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.saip.applicationservice.common.dto.lookup.LkStatusLightDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PublicationBatchViewDto {
    
    private Long applicationId;
    
    private String applicationTitleAr;
    
    private String applicationTitleEn;
    
    private String applicationNumber;
    
    private LocalDate depositDate;
    
    private LocalDate gazettePublicationDate;
    
    private LkStatusLightDto applicationStatus;
    
    private boolean haveRemainingTime;
    
    @JsonIgnore
    private LocalDateTime publicationDateTime;
    
    private String applicationRequestNumber;
    
}
