package gov.saip.applicationservice.common.service.supportService.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityModifyRequestDetails;
import gov.saip.applicationservice.common.repository.supportService.ApplicationPriorityModifyRequestDetailsRepository;
import gov.saip.applicationservice.common.service.supportService.ApplicationPriorityModifyRequestDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationPriorityModifyRequestDetailsServiceImpl extends BaseServiceImpl<ApplicationPriorityModifyRequestDetails, Long>
        implements ApplicationPriorityModifyRequestDetailsService {

    private final ApplicationPriorityModifyRequestDetailsRepository applicationPriorityModifyRequestDetailsRepository;
    @Override
    protected BaseRepository<ApplicationPriorityModifyRequestDetails, Long> getRepository() {
        return applicationPriorityModifyRequestDetailsRepository;
    }
    @Override
    public int hardDeleteByApplicationPriorityModifyRequestId(Long applicationPriorityModifyRequestId){
        return applicationPriorityModifyRequestDetailsRepository.hardDeleteByApplicationPriorityModifyRequestId(applicationPriorityModifyRequestId);
    }
}