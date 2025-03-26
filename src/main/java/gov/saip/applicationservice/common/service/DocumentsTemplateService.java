package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.DocumentsTemplateDto;
import gov.saip.applicationservice.common.model.DocumentsTemplate;

import java.util.List;

public interface DocumentsTemplateService extends BaseService<DocumentsTemplate, Long> {
    List<DocumentsTemplateDto> findDocumentsTemplates(String saipCode);

}
