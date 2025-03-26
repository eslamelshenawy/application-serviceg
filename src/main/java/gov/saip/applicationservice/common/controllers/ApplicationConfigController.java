package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.service.BPMCallerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/kc/application/config", "/internal-calling/application/config"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfigController {

    public final BPMCallerService bpmCallerService;

    @GetMapping(value = "")
    public ApiResponse<Long> getConfigValue(@RequestParam(value = "requestTypeCode") String code) {
        return ApiResponse.ok(bpmCallerService.getRequestTypeConfigValue(code));
    }
}

