package gov.saip.applicationservice.common.clients.rest.feigns;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.agency.LkAgentTypeDto;
import gov.saip.applicationservice.common.dto.customer.AgentListDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "customer-service", url = "${client.feign.customer}")

public interface CustomerServiceFeignClient {
    @PostMapping("/internal-calling/customers/list/by-code")
    ApiResponse<List<CustomerSampleInfoDto>> getCustomerByListOfCode(@RequestBody CustomerCodeListDto customerCodeListDto);

    @GetMapping("/internal-calling/users/{userId}/colleagues")
    ApiResponse<List<Long>> getUserColleagues(@PathVariable("userId") Long userId);

    @GetMapping("/internal-calling/users/{userId}/customer-id")
    ApiResponse<Long> getCustomerIdByUserId(@PathVariable("userId") Long userId);
 @GetMapping("/internal-calling/users/{userId}/customer-code")
    ApiResponse<String> getCustomerCodeByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/internal-calling/countries-by-ids")
    ApiResponse<List<CountryDto>> getCountriesByIds(@RequestParam("ids") List<Long> ids);

    @PostMapping(value = "/internal-calling/customers/list/{id}")
    ApiResponse<CustomerSampleInfoDto> getCustomerByListOfById(@PathVariable(name="id") Long id);

    @GetMapping(value = "/internal-calling/customer/{id}")
    ApiResponse<CustomerSampleInfoDto> getAnyCustomerById(@PathVariable(value = "id") Long id);

    @GetMapping(value = "/internal-calling/organization/customer/agents")
    ApiResponse<PaginationDto<List<AgentListDto>>> getCurrentAgentsByCustomerCode(
            @RequestParam(required = true, name = "applicationIds") List<Long> agentsIds,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit);
    
    @GetMapping(value = "/internal-calling/customer/{name}/ids")
    public ApiResponse<List<Long>> getCustomersIds(@PathVariable String name);
    
    @GetMapping(value = "/internal-calling/customer/{name}/codes")
    public ApiResponse<List<String>> getCustomersCodes(@PathVariable String name);

    @GetMapping(value = "/internal-calling/customer/customer-name/{customerName}")
     ApiResponse<CustomerSampleInfoDto> getCustomerByFullName(@PathVariable String  customerName);

    @GetMapping(value = "/internal-calling/organization/get-organization-size")
    public ApiResponse<String> getOrganizationDnsByCustomerCode(
            @RequestParam(value="customerCode") String customerCode);

    @GetMapping("/internal-calling/customer/user-group/{code}")
    ApiResponse<String> getUserGroupCodeByCustomerCode(@PathVariable String code);

    @PostMapping(value = "/internal-calling/customer/list/by-ids")
    public ApiResponse<Map<Long, CustomerSampleInfoDto>> getCustomersByIds(@RequestBody ListBodyDto<Long> ids );

    @GetMapping(value = "/internal-calling/customer/customer-id/{customerId}")
     ApiResponse<String> getCustomerCodeByCustomerId(@PathVariable(name="customerId") String customerId);

    @GetMapping("/internal-calling/agent-type/{id}")
    public ApiResponse<LkAgentTypeDto> getAgentTypeById(@PathVariable(name = "id") Integer id);

    @GetMapping(value = "/internal-calling/customer/code")
    public ApiResponse<CustomerSampleInfoDto> getAnyCustomerByCustomerCode(@RequestParam String customerCode);

    @PostMapping(value = "/internal-calling/customer/list/by-codes")
    ApiResponse<Map<String, CustomerSampleInfoDto>> getCustomersByCodes(@RequestBody ListBodyDto<String> codes);

    @PostMapping(value = "/internal-calling/customer/search-in-codes/{code}")
    ApiResponse<List<CustomerSearchResultDto>> searchInListOfCodesStartWith(@PathVariable String code, @RequestBody CustomerCodeListDto  customerCodeListDto);

    @GetMapping(value = "/internal-calling/customer/customer-code/{customerCode}")
    ApiResponse<Long> getCustomerIdByCustomerCode(@PathVariable(name="customerCode") String customerCode);


    @GetMapping(value = "/internal-calling/customer/validate/customer-code")
    ApiResponse<Object> validateCustomerCode(@RequestParam String customerCode);

    @GetMapping("/internal-calling/customer-name/{customerId}")
    public ApiResponse<String> findCustomerNameByCustomerId(@PathVariable(name="customerId") Long customerId);

   @GetMapping(value = "/internal-calling/customer/by-code")
   ApiResponse<CustomerSampleInfoDto> getCustomerInfoByCode(@RequestParam String customerCode);
}
