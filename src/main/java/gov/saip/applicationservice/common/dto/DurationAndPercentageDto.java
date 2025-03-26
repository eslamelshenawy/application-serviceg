package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class DurationAndPercentageDto {
    
    private DurationDto remainingDuration;
    
    private Double remainingDurationPercentage;

}
