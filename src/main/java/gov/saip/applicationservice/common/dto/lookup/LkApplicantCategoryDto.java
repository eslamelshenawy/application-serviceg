package gov.saip.applicationservice.common.dto.lookup;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LkApplicantCategoryDto extends BaseDto<Long> implements Serializable {
    private String applicantCategoryNameAr;

    private String applicantCategoryNameEn;

    private String saipCode;
}
