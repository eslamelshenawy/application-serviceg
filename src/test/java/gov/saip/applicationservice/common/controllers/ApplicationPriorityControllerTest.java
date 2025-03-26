package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.service.ApplicationPriorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApplicationPriorityControllerTest {

    @InjectMocks
    private ApplicationPriorityController applicationPriorityController;
    @Mock
    private ApplicationPriorityService applicationPriorityService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationPriorityController).build();
        objectMapper = new ObjectMapper();

    }

    @Test
    public void testCreateUpdateApplicationPriorityBulk() throws Exception {
        Long applicationInfoId = 1L;
        ApplicationPriorityBulkDto applicationPriorityBulkDto = new ApplicationPriorityBulkDto();
        List<Long> createdIds = new ArrayList<>();
        createdIds.add(1L);
        ApiResponse<List<Long>> expectedResponse = ApiResponse.ok(createdIds);
        Mockito.when(applicationPriorityService.createUpdateApplicationPriority(applicationPriorityBulkDto, applicationInfoId)).thenReturn(createdIds);

        mockMvc.perform(MockMvcRequestBuilders.post("/kc/applicationpriorities/{applicationInfoId}/bulk", applicationInfoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(applicationPriorityBulkDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testNullifyField() throws Exception {
        Long id = 1L;
        String fieldKey = "test";
        Long deletedId = 1L;
        ApiResponse<Long> expectedResponse = ApiResponse.ok(deletedId);
        Mockito.when(applicationPriorityService.deleteApplicationPriorityFile(id, fieldKey)).thenReturn(deletedId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/kc/applicationpriorities/{id}/delete-doc", id)
                        .param("fieldKey", fieldKey))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testDeleteApplicationPriority() throws Exception {
        Long id = 1L;
        Long deletedId = 1L;
        ApiResponse<Long> expectedResponse = ApiResponse.ok(deletedId);
        Mockito.when(applicationPriorityService.deleteApplicationPriority(id)).thenReturn(deletedId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/kc/applicationpriorities/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testUpdatePriorityDocs() throws Exception {
        Long id = 1L;
        ApplicationPriorityDocsDto applicationPriorityDocsDto = new ApplicationPriorityDocsDto();
        Long updatedId = 1L;
        ApiResponse<Long> expectedResponse = ApiResponse.ok(updatedId);
        Mockito.when(applicationPriorityService.updatePriorityDocs(id, applicationPriorityDocsDto)).thenReturn(updatedId);

        mockMvc.perform(MockMvcRequestBuilders.patch("/kc/applicationpriorities/{id}/update-priority-docs", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(applicationPriorityDocsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetPriorityDocumentsAllowanceDays() throws Exception {
        Long days = 10L;
        ApiResponse<Long> expectedResponse = ApiResponse.ok(days);
        Mockito.when(applicationPriorityService.getPriorityDocumentsAllowanceDays()).thenReturn(days);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applicationpriorities/priority_documents_allowance_days"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetApplicationPriorites() throws Exception {
        Long appId = 1L;
        List<ApplicationPriorityListDto> applicationPriorityListDtoList = new ArrayList<>();
        ApiResponse<List<ApplicationPriorityListDto>> expectedResponse = ApiResponse.ok(applicationPriorityListDtoList);
        Mockito.when(applicationPriorityService.listApplicationPriorites(appId)).thenReturn(applicationPriorityListDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applicationpriorities/application/{appId}", appId))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testUpdatePriorityStatusAndComment() throws Exception {
        Long id = 1L;
        ApplicationPriorityUpdateStatusAndCommentsDto updateStatuscommentDto = new ApplicationPriorityUpdateStatusAndCommentsDto();
        Long updatedId = 1L;
        ApiResponse<Long> expectedResponse = ApiResponse.ok(updatedId);
        Mockito.when(applicationPriorityService.updatePriorityStatusAndComment(id, updateStatuscommentDto)).thenReturn(updatedId);

        mockMvc.perform(MockMvcRequestBuilders.put("/kc/applicationpriorities/{id}/update-priority-status-comment", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateStatuscommentDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}