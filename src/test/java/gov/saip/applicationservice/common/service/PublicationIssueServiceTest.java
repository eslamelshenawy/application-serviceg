//package gov.saip.applicationservice.common.service;
//
//import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
//import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
//import gov.saip.applicationservice.common.model.*;
//import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
//import gov.saip.applicationservice.common.repository.PublicationIssueApplicationRepository;
//import gov.saip.applicationservice.common.repository.PublicationIssueRepository;
//import gov.saip.applicationservice.util.BaseIntegrationTest;
//import gov.saip.applicationservice.util.TestUtils;
//import org.assertj.core.api.AutoCloseableSoftAssertions;
//import org.assertj.core.api.ListAssert;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import java.time.Clock;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//
//class PublicationIssueServiceTest extends BaseIntegrationTest {
//
//    @Autowired
//    PublicationIssueService publicationIssueService;
//
//    @Autowired
//    ApplicationInfoRepository applicationInfoRepository;
//
//    @Autowired
//    PublicationIssueRepository publicationIssueRepository;
//
//    @Autowired
//    PublicationIssueApplicationRepository publicationIssueApplicationRepository;
//
//    @Autowired
//    TransactionTemplate transactionTemplate;
//
//    @Test
//    @Disabled("We no longer use this method as applications are published one by one from Camunda")
//    void New_issue_created_when_no_issues_exist() {
//        // Arrange
//        List<ApplicationInfo> applicationsReadyForPublishing = createApplicationsReadyForPublishing(testLkFactory.createApplicationStatus(ApplicationStatusEnum.ACCEPTANCE));
//
//        LocalDateTime nextIssueDate = LocalDateTime.of(2023, 6, 20, 15, 0);
//        long nextIssueNumber = 100L;
//        int issueCutOffInDays = 2;
//
//        // Act
//        updateLatestIssueOrCreateNewIssue(nextIssueDate, nextIssueNumber, TestUtils.WEDNESDAY_2023_06_14_AT_01_00_CLOCK, issueCutOffInDays, ApplicationCategoryEnum.TRADEMARK);
//
//        // Assert
//        PublicationIssue actualNewIssue = publicationIssueRepository.findAll().stream().findAny().orElseThrow();
//        List<PublicationIssueApplicationPublication> publicationIssueApplications = publicationIssueApplicationRepository.findAll();
//        List<ApplicationInfo> publishedApplications = applicationInfoRepository.findAll();
//
//        try (var softly = new AutoCloseableSoftAssertions()) {
//            assertThatIssueWasSaved(softly, nextIssueDate, nextIssueNumber, actualNewIssue);
//            assertThatAllApplicationsWereAddedToIssue(softly, applicationsReadyForPublishing, publicationIssueApplications);
//            assertThatAllApplicationsReadyForPublishingWerePublished(softly, publishedApplications);
//        }
//    }
//
//    private void updateLatestIssueOrCreateNewIssue(LocalDateTime nextIssueDate, long nextIssueNumber, Clock clock, int issueCutOffInDays, ApplicationCategoryEnum applicationCategoryEnum) {
//        // Must do in transaction so that the applications that will be published are modified in same transaction as the creation of the issue
//        transactionTemplate.executeWithoutResult(ts -> {
//            publicationIssueService.updateLatestIssueOrCreateNewIssue(
//                    applicationInfoRepository.findAll(),
//                    applicationCategoryEnum,
//                    nextIssueDate,
//                    nextIssueNumber,
//                    issueCutOffInDays,
//                    clock
//            );
//        });
//    }
//
//    /**
//     * <p>These ApplicationInfo entities do not meet the criteria for ready for publishing, as this is not in the scope of this test.
//     * See {@link ApplicationInfoServiceTest} for the test that covers the conditions for being ready for publishing.</p>
//     */
//    private List<ApplicationInfo> createApplicationsReadyForPublishing(LkApplicationStatus applicationStatus) {
//        LkApplicationCategory trademark = testLkFactory.createApplicationCategory(ApplicationCategoryEnum.TRADEMARK);
//        return applicationInfoRepository.saveAll(
//                List.of(
//                        ApplicationInfo.builder()
//                                .category(trademark)
//                                .applicationStatus(applicationStatus)
//                                .build(),
//                        ApplicationInfo.builder()
//                                .category(trademark)
//                                .applicationStatus(applicationStatus)
//                                .build(),
//                        ApplicationInfo.builder()
//                                .category(trademark)
//                                .applicationStatus(applicationStatus)
//                                .build()
//                )
//        );
//    }
//
//    @Test
//    @Disabled("We no longer use this method as applications are published one by one from Camunda")
//    void New_issue_created_when_latest_issue_cut_off_has_passed() {
//        // Arrange
//        ApplicationCategoryEnum trademarkApplicationCategory = ApplicationCategoryEnum.TRADEMARK;
//        LocalDateTime afterCutOffPreviousIssueDate = LocalDateTime.of(2023, 6, 13, 15, 0);
//        PublicationIssue previousIssue = publicationIssueRepository.save(PublicationIssue.builder()
//                .issueNumber(99L)
//                .issuingDate(afterCutOffPreviousIssueDate)
//                .lkApplicationCategory(testLkFactory.createApplicationCategory(trademarkApplicationCategory))
//                .build());
//
//
//        List<ApplicationInfo> applicationsReadyForPublishing = createApplicationsReadyForPublishing(testLkFactory.createApplicationStatus(ApplicationStatusEnum.ACCEPTANCE));
//
//        LocalDateTime nextIssueDate = LocalDateTime.of(2023, 6, 20, 15, 0);
//        int issueCutOffInDays = 2;
//        long nextIssueNumber = 100L;
//
//        // Act
//        // Today is the 14th, issuingDate is 13th, so we are past the cutoff of 2 days
//        updateLatestIssueOrCreateNewIssue(nextIssueDate, nextIssueNumber, TestUtils.WEDNESDAY_2023_06_14_AT_01_00_CLOCK, issueCutOffInDays, trademarkApplicationCategory);
//
//        // Assert
//        PublicationIssue actualNewIssue =
//                publicationIssueRepository.findAll().stream().filter(issue -> !Objects.equals(issue.getId(), previousIssue.getId())).findAny().orElseThrow();
//        List<PublicationIssueApplicationPublication> publicationIssueApplications = publicationIssueApplicationRepository.findAll();
//        List<ApplicationInfo> publishedApplications = applicationInfoRepository.findAll();
//
//        try (var softly = new AutoCloseableSoftAssertions()) {
//            assertThatIssueWasSaved(softly, nextIssueDate, nextIssueNumber, actualNewIssue);
//            assertThatAllApplicationsWereAddedToIssue(softly, applicationsReadyForPublishing, publicationIssueApplications);
//            assertThatAllApplicationsReadyForPublishingWerePublished(softly, publishedApplications);
//        }
//    }
//
//    private static void assertThatAllApplicationsReadyForPublishingWerePublished(AutoCloseableSoftAssertions softly, List<ApplicationInfo> publishedApplications) {
//        softly.assertThat(publishedApplications)
//                .allMatch(application -> application.getApplicationStatus().getCode().equals(ApplicationStatusEnum.PUBLISHED_ELECTRONICALLY.name()), "ALl applications' status is published");
//    }
//
//    private static ListAssert<PublicationIssueApplicationPublication> assertThatAllApplicationsWereAddedToIssue(AutoCloseableSoftAssertions softly, List<ApplicationInfo> applicationsReadyForPublishing, List<PublicationIssueApplicationPublication> publicationIssueApplications) {
//        return softly.assertThat(publicationIssueApplications)
//                .hasSize(applicationsReadyForPublishing.size());
//    }
//
//    private static void assertThatIssueWasSaved(AutoCloseableSoftAssertions softly, LocalDateTime nextIssueDate, long nextIssueNumber, PublicationIssue actualNewIssue) {
//        softly.assertThat(actualNewIssue).isNotNull();
//        softly.assertThat(actualNewIssue.getIssueNumber()).isEqualTo(nextIssueNumber);
//        softly.assertThat(actualNewIssue.getIssuingDate()).isEqualTo(nextIssueDate);
//    }
//
//    @Test
//    @Disabled("We no longer use this method as applications are published one by one from Camunda")
//    void Latest_issue_updated_if_within_cut_off() {
//        // Arrange
//        long previousIssueNumber = 99L;
//        LocalDateTime withinCutOffPreviousIssueDate = LocalDateTime.of(2023, 6, 19, 15, 0);
//        ApplicationCategoryEnum trademarkApplicationCategory = ApplicationCategoryEnum.TRADEMARK;
//        PublicationIssue previousIssue = publicationIssueRepository.save(PublicationIssue.builder()
//                .issueNumber(previousIssueNumber)
//                .issuingDate(withinCutOffPreviousIssueDate)
//                .lkApplicationCategory(testLkFactory.createApplicationCategory(trademarkApplicationCategory))
//                .build());
//
//
//        List<ApplicationInfo> applicationsReadyForPublishing = createApplicationsReadyForPublishing(testLkFactory.createApplicationStatus(ApplicationStatusEnum.ACCEPTANCE));
//
//        LocalDateTime nextIssueDate = LocalDateTime.of(2023, 6, 20, 15, 0);
//        int issueCutOffInDays = 2;
//        long nextIssueNumber = 100L;
//
//        // Act
//        // Today is the 14th, issuingDate is 13th, so we are past the cutoff of 2 days
//        updateLatestIssueOrCreateNewIssue(nextIssueDate, nextIssueNumber, TestUtils.WEDNESDAY_2023_06_14_AT_01_00_CLOCK, issueCutOffInDays, trademarkApplicationCategory);
//
//        // Assert
//        List<PublicationIssue> allIssues = publicationIssueRepository.findAll();
//        PublicationIssue actualPreviousIssue = allIssues.stream().findAny().orElseThrow();
//        List<PublicationIssueApplicationPublication> publicationIssueApplications = publicationIssueApplicationRepository.findAll();
//        List<ApplicationInfo> publishedApplications = applicationInfoRepository.findAll();
//
//        try (var softly = new AutoCloseableSoftAssertions()) {
//            assertThatPreviousIssueWasUpdatedAndNoNewIssueCreated(softly, previousIssueNumber, withinCutOffPreviousIssueDate, allIssues, actualPreviousIssue);
//            assertThatAllApplicationsWereAddedToIssue(softly, applicationsReadyForPublishing, publicationIssueApplications);
//            assertThatAllApplicationsReadyForPublishingWerePublished(softly, publishedApplications);
//        }
//    }
//
//    private static void assertThatPreviousIssueWasUpdatedAndNoNewIssueCreated(AutoCloseableSoftAssertions softly, long previousIssueNumber, LocalDateTime previousIssueDate, List<PublicationIssue> allIssues, PublicationIssue actualPreviousIssue) {
//        softly.assertThat(allIssues).hasSize(1);
//        softly.assertThat(actualPreviousIssue.getIssueNumber()).isEqualTo(previousIssueNumber);
//        softly.assertThat(actualPreviousIssue.getIssuingDate()).isEqualTo(previousIssueDate);
//    }
//}