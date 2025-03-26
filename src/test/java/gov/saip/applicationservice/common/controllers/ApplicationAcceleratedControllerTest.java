package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationAcceleratedDto;
import gov.saip.applicationservice.common.service.ApplicationAcceleratedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApplicationAcceleratedControllerTest {

    @InjectMocks
    private ApplicationAcceleratedController applicationAcceleratedController;
    @Mock
    private ApplicationAcceleratedService applicationAcceleratedService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationAcceleratedController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddApplicationAccelerated() throws Exception {
        ApplicationAcceleratedDto applicationAcceleratedDto = new ApplicationAcceleratedDto();
        applicationAcceleratedDto.setId(1L);
        Mockito.when(applicationAcceleratedService.addOrUpdateApplicationAccelerated(applicationAcceleratedDto)).thenReturn(1L);
        ApiResponse<Long> expectedResponse = ApiResponse.ok(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/kc/applicationAccelerated")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applicationAcceleratedDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteApplicationAcceleratedFile() throws Exception {
        Long id = 1L;
        String fieldKey = "file";
        Mockito.when(applicationAcceleratedService.deleteApplicationAcceleratedFile(id, fieldKey)).thenReturn(1L);
        ApiResponse<Long> expectedResponse = ApiResponse.ok(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/kc/applicationAccelerated/{id}/delete-doc", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fieldKey", fieldKey))
                .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("delete by application id ")
    public void deleteByApplicationId() throws Exception {
        Long appId = 1L;
        doNothing().when(applicationAcceleratedService).deleteByApplicationId(appId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/kc/applicationAccelerated/application/{id}/hard-deleted", appId))
                .andExpect(status().isOk());
    }
   
}
