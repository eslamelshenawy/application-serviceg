package gov.saip.applicationservice.common.dto.supportService;


import gov.saip.applicationservice.common.dto.BaseSupportServiceDto;
import gov.saip.applicationservice.common.enums.support_services_enums.EditTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class ApplicationEditNameAddressRequestDto extends BaseSupportServiceDto {

    private String newOwnerNameAr;

    private String newOwnerNameEn;

    private String newOwnerAddressAr;

    private String newOwnerAddressEn;

    private String notes;

    @Enumerated(EnumType.STRING)
    private EditTypeEnum editType;

    private String applicantNameAr;

    private String applicantNameEn;
}
