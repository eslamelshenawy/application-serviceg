package gov.saip.applicationservice.common.dto.supportService;

import gov.saip.applicationservice.common.dto.BaseSupportServiceDto;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.dto.trademark.LkTagTypeDescDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationEditTrademarkImageRequestDto extends BaseSupportServiceDto {

    private DocumentLightDto oldDocument;
    
    private DocumentLightDto newDocument;
    
    private String oldDescription;
    
    private String newDescription;
    
    private String oldNameAr;
    
    private String newNameAr;
    
    private String oldNameEn;
    
    private String newNameEn;
    
    private String ownerNameAr;
    
    private String ownerNameEn;
    private String applicantNameAr;


    private String applicantNameEn;
    
    private String applicationNumber;
    private String createdByCustomerCode;
    
    private LkTagTypeDescDto markTag;
    
    private LkTagTypeDescDto markType;
}
