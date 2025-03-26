package gov.saip.applicationservice.common.service.supportService.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.supportService.ApplicationSupportServicesTypeComment;
import gov.saip.applicationservice.common.repository.supportService.ApplicationSupportServicesTypeCommentRepository;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.supportService.ApplicationSupportServicesTypeCommentService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationSupportServicesTypeCommentServiceImpl extends BaseServiceImpl<ApplicationSupportServicesTypeComment, Long> implements ApplicationSupportServicesTypeCommentService {

    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final ApplicationSupportServicesTypeCommentRepository applicationSupportServicesTypeCommentRepository;
    @Override
    protected BaseRepository<ApplicationSupportServicesTypeComment, Long> getRepository() {
        return applicationSupportServicesTypeCommentRepository;
    }

    @Override
    public List<ApplicationSupportServicesTypeComment> getAllApplicationSupportServicesTypeCommentsByApplicationSupportServiceId(Long applicationSupportServiceId) {
        applicationSupportServicesTypeService.validateApplicationSupportServicesTypeExists(applicationSupportServiceId);
        return applicationSupportServicesTypeCommentRepository.findAllByApplicationSupportServicesTypeId(applicationSupportServiceId);
    }

    @Override
    public ApplicationSupportServicesTypeComment insert(ApplicationSupportServicesTypeComment applicationSupportServicesTypeComment) {
        return super.insert(applicationSupportServicesTypeComment);
    }

    @Override
    public ApplicationSupportServicesTypeComment getLastApplicationSupportServicesTypeCommentByApplicationSupportServiceId(Long applicationSupportServiceId) {
        applicationSupportServicesTypeService.validateApplicationSupportServicesTypeExists(applicationSupportServiceId);
        return getLastApplicationSupportServicesTypeComment(applicationSupportServiceId);
    }

    private ApplicationSupportServicesTypeComment getLastApplicationSupportServicesTypeComment(Long applicationSupportServiceId) {
        List<ApplicationSupportServicesTypeComment> applicationSupportServicesTypeCommentList = applicationSupportServicesTypeCommentRepository.findLastByApplicationSupportServicesTypeId(applicationSupportServiceId, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")));
        if (applicationSupportServicesTypeCommentList != null && !applicationSupportServicesTypeCommentList.isEmpty())
            return applicationSupportServicesTypeCommentList.get(0);
        throw new BusinessException(Constants.ErrorKeys.EXCEPTION_NO_COMMENTS_FOUND_FOR_THIS_APPLICATION_SUPPORT_SERVICE_TYPE, HttpStatus.NOT_FOUND, new String[]{applicationSupportServiceId.toString()});
    }

}
