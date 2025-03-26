package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerConfigParameterClient;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.CustomerConfigParameterEnum;
import gov.saip.applicationservice.common.repository.PublicationIssueRepository;
import gov.saip.applicationservice.common.service.PublicationIssueNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublicationIssueNumberServiceImpl implements PublicationIssueNumberService {
    private final PublicationIssueRepository publicationIssueRepository;
    private final CustomerConfigParameterClient customerConfigParameterClient;

    @Override@Transactional(readOnly = true)
    public long calculateNextIssueNumber(ApplicationCategoryEnum applicationCategoryEnum) {
        return publicationIssueRepository
                .findMaxIssueNumberByApplicationCategorySaipCode(applicationCategoryEnum.name())
                .map(maxIssueNumber -> maxIssueNumber + 1)
                .orElseGet(this::getFirstIssueNumber);
    }

    @Override public long getFirstIssueNumber() {
        var payload = customerConfigParameterClient.getConfig(CustomerConfigParameterEnum.PUBLICATION_ISSUE_NUMBER_START_FROM.name()).getPayload();
        return Long.parseLong(payload.getValue());
    }

}
