package gov.saip.applicationservice.common.controllers.installment;

import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.installment.InstallmentNotificationProjectionDto;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApplicationInstallmentControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private ApplicationInstallmentService applicationInstallmentService;

    @InjectMocks
    private ApplicationInstallmentController installmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(installmentController).build();

    }

    @Test
    public void testFilterApplicationInstallments() throws Exception {
        PaginationDto<List<InstallmentNotificationProjectionDto>> mockResponse = new PaginationDto<>();
        mockResponse.setTotalPages(1);
        mockResponse.setTotalElements(10l);

        when(applicationInstallmentService.filterApplicationInstallments(
                InstallmentNotificationStatus.PENDING,
                InstallmentStatus.POSTPONED,
                "12345",
                0,
                10
        )).thenReturn(mockResponse);

        mockMvc.perform(get("/kc/installment/filter")
                        .param("notificationStatus", "PENDING")
                        .param("installmentStatus", "POSTPONED")
                        .param("appNum", "12345")
                        .param("page", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk());
    }
}
