package gov.saip.applicationservice.common.controllers.trademark;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.trademark.TrademarkLookupResDto;
import gov.saip.applicationservice.common.service.trademark.TrademarkLookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class TrademarkLookupControllerTest {

    @Mock
    private TrademarkLookupService trademarkLookupService;

    private TrademarkLookupController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new TrademarkLookupController(trademarkLookupService);
    }

    @Test
    void testGetTrademarkLookup() {
        TrademarkLookupResDto expectedData = createMockTrademarkLookupData();

        when(trademarkLookupService.getTrademarkLookup(anyLong())).thenReturn(expectedData);

        ApiResponse<TrademarkLookupResDto> response = controller.getTrademarkLookup(anyLong());

        assertEquals(ApiResponse.ok(expectedData), response);
    }

    private TrademarkLookupResDto createMockTrademarkLookupData() {
        TrademarkLookupResDto mockData = new TrademarkLookupResDto();
        mockData.setMarkTypes(new ArrayList<>());
        return mockData;
    }
}

