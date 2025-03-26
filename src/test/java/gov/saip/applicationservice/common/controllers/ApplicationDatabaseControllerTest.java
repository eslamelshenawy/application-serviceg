package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.mapper.ApplicationDatabaseMapper;
import gov.saip.applicationservice.common.model.ApplicationDatabase;
import gov.saip.applicationservice.common.service.ApplicationDatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApplicationDatabaseControllerTest {

    @InjectMocks
    private ApplicationDatabaseController applicationDatabaseController;
    @Mock
    private ApplicationDatabaseService applicationDatabaseService;
    @Mock
    private ApplicationDatabaseMapper applicationDatabaseMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationDatabaseController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllApplicationWords() throws Exception {
        Long appId = 1L;
        List<ApplicationDatabase> applicationDatabases = new ArrayList<>();
        ApplicationDatabase applicationDatabase = new ApplicationDatabase();
        applicationDatabases.add(applicationDatabase);
        List<ApplicationDatabaseDto> applicationDatabaseDtos = new ArrayList<>();
        ApplicationDatabaseDto applicationDatabaseDto = new ApplicationDatabaseDto();
        applicationDatabaseDtos.add(applicationDatabaseDto);
        ApiResponse<List<ApplicationDatabaseDto>> expectedResponse = ApiResponse.ok(applicationDatabaseDtos);
        Mockito.when(applicationDatabaseService.getAllByApplicationId(appId)).thenReturn(applicationDatabases);
        Mockito.when(applicationDatabaseMapper.map(applicationDatabases)).thenReturn(applicationDatabaseDtos);


        mockMvc.perform(MockMvcRequestBuilders.get("/kc/app-database/{appId}/application", appId))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}