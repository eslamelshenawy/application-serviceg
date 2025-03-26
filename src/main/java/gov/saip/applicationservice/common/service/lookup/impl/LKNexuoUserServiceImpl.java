package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.enums.ApplicationTypeEnum;
import gov.saip.applicationservice.common.model.LkNexuoUser;
import gov.saip.applicationservice.common.repository.lookup.LkNexuoUserRepository;
import gov.saip.applicationservice.common.service.lookup.LKNexuoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LKNexuoUserServiceImpl extends BaseServiceImpl<LkNexuoUser, Long> implements LKNexuoUserService {

    private final LkNexuoUserRepository lkNexuoUserRepository;
    @Override
    protected BaseRepository<LkNexuoUser, Long> getRepository() {
        return lkNexuoUserRepository;
    }
    @Override
    public LkNexuoUser getNexuoUserTypeByType(String type) {
        return lkNexuoUserRepository.findByType(ApplicationTypeEnum.valueOf(type));
    }
}
