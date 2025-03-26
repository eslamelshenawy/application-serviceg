package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestDto;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.mapper.agency.TrademarkAgencyRequestMapper;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ChangeOwnershipRequestServiceImplTest {

    @InjectMocks
    private ChangeOwnershipRequestServiceImpl changeOwnershipRequestServiceImpl;
    @Mock
    private CustomerServiceFeignClient customerServiceFeignClient;
    @Mock
    private TrademarkAgencyRequestService trademarkAgencyRequestService;
    @Mock
    private TrademarkAgencyRequestMapper trademarkAgencyRequestMapper;

    @Mock
    HttpServletRequest request;


    @Test
    public void testGetTrademarkAgencyRequestByRequestNumber_pass() {
        String requestNumber = "TMA-000001";
        String agentCode = "AG-Code";
        Long applicationId = 1L;

        TrademarkAgencyRequest trademarkAgencyRequest = new TrademarkAgencyRequest();
        trademarkAgencyRequest.setRequestNumber(requestNumber);
        trademarkAgencyRequest.setEndAgency(LocalDate.of(LocalDate.now().getYear()+1, 5,5));
        trademarkAgencyRequest.setClientCustomerCode("AG-245");

        TrademarkAgencyRequestDto expectedTrademarkAgencyRequestDto = new TrademarkAgencyRequestDto();
        expectedTrademarkAgencyRequestDto.setRequestNumber(requestNumber);

        Mockito.when(trademarkAgencyRequestService.getActiveAgentAgnecyRequestOnApplication(null, requestNumber, applicationId, TrademarkAgencyType.CHANGE_OWNERSHIP))
                .thenReturn(trademarkAgencyRequest);

        Mockito.when(trademarkAgencyRequestMapper.map(trademarkAgencyRequest)).thenReturn(expectedTrademarkAgencyRequestDto);

        CustomerSampleInfoDto customerSampleInfoDto = new CustomerSampleInfoDto();
        Mockito.when(customerServiceFeignClient.getAnyCustomerByCustomerCode(Mockito.anyString())).thenReturn(ApiResponse.ok(customerSampleInfoDto));

        TrademarkAgencyRequestDto actualTrademarkAgencyRequestDto = changeOwnershipRequestServiceImpl.getTrademarkAgencyRequestByRequestNumber(requestNumber, applicationId);
        assertEquals(requestNumber,actualTrademarkAgencyRequestDto.getRequestNumber());
    }

    @Test
    public void testGetTrademarkAgencyRequestByRequestNumber_fail() {
        String requestNumber = "TMA-000001";
        String agentCode = "AG-Code";
        Long applicationId = 1L;
        Mockito.when(trademarkAgencyRequestService.getActiveAgentAgnecyRequestOnApplication(null, requestNumber, applicationId, TrademarkAgencyType.CHANGE_OWNERSHIP))
                .thenReturn(null);

        assertThrows(BusinessException.class,() -> changeOwnershipRequestServiceImpl.getTrademarkAgencyRequestByRequestNumber(requestNumber,  applicationId));
    }
}
