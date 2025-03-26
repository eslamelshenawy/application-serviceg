package gov.saip.applicationservice.common.dto.requests;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProcessInstanceRequestDto {
    
    List<Long> processesRequestsIds = new ArrayList<>();
}
