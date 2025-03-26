package gov.saip.applicationservice.common.service.bpm.impl;

import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.service.BPMCallerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskListServiceImplTest {

    @InjectMocks
    private TaskListServiceImpl taskListService;

    @Mock
    private BPMCallerService bpmCallerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCompleteTaskToUser() {
        String taskId = "task123";
        CompleteTaskRequestDto dto = new CompleteTaskRequestDto();
        dto.setNotes("Completed the task");
        dto.setVariables(null);

        String expectedResult = "Task completed successfully";

        when(bpmCallerService.completeTaskToUser(taskId, dto)).thenReturn(expectedResult);

        String result = taskListService.completeTaskToUser(taskId, dto);

        verify(bpmCallerService).completeTaskToUser(taskId, dto);

        assertEquals(expectedResult, result);
    }
}

