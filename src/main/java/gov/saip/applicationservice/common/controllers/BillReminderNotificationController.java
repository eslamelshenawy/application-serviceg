package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.BillReminderNotificationDetailsDto;
import gov.saip.applicationservice.common.service.BillReminderNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value =  "/internal-calling/bill-reminder-details")
@RequiredArgsConstructor
@Slf4j
public class BillReminderNotificationController  {

    private final BillReminderNotificationService billReminderNotificationService;

    @GetMapping("/{type}/{id}")
    public ApiResponse<BillReminderNotificationDetailsDto> getBillReminderDetails(@PathVariable(name = "type") String type, @PathVariable(name = "id") Long id) {
        return ApiResponse.ok(billReminderNotificationService.getBillReminderDetails(id, type));
    }
}
