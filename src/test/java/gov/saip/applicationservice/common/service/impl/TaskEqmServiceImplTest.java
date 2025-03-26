package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.TaskEqm;
import gov.saip.applicationservice.common.repository.TaskEqmRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.LkTaskEqmItemService;
import gov.saip.applicationservice.common.service.lookup.LkTaskEqmStatusService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskEqmServiceImplTest {

    @InjectMocks
    @Spy
    private TaskEqmServiceImpl taskEqmService;

    @Mock
    private TaskEqmRepository taskEqmRepository;

    @Mock
    private LkTaskEqmItemService taskEqmItemService;

    @Mock
    private ApplicationInfoService applicationInfoService;

    @Mock
    private LkTaskEqmStatusService lkTaskEqmStatusService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByTaskId() {
        String taskId = "123";
        TaskEqm taskEqm = new TaskEqm();
        when(taskEqmRepository.findByTaskId(taskId)).thenReturn(Optional.of(taskEqm));

        TaskEqm result = taskEqmService.findByTaskId(taskId);

        assertEquals(taskEqm, result);
    }

    @Test
    void testFindByTaskIdNotFound() {
        String taskId = "123";
        when(taskEqmRepository.findByTaskId(taskId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> taskEqmService.findByTaskId(taskId));

        assertEquals(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testFindByEqmApplicationId() {
        Long applicationId = 1L;
        TaskEqm taskEqm = new TaskEqm();
        when(taskEqmRepository.findByEqmApplicationId(applicationId)).thenReturn(Optional.of(taskEqm));

        TaskEqm result = taskEqmService.findByEqmApplicationId(applicationId);

        assertEquals(taskEqm, result);
    }

    @Test
    void testFindByEqmApplicationIdNotFound() {
        Long applicationId = 1L;
        when(taskEqmRepository.findByEqmApplicationId(applicationId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> taskEqmService.findByEqmApplicationId(applicationId));

        assertEquals(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testCheckByEqmApplicationId() {
        Long applicationId = 1L;
        when(taskEqmRepository.countByEqmApplicationId(applicationId)).thenReturn(1L);

        boolean result = taskEqmService.checkByEqmApplicationId(applicationId);

        assertTrue(result);
    }

    @Test
    void testCheckByEqmApplicationIdFalse() {
        Long applicationId = 1L;
        when(taskEqmRepository.countByEqmApplicationId(applicationId)).thenReturn(0L);

        boolean result = taskEqmService.checkByEqmApplicationId(applicationId);

        assertFalse(result);
    }

    @Test
    void testFindAllByApplicationId() {
        Long applicationId = 1L;
        List<TaskEqm> taskEqmList = new ArrayList<>();
        when(taskEqmRepository.findAllByApplicationInfoId(applicationId)).thenReturn(taskEqmList);

        List<TaskEqm> result = taskEqmService.findAllByApplicationId(applicationId);

        assertEquals(taskEqmList, result);
    }

    @Test
    void testUpdateTaskEqmWithExistingTaskId() {
        TaskEqm updatedTaskEqm = new TaskEqm();
        updatedTaskEqm.setId(1L);
        updatedTaskEqm.setTaskId("123");
        updatedTaskEqm.setComments("Updated comments");
        updatedTaskEqm.setTaskEqmRatingItems(new ArrayList<>());

        TaskEqm existingTaskEqm = new TaskEqm();
        when(taskEqmRepository.findByTaskId(updatedTaskEqm.getTaskId())).thenReturn(Optional.of(existingTaskEqm));

        when(taskEqmRepository.save(updatedTaskEqm)).thenReturn(updatedTaskEqm);

        doReturn(updatedTaskEqm).when(taskEqmService).insert(updatedTaskEqm);
        doReturn(updatedTaskEqm).when(taskEqmService).update(updatedTaskEqm);

        TaskEqm result = taskEqmService.update(updatedTaskEqm);

        assertNotNull(result);
        assertEquals("Updated comments", result.getComments());
    }



}

