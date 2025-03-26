package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApplicationEqmDto;
import gov.saip.applicationservice.common.dto.ApplicationEqmSearchRequestDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.service.ApplicationEqmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ApplicationEqmControllerTest {

    @Mock
    private ApplicationEqmService applicationEqmService;

    @InjectMocks
    private ApplicationEqmController applicationEqmController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListEqmApplicationsFor() {
        int page = 1;
        int limit = 10;
        String target = "example_target";
        ApplicationEqmSearchRequestDto searchRequest = new ApplicationEqmSearchRequestDto();

        PaginationDto<List<ApplicationEqmDto>> paginationDto = new PaginationDto<>();


        when(applicationEqmService.listEqmApplicationsFor(page, limit, target, searchRequest)).thenReturn(paginationDto);

        PaginationDto<List<ApplicationEqmDto>> response = applicationEqmController.listEqmApplicationsFor(page, limit, target, searchRequest);

        assertEquals(paginationDto, response);
    }
}

