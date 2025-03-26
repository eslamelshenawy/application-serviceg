package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationListDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.util.BaseIntegrationTest;
import gov.saip.applicationservice.util.actions.ApplicationListingActions;
import gov.saip.applicationservice.util.objectmother.ApplicationInfoTestFactory;
import gov.saip.applicationservice.util.stubs.CustomerServiceStubs;
import gov.saip.applicationservice.util.stubs.EFilingBpmServiceStubs;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

class ApplicationControllerListApplicationByUserIdAndCategoryCodeTest extends BaseIntegrationTest implements ApplicationListingActions, CustomerServiceStubs, EFilingBpmServiceStubs {

    @Autowired
    private ApplicationInfoRepository applicationInfoRepository;

    @Autowired
    private ApplicationRelevantTypeRepository applicationRelevantTypeRepository;


    // The system under test (SUT)...
//    @Test
//    void Lists_applications_by_created_user_ids() throws Exception {
//        // TODO[Fawzy]
//        // Arrange
//        Long createdByUserId = 1L;
//        Long colleagueUserId = 2L;
//        String applicantMainCustomerCode = "NP-107";
//        LkApplicationStatus acceptance = testLkFactory.createApplicationStatus(ApplicationStatusEnum.ACCEPTANCE);
//        LkApplicationCategory trademark = testLkFactory.createApplicationCategory(ApplicationCategoryEnum.TRADEMARK);
//
//        ApplicationInfo applicationCreatedByUser = applicationInfoRepository.save(ApplicationInfoTestFactory
//                .anApplication(acceptance, trademark)
//                .createdByUserId(createdByUserId)
//                .build());
//        applicationRelevantTypeRepository.save(ApplicationInfoTestFactory.aMainApplicant(applicantMainCustomerCode, applicationCreatedByUser).build());
//        ApplicationInfo applicationCreatedByColleague = applicationInfoRepository.save(ApplicationInfoTestFactory
//                .anApplication(acceptance, trademark)
//                .createdByUserId(colleagueUserId)
//
//                .build());
//        applicationRelevantTypeRepository.save(ApplicationInfoTestFactory.aMainApplicant(applicantMainCustomerCode, applicationCreatedByColleague).build());
//
//        ApplicationInfo applicationThatShouldNotBeReturned = applicationInfoRepository.save(ApplicationInfoTestFactory
//                .anApplication(acceptance, trademark)
//                .createdByUserId(500L)
//                .build());
//
//        stubGetUserColleagues(createdByUserId, List.of(colleagueUserId));
//
//
//        stubGetCustomerCodeByUserId(createdByUserId, null);
//        stubGetCustomerIdByUserId(createdByUserId, null);
//        // List.of(applicationCreatedByUser.getId(), applicationCreatedByColleague.getId()),
//        stubGetTaskRowById(List.of());
//        stubGetCustomerByListOfCode(List.of(applicantMainCustomerCode, applicantMainCustomerCode));
//
//        // Act
//        ResultActions result = getApplicationListByApplicationCategoryAndUserId(
//                Map.of("userId", List.of(createdByUserId.toString()))
//        ).andExpect(MockMvcResultMatchers.status().isOk());
//
//        ApiResponse<PaginationDto<List<ApplicationListDto>>> response = deserialize(result, new TypeReference<>() {
//        });
//
//        List<ApplicationListDto> actualApplications = response.getPayload().getContent();
//
//        // Never trust a test you have not seen fail at least once
//        // Assert
//        Assertions.assertThat(actualApplications)
//                .map(ApplicationListDto::getId)
//                .contains(applicationCreatedByUser.getId(), applicationCreatedByColleague.getId())
//                .hasSize(2);
//    }
//
//    @Test
//    void Lists_applications_by_main_applicant_customer_code() {
//        // TODO[Sabry]
//    }
//
//    @Test
//    void Lists_applications_by_active_agent_customer_id() {
//        // TODO[Sabry]
//    }
//
//
//    @Test
//    void Lists_applications_by_user_id_and_application_category() {
//        // TODO[Sabry]
//    }
//    @Test
//    void Lists_applications_by_main_applicant_customer_code() {
//        // TODO[Sabry]
//    }
//
//    @Test
//    void Lists_applications_by_active_agent_customer_id() {
//        // TODO[Sabry]
//    }
//
//
//    @Test
//    void Lists_applications_by_user_id_and_application_category() {
//        // TODO[Sabry]
//    }
}