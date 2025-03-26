package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.mapper.ApplicationOfficeReportMapper;
import gov.saip.applicationservice.common.model.ApplicationOfficeReport;
import gov.saip.applicationservice.common.service.ApplicationOfficeReportService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApplicationOfficeReportControllerTest {

    @InjectMocks
    private ApplicationOfficeReportController applicationOfficeReportController;
    @Mock
    private ApplicationOfficeReportService applicationOfficeReportService;
    @Mock
    private ApplicationOfficeReportMapper applicationOfficeReportMapper;
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationOfficeReportController).build();
    }

    @Test
    public void testGetAllApplicationWords() throws Exception {
        Long appId = 1L;
        List<ApplicationOfficeReport> applicationOfficeReportList = new ArrayList<>();
        ApplicationOfficeReport applicationOfficeReport = new ApplicationOfficeReport();
        applicationOfficeReportList.add(applicationOfficeReport);
        List<ApplicationOfficeReportDto> applicationOfficeReportDtoList = new ArrayList<>();
        ApplicationOfficeReportDto applicationOfficeReportDto = new ApplicationOfficeReportDto();
        applicationOfficeReportDtoList.add(applicationOfficeReportDto);
        Mockito.when(applicationOfficeReportService.getAllByApplicationId(appId)).thenReturn(applicationOfficeReportList);
        Mockito.when(applicationOfficeReportMapper.map(applicationOfficeReportList)).thenReturn(applicationOfficeReportDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/app-office-report/{appId}/application", appId))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}