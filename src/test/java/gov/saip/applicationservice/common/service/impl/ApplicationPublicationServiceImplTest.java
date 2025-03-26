package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ApplicationPublicationRepository;
import gov.saip.applicationservice.common.repository.lookup.LKPublicationTypeRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationStatusRepository;
import gov.saip.applicationservice.common.service.DocumentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicationPublicationServiceImplTest {

    @Mock
    private ApplicationPublicationRepository applicationPublicationRepository;

    @Mock
    private DocumentsService documentsService;

    @Mock
    private LkApplicationStatusRepository lkApplicationStatusRepository;

    @Mock
    private LKPublicationTypeRepository lkPublicationTypeRepository;

    @Mock
    private ApplicationInfoRepository applicationInfoRepository;

    @InjectMocks
    private ApplicationPublicationServiceImpl applicationPublicationService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant fixedInstant = Instant.parse("2023-09-14T12:00:00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, zoneId);

        applicationPublicationService.setClock(fixedClock);

    }

    @Test
    public void testFindByApplicationId() {
        Long applicationId = 1L;


        ApplicationPublication mockPublication = new ApplicationPublication();

        when(applicationPublicationRepository.findByApplicationId(applicationId))
                .thenReturn(Optional.of(List.of(mockPublication)));

        ApplicationPublication result = applicationPublicationService.findByApplicationId(applicationId);

        assertEquals(mockPublication, result);

        verify(applicationPublicationRepository).findByApplicationId(applicationId);
    }

    @Test
    public void testGetPublicationDateById() {
        Long id = 1L;

        LocalDateTime mockPublicationDate = LocalDateTime.now();

        when(applicationPublicationRepository.findPublicationDateById(id))
                .thenReturn(Optional.of(mockPublicationDate));

        LocalDateTime result = applicationPublicationService.getPublicationDateById(id);

        assertEquals(mockPublicationDate, result);

        verify(applicationPublicationRepository).findPublicationDateById(id);
    }
    @Test
    public void testCreateApplicationPublication() {
        Long applicationId = 1L;
        ApplicationInfo application = new ApplicationInfo();
        application.setId(applicationId);
        LkApplicationCategory lkApplicationCategory = new LkApplicationCategory();
        lkApplicationCategory.setId(1L);
        lkApplicationCategory.setSaipCode("TRADEMARK");
        LKPublicationType lkPublicationType = new LKPublicationType();
        lkPublicationType.setId(1);
        application.setCategory(lkApplicationCategory);
        LkApplicationStatus applicationStatus = new LkApplicationStatus();
        applicationStatus.setId(1L);
        Long categoryId = 1L;
        String code = "PUBLISHED_ELECTRONICALLY";
        when(applicationInfoRepository.findById(applicationId)).thenReturn(Optional.of(application));
        when(lkApplicationStatusRepository.findByCodeAndApplicationCategoryId(code, categoryId)).thenReturn(Optional.of(applicationStatus));
        when(lkPublicationTypeRepository.findByCodeAndApplicationCategoryId(any(),any())).thenReturn(Optional.of(lkPublicationType));
//        when(lkPublicationTypeRepository.findByCode(any())).thenReturn(Optional.of(new LKPublicationType()));
        applicationPublicationService.createApplicationPublication(applicationId,null);


        verify(applicationInfoRepository).findById(applicationId);
    }

    @Test
    public void testPrepareAndCreateApplicationPublication() {
        LkApplicationCategory lkApplicationCategory = new LkApplicationCategory();
        lkApplicationCategory.setId(1L);
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setCategory(lkApplicationCategory);
        LKPublicationType lkPublicationType = new LKPublicationType();
        lkPublicationType.setId(1);
        LkApplicationStatus applicationStatus = new LkApplicationStatus();
        applicationStatus.setId(1L);
        Long categoryId = 1L;
        String code = "TRADEMARK_REGISTERATION";
        when(lkApplicationStatusRepository.findByCodeAndApplicationCategoryId(code, categoryId)).thenReturn(Optional.of(applicationStatus));
        when(lkPublicationTypeRepository.findByCode(anyString())).thenReturn(Optional.of(lkPublicationType));
        when(documentsService.findLatestDocumentByApplicationIdAndDocumentType(anyLong(), anyString())).thenReturn(new DocumentDto());
        when(lkPublicationTypeRepository.findByCodeAndApplicationCategoryId(any(),any())).thenReturn(Optional.of(lkPublicationType));

        applicationPublicationService.createApplicationPublication(
                applicationInfo, "TRADEMARK_REGISTERATION", "TRADEMARK_REGISTERATION", "APPLICATION_XML", 1L
        );

        verify(lkPublicationTypeRepository, times(1)).findByCodeAndApplicationCategoryId(anyString(), any());
        verify(documentsService, times(1)).findLatestDocumentByApplicationIdAndDocumentType(any(), any());
        verify(applicationPublicationRepository, times(1)).save(any(ApplicationPublication.class));
    }

}

