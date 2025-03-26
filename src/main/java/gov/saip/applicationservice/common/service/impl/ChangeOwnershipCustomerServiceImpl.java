package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.ChangeOwnershipCustomer;
import gov.saip.applicationservice.common.repository.ChangeOwnershipCustomerRepository;
import gov.saip.applicationservice.common.service.ChangeOwnershipCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
@AllArgsConstructor
public class ChangeOwnershipCustomerServiceImpl extends BaseServiceImpl<ChangeOwnershipCustomer,Long> implements ChangeOwnershipCustomerService {

    private final ChangeOwnershipCustomerRepository changeOwnershipCustomerRepository;

    @Override
    protected BaseRepository<ChangeOwnershipCustomer, Long> getRepository() {
        return changeOwnershipCustomerRepository;
    }
}
