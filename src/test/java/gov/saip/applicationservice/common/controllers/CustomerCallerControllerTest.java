package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.CustomerCodeListDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerCallerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerServiceCaller customerServiceCaller;

    @InjectMocks
    CustomerCallerController customerCallerController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerCallerController).build();
    }

    @Test
    public void testGetCustomerByListOfCode() throws Exception {
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        customerCodeListDto.setCustomerCode(List.of("code1", "code2"));

        when(customerServiceCaller.getCustomerByListOfCode(any(CustomerCodeListDto.class)))
                .thenReturn(ApiResponse.ok(List.of(new CustomerSampleInfoDto())));

        mockMvc.perform(post("/customer/applications/by-code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerCodeListDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserColleagues() throws Exception {

        ApiResponse<List<Long>> res = ApiResponse.ok(List.of(2L, 3L));
        when(customerServiceCaller.getUserColleagues(eq(1L)))
                .thenReturn(res);

        mockMvc.perform(get("/customer/1/colleagues"))
                .andExpect(status().isOk());
    }
}