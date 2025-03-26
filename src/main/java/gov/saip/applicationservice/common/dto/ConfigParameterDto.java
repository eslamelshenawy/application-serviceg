package gov.saip.applicationservice.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfigParameterDto {

    private Long id;

    private String name;

    private String value;
    
    private String nameAr;
    
    private String nameEn;
    
    private int isDeleted;
}
