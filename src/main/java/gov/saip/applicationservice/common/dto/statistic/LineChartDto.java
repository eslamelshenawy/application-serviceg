package gov.saip.applicationservice.common.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class LineChartDto {
    private String id;
    private List<LineChartDataDto> data;
}
