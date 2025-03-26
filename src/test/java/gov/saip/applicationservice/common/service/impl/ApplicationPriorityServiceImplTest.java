package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.mapper.ApplicationPriorityMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationPriority;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.repository.ApplicationPriorityRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationPriorityStatusRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ConfigParameterService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static gov.saip.applicationservice.util.Constants.PRIORITY_CLAIM_DAYS_CODE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ApplicationPriorityServiceImplTest {

    @InjectMocks
    @Spy
    ApplicationPriorityServiceImpl applicationPriorityServiceImpl;
    @Mock
    private ApplicationPriorityRepository applicationPriorityRepository;
    @Mock
    private DocumentsService documentsService;
    @Mock
    private ApplicationPriorityMapper applicationPriorityMapper;
    @Mock

    private ApplicationInfoService applicationInfoService;
    @Mock
    private ConfigParameterService configParameterService;
    @Mock
    private NotificationCaller notificationCaller;
    @Mock
    private LkApplicationPriorityStatusRepository lkApplicationPriorityStatusRepository;

    @Mock
    private BPMCallerFeignClient bpmCallerFeignClient;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);}


    @Test
    public void testSetExpiredAndSendNotifiction() {
        List<ApplicationPriority> priorities = new ArrayList<>();
        ApplicationPriority applicationPriority = new ApplicationPriority();
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        applicationInfo.setApplicationNumber("test");
        applicationInfo.setCategory(new LkApplicationCategory());
        applicationPriority.setApplicationInfo(applicationInfo);
        priorities.add(applicationPriority);
        when(applicationPriorityRepository.getAppsOutOfDate(anyLong())).thenReturn(priorities);
        when(applicationInfoService.listMainApplicant(applicationInfo.getId())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.saveApplicationInfoEntity(any(ApplicationInfo.class))).thenReturn(1l);
        when(applicationInfoService.saveApplicationInfoEntity(any(ApplicationInfo.class))).thenReturn(1l);
        when(applicationPriorityRepository.save(any(ApplicationPriority.class))).thenReturn(applicationPriority);
        doReturn(0l).when(applicationPriorityServiceImpl).getPriorityDocumentsAllowanceDays();
        when(bpmCallerFeignClient.getRequestTypeConfigValue(PRIORITY_CLAIM_DAYS_CODE)).thenReturn("56");

        applicationPriorityServiceImpl.setExpiredAndSendNotifiction();
        ArgumentCaptor<ApplicationPriority> argument = ArgumentCaptor.forClass(ApplicationPriority.class);
        verify(applicationPriorityRepository, times(1)).save(argument.capture());
    }

    @Test
    public void testCreateUpdateApplicationPriority() {
        ApplicationPriorityDto dto = new ApplicationPriorityDto();
        dto.setDasCode("code");
        dto.setProvideDocLater(true);
        Long applicationInfoId = 1L;
        LkApplicationCategory category = new LkApplicationCategory();
        category.setSaipCode("TRADEMARK");
        ApplicationInfo applicationInfoExisted = new ApplicationInfo();
        applicationInfoExisted.setCategory(category);
        when(applicationInfoService.findById(applicationInfoId)).thenReturn(applicationInfoExisted);
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(1l);
        when(applicationPriorityRepository.save(any(ApplicationPriority.class))).thenReturn(applicationPriority);

        Long result = applicationPriorityServiceImpl.createUpdateApplicationPriority(dto, applicationInfoId);

        ArgumentCaptor<ApplicationPriority> argument = ArgumentCaptor.forClass(ApplicationPriority.class);
        verify(applicationPriorityRepository, times(1)).save(argument.capture());
        assertEquals(1l, result);
    }

    @Test
    public void testCreateUpdateApplicationPriorityDasCodeNull() {
        ApplicationPriorityDto dto = new ApplicationPriorityDto();
        Long applicationInfoId = 1L;
        LkApplicationCategory category = new LkApplicationCategory();
        category.setSaipCode("TRADEMARK");
        ApplicationInfo applicationInfoExisted = new ApplicationInfo();
        applicationInfoExisted.setCategory(category);
        when(applicationInfoService.findById(applicationInfoId)).thenReturn(applicationInfoExisted);
        when(applicationPriorityRepository.save(any(ApplicationPriority.class))).thenReturn(new ApplicationPriority());

        assertThrows(BusinessException.class, ()->{applicationPriorityServiceImpl.createUpdateApplicationPriority(dto, applicationInfoId);});

    }

    @Test
    public void testCreateUpdateApplicationPriorityPatent() {
        ApplicationPriorityDto dto = new ApplicationPriorityDto();
        dto.setDasCode("code");
        dto.setPriorityApplicationNumber("doc");
        dto.setProvideDocLater(true);
        dto.setId(1l);
        Long applicationInfoId = 1L;
        LkApplicationCategory category = new LkApplicationCategory();
        category.setSaipCode("PATENT");
        ApplicationInfo applicationInfoExisted = new ApplicationInfo();
        applicationInfoExisted.setCategory(category);
        when(applicationInfoService.findById(applicationInfoId)).thenReturn(applicationInfoExisted);
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(1l);
        when(applicationPriorityRepository.save(any(ApplicationPriority.class))).thenReturn(applicationPriority);
        when(applicationPriorityRepository.findById(anyLong())).thenReturn(Optional.of(new ApplicationPriority()));

        Long result = applicationPriorityServiceImpl.createUpdateApplicationPriority(dto, applicationInfoId);

        ArgumentCaptor<ApplicationPriority> argument = ArgumentCaptor.forClass(ApplicationPriority.class);
        verify(applicationPriorityRepository, times(1)).save(argument.capture());
        assertEquals(1l, result);
    }

    @Test
    public void testCreateUpdateApplicationPriorityDasCodeNullPatent() {
        ApplicationPriorityDto dto = new ApplicationPriorityDto();
        Long applicationInfoId = 1L;
        LkApplicationCategory category = new LkApplicationCategory();
        category.setSaipCode("PATENT");
        ApplicationInfo applicationInfoExisted = new ApplicationInfo();
        applicationInfoExisted.setCategory(category);
        when(applicationInfoService.findById(applicationInfoId)).thenReturn(applicationInfoExisted);
        when(applicationPriorityRepository.save(any(ApplicationPriority.class))).thenReturn(new ApplicationPriority());

        assertThrows(BusinessException.class, ()->{applicationPriorityServiceImpl.createUpdateApplicationPriority(dto, applicationInfoId);});

    }

    @Test
    public void testCreateUpdateApplicationPriorityThrowsBusinessException() {
        ApplicationPriorityDto dto = new ApplicationPriorityDto();
        Long applicationInfoId = 1L;
        LkApplicationCategory category = new LkApplicationCategory();
        category.setSaipCode("TRADEMARK");
        ApplicationInfo applicationInfoExisted = new ApplicationInfo();
        applicationInfoExisted.setCategory(category);
        when(applicationInfoService.findById(applicationInfoId)).thenReturn(applicationInfoExisted);
        when(applicationPriorityRepository.save(any(ApplicationPriority.class))).thenThrow(new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE));
        assertThrows(BusinessException.class, ()->{applicationPriorityServiceImpl.createUpdateApplicationPriority(dto, applicationInfoId);});
    }

    @Test
    public void testCreateUpdateApplicationPriorityWithException() {
            ApplicationPriorityDto dto = new ApplicationPriorityDto();
            Long applicationInfoId = 1L;
            LkApplicationCategory category = new LkApplicationCategory();
            category.setSaipCode("TRADEMARK");
            ApplicationInfo applicationInfoExisted = new ApplicationInfo();
            applicationInfoExisted.setCategory(category);
            when(applicationInfoService.findById(applicationInfoId)).thenThrow(new RuntimeException());
            assertThrows(RuntimeException.class, ()->{applicationPriorityServiceImpl.createUpdateApplicationPriority(dto, applicationInfoId);});
    }

    @Test
    public void testDeleteApplicationPriorityFileWithPriorityDocument() {
        Long id = 1L;
        String fileKey = "priorityDocument";
        Document priorityDocument = new Document();
        priorityDocument.setId(2L);
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(id);
        applicationPriority.setPriorityDocument(priorityDocument);
        when(applicationPriorityRepository.findById(id)).thenReturn(Optional.of(applicationPriority));

        Long result = applicationPriorityServiceImpl.deleteApplicationPriorityFile(id, fileKey);

        ArgumentCaptor<Document> documentArgumentCaptor = ArgumentCaptor.forClass(Document.class);
        ArgumentCaptor<ApplicationPriority> applicationPriorityArgumentCaptor = ArgumentCaptor.forClass(ApplicationPriority.class);
        verify(applicationPriorityRepository, times(1)).save(applicationPriorityArgumentCaptor.capture());
        verify(documentsService, times(1)).softDeleteDocumentById(priorityDocument.getId());
        assertEquals(id, result);
        assertNull(applicationPriorityArgumentCaptor.getValue().getPriorityDocument());
    }

    @Test
    public void testDeleteApplicationPriorityFileWithTranslatedDocument() {
        Long id = 1L;
        String fileKey = "translatedDocument";
        Document translatedDocument = new Document();
        translatedDocument.setId(2L);
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(id);
        applicationPriority.setTranslatedDocument(translatedDocument);
        when(applicationPriorityRepository.findById(id)).thenReturn(Optional.of(applicationPriority));

        Long result = applicationPriorityServiceImpl.deleteApplicationPriorityFile(id, fileKey);

        ArgumentCaptor<Document> documentArgumentCaptor = ArgumentCaptor.forClass(Document.class);
        ArgumentCaptor<ApplicationPriority> applicationPriorityArgumentCaptor = ArgumentCaptor.forClass(ApplicationPriority.class);
        verify(applicationPriorityRepository, times(1)).save(applicationPriorityArgumentCaptor.capture());
        verify(documentsService, times(1)).softDeleteDocumentById(translatedDocument.getId());
        assertEquals(id, result);
        assertNull(applicationPriorityArgumentCaptor.getValue().getTranslatedDocument());
    }

    @Test
    public void testDeleteApplicationPriorityFileWithInvalidKey() {
        Long id = 1L;
        String fileKey = "invalid";
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(id);
        when(applicationPriorityRepository.findById(id)).thenReturn(Optional.of(applicationPriority));

        assertThrows(RuntimeException.class, ()->{applicationPriorityServiceImpl.deleteApplicationPriorityFile(id, fileKey);});
    }

    @Test
    public void testDeleteApplicationPriorityFileWithNotFoundPriority() {
        Long id = null;
        String fileKey = "priorityDocument";
        when(applicationPriorityRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, ()->{applicationPriorityServiceImpl.deleteApplicationPriorityFile(id, fileKey);});
    }

    @Test
    public void testDeleteApplicationPriority() {
        Long id = 1L;
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(id);
        applicationPriority.setIsDeleted(0);
        doReturn(applicationPriority).when(applicationPriorityServiceImpl).findById(id);
        when(applicationPriorityRepository.save(any(ApplicationPriority.class))).thenReturn(applicationPriority);

        Long result = applicationPriorityServiceImpl.deleteApplicationPriority(id);

        assertEquals(id, result);
    }

    @Test
    public void testDeleteApplicationPriorityWithDeletedPriority() {
        Long id = 1L;
        doThrow(new RuntimeException("test")).when(applicationPriorityServiceImpl).findById(id);
        assertThrows(RuntimeException.class, ()->{applicationPriorityServiceImpl.deleteApplicationPriority(id);});
    }

    @Test
    public void testDeleteApplicationPriorityWithNotFoundPriority() {
        Long id = 1L;
        doThrow(new BusinessException("test")).when(applicationPriorityServiceImpl).findById(id);
        assertThrows(BusinessException.class, ()->{applicationPriorityServiceImpl.deleteApplicationPriority(id);});
    }

    @Test
    public void testUpdatePriorityDocs() {
        Long id = 1L;
        Long priorityDocId = 2L;
        Long translatedDocId = 3L;
        ApplicationPriorityDocsDto applicationPriorityDocsDto = new ApplicationPriorityDocsDto();
        applicationPriorityDocsDto.setPriorityDocument(priorityDocId);
        applicationPriorityDocsDto.setTranslatedDocument(translatedDocId);
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(id);
        applicationPriority.setIsExpired(false);
        doReturn(applicationPriority).when(applicationPriorityServiceImpl).findById(id);
        when(applicationPriorityRepository.save(applicationPriority)).thenReturn(applicationPriority);

        Long result = applicationPriorityServiceImpl.updatePriorityDocs(id, applicationPriorityDocsDto);

        assertEquals(id, result);
        assertEquals(priorityDocId, applicationPriority.getPriorityDocument().getId());
        assertEquals(translatedDocId, applicationPriority.getTranslatedDocument().getId());
        verify(applicationPriorityRepository, times(1)).save(applicationPriority);
    }

    @Test
    public void testUpdatePriorityDocsWithExpired() {
        Long id = 1L;
        Long priorityDocId = 2L;
        Long translatedDocId = 3L;
        ApplicationPriorityDocsDto applicationPriorityDocsDto = new ApplicationPriorityDocsDto();
        applicationPriorityDocsDto.setPriorityDocument(priorityDocId);
        applicationPriorityDocsDto.setTranslatedDocument(translatedDocId);
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(id);
        applicationPriority.setIsExpired(true);
        doReturn(applicationPriority).when(applicationPriorityServiceImpl).findById(id);
        when(applicationPriorityRepository.save(applicationPriority)).thenReturn(applicationPriority);
        assertThrows(RuntimeException.class, ()->{applicationPriorityServiceImpl.updatePriorityDocs(id, applicationPriorityDocsDto);});

    }

    @Test
    public void testUpdatePriorityDocsWithException() {
        Long id = 1L;
        ApplicationPriorityDocsDto applicationPriorityDocsDto = new ApplicationPriorityDocsDto();
        doThrow(new RuntimeException("test")).when(applicationPriorityServiceImpl).findById(id);
        assertThrows(RuntimeException.class, ()->{applicationPriorityServiceImpl.updatePriorityDocs(id, applicationPriorityDocsDto);});
    }

    @Test
    public void testUpdatePriorityDocsWithNotFoundPriority() {
        Long id = 1L;
        ApplicationPriorityDocsDto applicationPriorityDocsDto = new ApplicationPriorityDocsDto();
        doThrow(new BusinessException("test")).when(applicationPriorityServiceImpl).findById(id);
        assertThrows(BusinessException.class, ()->{applicationPriorityServiceImpl.updatePriorityDocs(id, applicationPriorityDocsDto);});
    }

    @Test
    public void testListApplicationPriorites() {
        Long applicationId = 1L;
        List<ApplicationPriority> list = new ArrayList<>();
        doReturn(list).when(applicationPriorityRepository).getPrioritesOfByApplicationId(applicationId, 0);
        doReturn(new ArrayList<>()).when(applicationPriorityMapper).map(list);
        List<ApplicationPriorityListDto> result = applicationPriorityServiceImpl.listApplicationPriorites(applicationId);
        assertNotNull(result);
    }

    @Test
    public void testSetAboutExpiredAndSendNotification() {
        Long priorityDocumentsAllowanceDays = 30L;
        Long organizationId = 23L;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setEmail("test@example.com");
        applicationInfo.setMobileCode("+123");
        applicationInfo.setMobileNumber("456789");
        ApplicantsDto applicantsDto = new ApplicantsDto();
        applicantsDto.setNameAr("Test");
        applicantsDto.setNameEn("Test");
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(1L);
        applicationPriority.setApplicationInfo(applicationInfo);
        applicationPriority.setIsExpired(false);
        List<ApplicationPriority> priorities = new ArrayList<>();
        priorities.add(applicationPriority);
        when(applicationPriorityRepository.getAppsAboutToExpire(priorityDocumentsAllowanceDays - 2L, organizationId)).thenReturn(priorities);
        when(applicationInfoService.listMainApplicant(applicationInfo.getId())).thenReturn(applicantsDto);
        when(bpmCallerFeignClient.getRequestTypeConfigValue(PRIORITY_CLAIM_DAYS_CODE)).thenReturn("56");

        applicationPriorityServiceImpl.setAboutExpiredAndSendNotifiction();
        assertTrue(true);
    }


    @Test
    public void testUpdatePriorityStatusAndComment() {
        Long id = 1L;
        ApplicationPriorityUpdateStatusAndCommentsDto applicationPriorityUpdateStatusAndCommentsDto = new ApplicationPriorityUpdateStatusAndCommentsDto();
        applicationPriorityUpdateStatusAndCommentsDto.setCode("code");
        applicationPriorityUpdateStatusAndCommentsDto.setComment("comment");
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(1l);
        doReturn(applicationPriority).when(applicationPriorityServiceImpl).findById(id);
        doReturn(applicationPriority).when(applicationPriorityRepository).save(applicationPriority);
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setCategory(new LkApplicationCategory());
        applicationPriority.setApplicationInfo(applicationInfo);
        Long result = applicationPriorityServiceImpl.updatePriorityStatusAndComment(id, applicationPriorityUpdateStatusAndCommentsDto);
        assertEquals(1, result);
    }

    @Test
    public void testUpdatePriorityStatusAndCommentWithoutCodeAndComment() {
        Long id = 1L;
        ApplicationPriorityUpdateStatusAndCommentsDto applicationPriorityUpdateStatusAndCommentsDto = new ApplicationPriorityUpdateStatusAndCommentsDto();
        ApplicationPriority applicationPriority = new ApplicationPriority();
        applicationPriority.setId(1l);
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setCategory(new LkApplicationCategory());
        applicationPriority.setApplicationInfo(applicationInfo);
        doReturn(applicationPriority).when(applicationPriorityServiceImpl).findById(id);
        doReturn(applicationPriority).when(applicationPriorityRepository).save(applicationPriority);
        Long result = applicationPriorityServiceImpl.updatePriorityStatusAndComment(id, applicationPriorityUpdateStatusAndCommentsDto);
        assertEquals(1, result);
    }

}