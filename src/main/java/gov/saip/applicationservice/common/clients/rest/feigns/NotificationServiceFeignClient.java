package gov.saip.applicationservice.common.clients.rest.feigns;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.NotificationDto;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service-client", url = "${client.feign.notification}/internal-calling")
public interface NotificationServiceFeignClient {

    @PostMapping("/notification-request/send")
    ApiResponse<Void> sendSynchronousNotifications(@RequestBody NotificationRequest dto);
}
