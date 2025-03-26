package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.ApplicationEqmDto;
import gov.saip.applicationservice.common.dto.ApplicationEqmSearchRequestDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.service.ApplicationEqmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static gov.saip.applicationservice.util.Constants.EQM_OPPOSITION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ApplicationEqmServiceImplTest {

    @Mock
    private ApplicationInfoRepository applicationInfoRepository;

    @InjectMocks
    private ApplicationEqmServiceImpl applicationEqmService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        applicationEqmService = new ApplicationEqmServiceImpl(applicationInfoRepository);
    }

    @Test
    public void testListEqmApplicationsFor() {
        int page = 0;
        int limit = 10;
        String target = EQM_OPPOSITION;
        ApplicationEqmSearchRequestDto searchRequestDto = new ApplicationEqmSearchRequestDto();
        Page<ApplicationEqmDto> expectedPage = createSamplePage();

        when(applicationInfoRepository.listEqmApplicationsFor(any(), any(), any(), any(),
                any(), any(), any()))
                .thenReturn(expectedPage);

        PaginationDto<List<ApplicationEqmDto>> result = applicationEqmService.listEqmApplicationsFor(page, limit, target, searchRequestDto);

        assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
        assertEquals(expectedPage.getTotalPages(), result.getTotalPages());
        assertEquals(expectedPage.getContent(), result.getContent());
    }

    private Page<ApplicationEqmDto> createSamplePage() {
        List<ApplicationEqmDto> content = new ArrayList<>();
        content.add(new ApplicationEqmDto(1L, "APP-001", "Title 1", "Title 1 EN", createSampleCategory(), createSampleStatus()));
        content.add(new ApplicationEqmDto(2L, "APP-002", "Title 2", "Title 2 EN", createSampleCategory(), createSampleStatus()));
        return new PageImpl<>(content);
    }

    private LkApplicationCategory createSampleCategory() {
        LkApplicationCategory category = new LkApplicationCategory();
        category.setId(1L);
        category.setApplicationCategoryDescAr("Category 1 AR");
        category.setApplicationCategoryDescEn("Category 1 EN");
        category.setSaipCode("SAIP-001");
        return category;
    }

    private LkApplicationStatus createSampleStatus() {
        LkApplicationStatus status = new LkApplicationStatus();
        status.setId(1L);
        status.setIpsStatusDescAr("Status 1 AR");
        status.setIpsStatusDescEn("Status 1 EN");
        status.setCode("CODE-001");
        return status;
    }
}
