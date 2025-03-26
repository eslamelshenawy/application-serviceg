package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.model.ApplicationWord;
import gov.saip.applicationservice.common.repository.ApplicationWordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationWordServiceImplTest {

    @Mock
    private ApplicationWordRepository applicationWordRepository;

    @InjectMocks
    private ApplicationWordServiceImpl applicationWordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAppWordsByApplicationId() {
        Long applicationId = 1L;

        List<ApplicationWord> applicationWords = new ArrayList<>();
        applicationWords.add(new ApplicationWord());
        applicationWords.add(new ApplicationWord());

        when(applicationWordRepository.findByApplicationInfoId(applicationId)).thenReturn(applicationWords);

        List<ApplicationWord> actualApplicationWords = applicationWordService.getAllAppWordsByApplicationId(applicationId);

        verify(applicationWordRepository).findByApplicationInfoId(applicationId);

        assertEquals(applicationWords, actualApplicationWords);
    }
}

