package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = {"/kc/support-service-status", "/internal-calling/support-service-status"})
@RequiredArgsConstructor
@Slf4j
public class SupportServiceRequestStatusController {

    private final LKSupportServiceRequestStatusService lkSupportServiceRequestStatusService;

    @GetMapping()
    public ApiResponse<List<LkSupportServiceRequestStatusDto>> getAllSupportServiceRequestStatus() {
        return ApiResponse.ok(lkSupportServiceRequestStatusService.findAllSupportServiceRequestStatus());
    }
}