package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.DocumentsTemplateDto;
import gov.saip.applicationservice.common.mapper.DocumentsTemplateMapper;
import gov.saip.applicationservice.common.model.DocumentsTemplate;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.repository.DocumentsTemplateRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationCategoryRepository;
import gov.saip.applicationservice.common.service.DocumentsTemplateService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentsTemplateServiceImpl extends BaseServiceImpl<DocumentsTemplate, Long> implements DocumentsTemplateService {

    private final LkApplicationCategoryRepository applicationCategoryRepository;

    private final DocumentsTemplateRepository documentsTemplateRepository;

    private final DocumentsTemplateMapper documentsTemplateMapper;

    public DocumentsTemplateServiceImpl(LkApplicationCategoryRepository applicationCategoryRepository, DocumentsTemplateRepository documentsTemplateRepository, DocumentsTemplateMapper documentsTemplateMapper) {
        this.applicationCategoryRepository = applicationCategoryRepository;
        this.documentsTemplateRepository = documentsTemplateRepository;
        this.documentsTemplateMapper = documentsTemplateMapper;
    }
    @Override
    protected BaseRepository<DocumentsTemplate, Long> getRepository() {
        return documentsTemplateRepository;
    }

    @Override
    public List<DocumentsTemplateDto> findDocumentsTemplates(String saipCode) {
        Optional<LkApplicationCategory> lkApplicationCategory = applicationCategoryRepository.findBySaipCode(saipCode);
        if (lkApplicationCategory.isPresent()) {
            List<DocumentsTemplate> documentsTemplate = documentsTemplateRepository.findByCategory(lkApplicationCategory.get());
            List<DocumentsTemplateDto> documentsTemplateDto = documentsTemplateMapper.map(documentsTemplate);
            return documentsTemplateDto;
        }
        return Collections.emptyList();
    }
}
