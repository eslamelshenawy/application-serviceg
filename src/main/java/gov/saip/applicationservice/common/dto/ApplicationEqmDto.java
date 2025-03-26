package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryLightDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusLightDto;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationEqmDto {
    
    private Long applicationId;
    
    private String applicationNumber;
    
    private String applicationTitleAr;
    
    private String applicationTitleEn;
    
    private LKApplicationCategoryLightDto applicationCategory = new LKApplicationCategoryLightDto();
    
    private LkApplicationStatusLightDto applicationStatus = new LkApplicationStatusLightDto();
    
    private String evaluation;
    
    public ApplicationEqmDto(Long applicationId, String applicationNumber, String applicationTitleAr,
                             String applicationTitleEn, LkApplicationCategory applicationCategory,
                             LkApplicationStatus applicationStatus) {
        this.applicationId = applicationId;
        this.applicationNumber = applicationNumber;
        this.applicationTitleAr = applicationTitleAr;
        this.applicationTitleEn = applicationTitleEn;
        mapApplicationCategory(applicationCategory);
        mapApplicationStatus(applicationStatus);
        // todo
//        this.evaluation = evaluation;
    }
    
    private void mapApplicationCategory(LkApplicationCategory applicationCategory) {
        this.applicationCategory.setId(applicationCategory.getId());
        this.applicationCategory.setApplicationCategoryDescAr(applicationCategory.getApplicationCategoryDescAr());
        this.applicationCategory.setApplicationCategoryDescEn(applicationCategory.getApplicationCategoryDescEn());
        this.applicationCategory.setSaipCode(applicationCategory.getSaipCode());
    }
    
    private void mapApplicationStatus(LkApplicationStatus applicationStatus) {
        this.applicationStatus.setId(applicationStatus.getId());
        this.applicationStatus.setIpsStatusDescAr(applicationStatus.getIpsStatusDescAr());
        this.applicationStatus.setIpsStatusDescEn(applicationStatus.getIpsStatusDescEn());
        this.applicationStatus.setCode(applicationStatus.getCode());
    }
}

