package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.model.PublicationSchedulingConfig;
import gov.saip.applicationservice.common.repository.PublicationSchedulingConfigRepository;
import gov.saip.applicationservice.common.service.PublicationSchedulingConfigService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PublicationSchedulingConfigServiceImpl implements PublicationSchedulingConfigService {

    private final PublicationSchedulingConfigRepository publicationSchedulingConfigRepository;

    @Override@Transactional
    public void createOrReplace(PublicationSchedulingConfig publicationSchedulingConfig) {
        deleteOldConfigsForApplicationCategory(publicationSchedulingConfig.getApplicationCategory().getSaipCode());
        publicationSchedulingConfigRepository.save(publicationSchedulingConfig);
    }

    @Override public void deleteOldConfigsForApplicationCategory(String applicationCategorySaipCode) {
        publicationSchedulingConfigRepository.findByApplicationCategorySaipCode(applicationCategorySaipCode)
                .ifPresent(publicationSchedulingConfigRepository::delete);
    }

    @Override public PublicationSchedulingConfig findByApplicationCategorySaipCode(String applicationCategorySaipCode) {
        return publicationSchedulingConfigRepository.findByApplicationCategorySaipCode(applicationCategorySaipCode)
                .orElseThrow(() -> new BusinessException(Constants.ErrorKeys.PUBLICATION_SCHEDULING_CONFIG_NOT_FOUND));
    }

    @Override@Transactional(readOnly = true)
    public LocalDateTime calculateNextIssueDate(ApplicationCategoryEnum applicationCategoryEnum, int issueCutOffInDays, Clock clock) {
        return publicationSchedulingConfigRepository
                .findByApplicationCategorySaipCode(applicationCategoryEnum.name())
                .map(config -> config.calculateNextIssueDate(clock, issueCutOffInDays))
                .orElseThrow(() -> new BusinessException("Can't create issue for application category %s as it has no publication scheduling config".formatted(applicationCategoryEnum)));
    }
}
