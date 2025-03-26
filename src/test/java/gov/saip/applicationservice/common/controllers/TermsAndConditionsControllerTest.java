package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.TermsAndConditionsDto;
import gov.saip.applicationservice.common.model.TermsAndConditions;
import gov.saip.applicationservice.common.service.TermsAndConditionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TermsAndConditionsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TermsAndConditionsService termsAndConditionsServiceMock;

    private TermsAndConditionsController termsAndConditionsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        termsAndConditionsController = new TermsAndConditionsController(termsAndConditionsServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(termsAndConditionsController).build();
    }

    @Test
    public void testGetAllTermsAndConditions() throws Exception {
        TermsAndConditions tc1 = new TermsAndConditions();
        TermsAndConditions tc2 = new TermsAndConditions();
        List<TermsAndConditions> tcs = Arrays.asList(tc1, tc2);

        TermsAndConditionsDto dtotc1 = new TermsAndConditionsDto();
        TermsAndConditionsDto dtotc2 = new TermsAndConditionsDto();
        List<TermsAndConditionsDto> dtos = Arrays.asList(dtotc1, dtotc2);

        when(termsAndConditionsServiceMock.getAllTermsAndConditionsSorted()).thenReturn(dtos);


        mockMvc.perform(get("/kc/termsAndConditions"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTermsAndConditions() throws Exception {
        String appCategory = "appCategory1";
        String requestType = "requestType1";
        TermsAndConditions tc1 = new TermsAndConditions();
        List<TermsAndConditions> tcs = Arrays.asList(tc1);

        TermsAndConditionsDto tc = new TermsAndConditionsDto();
        List<TermsAndConditionsDto> tcDtos = Arrays.asList(tc);

        when(termsAndConditionsServiceMock.getTermsAndConditionsSorted(anyString(), anyString())).thenReturn(tcDtos);

        mockMvc.perform(get("/kc/termsAndConditions/app-category/{appCategory}/request-type/{requestType}", appCategory, requestType))
                .andExpect(status().isOk());
    }
}