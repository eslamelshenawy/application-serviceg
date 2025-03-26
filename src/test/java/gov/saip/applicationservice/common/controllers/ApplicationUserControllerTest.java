package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSupportServicesTypeDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.mapper.ApplicationSupportServicesTypeMapper;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.ApplicationUserService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApplicationUserControllerTest {

    @InjectMocks
    private ApplicationUserController applicationUserController;
    @Mock
    private ApplicationUserService applicationUserService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationUserController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllSupportServicesByApplication() throws Exception {
        String userName = "name";
        Long applicationId = 1l;
        ApplicationUserRoleEnum userRole = ApplicationUserRoleEnum.CHECKER;

        ApplicationSupportServicesTypeDto similarDocumentDto1 = new ApplicationSupportServicesTypeDto();
        similarDocumentDto1.setId(1L);

        ApiResponse<List<ApplicationSupportServicesTypeDto>> expectedResponse = ApiResponse.ok(List.of(similarDocumentDto1));

        Mockito.doNothing().when(applicationUserService).updateApplicationUser(userName, applicationId, userRole);

        mockMvc.perform(MockMvcRequestBuilders.put("/kc/application-user/reassign")
                        .param("userName", userName)
                        .param("applicationId", String.valueOf(applicationId))
                        .param("userRole", ApplicationUserRoleEnum.CHECKER.toString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}