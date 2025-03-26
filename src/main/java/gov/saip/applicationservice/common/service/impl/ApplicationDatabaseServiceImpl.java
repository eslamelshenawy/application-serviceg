package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.ApplicationDatabase;
import gov.saip.applicationservice.common.repository.ApplicationDatabaseRepository;
import gov.saip.applicationservice.common.service.ApplicationDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationDatabaseServiceImpl extends BaseServiceImpl<ApplicationDatabase, Long> implements ApplicationDatabaseService {

    private final ApplicationDatabaseRepository applicationDatabaseRepository;
    @Override
    protected BaseRepository<ApplicationDatabase, Long> getRepository() {
        return applicationDatabaseRepository;
    }
    @Override
    public List<ApplicationDatabase> getAllByApplicationId(Long appId) {
        return applicationDatabaseRepository.findByApplicationInfoId(appId);
    }
}
