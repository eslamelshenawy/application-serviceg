package gov.saip.applicationservice.common.dto.lookup;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LKApplicationCategoryLightDto {
    private Long id;
    private String applicationCategoryDescAr;
    private String applicationCategoryDescEn;
    private String saipCode;
}
