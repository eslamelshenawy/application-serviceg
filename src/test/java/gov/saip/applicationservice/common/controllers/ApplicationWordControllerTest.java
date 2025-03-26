package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSupportServicesTypeDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.mapper.ApplicationWordMapper;
import gov.saip.applicationservice.common.model.ApplicationWord;
import gov.saip.applicationservice.common.service.ApplicationUserService;
import gov.saip.applicationservice.common.service.ApplicationWordService;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApplicationWordControllerTest {

    @InjectMocks
    private ApplicationWordController applicationWordController;
    @Mock
    private ApplicationWordService applicationWordService;
    @Mock
    private ApplicationWordMapper applicationWordMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationWordController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllSupportServicesByApplication() throws Exception {
        Long appId = 1l;
        List<ApplicationWord> list = new ArrayList<>();
        Mockito.doReturn(list).when(applicationWordService).getAllAppWordsByApplicationId(appId);
        Mockito.doReturn(new ArrayList<>()).when(applicationWordMapper).map(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/app-word/{appId}/application", appId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}