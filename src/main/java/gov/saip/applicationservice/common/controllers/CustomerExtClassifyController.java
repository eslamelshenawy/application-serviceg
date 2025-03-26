package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyDto;
import gov.saip.applicationservice.common.enums.CustomerExtClassifyEnum;
import gov.saip.applicationservice.common.service.impl.CustomerExtClassifyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping({"/kc/v1/customer-ext-classify", "/internal-calling/v1/customer-ext-classify"})
public class CustomerExtClassifyController {

    private final CustomerExtClassifyServiceImpl customerExtClassifyService;


    @GetMapping("/findByApplicationId/{applicationId}/{type}")
    public ApiResponse<CustomerExtClassifyDto> findByApplicationId(@PathVariable Long applicationId, @PathVariable CustomerExtClassifyEnum type) {
        return ApiResponse.ok(customerExtClassifyService.findByApplicationId(applicationId, type));
    }

    @PostMapping()
    public ApiResponse<Long> createCustomerExtClassify(@RequestBody CustomerExtClassifyDto customerExtClassifyDto) {
        return ApiResponse.ok(customerExtClassifyService.createCustomerExtClassify(customerExtClassifyDto));
    }
}
