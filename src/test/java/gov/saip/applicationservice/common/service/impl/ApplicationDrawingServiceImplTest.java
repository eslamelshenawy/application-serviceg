package gov.saip.applicationservice.common.service.impl;
import gov.saip.applicationservice.common.model.ApplicationDrawing;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.repository.ApplicationDrawingRepository;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationDrawingServiceImplTest {

    @Mock
    private ApplicationDrawingRepository applicationDrawingRepository;

    @Mock
    private DocumentsService documentsService;

    @InjectMocks
    @Spy
    private ApplicationDrawingServiceImpl applicationDrawingService;

    @Test
    void insertShouldSetDefaultFlagToTrueWhenTitleIsEmpty() {
        // Arrange
        ApplicationDrawing entity = new ApplicationDrawing();
        entity.setDefault(true);
        entity.setApplicationInfo(new ApplicationInfo());
        entity.setApplicationInfo(new ApplicationInfo(1L));

        doReturn(entity).when(applicationDrawingService).insert(entity);

        // Act
        ApplicationDrawing result = applicationDrawingService.insert(entity);

        // Assert
        assertTrue(result.isDefault());
    }

    @Test
    void insertShouldSetDefaultFlagToFalseWhenTitleIsNotEmpty() {
        // Arrange
        ApplicationDrawing entity = new ApplicationDrawing();
        entity.setTitle("Non-empty title");
        entity.setApplicationInfo(new ApplicationInfo());
        entity.setApplicationInfo(new ApplicationInfo(1L));

        doReturn(entity).when(applicationDrawingService).insert(entity);

        // Act
        ApplicationDrawing result = applicationDrawingService.insert(entity);

        // Assert
        assertFalse(result.isDefault());
    }

    @Test
    void insertShouldThrowExceptionWhenMainDrawExists() {
        // Arrange
        ApplicationDrawing entity = new ApplicationDrawing();
        entity.setApplicationInfo(new ApplicationInfo());
        entity.setApplicationInfo(new ApplicationInfo(1L));
        entity.setId(1l);
        entity.setDefault(true);
        when(applicationDrawingRepository.checkMainDrawExists(any())).thenReturn(List.of(entity));

        // Act and Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            applicationDrawingService.insert(entity);
        });

        assertEquals(Constants.ErrorKeys.MAIN_DRAW_IS_EXISTS, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        verify(applicationDrawingRepository, never()).save(entity);
    }

    @Test
    void checkMainDrawExistsShouldReturnTrueWhenMainDrawExists() {
        // Arrange
        when(applicationDrawingRepository.checkMainDrawExists(any())).thenReturn(List.of(new ApplicationDrawing())); // Main draw exists

        // Act
        boolean result = applicationDrawingService.checkMainDrawExists(1L);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkMainDrawExistsShouldReturnFalseWhenMainDrawDoesNotExist() {
        // Arrange
        when(applicationDrawingRepository.checkMainDrawExists(any())).thenReturn(List.of());

        // Act
        boolean result = applicationDrawingService.checkMainDrawExists(1L);

        // Assert
        assertFalse(result);
    }
}

