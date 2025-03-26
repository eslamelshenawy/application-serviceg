package gov.saip.applicationservice.common.service.statistic;

import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.statistic.LineChartDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;

import java.util.List;

public interface StatisticService {
    List<Object> getExternalStatisticPieChartList(ApplicationCategoryEnum categoryCode, String from, String to);
    PaginationDto<List<Object>> getCustomerStatisticDetails(ApplicationCategoryEnum categoryCode, String from, String to, List<String> types, int page, int pageSize);

    List<LineChartDto> getCustomerMonthlyCountByCustomerCode(String customerCode);
}
