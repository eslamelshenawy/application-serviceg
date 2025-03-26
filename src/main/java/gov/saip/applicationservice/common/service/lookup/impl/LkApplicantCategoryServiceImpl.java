package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.lookup.LkApplicantCategoryDto;
import gov.saip.applicationservice.common.model.LkApplicantCategory;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.repository.lookup.LkApplicantCategoryRepository;
import gov.saip.applicationservice.common.service.lookup.LkApplicantCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LkApplicantCategoryServiceImpl extends BaseServiceImpl<LkApplicantCategory, Long> implements LkApplicantCategoryService {

    private final LkApplicantCategoryRepository lkApplicantCategoryRepository;

    @Override
    protected BaseRepository<LkApplicantCategory, Long> getRepository() {
        return lkApplicantCategoryRepository;
    }

    @Override
    public LkApplicantCategory update(LkApplicantCategory lkApplicantCategory){

        LkApplicantCategory currentLkApplicationCategory = getReferenceById(lkApplicantCategory.getId());
        currentLkApplicationCategory.setApplicantCategoryNameAr(lkApplicantCategory.getApplicantCategoryNameAr());
        currentLkApplicationCategory.setApplicantCategoryNameEn(lkApplicantCategory.getApplicantCategoryNameEn());
        return super.update(currentLkApplicationCategory);

    }

}
