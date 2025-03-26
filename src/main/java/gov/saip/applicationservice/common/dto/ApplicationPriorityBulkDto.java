package gov.saip.applicationservice.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationPriorityBulkDto {

    List<Long> toBeDeleted;
    List<ApplicationPriorityDto> applicationPriorityDtoList;
    Boolean isPriorityConfirmed;
}
