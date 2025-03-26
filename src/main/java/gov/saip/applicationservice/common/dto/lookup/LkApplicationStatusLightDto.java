package gov.saip.applicationservice.common.dto.lookup;

import lombok.Data;

@Data
public class LkApplicationStatusLightDto {
    
    Long id;
    
    private String ipsStatusDescEn;
    
    private String ipsStatusDescAr;
    
    private String code;
}
