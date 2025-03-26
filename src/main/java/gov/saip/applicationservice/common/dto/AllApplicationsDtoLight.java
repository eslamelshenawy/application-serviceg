package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class AllApplicationsDtoLight {
    private long id;
    private String applicationNumber;
    private String titleEn;
    private String titleAr;
    private String address;
    private LkApplicationStatusDto applicationStatus;
    private LKApplicationCategoryDto category;
    private CustomerSampleInfoDto mainApplicant ;
    private String customerCode;
    private LocalDateTime filingDate;
    private RequestTasksDto task;
    private List<RequestTasksDto> allTasks;
    private String grantNumber;
    private DocumentDto trademarkImage;
}
