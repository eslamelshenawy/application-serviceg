package gov.saip.applicationservice.common.controllers.installment;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.service.installment.InstallmentNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = {"/kc/installment/notification", "/internal-calling/installment/notification"})
@RequiredArgsConstructor
public class InstallmentNotificationController /*extends BaseController<InstallmentNotification, ApplicationInstallmentNotificationDto, Long>*/ {

    private final InstallmentNotificationService installmentNotificationService;

    @PostMapping("/resend")
    public ApiResponse<Long> resendNotification(@RequestParam(value = "id", required = false) Long id,
                                                @RequestParam(value = "sendAll", required = false) boolean sendAll) {
        installmentNotificationService.resendNotification(id, sendAll);
        return ApiResponse.ok(id);
    }


}
