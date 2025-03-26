package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ProtectionExtendRequestDto;
import gov.saip.applicationservice.common.mapper.ProtectionExtendRequestMapper;
import gov.saip.applicationservice.common.model.ProtectionExtendRequest;
import gov.saip.applicationservice.common.service.ProtectionExtendRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.PROTECTION_PERIOD_EXTENSION_REQUEST;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProtectionExtendRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProtectionExtendRequestService protectionExtendRequestService;

    @Mock
    private ProtectionExtendRequestMapper protectionExtendRequestMapper;

    @InjectMocks
    private ProtectionExtendRequestController protectionExtendRequestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(protectionExtendRequestController).build();
    }

    @Test
    public void testGetAllReports() throws Exception {
        Long appId = 1L;

        List<ProtectionExtendRequestDto> protectionExtendRequestDtoList = createProtectionExtendRequestDtoList();

        when(protectionExtendRequestService.getAllByApplicationId(appId, PROTECTION_PERIOD_EXTENSION_REQUEST))
                .thenReturn(createProtectionExtendRequestList());

        when(protectionExtendRequestMapper.map(createProtectionExtendRequestList()))
                .thenReturn(protectionExtendRequestDtoList);

        mockMvc.perform(get("/kc/support-service/protection-extend/{appId}/application", appId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testGetProtectionExtendRequestByApplicationSupportServiceId() throws Exception {
        Long applicationSupportServiceId = 1L;

        ProtectionExtendRequestDto protectionExtendRequestDto = createProtectionExtendRequestDto();

        when(protectionExtendRequestService.findById(applicationSupportServiceId))
                .thenReturn(createProtectionExtendRequest());

        when(protectionExtendRequestMapper.map(createProtectionExtendRequest()))
                .thenReturn(protectionExtendRequestDto);

        mockMvc.perform(get("/kc/support-service/protection-extend/service/{id}", applicationSupportServiceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    private List<ProtectionExtendRequestDto> createProtectionExtendRequestDtoList() {
        return Arrays.asList(
                new ProtectionExtendRequestDto(),
                new ProtectionExtendRequestDto()
        );
    }

    private ProtectionExtendRequestDto createProtectionExtendRequestDto() {
        return new ProtectionExtendRequestDto();
    }

    private List<ProtectionExtendRequest> createProtectionExtendRequestList() {
        return Arrays.asList(
                new ProtectionExtendRequest(),
                new ProtectionExtendRequest()
        );
    }

    private ProtectionExtendRequest createProtectionExtendRequest() {
        return new ProtectionExtendRequest();
    }
}
