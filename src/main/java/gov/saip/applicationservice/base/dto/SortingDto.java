package gov.saip.applicationservice.base.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SortingDto {

    private String sortName;

    private String sortType;

    private String sortColumnType;

}
