package gov.saip.applicationservice.common.dto.notifications;

import gov.saip.applicationservice.common.dto.NotificationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class NotificationRequest {
    private NotificationLanguage lang = NotificationLanguage.AR;
    private NotificationTemplateCode code;
    private String appName;
    private Map<String, Object> templateParams;


    private NotificationDto email;
    private NotificationDto sms;
    private AppNotificationDto app;

}
