package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.TaskEqmDto;
import gov.saip.applicationservice.common.mapper.TaskEqmMapper;
import gov.saip.applicationservice.common.model.TaskEqm;
import gov.saip.applicationservice.common.service.TaskEqmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskEqmControllerTest {

    @InjectMocks
    private TaskEqmController taskEqmController;

    @Mock
    private TaskEqmService taskEqmService;

    @Mock
    private TaskEqmMapper taskEqmMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskEqmController).build();
    }

    @Test
    public void testFindByTaskId() throws Exception {
        when(taskEqmService.findByTaskId(anyString())).thenReturn(new TaskEqm());
        when(taskEqmMapper.map(any(TaskEqm.class))).thenReturn(new TaskEqmDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/task-eqm/task/{taskId}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskEqmService, times(1)).findByTaskId("123");
        verify(taskEqmMapper, times(1)).map(any(TaskEqm.class));
    }

    @Test
    public void testFindByApplicationId() throws Exception {
        when(taskEqmService.findByEqmApplicationId(anyLong())).thenReturn(new TaskEqm());
        when(taskEqmMapper.map(any(TaskEqm.class))).thenReturn(new TaskEqmDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/task-eqm/application/{applicationId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskEqmService, times(1)).findByEqmApplicationId(1L);
        verify(taskEqmMapper, times(1)).map(any(TaskEqm.class));
    }

    @Test
    public void testCheckByEqmApplicationId() throws Exception {
        when(taskEqmService.checkByEqmApplicationId(anyLong())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/task-eqm/application/check/{applicationId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(taskEqmService, times(1)).checkByEqmApplicationId(1L);
    }

    @Test
    public void testFindAllByApplicationId() throws Exception {
        when(taskEqmService.findAllByApplicationId(anyLong())).thenReturn(Collections.singletonList(new TaskEqm()));
        when(taskEqmMapper.map(any(TaskEqm.class))).thenReturn(new TaskEqmDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/task-eqm/application/all/{applicationId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskEqmService, times(1)).findAllByApplicationId(1L);
    }
}

