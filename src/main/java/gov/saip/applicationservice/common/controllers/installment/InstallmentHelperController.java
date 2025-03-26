package gov.saip.applicationservice.common.controllers.installment;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentDatesDto;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * used to update dates to test
 */
@RestController
@RequestMapping( value = {"/pb/installment"})
@RequiredArgsConstructor
public class InstallmentHelperController {

    private final ApplicationInstallmentService applicationInstallmentService;

    @GetMapping("/application/{id}/dates")
    public ApiResponse<ApplicationInstallmentDatesDto> getLastInstallmentInfoByAppId(@PathVariable("id") Long applicationId) {
        return ApiResponse.ok(applicationInstallmentService.getLastInstallmentInfoByAppId(applicationId));
    }

    @PutMapping("/application/{id}/resume-callback")
    public ApiResponse<String> resumeHoledCallBack(@PathVariable("id") Long applicationId) {
        return ApiResponse.ok(applicationInstallmentService.resumeHoledCallBack(applicationId));
    }

    @PutMapping("/application/update/dates")
    public ApiResponse<String> updateLastInstallmentDates(@RequestBody ApplicationInstallmentDatesDto dto) {
        return ApiResponse.ok(applicationInstallmentService.updateInstallmentDates(dto));
    }

    @PutMapping("/ignore-payment/{ignore}")
    public ApiResponse<Boolean> updateIgnoreAutomaticPaymentCallback(@PathVariable("ignore") boolean ignore) {
        applicationInstallmentService.ignoreAutomaticPaymentCallback(ignore);
        return ApiResponse.ok(ignore);
    }

    @PutMapping("/ignore-annual-months/{ignore}")
    public ApiResponse<Boolean> ignoreAnnualMonths(@PathVariable("ignore") boolean ignore) {
        applicationInstallmentService.ignoreAnnualMonths(ignore);
        return ApiResponse.ok(ignore);
    }

    
}
