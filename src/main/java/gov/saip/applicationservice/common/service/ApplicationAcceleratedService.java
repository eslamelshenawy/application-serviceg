package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.ApplicationAcceleratedDto;
import gov.saip.applicationservice.common.dto.ApplicationAcceleratedLightDto;
import gov.saip.applicationservice.common.model.ApplicationAccelerated;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ApplicationAcceleratedService {
    Long addOrUpdateApplicationAccelerated(ApplicationAcceleratedDto applicationAccelerated);

    Long deleteApplicationAcceleratedFile(Long id, String fileKey);

    Optional<ApplicationAccelerated> getByApplicationInfo(ApplicationInfo applicationInfo);
    Boolean checkIfApplicationAccelrated( Long id);

    public ApplicationAcceleratedLightDto getByApplicationId(Long appId);

    public void updateApplicationAcceleratedStatus(Long id, Boolean refused);
    
    public void deleteByApplicationId(Long appId);

    Map<Long, ApplicationAcceleratedDto> getAcceleratedApplications(List<Long> appIds);
    Boolean isApplicationInfoHasAcceleratedApplication(Long appId);
    ApplicationAcceleratedDto getAcceleratedApplicationByApplicationId (Long appId);
    public void updateApplicationAcceleratedDecision(Long appId, String decision);

    Boolean checkIfApplicationAcceleratedHasDecisionTakenYet(Long appId);
    ApplicationAcceleratedDto findAcceleratedByApplicationId(Long appId);
}
