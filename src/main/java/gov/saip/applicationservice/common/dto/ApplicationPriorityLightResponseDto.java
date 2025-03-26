package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.customer.CountryDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ApplicationPriorityLightResponseDto {

    private Long id;
    
    private String priorityApplicationNumber;

    private LocalDate filingDate;
    
    private int isDeleted;
    
    private CountryDto country;

}
