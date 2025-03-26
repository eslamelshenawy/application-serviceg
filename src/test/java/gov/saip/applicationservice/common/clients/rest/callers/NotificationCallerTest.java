package gov.saip.applicationservice.common.clients.rest.callers;

import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.NotificationServiceFeignClient;
import gov.saip.applicationservice.common.dto.NotificationDto;
import gov.saip.applicationservice.common.dto.jms.NotificationMessageDestination;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.jms.JmsService;
import gov.saip.applicationservice.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class NotificationCallerTest {

    @Mock
    private JmsService jmsService;
    @Mock
    private NotificationServiceFeignClient notificationServiceFeignClient;


    private NotificationCaller notificationCaller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationCaller = new NotificationCaller(jmsService, notificationServiceFeignClient);
    }

    @Test
    void sendEmailAndSmsShouldSendEmailAndSms() {
        // Arrange
        String emailTo = "test@example.com";
        String smsTo = "+123456789";
        String emailSubject = "Test Email";
        String emailMsg = "Hello, this is an email";
        String smsMsg = "Hello, this is an SMS";
        Map<String, Object> params = new HashMap<>();
        String emailTempType = "emailTemplate";

        notificationCaller.sendEmailAndSms(emailTo, smsTo, emailSubject, emailMsg, smsMsg, params, emailTempType);

        verify(jmsService, times(2)).sendNotification(
                eq(NotificationMessageDestination.NOTIFICATION_DTO),
                any(NotificationDto.class)
        );
    }

    @Test
    void sendSmsShouldSendSms() {
        // Arrange
        String smsTo = "+123456789";
        String emailSubject = "Test SMS";
        String smsMsg = "Hello, this is an SMS";
        Map<String, Object> params = new HashMap<>();

        // Act
        notificationCaller.sendSms(smsTo, emailSubject, smsMsg, params);

        // Assert
        verify(jmsService).sendNotification(eq(NotificationMessageDestination.NOTIFICATION_DTO), any(NotificationDto.class));
    }

    @Test
    void sendEmailShouldSendEmail() {
        // Arrange
        String emailTo = "test@example.com";
        String emailSubject = "Test Email";
        String emailMsg = "Hello, this is an email";
        Map<String, Object> params = new HashMap<>();
        String emailTempType = "emailTemplate";

        // Act
        notificationCaller.sendEmail(emailTo, emailSubject, emailMsg, params, emailTempType);

        // Assert
        verify(jmsService).sendNotification(eq(NotificationMessageDestination.NOTIFICATION_DTO), any(NotificationDto.class));
    }

    @Test
    void sendNotificationShouldSendNotification() {
        // Arrange
        String to = "test@example.com";
        String emailSubject = "Test Notification";
        String message = "This is a notification message";
        Map<String, Object> params = new HashMap<>();
        String messageType = Constants.MessageType.EMAIL_TYPE_MESSAGE;
        String tempType = "emailTemplate";

        // Act
        notificationCaller.sendNotification(to, emailSubject, message, params, messageType, tempType);

        // Assert
        verify(jmsService).sendNotification(eq(NotificationMessageDestination.NOTIFICATION_DTO), any(NotificationDto.class));
    }

    @Test
    void sendAllToSpecificUserShouldSendNotificationRequest() {
        // Arrange
        NotificationRequest dto = NotificationRequest.builder()
                .appName(Constants.AppName)
                .templateParams(new HashMap<>())
                .build();

        // Act
        notificationCaller.sendAllToSpecificUser(dto);

        // Assert
        verify(jmsService).sendNotification(eq(NotificationMessageDestination.NOTIFICATION_REQUEST), any(NotificationRequest.class));
    }
}
