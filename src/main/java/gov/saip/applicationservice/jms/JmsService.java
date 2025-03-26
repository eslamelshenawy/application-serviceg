package gov.saip.applicationservice.jms;

import gov.saip.applicationservice.common.dto.jms.JmsNotificationMessage;
import gov.saip.applicationservice.common.dto.jms.NotificationMessageDestination;
import gov.saip.applicationservice.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class JmsService {
    private final static Logger logger = LoggerFactory.getLogger(JmsService.class);
    private final JmsTemplate jmsTemplate;

    @Value("${event.queue.notification}")
    String notificationQueue;

    public void sendNotification(NotificationMessageDestination destination, Object dto) {
        logger.info("Sending message to queue {}", notificationQueue);
        logger.info("notification type {}", destination);
        String message = buildJmsNotificationMessage(destination, dto);
        sendMessage(notificationQueue, message);
    }


    private String buildJmsNotificationMessage(NotificationMessageDestination destination, Object dto) {
        String messageJson = JsonUtils.convertToJson(dto);
        JmsNotificationMessage message = new JmsNotificationMessage(destination, messageJson);
        return JsonUtils.convertToJson(message);
    }

    private void sendMessage(String queueName, String message) {
        logger.info("Sending message to queue {}", queueName);
        try {
            jmsTemplate.convertAndSend(queueName, message);
            logger.info("message sent to queue {}", queueName);
        } catch (Exception e) {
            logger.error("Something went wrong while sending message to queue {}", queueName);
            logger.error("error -> {}", e.getMessage());
        }

    }


}
