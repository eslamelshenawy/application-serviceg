package gov.saip.applicationservice.common.service.statistic.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.StatisticClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.statistic.LineChartDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StatisticServiceImplTest {

    @Mock
    private StatisticClient statisticClient;

    private StatisticServiceImpl statisticService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        statisticService = new StatisticServiceImpl(statisticClient);
    }

    @Test
    public void testGetExternalStatisticPieChartList() {
        ApiResponse<List<Object>> apiResponse = ApiResponse.ok(List.of());

        when(statisticClient.getExternalStatisticPieChartList(ApplicationCategoryEnum.TRADEMARK, null, null)).thenReturn(apiResponse);

        List<Object> actualPieCharts = statisticService.getExternalStatisticPieChartList(ApplicationCategoryEnum.TRADEMARK, null, null);

        assertEquals(actualPieCharts.size(), apiResponse.getPayload().size());
    }

    @Test
    public void testGetCustomerStatisticDetails() {
        int page = 1;
        int pageSize = 10;
        PaginationDto<List<Object>> expectedDetails = new PaginationDto<>();
        ApiResponse apiResponse = ApiResponse.ok(expectedDetails);
        when(statisticClient.getCustomerStatisticDetails(ApplicationCategoryEnum.TRADEMARK, null, null, null, page, pageSize))
                .thenReturn(apiResponse);

        PaginationDto<List<Object>> actualDetails =
                statisticService.getCustomerStatisticDetails(ApplicationCategoryEnum.TRADEMARK, null, null, null, page, pageSize);

        assertEquals(expectedDetails, actualDetails);
    }

    @Test
    public void testGetCustomerMonthlyCountByCustomerCode() {
        String customerCode = "123";
        List<LineChartDto> expectedLineChart = new ArrayList<>();
        ApiResponse apiResponse = ApiResponse.ok(expectedLineChart);

        when(statisticClient.getCustomerMonthlyCountByCustomerCode(customerCode))
                .thenReturn(apiResponse);

        List<LineChartDto> actualLineChart = statisticService.getCustomerMonthlyCountByCustomerCode(customerCode);

        assertEquals(expectedLineChart, actualLineChart);
    }
}

