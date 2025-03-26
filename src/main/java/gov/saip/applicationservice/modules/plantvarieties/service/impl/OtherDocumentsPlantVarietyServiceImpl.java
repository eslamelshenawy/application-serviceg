package gov.saip.applicationservice.modules.plantvarieties.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.OtherPlantVarietyDocumentsDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.OtherPlantVarietyDocumentsListDto;
import gov.saip.applicationservice.modules.plantvarieties.mapper.OtherPlantVarietyDocumentsMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.OtherPlantVarietyDocuments;
import gov.saip.applicationservice.modules.plantvarieties.repository.OtherDocumentPlantVarietyRepository;
import gov.saip.applicationservice.modules.plantvarieties.service.OtherDocumentPlantVarietyService;
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
public class OtherDocumentsPlantVarietyServiceImpl extends BaseServiceImpl<OtherPlantVarietyDocuments, Long> implements OtherDocumentPlantVarietyService {
    private final OtherDocumentPlantVarietyRepository otherDocumentPlantVarietyRepository;
    private final OtherPlantVarietyDocumentsMapper otherPlantVarietyDocumentsMapper;
    @Override
    protected BaseRepository<OtherPlantVarietyDocuments, Long> getRepository() {
        return otherDocumentPlantVarietyRepository;
    }

    @Override
    @Transactional
    public OtherPlantVarietyDocuments insert(OtherPlantVarietyDocuments entity) {
        return super.insert(entity);
    }

    @Override
    public Long softDeleteOtherDocumentById(Long id) {
        Optional<OtherPlantVarietyDocuments> optionalOtherPlantVarietyDocuments = getRepository().findById(id);
        if (optionalOtherPlantVarietyDocuments.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.DOCUMENT_ID_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        OtherPlantVarietyDocuments otherPlantVarietyDocuments = optionalOtherPlantVarietyDocuments.get();
        otherPlantVarietyDocuments.setIsDeleted(1);
        return getRepository().save(otherPlantVarietyDocuments).getId();
    }

    @Override
    public List<OtherPlantVarietyDocumentsListDto> findApplicationOtherPlantVarietyDocumentsByApplicationId(Long applicationId) {
        List<OtherPlantVarietyDocuments> otherPlantVarietyDocumentsList = otherDocumentPlantVarietyRepository.findAllByApplicationId(applicationId);
        return otherPlantVarietyDocumentsList.stream().map(otherPlantVarietyDocumentsMapper::mapToOtherPlantVarietyDocumentsListDto).collect(Collectors.toList());
    }
}
