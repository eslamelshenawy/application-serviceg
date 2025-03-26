package gov.saip.applicationservice.common.dto.jms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JmsNotificationMessage implements Serializable {
    private NotificationMessageDestination destination;
    private String message;
}
