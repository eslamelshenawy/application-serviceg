package gov.saip.applicationservice.common.dto.trademark;

import gov.saip.applicationservice.common.dto.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ApplicationTradeMarkListClassificationsDto {
    private  List<ListApplicationClassificationDto> classifications;

}