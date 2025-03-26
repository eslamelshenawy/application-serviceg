package gov.saip.applicationservice.common.controllers.installment;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentDto;
import gov.saip.applicationservice.common.dto.installment.InstallmentNotificationProjectionDto;
import gov.saip.applicationservice.common.dto.installment.InstallmentStatusBannerDto;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.mapper.installment.ApplicationInstallmentMapper;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = {"/kc/installment", "/internal-calling/installment"})
@RequiredArgsConstructor
public class ApplicationInstallmentController extends BaseController<ApplicationInstallment, ApplicationInstallmentDto, Long> {

    private final ApplicationInstallmentService applicationInstallmentService;
    private final ApplicationInstallmentMapper applicationInstallmentMapper;

    @Override
    protected BaseService<ApplicationInstallment, Long> getService() {
        return  applicationInstallmentService;
    }

    @Override
    protected BaseMapper<ApplicationInstallment, ApplicationInstallmentDto> getMapper() {
        return applicationInstallmentMapper;
    }
    @GetMapping("filter")
    public ApiResponse<PaginationDto<List<InstallmentNotificationProjectionDto>>> filterApplicationInstallments(
            @RequestParam(value = "notificationStatus", required = false) InstallmentNotificationStatus notificationStatus,
            @RequestParam(value = "installmentStatus", required = false) InstallmentStatus installmentStatus,
            @RequestParam(value = "appNum", required = false) String appNum,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ) {
        PaginationDto<List<InstallmentNotificationProjectionDto>> notificationsByStatus = applicationInstallmentService.filterApplicationInstallments(notificationStatus, installmentStatus, appNum, page, limit);
        return ApiResponse.ok(notificationsByStatus);
    }

    @GetMapping("/banner/{id}")
    public ApiResponse<InstallmentStatusBannerDto> get(@PathVariable("id") Long applicationId) {
        return ApiResponse.ok(applicationInstallmentService.getInstallmentConfigAndStatus(applicationId));
    }

    @PostMapping("/annual-fee/create/app/{id}")
    public ApiResponse<Long> createFirstAnnualFees(@PathVariable("id") Long id) {
        return ApiResponse.ok(applicationInstallmentService.createAndSaveFirstAnnualFees(id));
    }

    @PutMapping("/annual-fee/cancel-postponement/app/{id}")
    public ApiResponse<Void> cancelAnnualFeePostponement(@PathVariable("id") Long id) {
        applicationInstallmentService.cancelAnnualFeePostponement(id);
        return ApiResponse.noContent();
    }

    @DeleteMapping("/annual-fee/remove/app/{id}")
    public ApiResponse<Void> removeAnnualFeeInstallmentsAfterApplicationRejection(@PathVariable("id") Long id) {
        applicationInstallmentService.removeAnnualFeeInstallmentsAfterApplicationRejection(id);
        return ApiResponse.noContent();
    }


}
