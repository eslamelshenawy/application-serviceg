package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.TermsAndConditionsDto;
import gov.saip.applicationservice.common.mapper.TermsAndConditionsMapper;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.TermsAndConditions;
import gov.saip.applicationservice.common.repository.TermsAndConditionsRepository;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TermsAndConditionsServiceImplTest {

    @Mock
    private TermsAndConditionsRepository termsAndConditionsRepository;

    @Mock
    private TermsAndConditionsMapper termsAndConditionsMapper;

    @Mock
    private LkApplicationCategoryService lkApplicationCategoryService;

    @InjectMocks
    private TermsAndConditionsServiceImpl termsAndConditionsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTermsAndConditionsSorted() {
        List<TermsAndConditions> mockTermsAndConditions = new ArrayList<>();
        mockTermsAndConditions.add(new TermsAndConditions());
        mockTermsAndConditions.add(new TermsAndConditions());
        when(termsAndConditionsRepository.findAllByOrderBySortingAsc()).thenReturn(mockTermsAndConditions);

        List<TermsAndConditionsDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new TermsAndConditionsDto());
        expectedDtos.add(new TermsAndConditionsDto());
        when(termsAndConditionsMapper.mapRequestToEntity(mockTermsAndConditions)).thenReturn(expectedDtos);

        List<TermsAndConditionsDto> result = termsAndConditionsService.getAllTermsAndConditionsSorted();

        assertEquals(expectedDtos, result);
    }

    @Test
    void testGetTermsAndConditionsSorted() {
        String appCategory = "Category";
        String requestType = "RequestType";

        Long categoryId = 1L;
        Long typeId = 2L;

        List<TermsAndConditions> mockTermsAndConditions = new ArrayList<>();
        mockTermsAndConditions.add(new TermsAndConditions());
        when(lkApplicationCategoryService.getApplicationCategoryByDescEn(appCategory)).thenReturn(new LkApplicationCategory());
        when(termsAndConditionsRepository.findByApplicationCategoryIdAndRequestTypeIdOrderBySortingAsc(any(), any())).thenReturn(mockTermsAndConditions);

        List<TermsAndConditionsDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new TermsAndConditionsDto());
        when(termsAndConditionsMapper.mapRequestToEntity(mockTermsAndConditions)).thenReturn(expectedDtos);

        List<TermsAndConditionsDto> result = termsAndConditionsService.getTermsAndConditionsSorted(appCategory, requestType);

        assertEquals(expectedDtos, result);
    }
}
