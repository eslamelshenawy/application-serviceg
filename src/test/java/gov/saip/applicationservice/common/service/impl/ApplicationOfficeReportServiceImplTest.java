package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.model.ApplicationOfficeReport;
import gov.saip.applicationservice.common.repository.ApplicationOfficeReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApplicationOfficeReportServiceImplTest {

    @Mock
    private ApplicationOfficeReportRepository applicationOfficeReportRepository;

    @InjectMocks
    private ApplicationOfficeReportServiceImpl applicationOfficeReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllByApplicationId() {
        Long applicationId = 1L;

        List<ApplicationOfficeReport> reports = new ArrayList<>();
        reports.add(new ApplicationOfficeReport());
        reports.add(new ApplicationOfficeReport());

        when(applicationOfficeReportRepository.findByApplicationInfoId(applicationId)).thenReturn(reports);

        List<ApplicationOfficeReport> actualReports = applicationOfficeReportService.getAllByApplicationId(applicationId);

        verify(applicationOfficeReportRepository).findByApplicationInfoId(applicationId);

        assertEquals(reports, actualReports);
    }
}
