package gov.saip.applicationservice.common.controllers.bpm;

import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.service.bpm.TaskListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;

public class TaskListControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private TaskListService taskListService;

    @InjectMocks
    private TaskListController taskListController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskListController).build();

    }

    @Test
    public void testCompleteUserTask() throws Exception {
        String taskId = "task123";
        CompleteTaskRequestDto dto = new CompleteTaskRequestDto();

        when(taskListService.completeTaskToUser(taskId, dto)).thenReturn("Task completed successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/kc/v1/task/{taskId}/complete", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

