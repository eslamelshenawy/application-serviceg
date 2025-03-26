package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationPublication;
import gov.saip.applicationservice.common.model.ApplicationPublicationSummaryProjection;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ApplicationPublicationService extends BaseService<ApplicationPublication, Long> {
    ApplicationPublication findByApplicationId(Long applicationId);
    
    LocalDateTime getPublicationDateById(Long id);
    
    void createApplicationPublication(Long applicationId,String publicationType);
    
    void createApplicationPublication(ApplicationInfo application, String appStatusCode, String publicationType, String documentType, Long serviceId);
    
    
    Optional<LocalDate> findApplicationPublicationDateByAppId(@Param("appId")Long appId);

    ApplicationPublicationSummaryProjection getPublicationSummary(Long applicationId, String publicationType);

    String findApplicationPublicationDateByAppIdAndPublicationType(Long appId, Long serviceId, String publicationCode);

    Optional<LocalDate> findApplicationPublicationDateByAppIdAndType(Long appId,String publicationType);





}
