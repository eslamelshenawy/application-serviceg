package gov.saip.applicationservice.common.service.supportService.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.enums.support_services_enums.SupportServiceReviewStatus;
import gov.saip.applicationservice.common.model.supportService.SupportServiceReview;
import gov.saip.applicationservice.common.repository.supportService.SupportServiceReviewRepository;
import gov.saip.applicationservice.common.service.supportService.SupportServiceReviewService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportServiceReviewServiceImpl extends BaseServiceImpl<SupportServiceReview, Long> implements SupportServiceReviewService {

    private final SupportServiceReviewRepository supportServiceReviewRepository;
    private final Util util;
    @Override
    protected BaseRepository<SupportServiceReview, Long> getRepository() {
        return supportServiceReviewRepository;
    }

    @Override
    public SupportServiceReview insert(SupportServiceReview entity) {
        entity.setReviewStatus(SupportServiceReviewStatus.NEW);
        return super.insert(entity);
    }

    @Override
    public SupportServiceReview update(SupportServiceReview entity) {
        SupportServiceReview supportServiceReview = supportServiceReviewRepository.findById(entity.getId()).orElseThrow(() -> new BusinessException(""));
        supportServiceReview.setReview(entity.getReview() == null || entity.getReview().isBlank()? supportServiceReview.getReview() : entity.getReview());
        supportServiceReview.setReviewStatus(entity.getReviewStatus() != null ? entity.getReviewStatus() : supportServiceReview.getReviewStatus());
        return super.update(entity);
    }

    public List<SupportServiceReview> getReviewsByServiceId(Long serviceId) {
        return supportServiceReviewRepository.findBySupportServicesTypeId(serviceId);
    }

    @Override
    public SupportServiceReview getEmployeeReviewByServiceId(Long serviceId) {
        String userName = (String) util.getFromBasicUserinfo("userName");
        return supportServiceReviewRepository.findBySupportServicesTypeIdAAndCreatedByUser(serviceId, userName);
    }

    @Transactional
    @Override
    public void updateReviewStatus(Long id, SupportServiceReviewStatus reviewStatus) {
        supportServiceReviewRepository.updateReviewStatus(id,reviewStatus);
    }
}
