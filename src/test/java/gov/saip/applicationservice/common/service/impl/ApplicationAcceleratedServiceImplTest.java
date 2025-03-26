package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.ApplicationAcceleratedDto;
import gov.saip.applicationservice.common.dto.ApplicationAcceleratedLightDto;
import gov.saip.applicationservice.common.mapper.ApplicationAcceleratedMapper;
import gov.saip.applicationservice.common.model.ApplicationAccelerated;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.repository.ApplicationAcceleratedRepository;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.validators.ApplicationAcceleratedValidator;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApplicationAcceleratedServiceImplTest {

    @Mock
    private ApplicationAcceleratedRepository applicationAcceleratedRepository;

    @Mock
    private ApplicationAcceleratedMapper applicationAcceleratedMapper;

    @Mock
    private ApplicationAcceleratedValidator applicationAcceleratedValidator;

    @Mock
    private DocumentsService documentsService;

    @InjectMocks
    private ApplicationAcceleratedServiceImpl applicationAcceleratedService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrUpdateApplicationAccelerated() {
        ApplicationAcceleratedDto applicationAcceleratedDto = new ApplicationAcceleratedDto();
        applicationAcceleratedDto.setId(1L);
        applicationAcceleratedDto.setApplicationInfoId(2L);

        ApplicationAccelerated applicationAccelerated = new ApplicationAccelerated();
        applicationAccelerated.setId(1L);
        applicationAccelerated.setApplicationInfo(new ApplicationInfo());
        applicationAccelerated.getApplicationInfo().setId(2L);

        when(applicationAcceleratedRepository.findById(1L)).thenReturn(Optional.of(applicationAccelerated));
        when(applicationAcceleratedRepository.save(any())).thenReturn(applicationAccelerated);

        Long result = applicationAcceleratedService.addOrUpdateApplicationAccelerated(applicationAcceleratedDto);

        verify(applicationAcceleratedValidator, never()).validateApplicationAccelerated(any());
        verify(applicationAcceleratedRepository).save(any(ApplicationAccelerated.class));
        assertEquals(1L, result);
    }

    @Test
    public void testDeleteApplicationAcceleratedFile() {
        Long id = 1L;
        String fileKey = "comparative";

        ApplicationAccelerated applicationAccelerated = new ApplicationAccelerated();
        Document comparativeDocument = new Document();
        comparativeDocument.setId(1L);
        applicationAccelerated.setComparative(comparativeDocument);

        when(applicationAcceleratedRepository.findById(id)).thenReturn(Optional.of(applicationAccelerated));
        when(documentsService.softDeleteDocumentById(1L)).thenReturn(1L);

        Long result = applicationAcceleratedService.deleteApplicationAcceleratedFile(id, fileKey);

        verify(applicationAcceleratedRepository).save(applicationAccelerated);
        assertEquals(1L, result);
    }

    @Test
    public void testGetByApplicationInfo() {
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);

        ApplicationAccelerated applicationAccelerated = new ApplicationAccelerated();
        applicationAccelerated.setId(1L);

        when(applicationAcceleratedRepository.findByApplicationInfo(applicationInfo)).thenReturn(Optional.of(applicationAccelerated));

        Optional<ApplicationAccelerated> result = applicationAcceleratedService.getByApplicationInfo(applicationInfo);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testCheckIfApplicationAccelrated() {
        Long id = 1L;

        when(applicationAcceleratedRepository.checkIfApplicationAccelrated(id)).thenReturn(true);

        Boolean result = applicationAcceleratedService.checkIfApplicationAccelrated(id);

        assertTrue(result);
    }

    @Test
    public void testGetByApplicationId() {
        Long id = 1L;

        ApplicationAccelerated applicationAccelerated = new ApplicationAccelerated();
        applicationAccelerated.setId(1L);

        ApplicationAcceleratedLightDto applicationAcceleratedLightDto = new ApplicationAcceleratedLightDto();
        applicationAcceleratedLightDto.setId(1l);
        when(applicationAcceleratedRepository.findByApplicationInfoId(id)).thenReturn(List.of(applicationAccelerated));
        when(applicationAcceleratedMapper.mapAppAccelerated(applicationAccelerated)).thenReturn(applicationAcceleratedLightDto);

        ApplicationAcceleratedLightDto result = applicationAcceleratedService.getByApplicationId(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testUpdateApplicationAcceleratedStatus() {
        ApplicationAcceleratedRepository applicationAcceleratedRepository = mock(ApplicationAcceleratedRepository.class);

        Long appId = 1L;
        Boolean refused = true;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(appId);

        when(applicationAcceleratedRepository.findByApplicationInfo(applicationInfo)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            applicationAcceleratedService.updateApplicationAcceleratedStatus(appId, refused);
        });

        verify(applicationAcceleratedRepository, never()).save(any());

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("EXCEPTION_RECORD_NOT_FOUND", exception.getMessage());
    }
    
    
    @Test
    @DisplayName("delete by application id ")
    void deleteByApplicationId_givenAppId_removeEntity() {
        // arrange
        doNothing().when(applicationAcceleratedRepository).deleteByApplicationId(anyLong());
        // acct
        applicationAcceleratedRepository.deleteByApplicationId(anyLong());
        // assert
        verify(applicationAcceleratedRepository).deleteByApplicationId(anyLong());
    }
}
