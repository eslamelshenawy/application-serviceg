package gov.saip.applicationservice.common.controllers.statistic;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.statistic.LineChartDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.service.statistic.StatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StatisticControllerTest {

    @Mock
    private StatisticService statisticService;

    @InjectMocks
    private StatisticController statisticController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetExternalStatisticPieChartList() {
        when(statisticService.getExternalStatisticPieChartList(ApplicationCategoryEnum.TRADEMARK, null, null)).thenReturn(null);
        ApiResponse<List<Object>> response = statisticController.getExternalStatisticPieChartList(ApplicationCategoryEnum.TRADEMARK, null, null);
        assertEquals(response.getSuccess(), true);
    }

    @Test
    public void testGetCustomerStatisticDetails() {
        // Arrange
        int page = 1;
        int pageSize = 10;
        when(statisticService.getCustomerStatisticDetails(ApplicationCategoryEnum.TRADEMARK, null, null, null, page, pageSize)).thenReturn(null);
        ApiResponse<PaginationDto<List<Object>>> response = statisticController.getCustomerStatisticDetails(ApplicationCategoryEnum.TRADEMARK, null, null, null, page, pageSize);
        assertEquals(response.getSuccess(), true);
    }

    @Test
    public void testGetCustomerMonthlyCountByCustomerCode() {
        // Arrange
        String customerCode = "12345";
        List<LineChartDto> lineChartList = Arrays.asList(
                new LineChartDto("2023-01",new ArrayList<>()),
                new LineChartDto("2023-01",new ArrayList<>())
        );
        when(statisticService.getCustomerMonthlyCountByCustomerCode(customerCode)).thenReturn(lineChartList);

        ApiResponse<List<LineChartDto>> response = statisticController.getCustomerMonthlyCountByCustomerCode(customerCode);

        assertEquals(ApiResponse.ok(lineChartList), response);
    }
}
