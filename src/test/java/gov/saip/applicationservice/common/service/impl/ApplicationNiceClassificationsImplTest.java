package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.model.ApplicationNiceClassification;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.repository.ApplicationNiceClassificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationNiceClassificationsImplTest {

    @Mock
    private ApplicationNiceClassificationRepository applicationNiceClassificationRepository;

    @Mock
    private ClassificationMapper classificationMapper;

    @InjectMocks
    private ApplicationNiceClassificationsImpl applicationNiceClassificationsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListSelectedApplicationNiceClassifications() {
        Long applicationId = 1L;

        List<ApplicationNiceClassification> classifications = new ArrayList<>();
        classifications.add(new ApplicationNiceClassification());
        classifications.add(new ApplicationNiceClassification());

        when(applicationNiceClassificationRepository.getSelectedApplicationClassifications(applicationId))
                .thenReturn(classifications);

        List<ClassificationDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new ClassificationDto());
        expectedDtos.add(new ClassificationDto());

        when(classificationMapper.map(anyList())).thenReturn(expectedDtos);

        List<ClassificationDto> actualDtos = applicationNiceClassificationsService.listSelectedApplicationNiceClassifications(applicationId);

        verify(applicationNiceClassificationRepository).getSelectedApplicationClassifications(applicationId);

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    public void testGetLightNiceClassificationsByAppId() {
        Long applicationId = 2L;

        List<Classification> classifications = new ArrayList<>();
        classifications.add(new Classification());
        classifications.add(new Classification());

        when(applicationNiceClassificationRepository.getLightNiceClassificationsByAppId(applicationId))
                .thenReturn(classifications);

        List<ClassificationLightDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new ClassificationLightDto());
        expectedDtos.add(new ClassificationLightDto());

        when(classificationMapper.mapLight(classifications)).thenReturn(expectedDtos);

        List<ClassificationLightDto> actualDtos = applicationNiceClassificationsService.getLightNiceClassificationsByAppId(applicationId);

        verify(applicationNiceClassificationRepository).getLightNiceClassificationsByAppId(applicationId);

        verify(classificationMapper).mapLight(classifications);

        assertEquals(expectedDtos, actualDtos);
    }
}

