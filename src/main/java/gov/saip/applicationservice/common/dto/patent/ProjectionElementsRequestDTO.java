package gov.saip.applicationservice.common.dto.patent;

import lombok.Data;

import java.util.List;

@Data
public class ProjectionElementsRequestDTO {
    private Long id;
    private String description;
    private Long applicationId;
    private Boolean isEnglish;
    private List<ProjectionElementsRequestDTO> dependents;
}
