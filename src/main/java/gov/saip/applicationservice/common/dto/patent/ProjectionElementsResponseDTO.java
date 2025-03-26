package gov.saip.applicationservice.common.dto.patent;

import lombok.Data;

import java.util.List;
@Data
public class ProjectionElementsResponseDTO {
    private ProtectionElementsDTO parent;
    private List<ProtectionElementsDTO> dependents;
}
