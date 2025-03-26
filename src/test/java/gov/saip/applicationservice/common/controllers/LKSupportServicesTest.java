package gov.saip.applicationservice.common.controllers;


import gov.saip.applicationservice.common.mapper.LKSupportServicesMapper;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.service.LKSupportServicesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class LKSupportServicesTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private LKSupportServicesService lkSupportServicesService;


    @Mock
    private LKSupportServicesMapper lkSupportServicesMapper;


    @InjectMocks
    private LKSupportServicesController lkSupportServicesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(lkSupportServicesController).build();
    }

    @Test
    public void findSupportServicesByApplicationCategoryName_ShouldReturnServices() throws Exception {
        LKSupportServices mockService = new LKSupportServices();
        when(lkSupportServicesService.findServicesByCategoryId(1L))
                .thenReturn(Collections.singletonList(mockService));
        mockMvc.perform(get("/kc/support-service/requests/specific-category")
                        .param("category-id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
