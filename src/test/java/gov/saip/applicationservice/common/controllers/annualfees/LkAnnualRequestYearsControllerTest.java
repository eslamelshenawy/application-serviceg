package gov.saip.applicationservice.common.controllers.annualfees;

import gov.saip.applicationservice.common.dto.annualfees.LkAnnualRequestYearsDto;
import gov.saip.applicationservice.common.service.annualfees.LkAnnualRequestYearsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class LkAnnualRequestYearsControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private LkAnnualRequestYearsService lkAnnualRequestYearsService;

    @InjectMocks
    private LkAnnualRequestYearsController lkAnnualRequestYearsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lkAnnualRequestYearsController).build();

    }

    @Test
    public void testGetAnnualYearsByAppId() throws Exception {
        Long appId = 1L;
        List<LkAnnualRequestYearsDto> yearsDtoList = new ArrayList<>();

        when(lkAnnualRequestYearsService.getAnnualYearsByAppId(appId)).thenReturn(yearsDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/annual-fees/years/application/{id}", appId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
