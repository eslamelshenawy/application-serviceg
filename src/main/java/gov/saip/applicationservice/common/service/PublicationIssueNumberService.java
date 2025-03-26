package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import org.springframework.transaction.annotation.Transactional;

public interface PublicationIssueNumberService {
    @Transactional(readOnly = true)
    long calculateNextIssueNumber(ApplicationCategoryEnum applicationCategoryEnum);

    long getFirstIssueNumber();
}
