package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyDto;
import gov.saip.applicationservice.common.enums.CustomerExtClassifyEnum;
import gov.saip.applicationservice.common.mapper.CustomerExtClassifyMapper;
import gov.saip.applicationservice.common.model.CustomerExtClassify;
import gov.saip.applicationservice.common.repository.CustomerExtClassifyRepository;
import gov.saip.applicationservice.common.service.CustomerExtClassifyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerExtClassifyServiceImpl extends BaseServiceImpl<CustomerExtClassify, Long> implements CustomerExtClassifyService {

    private final CustomerExtClassifyRepository customerExtClassifyRepository;
    private final CustomerExtClassifyMapper customerExtClassifyMapper;
    @Override
    protected BaseRepository<CustomerExtClassify, Long> getRepository() {
        return customerExtClassifyRepository;
    }

    @Override
    public Long createCustomerExtClassify(CustomerExtClassifyDto customerExtClassifyDto) {
        List<CustomerExtClassify> customerExtClassifiesList = this.customerExtClassifyRepository.findByApplicationId(
                customerExtClassifyDto.getApplicationId(),
                customerExtClassifyDto.getCustomerExtClassifyType());
        if (customerExtClassifiesList != null && customerExtClassifiesList.size() > 0) {
            // update
            CustomerExtClassify customerExtClassifyEntity = customerExtClassifiesList.get(0);
            CustomerExtClassify customerExtClassify =  customerExtClassifyMapper.unMap(customerExtClassifyDto);
            customerExtClassify.setId(customerExtClassifyEntity.getId());
            customerExtClassify.setDuration(LocalDate.now().plusDays(customerExtClassifyDto.getDurationDays()));
            return this.update(customerExtClassify).getId();
        } else {
            // create
            CustomerExtClassify customerExtClassify = customerExtClassifyMapper.unMap(customerExtClassifyDto);
            customerExtClassify.setDuration(LocalDate.now().plusDays(customerExtClassifyDto.getDurationDays()));
            return this.insert(customerExtClassify).getId();
        }
    }

    @Override
    public CustomerExtClassifyDto findByApplicationId(Long applicationId, CustomerExtClassifyEnum type) {
        List<CustomerExtClassify> customerExtClassifies = this.customerExtClassifyRepository.findByApplicationId(applicationId, type);
        if (customerExtClassifies != null && customerExtClassifies.size() > 0)
            return customerExtClassifyMapper.map(customerExtClassifies.get(0));
        else
            return null;
    }


}
