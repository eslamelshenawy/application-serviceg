package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.RevokeLicenceRequestDto;
import gov.saip.applicationservice.common.mapper.RevokeLicenceRequestMapper;
import gov.saip.applicationservice.common.model.RevokeLicenceRequest;
import gov.saip.applicationservice.common.service.RevokeLicenceRequestService;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RevokeLicenceRequestControllerTest {

    @Mock
    private RevokeLicenceRequestService revokeLicenceRequestService;

    @Mock
    private RevokeLicenceRequestMapper revokeLicenceRequestMapper;

    @InjectMocks
    private RevokeLicenceRequestController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllByApplicationID() throws Exception {
        long appId = 1L;
        SupportServiceType supportServiceType = SupportServiceType.REVOKE_LICENSE_REQUEST;

        RevokeLicenceRequest revokeLicenceRequest = new RevokeLicenceRequest();
        revokeLicenceRequest.setId(appId);

        RevokeLicenceRequestDto revokeLicenceRequestDto = new RevokeLicenceRequestDto();
        revokeLicenceRequestDto.setId(appId);

        List<RevokeLicenceRequest> revokeLicenceRequestList = Arrays.asList(revokeLicenceRequest);
        List<RevokeLicenceRequestDto> revokeLicenceRequestDtoList = Arrays.asList(revokeLicenceRequestDto);

        when(revokeLicenceRequestService.getAllByApplicationId(appId, supportServiceType))
                .thenReturn(revokeLicenceRequestList);
        when(revokeLicenceRequestMapper.map(revokeLicenceRequestList))
                .thenReturn(revokeLicenceRequestDtoList);

        mockMvc.perform(get("/kc/support-service/revoke-licence-request/{appId}/application", appId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").isArray())
                .andExpect(jsonPath("$.payload[0].id").value(appId));

        verify(revokeLicenceRequestService, times(1)).getAllByApplicationId(appId, supportServiceType);
        verify(revokeLicenceRequestMapper, times(1)).map(revokeLicenceRequestList);
    }

    @Test
    void getByID() throws Exception {
        long id = 1L;

        RevokeLicenceRequest revokeLicenceRequest = new RevokeLicenceRequest();
        revokeLicenceRequest.setId(id);

        RevokeLicenceRequestDto revokeLicenceRequestDto = new RevokeLicenceRequestDto();
        revokeLicenceRequestDto.setId(id);

        when(revokeLicenceRequestService.findByServiceId(id)).thenReturn(revokeLicenceRequestDto);

        ResultActions resultActions = mockMvc.perform(get("/kc/support-service/revoke-licence-request/service/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
         String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode jsonNode = objectMapper.readTree(contentAsString);
         assertEquals(true, jsonNode.get("success").asBoolean());
         assertEquals(200, jsonNode.get("code").asInt());
        verify(revokeLicenceRequestService, times(1)).findByServiceId(id);
    }


    @Test
    void getLicenseRequestAllInvolvedUsersInfo() throws Exception {
        long id = 1L;

        CustomerSampleInfoDto customerSampleInfoDto = new CustomerSampleInfoDto();
        customerSampleInfoDto.setId(id);

        List<CustomerSampleInfoDto> customerSampleInfoDtoList = Arrays.asList(customerSampleInfoDto);

        when(revokeLicenceRequestService.getLicenseRequestAllInvolvedUsersInfo(id))
                .thenReturn(customerSampleInfoDtoList);

        mockMvc.perform(get("/kc/support-service/revoke-licence-request/{id}/allInvolvedUsers", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").isArray())
                .andExpect(jsonPath("$.payload[0].id").value(id));

        verify(revokeLicenceRequestService, times(1)).getLicenseRequestAllInvolvedUsersInfo(id);
    }

}

