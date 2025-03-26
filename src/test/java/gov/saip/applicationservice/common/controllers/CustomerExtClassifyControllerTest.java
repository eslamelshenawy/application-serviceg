package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyDto;
import gov.saip.applicationservice.common.enums.CustomerExtClassifyEnum;
import gov.saip.applicationservice.common.service.impl.CustomerExtClassifyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerExtClassifyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerExtClassifyServiceImpl customerExtClassifyService;

    @InjectMocks
    private CustomerExtClassifyController customerExtClassifyController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerExtClassifyController).build();
    }

    @Test
    public void testFindByApplicationId() throws Exception {
        Long applicationId = 1L;
        CustomerExtClassifyEnum type = CustomerExtClassifyEnum.AGENT;

        CustomerExtClassifyDto customerExtClassifyDto = new CustomerExtClassifyDto();
        customerExtClassifyDto.setId(1L);
        customerExtClassifyDto.setApplicationId(applicationId);
        customerExtClassifyDto.setCustomerExtClassifyType(type);

        when(customerExtClassifyService.findByApplicationId(applicationId, type))
                .thenReturn(customerExtClassifyDto);

        mockMvc.perform(get("/kc/v1/customer-ext-classify/findByApplicationId/{applicationId}/{type}", applicationId, type.name()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateCustomerExtClassify() throws Exception {
        CustomerExtClassifyDto customerExtClassifyDto = new CustomerExtClassifyDto();
        customerExtClassifyDto.setApplicationId(1L);
        customerExtClassifyDto.setCustomerExtClassifyType(CustomerExtClassifyEnum.INSTITUTION);

        when(customerExtClassifyService.createCustomerExtClassify(any(CustomerExtClassifyDto.class)))
                .thenReturn(1L);

        mockMvc.perform(post("/kc/v1/customer-ext-classify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerExtClassifyDto)))
                .andExpect(status().isOk());
    }

}