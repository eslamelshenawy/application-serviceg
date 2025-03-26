package gov.saip.applicationservice.common;

import gov.saip.applicationservice.common.controllers.RevokeByCourtOrderController;
import gov.saip.applicationservice.common.dto.RevokeByCourtOrderInternalDto;
import gov.saip.applicationservice.common.dto.RevokeByCourtOrderRequestDto;
import gov.saip.applicationservice.common.mapper.RevokeByCourtOrderMapper;
import gov.saip.applicationservice.common.model.RevokeByCourtOrder;
import gov.saip.applicationservice.common.service.RevokeByCourtOrderService;
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
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RevokeByCourtOrderControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private RevokeByCourtOrderService revokeByCourtOrderService;

    @InjectMocks
    private RevokeByCourtOrderController revokeByCourtOrderController;

    @Mock
    private RevokeByCourtOrderMapper revokeByCourtOrderMapper;

    private RevokeByCourtOrderRequestDto revokeByCourtOrderRequestDto;

    private RevokeByCourtOrder revokeByCourtOrder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(revokeByCourtOrderController).build();
    }

    public RevokeByCourtOrderControllerTest() {
        revokeByCourtOrderRequestDto = new RevokeByCourtOrderRequestDto();
        revokeByCourtOrderRequestDto.setId(1L);
        revokeByCourtOrderRequestDto.setRequestNumber("RF123");

        revokeByCourtOrder = new RevokeByCourtOrder();
        revokeByCourtOrder.setId(1L);
        revokeByCourtOrder.setRequestNumber("RF123");
    }

    @Test
    void givenCorrectApplicationSupportServiceId_whenGetRevokeByCourtOrderRequestByApplicationSupportServiceId_thenCorrect() throws Exception {
        when(revokeByCourtOrderService.findById(any())).thenReturn(revokeByCourtOrder);
        when(revokeByCourtOrderMapper.map(revokeByCourtOrder)).thenReturn(revokeByCourtOrderRequestDto);
        mockMvc.perform(get("/kc/support-service/revoke-by-court-order/service/1"))
                .andExpect(status().isOk());
    }

    @Test
    void givenInCorrectApplicationSupportServiceId_whenGetRevokeByCourtOrderRequestByApplicationSupportServiceId_thenThrowException() throws Exception {
        when(revokeByCourtOrderService.findById(1L)).thenThrow(BusinessException.class);
            mockMvc.perform(get("/kc/support-service/revoke-by-court-order/service/1")).andExpect(status().isOk());;

    }

    @Test
    void givenCorrectApplicationId_whenGetAllRevokeByCourtOrderRequests_thenCorrect() throws Exception {
        List<RevokeByCourtOrder> revokeByCourtOrders = Arrays.asList(revokeByCourtOrder);
        when(revokeByCourtOrderService.getAllByApplicationId(1L, REVOKE_BY_COURT_ORDER)).thenReturn(revokeByCourtOrders);
        when(revokeByCourtOrderMapper.map(revokeByCourtOrders)).thenReturn(Arrays.asList(revokeByCourtOrderRequestDto));
        mockMvc.perform(get("/kc/support-service/revoke-by-court-order/1/application"))
                .andExpect(status().isOk());
    }

    @Test
    void givenInCorrectApplicationId_whenGetAllRevokeByCourtOrderRequests_thenThrowException() throws Exception {
        when(revokeByCourtOrderService.getAllByApplicationId(1L, REVOKE_BY_COURT_ORDER)).thenThrow(BusinessException.class);
        assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/kc/support-service/revoke-by-court-order/1/application"));
        });
    }

    @Test
    void givenCorrectApplicationId_whenUpdateRevokeByCourtOrderRequestWithInternalData_thenCorrect() throws Exception {
        doNothing().when(revokeByCourtOrderService).updateRevokeByCourtOrderRequestWithInternalData(any());
        mockMvc.perform(put("/kc/support-service/revoke-by-court-order/internalData").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(new RevokeByCourtOrderInternalDto())))
                .andExpect(status().isOk());
    }


}