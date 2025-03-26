package gov.saip.applicationservice.common.clients.rest.callers;


import gov.saip.applicationservice.common.clients.rest.feigns.NotificationServiceFeignClient;
import gov.saip.applicationservice.common.dto.NotificationDto;
import gov.saip.applicationservice.common.dto.jms.NotificationMessageDestination;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.jms.JmsService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationCaller {

    private final JmsService jmsService;


    public void sendEmailAndSms(String emailTo, String smsTo, String emailSubject, String emailMsg, String smsMsg, Map<String, Object> params, String emailTempType) {
        sendEmail(emailTo, emailSubject, emailMsg, params, emailTempType);
        sendSms(smsTo, emailSubject, smsMsg, params);
    }

    public void sendSms(String to, String emailSubject, String message, Map<String, Object> params) {
        sendNotification(to, emailSubject, message, params, Constants.TemplateType.SMS, Constants.MessageType.SMS_TYPE_MESSAGE);
    }

    public void sendEmail(String to, String emailSubject, String message, Map<String, Object> params, String tempType) {
        sendNotification(to, emailSubject, message, params, Constants.MessageType.EMAIL_TYPE_MESSAGE, tempType);
    }

    protected void sendNotification(String to, String emailSubject, String message, Map<String, Object> params, String messageType, String tempType) {
        NotificationDto notificationDto = NotificationDto
                .builder()
                .appName("Patent Application")
                .templateType(tempType)
                .to(to)
                .emailSubject(emailSubject)
                .message(message)
                .messageType(messageType)
                .templateParams(params)
                .build();
        jmsService.sendNotification(NotificationMessageDestination.NOTIFICATION_DTO, notificationDto);
    }

    public void sendAllToSpecificUser(NotificationRequest dto) {
        dto.setAppName(Constants.AppName);
        jmsService.sendNotification(NotificationMessageDestination.NOTIFICATION_REQUEST, dto);
    }


    private final NotificationServiceFeignClient notificationServiceFeignClient;
    public void sendSynchronousNotifications(NotificationRequest dto) {
        dto.setAppName(Constants.AppName);
        notificationServiceFeignClient.sendSynchronousNotifications(dto);
    }
}
