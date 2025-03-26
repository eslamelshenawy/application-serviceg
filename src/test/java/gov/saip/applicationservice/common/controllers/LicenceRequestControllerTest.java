package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.LicenceRequestDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.LicenceRequestMapper;
import gov.saip.applicationservice.common.model.LicenceRequest;
import gov.saip.applicationservice.common.service.LicenceRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LicenceRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LicenceRequestService licenceRequestService;

    @Mock
    private LicenceRequestMapper licenceRequestMapper;

    @InjectMocks
    private LicenceRequestController licenceRequestController;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(licenceRequestController).build();
    }

    @Test
    public void testGetAllReports() throws Exception {
        Long appId = 1L;

        List<LicenceRequestDto> licenceRequestDtoList = createLicenceRequestDtoList();

        List<LicenceRequest> licenceRequest = new ArrayList<>();
        when(licenceRequestMapper.map(licenceRequest)).thenReturn(licenceRequestDtoList);
        when(licenceRequestService.getAllByApplicationId(appId, SupportServiceType.LICENSING_REGISTRATION))
                .thenReturn(licenceRequest);

        mockMvc.perform(get("/kc/support-service/licence-request/{appId}/application", appId)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testGetEvictionRequestByApplicationSupportServiceId() throws Exception {
        Long id = 1L;

        LicenceRequestDto licenceRequestDto = createLicenceRequestDto();

        when(licenceRequestService.getLicenceRequest(id))
                .thenReturn(licenceRequestDto);

        mockMvc.perform(get("/kc/support-service/licence-request/service/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    private List<LicenceRequestDto> createLicenceRequestDtoList() {
        return Arrays.asList(
                new LicenceRequestDto(),
                new LicenceRequestDto()
        );
    }

    private LicenceRequestDto createLicenceRequestDto() {
        return new LicenceRequestDto();
    }
}

