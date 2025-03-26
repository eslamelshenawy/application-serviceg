package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.customer.AgentListDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CustomerServiceCallerImpl implements CustomerServiceCaller {


    private final CustomerServiceFeignClient customerServiceFeignClient;

    @Autowired
    public CustomerServiceCallerImpl(CustomerServiceFeignClient customerServiceFeignClient) {
        this.customerServiceFeignClient = customerServiceFeignClient;
    }

    @Override
    public ApiResponse<List<CustomerSampleInfoDto>> getCustomerByListOfCode(CustomerCodeListDto customerCodeListDto) {
        return customerServiceFeignClient.getCustomerByListOfCode(customerCodeListDto);
    }

    @Override
    public Map<String, CustomerSampleInfoDto> getCustomerMapByListOfCode(CustomerCodeListDto customerCodeListDto) {
        Map<String, CustomerSampleInfoDto> map = new LinkedHashMap<>();
        if (Objects.isNull(customerCodeListDto) || CollectionUtils.isEmpty(customerCodeListDto.getCustomerCode())) {
            return map;
        }
        List<CustomerSampleInfoDto> list = customerServiceFeignClient.getCustomerByListOfCode(customerCodeListDto).getPayload();
        if (CollectionUtils.isEmpty(list)) {
            return map;
        }
        for (CustomerSampleInfoDto customerSampleInfoDto : list) {
            map.put(customerSampleInfoDto.getCode().toLowerCase(), customerSampleInfoDto);
        }
        return map;
    }

    @Override
    public Map<Long, CountryDto> getCountriesMapByListOfCode(List<Long> countriesIds) {
        Map<Long, CountryDto> map = new LinkedHashMap<>();
        if (Objects.isNull(countriesIds) || CollectionUtils.isEmpty(countriesIds)) {
            return map;
        }
        List<CountryDto> list = customerServiceFeignClient.getCountriesByIds(countriesIds).getPayload();
        if (CollectionUtils.isEmpty(list)) {
            return map;
        }
        for (CountryDto countryDto : list) {
            map.put(countryDto.getId(), countryDto);
        }
        return map;
    }

    @Override
    public ApiResponse<List<Long>> getUserColleagues(Long userId) {
        return customerServiceFeignClient.getUserColleagues(userId);
    }

    @Override
    public Long getCustomerIdByUserId(Long userId) {
        return customerServiceFeignClient.getCustomerIdByUserId(userId).getPayload();
    }

    @Override
    public String getCustomerCodeByUserId(Long userId) {
        return customerServiceFeignClient.getCustomerCodeByUserId(userId).getPayload();
    }
    @Override
    public ApiResponse<List<CountryDto>> getCountriesByIds(List<Long> ids) {
        return customerServiceFeignClient.getCountriesByIds(ids);
    }


    @Override
    public CustomerSampleInfoDto getAnyCustomerDetails(Long id) {
        return customerServiceFeignClient.getAnyCustomerById(id).getPayload();
    }

    @Override
    public PaginationDto<List<AgentListDto>> getCurrentAgentsByCustomerCode(List<Long> agentsIds, String name, Integer page, Integer limit) {
        return customerServiceFeignClient.getCurrentAgentsByCustomerCode(agentsIds, name, page, limit).getPayload();
    }
    
    @Override
    public List<Long> getCustomersIds(String customerName) {
        if(customerName == null)
            return null;
        return customerServiceFeignClient.getCustomersIds(customerName).getPayload();
    }
    
    @Override
    public List<String> getCustomersCodes(String customerName) {
        if(customerName == null)
            return null;
        return customerServiceFeignClient.getCustomersCodes(customerName).getPayload();
    }

    @Override
    public String getUserGroupCodeByCustomerCode(String code) {
        return this.customerServiceFeignClient.getUserGroupCodeByCustomerCode(code).getPayload();
    }

    @Override
    public Map<Long, CustomerSampleInfoDto> getCustomersByIds(ListBodyDto<Long> ids) {
        return customerServiceFeignClient.getCustomersByIds(ids).getPayload();
    }

    @Override
    public Long getCustomerIdByCustomerCode(String customerCode) {
        return customerServiceFeignClient.getCustomerIdByCustomerCode(customerCode).getPayload();
    }

    @Override
    public String getCustomerCodeByCustomerId(Long customerId) {
        return customerServiceFeignClient.getCustomerCodeByCustomerId(customerId.toString()).getPayload();
    }

    @Override
    public CustomerSampleInfoDto getCustomerInfoByCustomerCode(String customerCode) {
        return customerServiceFeignClient.getCustomerInfoByCode(customerCode).getPayload();
    }

    @Override
    public CustomerSampleInfoDto getCustomerInfoFromRequest() {
        String customerId = Utilities.getCustomerIdFromHeaders();
        return customerServiceFeignClient.getAnyCustomerById(Long.valueOf(customerId)).getPayload();
    }
}
