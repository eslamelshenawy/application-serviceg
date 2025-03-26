package gov.saip.applicationservice.common.controllers.installment;

import gov.saip.applicationservice.common.service.installment.InstallmentNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InstallmentNotificationControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private InstallmentNotificationService installmentNotificationService;

    @InjectMocks
    private InstallmentNotificationController notificationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    public void testResendNotification() throws Exception {

        mockMvc.perform(post("/kc/installment/notification/resend")
                        .param("id", "1")
                        .param("sendAll", "true"))
                .andExpect(status().isOk());
        verify(installmentNotificationService, times(1)).resendNotification(1L, true);

    }
}

