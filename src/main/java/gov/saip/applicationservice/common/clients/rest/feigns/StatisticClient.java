package gov.saip.applicationservice.common.clients.rest.feigns;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.statistic.LineChartDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user-administration-service-statistic", url = "${client.feign.user.manage}/internal-calling/statistics")
public interface StatisticClient {

    @GetMapping("/customer-pie-card/{categoryCode}")
    public ApiResponse<List<Object>> getExternalStatisticPieChartList(@PathVariable(name = "categoryCode") ApplicationCategoryEnum categoryCode,
                                                                                 @RequestParam(name = "from", required = false) String from,
                                                                                 @RequestParam(name = "to", required = false) String to);

    @GetMapping("/customer-statistic-details/{categoryCode}")
    public ApiResponse<PaginationDto<List<Object>>> getCustomerStatisticDetails(
            @PathVariable(name = "categoryCode") ApplicationCategoryEnum categoryCode,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "typesFilter", required = false) List<String> types,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize);

    @GetMapping("/customer-line-chart")
    public ApiResponse<List<LineChartDto>> getCustomerMonthlyCountByCustomerCode(@RequestParam("customerCode") String customerCode);

}
