package gov.saip.applicationservice.common.dto.trademark;

import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TradeMarkLightDto {
    
    private String tagLanguageNameAr;
    
    private String tagLanguageNameEn;
    
    private String typeNameAr;
    
    private String typeNameEn;
    
    private String tagTypeNameEn;
    
    private String tagTypeNameAr;
    
    private String tagTypeCode;
    
    private String nameEn;
    
    private String nameAr;
    
    private DocumentDto trademarkImage;

    private String markDescription;

    private String examinerGrantCondition;

    private String markClaimingColor;
}