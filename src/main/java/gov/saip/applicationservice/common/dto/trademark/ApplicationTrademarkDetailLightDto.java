package gov.saip.applicationservice.common.dto.trademark;

import lombok.Data;

@Data
public class ApplicationTrademarkDetailLightDto {
    
    Long appId;
    
    LkMarkTypeDto markType;
    
    String tmTypeAr;
    
    String tmTypeEn;
}
