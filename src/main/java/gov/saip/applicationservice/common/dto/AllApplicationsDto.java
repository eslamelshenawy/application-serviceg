package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class AllApplicationsDto {
    private long id;
    private String applicationNumber;
    private String titleEn;
    private String titleAr;
    private String address;
    private LkApplicationStatus applicationStatus;
    private LkApplicationCategory category;
    private CustomerSampleInfoDto mainApplicant ;
    private String customerCode;
    private String grantNumber;

    public AllApplicationsDto(long id, String applicationNumber, String titleEn, String titleAr, String address, LkApplicationStatus applicationStatus, LkApplicationCategory category, String customerCode, LocalDateTime filingDate,String grantNumber) {
        this.id = id;
        this.applicationNumber = applicationNumber;
        this.titleEn = titleEn;
        this.titleAr = titleAr;
        this.address = address;
        this.applicationStatus = applicationStatus;
        this.category = category;
        this.customerCode = customerCode;
        this.filingDate = filingDate;
        this.grantNumber = grantNumber;
    }




    private LocalDateTime filingDate;
    private RequestTasksDto task;
    private List<RequestTasksDto> allTasks;
    private DocumentDto trademarkImage;



}
