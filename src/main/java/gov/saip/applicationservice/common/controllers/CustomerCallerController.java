package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.CustomerLightDto;
import gov.saip.applicationservice.common.dto.CustomerCodeListDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.facade.CustomerFacade;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/customer", "/kc/customer"})
@RequiredArgsConstructor
@Slf4j
public class CustomerCallerController {

    private final CustomerServiceCaller customerServiceCaller;
    private final CustomerFacade customerFacade;

    @PostMapping(value = "/applications/by-code")
    public ApiResponse<List<CustomerSampleInfoDto>> getCustomerByListOfCode(@RequestBody CustomerCodeListDto customerCodeListDto) {
        return customerServiceCaller.getCustomerByListOfCode(customerCodeListDto);
    }

    @GetMapping("/{userId}/colleagues")
    ApiResponse<List<Long>> getUserColleagues(@PathVariable Long userId){
        return customerServiceCaller.getUserColleagues(userId);
    }
    
    @GetMapping("/application/{id}/group")
    ApiResponse<CustomerLightDto> getCustomerGroup(@PathVariable(name = "id") Long appId) {
        return ApiResponse.ok(customerFacade.getCustomerUserGroup(appId));
    }

    @GetMapping("/application/{id}/owner")
    ApiResponse<CustomerSampleInfoDto> getApplicationOwnerInformation(@PathVariable(name = "id") Long appId) {
        return ApiResponse.ok(customerFacade.getCustomerInfo(appId, ApplicationCustomerType.MAIN_OWNER));
    }

}
