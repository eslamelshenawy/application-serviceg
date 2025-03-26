package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PetitionRecoveryRequestDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.PetitionRecoveryRequestMapper;
import gov.saip.applicationservice.common.model.PetitionRecoveryRequest;
import gov.saip.applicationservice.common.service.PetitionRecoveryRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PetitionRecoveryRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetitionRecoveryRequestService petitionRecoveryRequestServiceMock;

    @InjectMocks
    private PetitionRecoveryRequestController petitionRecoveryRequestController;

    @Mock
    private PetitionRecoveryRequestMapper petitionRecoveryRequestMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petitionRecoveryRequestController).build();
    }

    @Test
    void testGetAllReports() throws Exception {
        Long appId = 1L;
        PetitionRecoveryRequest request1 = new PetitionRecoveryRequest();
        PetitionRecoveryRequest request2 = new PetitionRecoveryRequest();
        List<PetitionRecoveryRequest> requestList = Arrays.asList(request1, request2);
        PetitionRecoveryRequestDto dto1 = new PetitionRecoveryRequestDto();
        PetitionRecoveryRequestDto dto2 = new PetitionRecoveryRequestDto();
        List<PetitionRecoveryRequestDto> dtoList = Arrays.asList(dto1, dto2);
        ApiResponse<List<PetitionRecoveryRequestDto>> apiResponse = ApiResponse.ok(dtoList);

        when(petitionRecoveryRequestServiceMock.getAllByApplicationId(1L, SupportServiceType.PETITION_RECOVERY)).thenReturn(requestList);
        when(petitionRecoveryRequestMapper.map(Mockito.anyList())).thenReturn(dtoList);

        mockMvc.perform(get("/kc/support-service/petition-recovery/" + appId + "/application"))
                .andExpect(status().isOk());
    }
}