package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationInfoTaskDto {
    private Long id;
    private String applicationNumber;
    private String titleEn;
    private String titleAr;
    private String applicationRequestNumber;
    private ApplicationStatusDto applicationStatus;
    private LKApplicationCategoryDto category;
    private String grantNumber;
}
