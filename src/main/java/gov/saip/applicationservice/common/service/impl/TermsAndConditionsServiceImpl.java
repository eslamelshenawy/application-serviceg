package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.TermsAndConditionsDto;
import gov.saip.applicationservice.common.mapper.TermsAndConditionsMapper;
import gov.saip.applicationservice.common.model.TermsAndConditions;
import gov.saip.applicationservice.common.repository.TermsAndConditionsRepository;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import gov.saip.applicationservice.common.service.TermsAndConditionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermsAndConditionsServiceImpl implements TermsAndConditionsService {

    private final TermsAndConditionsRepository termsAndConditionsRepository;

    private final TermsAndConditionsMapper termsAndConditionsMapper;

    private final LkApplicationCategoryService lkApplicationCategoryService;

//    private final LkRequestTypeService  lkRequestTypeService;

    public List<TermsAndConditionsDto> getAllTermsAndConditionsSorted() {
        List<TermsAndConditions> termsAndConditions = termsAndConditionsRepository.findAllByOrderBySortingAsc();
        return termsAndConditionsMapper.mapRequestToEntity(termsAndConditions);
    }

    @Override
    public List<TermsAndConditionsDto> getTermsAndConditionsSorted(String appCategory, String requestType) {
        Long applicationCategoryId = lkApplicationCategoryService.getApplicationCategoryByDescEn(appCategory).getId();
        Long requestTypeId = null ;  //lkRequestTypeService.getRequestTypeByName(requestType).getId();
        List<TermsAndConditions> termsAndConditions = termsAndConditionsRepository.findByApplicationCategoryIdAndRequestTypeIdOrderBySortingAsc(applicationCategoryId, requestTypeId);
        return termsAndConditionsMapper.mapRequestToEntity(termsAndConditions);
    }
}
