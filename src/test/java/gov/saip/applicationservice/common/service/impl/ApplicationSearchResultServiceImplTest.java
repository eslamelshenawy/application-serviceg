package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.model.ApplicationSearchResult;
import gov.saip.applicationservice.common.repository.ApplicationSearchResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationSearchResultServiceImplTest {

    @Mock
    private ApplicationSearchResultRepository applicationSearchResultRepository;

    @InjectMocks
    private ApplicationSearchResultServiceImpl applicationSearchResultService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testGetAllSearchResultsByApplicationId() {
        Long applicationId = 1L;

        List<ApplicationSearchResult> searchResults = new ArrayList<>();
        searchResults.add(new ApplicationSearchResult());
        searchResults.add(new ApplicationSearchResult());

        when(applicationSearchResultRepository.findByApplicationInfoId(applicationId)).thenReturn(searchResults);

        List<ApplicationSearchResult> actualSearchResults = applicationSearchResultService.getAllSearchResultsByApplicationId(applicationId);

        verify(applicationSearchResultRepository).findByApplicationInfoId(applicationId);

        assertEquals(searchResults, actualSearchResults);
    }
}

