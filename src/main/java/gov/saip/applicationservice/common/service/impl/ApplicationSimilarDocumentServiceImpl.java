package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.ApplicationSimilarDocument;
import gov.saip.applicationservice.common.repository.ApplicationSimilarDocumentRepository;
import gov.saip.applicationservice.common.service.ApplicationSimilarDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationSimilarDocumentServiceImpl extends BaseServiceImpl<ApplicationSimilarDocument, Long> implements ApplicationSimilarDocumentService {

    private final ApplicationSimilarDocumentRepository applicationSimilarDocumentRepository;
    @Override
    protected BaseRepository<ApplicationSimilarDocument, Long> getRepository() {
        return applicationSimilarDocumentRepository;
    }
    @Override
    public List<ApplicationSimilarDocument> getAllByApplicationId(Long appId) {
        return applicationSimilarDocumentRepository.findByApplicationInfoId(appId);
    }
}
