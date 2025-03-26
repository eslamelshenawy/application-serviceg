package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.TasksUiDto;
import gov.saip.applicationservice.common.service.BPMCallerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserTasksControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BPMCallerService bpmCallerServiceMock;

    @InjectMocks
    private UserTasksController userTasksController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userTasksController).build();
    }

    @Test
    public void testGetUserTasks() throws Exception {
        int page = 0;
        int limit = 10;
        String query = "query";
        String type = "type";
        String fromDate = "2022-01-01";
        String toDate = "2022-01-31";

        PaginationDto paginationDto = new PaginationDto();
        ApiResponse<PaginationDto<List<TasksUiDto>>> apiResponse = ApiResponse.ok(paginationDto);

        when(bpmCallerServiceMock.getAllUserTasks(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString(),anyString(),null,null,null)).thenReturn(apiResponse);

        mockMvc.perform(get("/kc/user/tasks/me")
                        .param("page", String.valueOf(page))
                        .param("limit", String.valueOf(limit))
                        .param("query", query)
                        .param("type", type)
                        .param("fromDate", fromDate)
                        .param("toDate", toDate)
                        .param("expiryDate", "expiryDate"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTaskRemainingTime() throws Exception {
        long applicationId = 123L;

        Map<String, Object> remainingTimeMap = new HashMap<>();
        remainingTimeMap.put("remainingTime", 10L);

        when(bpmCallerServiceMock.getTaskRemainingTimeByApplicationId(anyLong(),anyString())).thenReturn(remainingTimeMap);

        mockMvc.perform(get("/kc/user/task/remaining-time")
                        .param("applicationId", String.valueOf(applicationId)))
                .andExpect(status().isOk());
    }
}