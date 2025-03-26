package gov.saip.applicationservice.common.dto.search;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationListSortingColumns;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;
    private String status;
    private String applicationNumber;
    private String applicationTitleAr;
    private String applicationTitleEn;
    private String agentName;
    private SupportServiceType supportService;
    private String supportServiceStatusCode;
    private Long categoryId;
    private Long supportServiceTypeId;
    private Long applicationId;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private String ownerName;
    private ApplicationCategoryEnum applicationCategoryCode;
    private ApplicationListSortingColumns sortingColumn;
}
