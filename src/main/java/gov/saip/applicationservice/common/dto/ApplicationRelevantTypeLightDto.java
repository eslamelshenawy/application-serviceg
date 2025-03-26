package gov.saip.applicationservice.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRelevantTypeLightDto {
    
    @JsonIgnore
    private Long id;
    
    @JsonIgnore
    private Long appId;
    
    @JsonIgnore
    private String customerCode;
    
    @JsonIgnore
    private String type;
    
    private ApplicationRelevantLightDto applicationRelevant;

    public ApplicationRelevantTypeLightDto(ApplicationRelevantEnum applicationRelevantEnum) {
        this.type = applicationRelevantEnum.name();
    }
}
