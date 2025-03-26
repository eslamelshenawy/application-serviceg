package gov.saip.applicationservice.common.controllers.statistic;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.statistic.LineChartDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/kc/v1/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;



    @GetMapping("/customer-pie-card/{categoryCode}")
    public ApiResponse<List<Object>> getExternalStatisticPieChartList(@PathVariable(name = "categoryCode") ApplicationCategoryEnum categoryCode,
                                                                                 @RequestParam(name = "from", required = false) String from,
                                                                                 @RequestParam(name = "to", required = false) String to) {
        return  ApiResponse.ok(statisticService.getExternalStatisticPieChartList(categoryCode, from , to));
    }


    @GetMapping("/customer-statistic-details/{categoryCode}")
    public ApiResponse<PaginationDto<List<Object>>> getCustomerStatisticDetails(
            @PathVariable(name = "categoryCode") ApplicationCategoryEnum categoryCode,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "typesFilter", required = false) List<String> types,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return  ApiResponse.ok(statisticService.getCustomerStatisticDetails(categoryCode, from, to, types, page, pageSize));
    }

    @GetMapping("/line-chart")
    public ApiResponse<List<LineChartDto>> getCustomerMonthlyCountByCustomerCode(@RequestParam("customerCode") String customerCode) {
        return  ApiResponse.ok(statisticService.getCustomerMonthlyCountByCustomerCode(customerCode));
    }

}
