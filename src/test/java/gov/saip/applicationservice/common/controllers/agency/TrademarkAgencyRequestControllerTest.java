package gov.saip.applicationservice.common.controllers.agency;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestListDto;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
class TrademarkAgencyRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TrademarkAgencyRequestService trademarkAgencyRequestService;

    @InjectMocks
    private TrademarkAgencyRequestController trademarkAgencyRequestController;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trademarkAgencyRequestController).build();
    }


    @Test
    void updateCheckerDecision() throws Exception {
        TrademarkAgencyRequestDto dto = new TrademarkAgencyRequestDto();
        dto.setId(1l);
        doNothing().when(trademarkAgencyRequestService).updateAgencyCheckerDecision(dto);

        mockMvc.perform(put("/kc/tm-agency/checker-decision")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());

    }

    @Test
    void getRequestDetails() throws Exception {
        Long id = 1L;
        TrademarkAgencyRequestDto responseDto = new TrademarkAgencyRequestDto();
        when(trademarkAgencyRequestService.getRequestDetailsById(id)).thenReturn(responseDto);

        mockMvc.perform(get("/kc/tm-agency/{id}/id", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").exists());
    }

    @Test
    void getAgencyRequestList() throws Exception {
        when(trademarkAgencyRequestService.filterAndListAgenciesRequests(anyBoolean(), any(), any(), any(), any())).thenReturn(createPaginationDto());

        mockMvc.perform(get("/kc/tm-agency/paginated").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload.content").isArray());
    }

    private PaginationDto<List<TrademarkAgencyRequestListDto>> createPaginationDto() {
        List<TrademarkAgencyRequestListDto> listDtos = new ArrayList<>();

        PaginationDto<List<TrademarkAgencyRequestListDto>> paginationDto = new PaginationDto<>();
        paginationDto.setContent(listDtos);

        return paginationDto;
    }

}
