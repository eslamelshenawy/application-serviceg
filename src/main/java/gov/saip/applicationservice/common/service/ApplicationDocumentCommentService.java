package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.ApplicationDocumentComment;
import gov.saip.applicationservice.common.model.ApplicationWord;

import java.util.List;

public interface ApplicationDocumentCommentService extends BaseService<ApplicationDocumentComment, Long> {

    List<ApplicationDocumentComment> findByDocumentId(Long documentId);
}
