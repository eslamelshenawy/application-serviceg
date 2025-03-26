package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyCommentsDto;
import gov.saip.applicationservice.common.model.CustomerExtClassifyComments;



public interface CustomerExtClassifyCommentsService extends BaseService<CustomerExtClassifyComments, Long> {
    Long createCustomerExtClassifyComment(CustomerExtClassifyCommentsDto customerExtClassifyCommentsDto);
    Long updateCustomerExtClassifyComment(CustomerExtClassifyCommentsDto customerExtClassifyCommentsDto);
    void deleteCustomerExtClassifyComment(Long commentId);
}
