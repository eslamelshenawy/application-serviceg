package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kc/health")
public class HealthCheckController {

    @GetMapping
    public ApiResponse<String> getHealth() {
        return ApiResponse.ok("Up");
    }

}
