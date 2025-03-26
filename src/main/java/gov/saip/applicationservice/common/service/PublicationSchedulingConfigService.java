package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.model.PublicationSchedulingConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

public interface PublicationSchedulingConfigService {
    @Transactional
    void createOrReplace(PublicationSchedulingConfig publicationSchedulingConfig);

    void deleteOldConfigsForApplicationCategory(String applicationCategorySaipCode);

    PublicationSchedulingConfig findByApplicationCategorySaipCode(String applicationCategorySaipCode);

    @Transactional(readOnly = true)
    LocalDateTime calculateNextIssueDate(ApplicationCategoryEnum applicationCategoryEnum, int issueCutOffInDays, Clock clock);
}
