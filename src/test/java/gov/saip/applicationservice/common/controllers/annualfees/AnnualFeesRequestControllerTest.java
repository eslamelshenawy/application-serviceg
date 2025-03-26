package gov.saip.applicationservice.common.controllers.annualfees;

import gov.saip.applicationservice.common.dto.annualfees.AnnualFeesRequestDto;
import gov.saip.applicationservice.common.mapper.annualfees.AnnualFeesMapper;
import gov.saip.applicationservice.common.model.annual_fees.AnnualFeesRequest;
import gov.saip.applicationservice.common.service.annualfees.AnnualFeesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnnualFeesRequestControllerTest {

    @Mock
    private MockMvc mockMvc;


    @Mock
    private AnnualFeesService annualFeesService;

    @InjectMocks
    private AnnualFeesRequestController annualFeesRequestController;


    @Mock
    AnnualFeesMapper annualFeesMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(annualFeesRequestController).build();
    }

    @Test
    void givenCorrectApplicationSupportServiceId_whenGetTingAnnualFeesByApplicationSupportSerssviceId_thenCorrect() throws Exception {
        AnnualFeesRequest annualFeesRequest = new AnnualFeesRequest();
        annualFeesRequest.setId(1L);
        annualFeesRequest.setRequestNumber("RF123");

        AnnualFeesRequestDto annualFeesRequestDto = new AnnualFeesRequestDto();
        annualFeesRequest.setId(1L);
        annualFeesRequest.setRequestNumber("RF123");

        when(annualFeesService.findById(1L)).thenReturn(annualFeesRequest);
        when(annualFeesMapper.map(annualFeesRequest)).thenReturn(annualFeesRequestDto);
        mockMvc.perform(get("/kc/annual-fees/service/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}
