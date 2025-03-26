package gov.saip.applicationservice.common.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class InventorRequestsDto {

    private Long appInfoId;
    private Set<Long> fromApplicants;
    private Set<Long> inventorsToBeDeleted;
    private List<ApplicationRelevantRequestsDto> relvants;

}
