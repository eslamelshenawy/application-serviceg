package gov.saip.applicationservice.common.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LineChartDataDto {
    private String x;
    private int y;
}
