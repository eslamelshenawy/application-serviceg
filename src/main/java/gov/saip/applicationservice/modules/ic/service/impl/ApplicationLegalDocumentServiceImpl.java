package gov.saip.applicationservice.modules.ic.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.ic.dto.LegalDocumentListDto;
import gov.saip.applicationservice.modules.ic.mapper.ApplicationLegalDocumentMapper;
import gov.saip.applicationservice.modules.ic.model.ApplicationLegalDocument;
import gov.saip.applicationservice.modules.ic.repository.ApplicationLegalDocumentRepository;
import gov.saip.applicationservice.modules.ic.service.ApplicationLegalDocumentService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationLegalDocumentServiceImpl extends BaseServiceImpl<ApplicationLegalDocument, Long> implements ApplicationLegalDocumentService {

    private final ApplicationLegalDocumentRepository applicationLegalDocumentRepository;
    private final ApplicationLegalDocumentMapper applicationLegalDocumentMapper;

    @Override
    protected BaseRepository<ApplicationLegalDocument, Long> getRepository() {
        return applicationLegalDocumentRepository;
    }


    @Override
    @Transactional
    public ApplicationLegalDocument insert(ApplicationLegalDocument entity) {
        return super.insert(entity);
    }

    @Override
    public Long softDeleteLegalDocumentById(Long id) {
        Optional<ApplicationLegalDocument> optionalLegalDocument = getRepository().findById(id);
        if (optionalLegalDocument.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.DOCUMENT_ID_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        ApplicationLegalDocument legalDocument = optionalLegalDocument.get();
        legalDocument.setIsDeleted(1);
        return getRepository().save(legalDocument).getId();
    }

    @Override
    public List<LegalDocumentListDto> findApplicationLegalDocumentsByApplicationId(Long applicationId) {
        List<ApplicationLegalDocument> legalDocumentList = applicationLegalDocumentRepository.findAllByApplicationId(applicationId);
        return legalDocumentList.stream().map(applicationLegalDocumentMapper::mapToLegalDocumentListDto).collect(Collectors.toList());
    }
}
