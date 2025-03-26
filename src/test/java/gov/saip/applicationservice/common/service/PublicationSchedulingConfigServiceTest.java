//package gov.saip.applicationservice.common.service;
//
//import gov.saip.applicationservice.common.dto.PublicationSchedulingConfigCreateDto;
//import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
//import gov.saip.applicationservice.exception.BusinessException;
//import gov.saip.applicationservice.util.BaseIntegrationTest;
//import gov.saip.applicationservice.util.TestUtils;
//import gov.saip.applicationservice.util.actions.PublicationSchedulingConfigActions;
//import gov.saip.applicationservice.util.objectmother.PublicationSchedulingConfigTestFactory;
//import gov.saip.applicationservice.util.objectmother.PublicationTimeTestFactory;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.Set;
//
//class PublicationSchedulingConfigServiceTest extends BaseIntegrationTest implements PublicationSchedulingConfigActions {
//
//    @Autowired
//    PublicationSchedulingConfigService publicationSchedulingConfigService;
//
//    @Test
//    void Calculates_next_issue_date_based_on_config() throws Exception {
//        // Arrange
//        PublicationSchedulingConfigCreateDto publicationSchedulingConfigViewDto = PublicationSchedulingConfigTestFactory.aDefaultWeeklyConfigCreateDto(
//                ApplicationCategoryEnum.TRADEMARK,
//                Set.of(
//                        PublicationTimeTestFactory.aDefaultWeeklyPublicationTimeCreateDto(DayOfWeek.MONDAY)
//                                .build()
//                )
//        ).build();
//
//        putPublicationSchedulingConfig(publicationSchedulingConfigViewDto);
//
//        // Act
//        LocalDateTime actualNextIssueDate = publicationSchedulingConfigService.calculateNextIssueDate(
//                ApplicationCategoryEnum.TRADEMARK,
//                2,
//                TestUtils.WEDNESDAY_2023_06_14_AT_01_00_CLOCK);
//
//        // Assert
//        LocalDate MONDAY_2023_06_19 = LocalDate.of(2023, 6, 19);
//        LocalTime _15_00 = PublicationTimeTestFactory.TIME.toLocalTime();
//        LocalDateTime expectedNextIssueDate = LocalDateTime.of(
//                MONDAY_2023_06_19,
//                _15_00
//        );
//        Assertions.assertThat(actualNextIssueDate)
//                .isEqualTo(expectedNextIssueDate);
//    }
//
//    @Test
//    void Calculating_next_issue_date_fails_if_no_config_present() {
//        // Arrange
//        // Act, Assert
//        Assertions.assertThatExceptionOfType(BusinessException.class)
//                .isThrownBy(
//                        () -> publicationSchedulingConfigService.calculateNextIssueDate(
//                                ApplicationCategoryEnum.TRADEMARK,
//                                2,
//                                TestUtils.WEDNESDAY_2023_06_14_AT_01_00_CLOCK)
//                );
//    }
//}