package gov.saip.applicationservice.common.dto.supportService;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.support_services_enums.SupportServiceReviewStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SupportServiceReviewDto extends BaseDto<Long> {
    private String review;
    private Long supportServicesId;
    private SupportServiceReviewStatus reviewStatus;
    private LocalDateTime createdDate;
    private String createdByUser;
}
