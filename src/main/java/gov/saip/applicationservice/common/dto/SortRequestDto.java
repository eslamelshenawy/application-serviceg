package gov.saip.applicationservice.common.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Data
public class SortRequestDto {
    
    private String sortBy;
    
    private Sort.Direction sortOrder = DESC;

}
