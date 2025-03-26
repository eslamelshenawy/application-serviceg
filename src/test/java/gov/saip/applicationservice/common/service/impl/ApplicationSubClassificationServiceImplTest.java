package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.ApplicationSubClassificationDto;
import gov.saip.applicationservice.common.enums.SubClassificationType;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ApplicationNiceClassificationRepository;
import gov.saip.applicationservice.common.repository.ApplicationSubClassificationRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.SubClassificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicationSubClassificationServiceImplTest {

    @Mock
    private SubClassificationService subClassificationService;

    @Mock
    private ApplicationSubClassificationRepository applicationSubClassificationsRepository;

    @Mock
    private ApplicationInfoService applicationInfoService;

    @Mock
    private ApplicationNiceClassificationRepository applicationNiceClassificationRepository;

    @InjectMocks
    @Spy
    private ApplicationSubClassificationServiceImpl applicationSubClassificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateApplicationSubClassification() {
        Long applicationId = 1L;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1l);
        ApplicationSubClassificationDto dto = new ApplicationSubClassificationDto();
        dto.setClassificationId(1L);
        dto.setSubClassificationType(SubClassificationType.DETAIL_LIST);
        dto.setSelectedSubClassifications(Arrays.asList(2L, 3L));

        when(applicationInfoService.findById(applicationId)).thenReturn(applicationInfo);
        when(subClassificationService.findByIdIn(Arrays.asList(2L, 3L))).thenReturn(Arrays.asList(new SubClassification(), new SubClassification()));
        doReturn(Arrays.asList(new ApplicationSubClassification(), new ApplicationSubClassification()))
                .when(applicationSubClassificationService)
                .saveAll(anyList());
        Long result = applicationSubClassificationService.createApplicationSubClassification(dto, applicationId);

        assertEquals(applicationId, result);
        verify(applicationInfoService).findById(applicationId);
        verify(subClassificationService).findByIdIn(Arrays.asList(2L, 3L));
        verify(applicationSubClassificationsRepository, times(1)).deleteByApplicationIdAndClassificationId(any(),any());
    }

    @Test
    public void testUpdateApplicationSubClassification_Add() {
        Long applicationId = 1L;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1l);
        ApplicationNiceClassification applicationNiceClassification = new ApplicationNiceClassification();
        applicationNiceClassification.setSubClassificationType(SubClassificationType.DETAIL_LIST);
        applicationNiceClassification.setClassification(new Classification(1L));

        ApplicationSubClassificationDto dto = new ApplicationSubClassificationDto();
        dto.setClassificationId(1L);
        dto.setSubClassificationType(SubClassificationType.DETAIL_LIST);
        dto.setSelectedSubClassifications(Arrays.asList(2L, 3L));

        when(applicationInfoService.findById(applicationId)).thenReturn(applicationInfo);
        when(applicationNiceClassificationRepository.getByApplicationIdAndCategory(applicationId, 1L)).thenReturn(applicationNiceClassification);
        when(subClassificationService.findByIdIn(Arrays.asList(2L, 3L))).thenReturn(Arrays.asList(new SubClassification(), new SubClassification()));
        doReturn(Arrays.asList(new ApplicationSubClassification(), new ApplicationSubClassification()))
                .when(applicationSubClassificationService)
                .saveAll(anyList());
        Long result = applicationSubClassificationService.updateApplicationSubClassification(dto, applicationId);

        assertEquals(applicationId, result);
        verify(applicationInfoService).findById(applicationId);
        verify(applicationNiceClassificationRepository).getByApplicationIdAndCategory(applicationId, 1L);
        verify(subClassificationService).findByIdIn(Arrays.asList(2L, 3L));
        verify(applicationSubClassificationsRepository, times(1)).getPersistedSubClassificationsByAppIdAndClassificationId(any(),any());
    }

    @Test
    public void testUpdateApplicationSubClassification_Delete() {
        Long applicationId = 1L;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1l);

        ApplicationNiceClassification applicationNiceClassification = new ApplicationNiceClassification();
        applicationNiceClassification.setSubClassificationType(SubClassificationType.DETAIL_LIST);
        applicationNiceClassification.setClassification(new Classification(1L));

        ApplicationSubClassificationDto dto = new ApplicationSubClassificationDto();
        dto.setClassificationId(1L);
        dto.setSubClassificationType(SubClassificationType.DETAIL_LIST);
        dto.setSelectedSubClassifications(Arrays.asList(2L, 3L));
        dto.setUnSelectedSubClassifications(Arrays.asList(4L, 5L));

        when(applicationInfoService.findById(applicationId)).thenReturn(applicationInfo);
        when(applicationNiceClassificationRepository.getByApplicationIdAndCategory(applicationId, 1L)).thenReturn(applicationNiceClassification);
        when(subClassificationService.findByIdIn(Arrays.asList(2L, 3L))).thenReturn(Arrays.asList(new SubClassification(), new SubClassification()));
        when(applicationSubClassificationsRepository.getPersistedSubClassificationsByAppIdAndClassificationId(applicationId, 1L)).thenReturn(new HashSet<>(Arrays.asList(2L, 3L)));

        Long result = applicationSubClassificationService.updateApplicationSubClassification(dto, applicationId);

        assertEquals(applicationId, result);
        verify(applicationInfoService).findById(applicationId);
        verify(applicationNiceClassificationRepository).getByApplicationIdAndCategory(applicationId, 1L);
        verify(applicationSubClassificationsRepository).getPersistedSubClassificationsByAppIdAndClassificationId(applicationId, 1L);
        verify(applicationSubClassificationsRepository).deleteBySubClassIdInAndApplicationId(Arrays.asList(4L, 5L), applicationInfo.getId(), 1L);
        verify(applicationSubClassificationsRepository, times(1)).getPersistedSubClassificationsByAppIdAndClassificationId(any(),any());
    }


}
