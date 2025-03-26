package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryLightDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusLightDto;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ApplicationInfoBaseDto {

    private Long applicationId;

    private String applicationNumber;

    private String applicationTitleAr;

    private String applicationTitleEn;

    private LkApplicationStatusLightDto applicationStatus = new LkApplicationStatusLightDto();
    
    private LKApplicationCategoryLightDto applicationCategory = new LKApplicationCategoryLightDto();

    private LocalDate filingDate;

    private List<ClassificationDto> niceClassifications;
    
    private List<ApplicationRelevantTypeDto> applicationRelevantTypes;
    
    private String categoryDescAr;
    
    private String categoryDescEn;
    
    private String categoryCode;

    private String grantNumber;
    private String applicationRequestNumber;
    


    public ApplicationInfoBaseDto(Long applicationId, String applicationNumber, String applicationTitleAr,
                                  String applicationTitleEn, LocalDateTime filingDate,
                                  LkApplicationStatus applicationStatus, String applicationRequestNumber, LkApplicationCategory applicationCategory) {
        this.applicationId = applicationId;
        this.applicationNumber = applicationNumber;
        this.applicationTitleAr = applicationTitleAr;
        this.applicationTitleEn = applicationTitleEn;
        mapApplicationStatus(applicationStatus);
        mapApplicationCategory(applicationCategory);
        this.filingDate = filingDate.toLocalDate();
        this.applicationRequestNumber = applicationRequestNumber;
    }
    
    private void mapApplicationStatus(LkApplicationStatus applicationStatus) {
        this.applicationStatus.setId(applicationStatus.getId());
        this.applicationStatus.setIpsStatusDescAr(applicationStatus.getIpsStatusDescAr());
        this.applicationStatus.setIpsStatusDescEn(applicationStatus.getIpsStatusDescEn());
        this.applicationStatus.setCode(applicationStatus.getCode());
    }
    
    private void mapApplicationCategory(LkApplicationCategory applicationCategory) {
        this.applicationCategory.setId(applicationCategory.getId());
        this.applicationCategory.setApplicationCategoryDescAr(applicationCategory.getApplicationCategoryDescAr());
        this.applicationCategory.setApplicationCategoryDescEn(applicationCategory.getApplicationCategoryDescEn());
        this.applicationCategory.setSaipCode(applicationCategory.getSaipCode());
    }
    
}
