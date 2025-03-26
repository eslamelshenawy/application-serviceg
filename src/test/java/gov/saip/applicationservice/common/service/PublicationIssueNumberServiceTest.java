//package gov.saip.applicationservice.common.service;
//
//import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
//import gov.saip.applicationservice.common.enums.CustomerConfigParameterEnum;
//import gov.saip.applicationservice.common.repository.PublicationIssueRepository;
//import gov.saip.applicationservice.util.BaseIntegrationTest;
//import gov.saip.applicationservice.util.objectmother.PublicationIssueTestFactory;
//import gov.saip.applicationservice.util.stubs.CustomerServiceStubs;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//class PublicationIssueNumberServiceTest extends BaseIntegrationTest implements CustomerServiceStubs {
//    @Autowired
//    PublicationIssueNumberService publicationIssueNumberService;
//
//    @Autowired
//    PublicationIssueRepository publicationIssueRepository;
//
//    @Test
//    void Gets_first_issue_number_from_customer_config_params_if_no_issues_exist() {
//        // Arrange
//        long expectedNextIssueNumber = 100L;
//        stubGetCustomerConfigParameter(CustomerConfigParameterEnum.PUBLICATION_ISSUE_NUMBER_START_FROM,
//                String.valueOf(expectedNextIssueNumber));
//
//        // Act
//        long actualNextIssueNumber = publicationIssueNumberService.calculateNextIssueNumber(ApplicationCategoryEnum.TRADEMARK);
//
//        // Assert
//        Assertions.assertThat(actualNextIssueNumber)
//                .isEqualTo(expectedNextIssueNumber);
//    }
//
//    @Test
//    void Increments_max_issue_number_by_one_if_issues_exist() {
//        // Arrange
//        long lowIssueNumber = 1L;
//        publicationIssueRepository.save(
//                PublicationIssueTestFactory
//                        .aDefaultPublicationIssue(testLkFactory.createApplicationCategory(ApplicationCategoryEnum.TRADEMARK))
//                        .issueNumber(lowIssueNumber)
//                        .build()
//        );
//
//        long maxIssueNumber = 500L;
//        publicationIssueRepository.save(
//                PublicationIssueTestFactory
//                        .aDefaultPublicationIssue(testLkFactory.createApplicationCategory(ApplicationCategoryEnum.TRADEMARK))
//                        .issueNumber(maxIssueNumber)
//                        .build()
//        );
//
//        // Act
//        long expectedNextIssueNumber = 501L;
//        long actualNextIssueNumber = publicationIssueNumberService.calculateNextIssueNumber(ApplicationCategoryEnum.TRADEMARK);
//
//        // Assert
//        Assertions.assertThat(actualNextIssueNumber)
//                .isEqualTo(expectedNextIssueNumber);
//    }
//
//}