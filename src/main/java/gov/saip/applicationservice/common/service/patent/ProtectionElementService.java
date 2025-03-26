package gov.saip.applicationservice.common.service.patent;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ProtectionElementCounts;
import gov.saip.applicationservice.common.dto.patent.ProjectionElementsRequestDTO;
import gov.saip.applicationservice.common.dto.patent.ProjectionElementsResponseDTO;
import gov.saip.applicationservice.common.model.patent.ProtectionElements;

import java.util.List;

public interface ProtectionElementService extends BaseService<ProtectionElements, Long> {
    List<ProjectionElementsResponseDTO> getProtectionElements(Long applicationId, Boolean isEnglish);
    List<ProjectionElementsResponseDTO> getProtectionElementsByApplicationId(Long applicationId);

    void addProtectionElements(ProjectionElementsRequestDTO projectionElementsRequestDTO);
    void deleteProtectionElements(Long protectionElementId);
    Long getCountParentProtectionElementsByApplicationId( Long applicationId);
    Long getCountChildrenProtectionElementsByApplicationId( Long applicationId);
    ProtectionElementCounts getProtectionElementCountsByApplicationId(Long applicationId);
    List<String> getProtectionElementsDescByApplicationId (Long applicationId);




}
