package gov.saip.applicationservice.common.service.impl;


import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.ApplicationDocumentComment;
import gov.saip.applicationservice.common.repository.ApplicationDocumentCommentRepository;
import gov.saip.applicationservice.common.service.ApplicationDocumentCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ApplicationDocumentCommentServiceImpl extends BaseServiceImpl<ApplicationDocumentComment, Long> implements ApplicationDocumentCommentService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationDocumentCommentServiceImpl.class);

    private final ApplicationDocumentCommentRepository applicationDocumentCommentRepository;
    @Override
    protected BaseRepository<ApplicationDocumentComment, Long> getRepository() {
        return applicationDocumentCommentRepository;
    }

    @Override
    public List<ApplicationDocumentComment> findByDocumentId(Long documentId) {
        return applicationDocumentCommentRepository.findByDocumentId(documentId);
    }
}
