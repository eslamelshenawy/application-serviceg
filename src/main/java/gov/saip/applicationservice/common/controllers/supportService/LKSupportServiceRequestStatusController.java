package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.mapper.lookup.LkSupportServiceRequestStatusMapper;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping({ "/internal-calling/support-service-status"})
@RequiredArgsConstructor
@Getter
public class LKSupportServiceRequestStatusController {

    private final LKSupportServiceRequestStatusService lkSupportServiceRequestStatusService;
    private final LkSupportServiceRequestStatusMapper lkSupportServiceRequestStatusMapper;

    @GetMapping("/by-code")
    ApiResponse<LkSupportServiceRequestStatusDto> findByCode(@RequestParam(name = "code") String code){
        LKSupportServiceRequestStatus serviceStatus = lkSupportServiceRequestStatusService.getStatusByCode(code);
        return ApiResponse.ok(lkSupportServiceRequestStatusMapper.map(serviceStatus));
    }
}
