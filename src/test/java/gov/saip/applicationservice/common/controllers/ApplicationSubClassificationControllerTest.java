package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSimilarDocumentDto;
import gov.saip.applicationservice.common.dto.ApplicationSubClassificationDto;
import gov.saip.applicationservice.common.mapper.ApplicationSimilarDocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationSimilarDocument;
import gov.saip.applicationservice.common.service.ApplicationSimilarDocumentService;
import gov.saip.applicationservice.common.service.ApplicationSubClassificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApplicationSubClassificationControllerTest {

    @InjectMocks
    private ApplicationSubClassificationController applicationSubClassificationController;
    @Mock
    private ApplicationSubClassificationService applicationSubClassificationService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationSubClassificationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreate() throws Exception {
        Long applicationId = 1L;
        Long subClassificationId = 2L;
        ApplicationSubClassificationDto req = new ApplicationSubClassificationDto();
        req.setClassificationId(1l);
        ApiResponse<Long> expectedResponse = ApiResponse.created(subClassificationId);
        Mockito.when(applicationSubClassificationService.createApplicationSubClassification(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(subClassificationId);
        mockMvc.perform(MockMvcRequestBuilders.post("/kc/application-sub-classification/application/{id}", applicationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Long applicationId = 1L;
        Long subClassificationId = 2L;
        ApplicationSubClassificationDto req = new ApplicationSubClassificationDto();
        req.setClassificationId(1l);
        ApiResponse<Long> expectedResponse = ApiResponse.created(subClassificationId);
        Mockito.when(applicationSubClassificationService.updateApplicationSubClassification(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(subClassificationId);
        mockMvc.perform(MockMvcRequestBuilders.put("/kc/application-sub-classification/application/{id}", applicationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteByAppIdAndClassId() throws Exception {
        Long appId = 1L;
        Long classId = 1L;
        ApiResponse<Void> expectedResponse = ApiResponse.noContent();
        Mockito.doNothing().when(applicationSubClassificationService).deleteByAppIdAndClassId(appId, classId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/kc/application-sub-classification/application/{appId}/classification/{classId}", appId, classId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}