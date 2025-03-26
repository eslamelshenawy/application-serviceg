package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyCommentsDto;
import gov.saip.applicationservice.common.mapper.CustomerExtClassifyCommentsMapper;
import gov.saip.applicationservice.common.model.CustomerExtClassifyComments;
import gov.saip.applicationservice.common.repository.CustomerExtClassifyCommentsRepository;
import gov.saip.applicationservice.common.service.CustomerExtClassifyCommentsService;
import gov.saip.applicationservice.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CustomerExtClassifyCommentsServiceImpl extends BaseServiceImpl<CustomerExtClassifyComments, Long>
        implements CustomerExtClassifyCommentsService {

    private final CustomerExtClassifyCommentsMapper customerExtClassifyCommentsMapper;
    private final CustomerExtClassifyCommentsRepository customerExtClassifyCommentsRepository;
    @Override
    protected BaseRepository<CustomerExtClassifyComments, Long> getRepository() {
        return customerExtClassifyCommentsRepository;
    }
    @Override
    public Long createCustomerExtClassifyComment(
            CustomerExtClassifyCommentsDto customerExtClassifyCommentsDto
    ) {
        return this.insert(customerExtClassifyCommentsMapper.unMap(prepareCurrentCommentDto(customerExtClassifyCommentsDto))).getId();
    }

    @Override
    public Long updateCustomerExtClassifyComment(
            CustomerExtClassifyCommentsDto customerExtClassifyCommentsDto
    ) {
        return this.update(customerExtClassifyCommentsMapper.unMap(prepareCurrentCommentDto(customerExtClassifyCommentsDto))).getId();
    }

    @Override
    public void deleteCustomerExtClassifyComment(Long commentId) {
        this.deleteById(commentId);
    }

    protected CustomerExtClassifyCommentsDto prepareCurrentCommentDto( CustomerExtClassifyCommentsDto customerExtClassifyCommentsDto){
        customerExtClassifyCommentsDto.setCommenterName(new Util().getFromBasicUserinfo("userName").toString());
        customerExtClassifyCommentsDto.setCommentDate(LocalDateTime.now());
        return customerExtClassifyCommentsDto;
    }

}
