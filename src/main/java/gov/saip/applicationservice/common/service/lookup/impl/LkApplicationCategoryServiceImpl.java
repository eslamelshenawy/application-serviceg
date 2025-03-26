package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryLightDto;
import gov.saip.applicationservice.common.mapper.LkApplicationCategoryMapper;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationCategoryRepository;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LkApplicationCategoryServiceImpl extends BaseServiceImpl<LkApplicationCategory, Long> implements LkApplicationCategoryService {
    private final LkApplicationCategoryRepository lkApplicationCategoryRepository;
    private final LkApplicationCategoryMapper applicationCategoryMapper;
    @Override
    protected BaseRepository<LkApplicationCategory, Long> getRepository() {
        return lkApplicationCategoryRepository;
    }

    public LkApplicationCategory getApplicationCategoryByDescEn(String applicationCategoryDescEn) {
        return lkApplicationCategoryRepository.findByApplicationCategoryDescEn(applicationCategoryDescEn)
                .orElseThrow(() -> new EntityNotFoundException("Application category not found with applicationCategoryDescEn: " + applicationCategoryDescEn));
    }

    @Override
    public List<LKApplicationCategoryDto> getAll() {
        return applicationCategoryMapper.map(lkApplicationCategoryRepository.findAll());
    }

    @Override
    public LkApplicationCategory findBySaipCode(String saipCode) {
        String[] params = {saipCode};
        return lkApplicationCategoryRepository.findBySaipCode(saipCode).orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, params));
    }

    @Override
    public List<LKApplicationCategoryDto> findAllActiveCategories() {
        List<LkApplicationCategory> allActiveCategories = lkApplicationCategoryRepository.findAllActiveCategories();
        return applicationCategoryMapper.map(allActiveCategories);
    }


    @Override
    public LkApplicationCategory update(LkApplicationCategory lkApplicationCategory){
        LkApplicationCategory currentLkApplicationCategory = getReferenceById(lkApplicationCategory.getId());
        currentLkApplicationCategory.setApplicationCategoryDescEn(lkApplicationCategory.getApplicationCategoryDescEn());
        currentLkApplicationCategory.setApplicationCategoryDescAr(lkApplicationCategory.getApplicationCategoryDescAr());
        currentLkApplicationCategory.setApplicationCategoryIsActive(lkApplicationCategory.isApplicationCategoryIsActive());
        return super.update(currentLkApplicationCategory);

    }
    
    @Override
    public List<LKApplicationCategoryLightDto> getCategoriesByCodes(List<String> codes) {
        List<LkApplicationCategory>  categories =  lkApplicationCategoryRepository.findCategoriesByCodes(codes);
        if(categories == null || categories.isEmpty())
            return new ArrayList<>();
        
        return applicationCategoryMapper.mapEntityToLightDto(categories);
    }
    
    
}
