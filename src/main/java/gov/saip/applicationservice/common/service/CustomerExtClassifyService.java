package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyDto;
import gov.saip.applicationservice.common.enums.CustomerExtClassifyEnum;
import gov.saip.applicationservice.common.model.CustomerExtClassify;

public interface CustomerExtClassifyService extends BaseService<CustomerExtClassify, Long> {
    Long createCustomerExtClassify (CustomerExtClassifyDto customerExtClassifyDto);

    CustomerExtClassifyDto findByApplicationId(Long applicationId , CustomerExtClassifyEnum type);

}
