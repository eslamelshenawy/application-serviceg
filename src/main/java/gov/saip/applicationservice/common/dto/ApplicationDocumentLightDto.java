package gov.saip.applicationservice.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationDocumentLightDto {
    
    Long applicationId;
    
    private String fileReviewUrl;
    
    @JsonIgnore
    private String fileName;
    
}
