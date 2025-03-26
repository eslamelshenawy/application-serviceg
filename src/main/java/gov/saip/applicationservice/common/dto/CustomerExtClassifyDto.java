package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.CustomerExtClassifyEnum;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CustomerExtClassifyDto {

    private Long id;
    private CustomerExtClassifyEnum customerExtClassifyType;
    private Long customerId;
    private Long applicationId;
    private Integer durationDays;
    private String notes;
    private List<CustomerExtClassifyCommentsDto> customerExtClassifyComments;

}
