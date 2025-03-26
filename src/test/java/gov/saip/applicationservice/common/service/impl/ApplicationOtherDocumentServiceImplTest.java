package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.model.ApplicationOtherDocument;
import gov.saip.applicationservice.common.repository.ApplicationOtherDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationOtherDocumentServiceImplTest {

    @Mock
    private ApplicationOtherDocumentRepository applicationOtherDocumentRepository;

    @InjectMocks
    private ApplicationOtherDocumentServiceImpl applicationOtherDocumentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAppOtherDocumentsByApplicationId() {
        Long applicationId = 1L;

        List<ApplicationOtherDocument> documents = new ArrayList<>();
        documents.add(new ApplicationOtherDocument());
        documents.add(new ApplicationOtherDocument());

        when(applicationOtherDocumentRepository.findByApplicationInfoId(applicationId)).thenReturn(documents);

        List<ApplicationOtherDocument> actualDocuments = applicationOtherDocumentService.getAllAppOtherDocumentsByApplicationId(applicationId);

        verify(applicationOtherDocumentRepository).findByApplicationInfoId(applicationId);

        assertEquals(documents, actualDocuments);
    }
}
