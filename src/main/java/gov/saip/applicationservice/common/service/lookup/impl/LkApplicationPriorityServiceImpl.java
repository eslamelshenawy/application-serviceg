package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.LkApplicantCategory;
import gov.saip.applicationservice.common.model.LkApplicationPriorityStatus;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationPriorityStatusRepository;
import gov.saip.applicationservice.common.service.lookup.LkApplicantCategoryService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationPriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LkApplicationPriorityServiceImpl extends BaseServiceImpl<LkApplicationPriorityStatus, Long> implements LkApplicationPriorityService {

    private final LkApplicationPriorityStatusRepository  lkApplicationPriorityStatusRepository;
    @Override
    protected BaseRepository<LkApplicationPriorityStatus, Long> getRepository() {
        return lkApplicationPriorityStatusRepository;
    }
    @Override
    public LkApplicationPriorityStatus update(LkApplicationPriorityStatus lkApplicationPriorityStatus){
        LkApplicationPriorityStatus currentLkApplicationPriorityStatus = getReferenceById(lkApplicationPriorityStatus.getId());
        currentLkApplicationPriorityStatus.setIpsStatusDescAr(lkApplicationPriorityStatus.getIpsStatusDescAr());
        currentLkApplicationPriorityStatus.setIpsStatusDescEn(lkApplicationPriorityStatus.getIpsStatusDescEn());
        return super.update(currentLkApplicationPriorityStatus);

    }

}
