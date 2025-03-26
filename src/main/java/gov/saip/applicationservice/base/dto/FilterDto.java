package gov.saip.applicationservice.base.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterDto {
    private String columnName;
    private String columnValue;
    private List<String> columnValues;
    private String operator;

}
