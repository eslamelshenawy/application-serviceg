package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.RevokeByCourtOrderInternalDto;
import gov.saip.applicationservice.common.dto.RevokeByCourtOrderRequestDto;
import gov.saip.applicationservice.common.dto.RevokeVoluntaryRequestDto;
import gov.saip.applicationservice.common.mapper.RevokeByCourtOrderMapper;
import gov.saip.applicationservice.common.mapper.RevokeVoluntaryRequestMapper;
import gov.saip.applicationservice.common.model.RevokeByCourtOrder;
import gov.saip.applicationservice.common.model.RevokeVoluntaryRequest;
import gov.saip.applicationservice.common.service.RevokeByCourtOrderService;
import gov.saip.applicationservice.common.service.RevokeVoluntaryRequestService;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.REVOKE_BY_COURT_ORDER;
import static gov.saip.applicationservice.common.enums.SupportServiceType.VOLUNTARY_REVOKE;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RevokeVoluntaryControllerTest {


    @Mock
    private MockMvc mockMvc;

    @Mock
    private RevokeVoluntaryRequestService revokeVoluntaryRequestService;

    @InjectMocks
    private RevokeVoluntaryController revokeVoluntaryController;

    @Mock
    private RevokeVoluntaryRequestMapper revokeVoluntaryRequestMapper;

    private RevokeVoluntaryRequestDto revokeVoluntaryRequestDto;

    private RevokeVoluntaryRequest revokeVoluntaryRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(revokeVoluntaryController).build();
    }

    public RevokeVoluntaryControllerTest() {
        revokeVoluntaryRequestDto = new RevokeVoluntaryRequestDto();
        revokeVoluntaryRequestDto.setId(1L);
        revokeVoluntaryRequestDto.setRequestNumber("RF123");

        revokeVoluntaryRequest = new RevokeVoluntaryRequest();
        revokeVoluntaryRequest.setId(1L);
        revokeVoluntaryRequest.setRequestNumber("RF123");
    }

    @Test
    void givenCorrectApplicationSupportServiceId_whenGetRevokeVoluntaryRequestByApplicationSupportServiceId_thenCorrect() throws Exception {
        when(revokeVoluntaryRequestService.findById(any())).thenReturn(revokeVoluntaryRequest);
        when(revokeVoluntaryRequestMapper.map(revokeVoluntaryRequest)).thenReturn(revokeVoluntaryRequestDto);
        mockMvc.perform(get("/kc/support-service/voluntary-revoke/service/1"))
                .andExpect(status().isOk());
    }

    @Test
    void givenInCorrectApplicationSupportServiceId_whenGetRevokeVoluntaryRequestByApplicationSupportServiceId_thenThrowException() throws Exception {
        when(revokeVoluntaryRequestService.findById(1L)).thenThrow(BusinessException.class);
        assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/kc/support-service/voluntary-revoke/service/1"));
        });
    }

    @Test
    void givenCorrectApplicationId_whenGetAllRevokeVoluntaries_thenCorrect() throws Exception {
        List<RevokeVoluntaryRequest> revokeVoluntaries = Arrays.asList(revokeVoluntaryRequest);
        when(revokeVoluntaryRequestService.getAllByApplicationId(1L, VOLUNTARY_REVOKE)).thenReturn(revokeVoluntaries);
        when(revokeVoluntaryRequestMapper.map(revokeVoluntaries)).thenReturn(Arrays.asList(revokeVoluntaryRequestDto));
        mockMvc.perform(get("/kc/support-service/voluntary-revoke/1/application"))
                .andExpect(status().isOk());
    }

    @Test
    void givenInCorrectApplicationId_whenGetAllRevokeVoluntaries_thenThrowException() throws Exception {
        when(revokeVoluntaryRequestService.getAllByApplicationId(1L, VOLUNTARY_REVOKE)).thenThrow(BusinessException.class);
        assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/kc/support-service/voluntary-revoke/1/application"));
        });
    }


}
