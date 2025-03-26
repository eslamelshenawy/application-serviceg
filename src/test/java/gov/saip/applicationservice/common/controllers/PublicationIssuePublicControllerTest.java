package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PublicationYearsDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.model.ApplicationCategoryToIssueCountProjection;
import gov.saip.applicationservice.common.model.PublicationIssueProjection;
import gov.saip.applicationservice.common.repository.PublicationIssueRepository;
import gov.saip.applicationservice.common.service.PublicationIssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class PublicationIssuePublicControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PublicationIssueService publicationIssueService;

    @Mock
    private PublicationIssueRepository publicationIssueRepository;

    @InjectMocks
    private PublicationIssuePublicController publicationIssuePublicController;

    @Mock
    Clock clock;

    @Mock
    Page<PublicationIssueProjection> publicationIssueProjections;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(publicationIssuePublicController).build();
    }

    @Test
    public void testCountPublishedIssuesByApplicationCategory() {
//        Clock fixedClock = Clock.fixed(Instant.parse("2023-09-11T10:00:00Z"), ZoneId.of("America/Los_Angeles"));
        List<ApplicationCategoryToIssueCountProjection> expectedResult = Collections.singletonList(
                new ApplicationCategoryToIssueCountProjection(
                        1L,
                        "SampleSaipCode",
                        "SampleDescEn",
                        "SampleDescAr",
                        42L
                )
        );
        when(publicationIssueRepository.countByApplicationCategoryWithIssuingDateBefore(any(), anyList())).thenReturn(expectedResult);
        when(publicationIssueService.countPublishedIssuesByApplicationCategory(clock)).thenReturn(expectedResult);

        List<ApplicationCategoryToIssueCountProjection> result = publicationIssuePublicController.countPublishedIssuesByApplicationCategory().getPayload();

        assertNotNull(result);
        assertEquals(expectedResult, result);

        verify(publicationIssueService).countPublishedIssuesByApplicationCategory(clock);
    }


    @Test
    public void testGetPublicationYears() {
        ApplicationCategoryEnum applicationCategory = ApplicationCategoryEnum.PATENT;

        List<Integer> gregorianYears = List.of(2021, 2022, 2023);
        List<Integer> hijriYears = List.of(1443, 1444, 1445);
        PublicationYearsDto publicationYearsDto = new PublicationYearsDto(gregorianYears, hijriYears);
        when(publicationIssueService.getPublicationYears(applicationCategory)).thenReturn(publicationYearsDto);

        // Act
        ApiResponse<PublicationYearsDto> response = publicationIssuePublicController.getPublicationYears(applicationCategory);

        // Assert
        assertNotNull(response);
        assertEquals(Optional.of(HttpStatus.OK.value()), Optional.of(response.getCode()));
        assertEquals(publicationYearsDto, response.getPayload());
        verify(publicationIssueService).getPublicationYears(applicationCategory);
    }


    private List<ApplicationCategoryToIssueCountProjection> createCountsByApplicationCategory() {
        return List.of(
                new ApplicationCategoryToIssueCountProjection(1L, "CategoryCode1", "Category 1 EN", "Category 1 AR", 10L),
                new ApplicationCategoryToIssueCountProjection(2L, "CategoryCode2", "Category 2 EN", "Category 2 AR", 5L)
        );
    }

    private Page<PublicationIssueProjection> createPublicationIssuePage() {
        List<PublicationIssueProjection> content = List.of(
                new PublicationIssueProjection(1L, 1L, LocalDate.of(2023, 9, 11), "1444-12-04", 10L),
                new PublicationIssueProjection(2L, 2L, LocalDate.of(2023, 9, 12), "1444-12-05", 5L)
        );
        return new PageImpl<>(content, PageRequest.of(0, 10, Sort.by(Sort.Order.asc("issueId"))), 2);
    }
}
