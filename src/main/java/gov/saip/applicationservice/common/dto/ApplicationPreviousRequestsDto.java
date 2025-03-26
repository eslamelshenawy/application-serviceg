package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.enums.CustomerApplicationAccessLevel;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApplicationPreviousRequestsDto {
    private Long id;
    private Long applicationId;
    private String requestNumber;
    private String titleAr;
    private String titleEn;
    private LocalDateTime createdDate;
    private LKApplicationCategoryDto applicationCategory;
    private LKSupportServicesDto supportServices;
    private SupportServicePaymentStatus supportServicePaymentStatus;
    private RequestTasksDto task;
    private LKSupportServiceRequestStatus requestStatus;
    private CustomerApplicationAccessLevel accessLevel;
    private Long licenseRequestId;
    private Boolean isAppealed;
    private Long oppositionId;

}
