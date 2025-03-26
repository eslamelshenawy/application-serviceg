package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.NotificationDto;
import gov.saip.applicationservice.common.dto.ReCreateBillDto;
import gov.saip.applicationservice.common.dto.notifications.AppNotificationDto;
import gov.saip.applicationservice.common.dto.notifications.NotificationLanguage;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Setter
@RequiredArgsConstructor
public class ReGenerateBillCallbackService {

    private final NotificationCaller notificationCaller;
    private final ApplicationCustomerService applicationCustomerService;
    private final ApplicationInfoService applicationServices;


    public Long reGenerateBillCallBack(ReCreateBillDto billDto) {
        log.info("bill call back for application {} with old {} and new {} invoices ", billDto.getApplicationId(), billDto.getOldBill(), billDto.getNewBill());
        ApplicationInfo applicationInfo = applicationServices.getById(billDto.getApplicationId()).get();
        CustomerSampleInfoDto applicationActiveCustomer = applicationCustomerService.getApplicationActiveCustomer(billDto.getApplicationId());

        Map<String, Object> params = new HashMap<>();
        params.put("oldBill", billDto.getOldBill());
        params.put("newBill", billDto.getNewBill());
        params.put("id", applicationInfo.getId());
        params.put("applicationId", applicationInfo.getId());

        NotificationRequest notificationRequest = buildNotificationRequest(applicationInfo, applicationActiveCustomer, params);
        notificationCaller.sendAllToSpecificUser(notificationRequest);

        return billDto.getApplicationId();
    }


    private static NotificationRequest buildNotificationRequest(ApplicationInfo applicationInfo,
                                                                CustomerSampleInfoDto customer,
                                                                Map<String, Object> vars) {
        NotificationDto emailDto = NotificationDto
                .builder()
                .to(customer.getEmail())
                .messageType(Constants.MessageType.EMAIL_TYPE_MESSAGE)
                .build();

        NotificationDto smsDto = NotificationDto
                .builder()
                .to(customer.getMobileCountryCode() + customer.getMobile())
                .build();


        AppNotificationDto appDto = AppNotificationDto.builder()
                .rowId(String.valueOf(applicationInfo.getId()))
                .routing(applicationInfo.getCategory().getSaipCode())
                .date(LocalDateTime.now())
                .userNames(List.of(customer.getCode())).build();

        return NotificationRequest.builder()
                .lang(NotificationLanguage.AR)
                .code(NotificationTemplateCode.RE_GENERATE_BILL_NUMBER)
                .templateParams(vars)
                .email(emailDto)
                .sms(smsDto)
                .app(appDto)
                .build();
    }

}
