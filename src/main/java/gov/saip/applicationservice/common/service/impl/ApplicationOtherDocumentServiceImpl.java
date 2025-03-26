package gov.saip.applicationservice.common.service.impl;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.ApplicationOtherDocument;
import gov.saip.applicationservice.common.repository.ApplicationOtherDocumentRepository;
import gov.saip.applicationservice.common.service.ApplicationOtherDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationOtherDocumentServiceImpl extends BaseServiceImpl<ApplicationOtherDocument, Long> implements ApplicationOtherDocumentService {

    private final ApplicationOtherDocumentRepository applicationOtherDocumentRepository;
    @Override
    protected BaseRepository<ApplicationOtherDocument, Long> getRepository() {
        return applicationOtherDocumentRepository;
    }
    @Override
    public List<ApplicationOtherDocument> getAllAppOtherDocumentsByApplicationId(Long appId) {
        return applicationOtherDocumentRepository.findByApplicationInfoId(appId);
    }
}
