package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.customer.AgentListDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface CustomerServiceCaller {


    ApiResponse<List<CustomerSampleInfoDto>> getCustomerByListOfCode(CustomerCodeListDto customerCodeListDto);

    Map<String, CustomerSampleInfoDto> getCustomerMapByListOfCode(CustomerCodeListDto customerCodeListDto);

    Map<Long, CountryDto> getCountriesMapByListOfCode(List<Long> countriesIds);

    ApiResponse<List<Long>> getUserColleagues(Long userId);
    Long getCustomerIdByUserId(Long userId);
    String getCustomerCodeByUserId(Long userId);

    ApiResponse<List<CountryDto>> getCountriesByIds(@RequestParam("ids") List<Long> ids);
    CustomerSampleInfoDto getAnyCustomerDetails(Long id);

    PaginationDto<List<AgentListDto>> getCurrentAgentsByCustomerCode(List<Long> agentsIds, String name, Integer page,  Integer limit);
    
    public List<Long> getCustomersIds(String customerName);
    
    public List<String> getCustomersCodes(String customerName);

    String getUserGroupCodeByCustomerCode(String code);
    Map<Long, CustomerSampleInfoDto> getCustomersByIds(ListBodyDto<Long> code);
    Long getCustomerIdByCustomerCode(String customerCode);
    String getCustomerCodeByCustomerId(Long customerId);

    CustomerSampleInfoDto getCustomerInfoByCustomerCode(String customerCode);

    CustomerSampleInfoDto getCustomerInfoFromRequest();
}
