package gov.saip.applicationservice.modules.plantvarieties.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerClient;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentListDto;
import gov.saip.applicationservice.modules.plantvarieties.mapper.DUSTestingDocumentMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.DUSTestingDocument;
import gov.saip.applicationservice.modules.plantvarieties.repository.DUSTestingDocumentPlantVarietyRepository;
import gov.saip.applicationservice.modules.plantvarieties.service.DUSTestingDocumentPlantVarietyService;
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
public class DUSTestingDocumentPlantVarietyServiceImpl extends BaseServiceImpl<DUSTestingDocument, Long> implements DUSTestingDocumentPlantVarietyService{
    private final DUSTestingDocumentPlantVarietyRepository dusTestingDocumentPlantVarietyRepository;
    private final DUSTestingDocumentMapper dusTestingDocumentMapper;
    private final CustomerClient customerClient;
    @Override
    protected BaseRepository<DUSTestingDocument, Long> getRepository() {
        return dusTestingDocumentPlantVarietyRepository;
    }

    @Override
    @Transactional
    public DUSTestingDocument insert(DUSTestingDocument entity) {
        return super.insert(entity);
    }

    @Override
    public Long softDeleteDusTestDocumentById(Long id) {
        Optional<DUSTestingDocument> optionalDUSTestingDocument = getRepository().findById(id);
        if (optionalDUSTestingDocument.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.DOCUMENT_ID_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        DUSTestingDocument dusTestDocument = optionalDUSTestingDocument.get();
        dusTestDocument.setIsDeleted(1);
        return getRepository().save(dusTestDocument).getId();
    }

    @Override
    public List<DUSTestingDocumentListDto> findApplicationDusTestDocumentsByApplicationId(Long applicationId) {
        List<DUSTestingDocument> dusTestDocumentList = dusTestingDocumentPlantVarietyRepository.findAllByApplicationId(applicationId);
        List<DUSTestingDocumentListDto> dusTestingDocumentListDtos = dusTestingDocumentMapper.mapToListOfDUSTestingDocumentListDto(dusTestDocumentList);
        dusTestingDocumentListDtos.stream().forEach(val -> {
            CountryDto countryDto = customerClient.getCountryById(val.getCountryId()).getPayload();
            val.setCountryDto(countryDto);
        });
        return dusTestingDocumentListDtos;
    }
}
