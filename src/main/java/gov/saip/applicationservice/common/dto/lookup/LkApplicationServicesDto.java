package gov.saip.applicationservice.common.dto.lookup;


import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LkApplicationServicesDto extends BaseDto<Long> {

    private String code;
    private String nameAr;
    private String nameEn;
    private String operationNumber;
    private String applicationCategoryDescAr;
    private String applicationCategoryDescEn;



}
