package gov.saip.applicationservice.common.service.impl;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.ApplicationSearchResult;
import gov.saip.applicationservice.common.repository.ApplicationSearchResultRepository;
import gov.saip.applicationservice.common.service.ApplicationSearchResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationSearchResultServiceImpl extends BaseServiceImpl<ApplicationSearchResult, Long> implements ApplicationSearchResultService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationSearchResultServiceImpl.class);

    private final ApplicationSearchResultRepository applicationSearchResultRepository;
    @Override
    protected BaseRepository<ApplicationSearchResult, Long> getRepository() {
        return applicationSearchResultRepository;
    }

    @Override
    public List<ApplicationSearchResult> getAllSearchResultsByApplicationId(Long appId) {
        return applicationSearchResultRepository.findByApplicationInfoId(appId);
    }
}
