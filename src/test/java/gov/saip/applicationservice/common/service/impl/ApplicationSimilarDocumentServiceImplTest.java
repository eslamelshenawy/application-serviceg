package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.model.ApplicationSimilarDocument;
import gov.saip.applicationservice.common.repository.ApplicationSimilarDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationSimilarDocumentServiceImplTest {

    @Mock
    private ApplicationSimilarDocumentRepository applicationSimilarDocumentRepository;

    @InjectMocks
    private ApplicationSimilarDocumentServiceImpl applicationSimilarDocumentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllByApplicationId() {
        Long applicationId = 1L;

        List<ApplicationSimilarDocument> similarDocuments = new ArrayList<>();
        similarDocuments.add(new ApplicationSimilarDocument());
        similarDocuments.add(new ApplicationSimilarDocument());

        when(applicationSimilarDocumentRepository.findByApplicationInfoId(applicationId)).thenReturn(similarDocuments);

        List<ApplicationSimilarDocument> actualSimilarDocuments = applicationSimilarDocumentService.getAllByApplicationId(applicationId);

        verify(applicationSimilarDocumentRepository).findByApplicationInfoId(applicationId);

        assertEquals(similarDocuments, actualSimilarDocuments);
    }
}

