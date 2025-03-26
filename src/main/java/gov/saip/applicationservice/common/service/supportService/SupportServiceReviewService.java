package gov.saip.applicationservice.common.service.supportService;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.enums.support_services_enums.SupportServiceReviewStatus;
import gov.saip.applicationservice.common.model.supportService.SupportServiceReview;

import java.util.List;

public interface SupportServiceReviewService extends BaseService<SupportServiceReview, Long> {


    List<SupportServiceReview> getReviewsByServiceId(Long serviceId);

    SupportServiceReview getEmployeeReviewByServiceId(Long serviceId);

    void updateReviewStatus(Long id, SupportServiceReviewStatus reviewStatus);
}
