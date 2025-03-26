package gov.saip.applicationservice.common.service.impl;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.ApplicationWord;
import gov.saip.applicationservice.common.repository.ApplicationWordRepository;
import gov.saip.applicationservice.common.service.ApplicationWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationWordServiceImpl extends BaseServiceImpl<ApplicationWord, Long> implements ApplicationWordService {

    private final ApplicationWordRepository applicationWordRepository;
    @Override
    protected BaseRepository<ApplicationWord, Long> getRepository() {
        return applicationWordRepository;
    }
    @Override
    public List<ApplicationWord> getAllAppWordsByApplicationId(Long appId) {
        return applicationWordRepository.findByApplicationInfoId(appId);
    }
}
