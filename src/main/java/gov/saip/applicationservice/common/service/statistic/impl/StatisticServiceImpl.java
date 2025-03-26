package gov.saip.applicationservice.common.service.statistic.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.StatisticClient;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.statistic.LineChartDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.service.statistic.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticClient statisticClient;

    @Override
    public List<Object> getExternalStatisticPieChartList(ApplicationCategoryEnum categoryCode, String from, String to) {
            return statisticClient.getExternalStatisticPieChartList(categoryCode, from, to).getPayload();
    }

    @Override
    public PaginationDto<List<Object>> getCustomerStatisticDetails(ApplicationCategoryEnum categoryCode, String from, String to , List<String> types, int page, int pageSize) {
        return statisticClient.getCustomerStatisticDetails(categoryCode, from , to, types, page, pageSize).getPayload();
    }

    @Override
    public List<LineChartDto> getCustomerMonthlyCountByCustomerCode(String customerCode) {
        return statisticClient.getCustomerMonthlyCountByCustomerCode(customerCode).getPayload();
    }

}
