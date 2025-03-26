package gov.saip.applicationservice.modules.plantvarieties.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.PlantDetailsTestingDifferenceExcellenceDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceLightDto;
import gov.saip.applicationservice.modules.plantvarieties.mapper.ProveExcellenceMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.PlantVarietyDetails;
import gov.saip.applicationservice.modules.plantvarieties.model.ProveExcellence;
import gov.saip.applicationservice.modules.plantvarieties.repository.*;
import gov.saip.applicationservice.modules.plantvarieties.service.PlantVarietyService;
import gov.saip.applicationservice.modules.plantvarieties.service.ProveExcellenceService;
import gov.saip.applicationservice.modules.plantvarieties.validators.ProveExcellenceValidator;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProveExcellenceServiceImpl extends BaseServiceImpl<ProveExcellence, Long> implements ProveExcellenceService {

    private final ProveExcellenceRepository proveExcellenceRepository;
    private final ProveExcellenceValidator proveExcellenceValidator;
    private final ProveExcellenceMapper proveExcellenceMapper;

    private final LKPVPropertyRepository lkpvPropertyRepository;
    private final LKPVPropertyOptionsRepository lkpvPropertyOptionsRepository;
    private final LkVegetarianTypeRepository lkVegetarianTypeRepository;
    private final PlantVarietyService plantVarietyService;
    private final PlantVarietyRepository plantVarietyRepository;

    @Override
    protected BaseRepository<ProveExcellence, Long> getRepository() {
        return proveExcellenceRepository;
    }
    @Override
    public Long saveProveExcellence(ProveExcellenceDto dto) {
        Long plantVarietyId = plantVarietyService.getPlantVarietyId(dto.getApplication().getId());
        dto.setPlantVarietyDetailsId(plantVarietyId);
        ProveExcellence proveExcellence = proveExcellenceMapper.unMap(dto);
        proveExcellence.setPlantVarietyDetails(findByIdOrNull(plantVarietyRepository, dto.getPlantVarietyDetailsId())); // Set PlantVariety
        proveExcellence.setLkpvProperty(findByIdOrNull(lkpvPropertyRepository, dto.getLkpvPropertyId())); // Set LKPVProperty
        proveExcellence.setLkpvPropertyOptions(findByIdOrNull(lkpvPropertyOptionsRepository, dto.getLkpvPropertyOptionsId()));// Set LKPVPropertyOptions
        proveExcellence.setLkpvPropertyOptionsSecond(findByIdOrNull(lkpvPropertyOptionsRepository, dto.getLkpvPropertyOptionsSecondId()));// Set LKPVPropertyOptionsSecond
        proveExcellence.setLkVegetarianTypes(findByIdOrNull(lkVegetarianTypeRepository, dto.getLkVegetarianTypesId()));// Set LkVegetarianTypes
        return proveExcellenceRepository.save(proveExcellence).getId();
    }

    @Override
    public Long softDeleteProveExcellenceById(Long id) {
        Optional<ProveExcellence> optionalProveExcellence = getRepository().findById(id);
        if (optionalProveExcellence.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.DOCUMENT_ID_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        ProveExcellence proveExcellence = optionalProveExcellence.get();
        proveExcellence.setIsDeleted(1);
        return getRepository().save(proveExcellence).getId();
    }

    @Override
    @Transactional
    public Long updateProveExcellenceWithApplication(ProveExcellenceDto proveExcellenceDto) {
        Long plantVarietyId = plantVarietyService.getPlantVarietyId(proveExcellenceDto.getApplication().getId());
        proveExcellenceDto.setPlantVarietyDetailsId(plantVarietyId);
        proveExcellenceValidator.validate(proveExcellenceDto, null);
        ProveExcellence proveExcellence = getProveExcellence(proveExcellenceDto);
        proveExcellence = proveExcellenceMapper.unMap(proveExcellenceDto);
        proveExcellence.setPlantVarietyDetails(findByIdOrNull(plantVarietyRepository, proveExcellenceDto.getPlantVarietyDetailsId())); // Set PlantVariety
        proveExcellence.setLkpvProperty(findByIdOrNull(lkpvPropertyRepository, proveExcellenceDto.getLkpvPropertyId())); // Set LKPVProperty
        proveExcellence.setLkpvPropertyOptions(findByIdOrNull(lkpvPropertyOptionsRepository, proveExcellenceDto.getLkpvPropertyOptionsId()));// Set LKPVPropertyOptions
        proveExcellence.setLkpvPropertyOptionsSecond(findByIdOrNull(lkpvPropertyOptionsRepository, proveExcellenceDto.getLkpvPropertyOptionsSecondId()));// Set LKPVPropertyOptionsSecond
        proveExcellence.setLkVegetarianTypes(findByIdOrNull(lkVegetarianTypeRepository, proveExcellenceDto.getLkVegetarianTypesId()));// Set LkVegetarianTypes
        return proveExcellenceRepository.save(proveExcellence).getId();

    }

    private ProveExcellence getProveExcellence(ProveExcellenceDto proveExcellenceDto) {
        Optional<ProveExcellence> proveExcellence = Optional.empty();
        if (proveExcellenceDto.getId() != null){
            proveExcellence = proveExcellenceRepository.findById(proveExcellenceDto.getId());
        } else if (proveExcellenceDto.getPlantVarietyDetailsId() !=null) {
            proveExcellence =proveExcellenceRepository.getProveExcellenceByPlantVarietyDetailsId(proveExcellenceDto.getApplication().getId());
        }
        return proveExcellence.orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<ProveExcellenceLightDto> findApplicationProveExcellenceByApplicationId(Long appId) {
        return proveExcellenceRepository.findApplicationProveExcellenceByApplicationId(appId);
    }

    @Override
    public PaginationDto<List<ProveExcellenceLightDto>> listProveExcellenceApplication(Integer page, Integer limit,Long appId,Long lkVegetarianTypesId, Sort.Direction sortDirection) {
        Page<ProveExcellenceLightDto> proveExcellenceApplicationsApplication = getProveExcellenceApplicationsPage(page, limit,appId,lkVegetarianTypesId, sortDirection);

        if (proveExcellenceApplicationsApplication == null || proveExcellenceApplicationsApplication.isEmpty()) {
            return PaginationDto.<List<ProveExcellenceLightDto>>builder()
                    .totalElements(0L)
                    .content(List.of())
                    .totalPages(0)
                    .build();
        }

        return PaginationDto.<List<ProveExcellenceLightDto>>builder()
                .totalElements(proveExcellenceApplicationsApplication.getTotalElements())
                .content(proveExcellenceApplicationsApplication.getContent())
                .totalPages(proveExcellenceApplicationsApplication.getTotalPages())
                .build();
    }



    private Page<ProveExcellenceLightDto> getProveExcellenceApplicationsPage(Integer page, Integer limit, Long appId,Long lkVegetarianTypesId,Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "createdDate"));
        return proveExcellenceRepository.getProveExcellenceApplicationsFiltering( Utilities.getCustomerCodeFromHeaders(), Utilities.getCustomerIdFromHeadersAsLong(),appId,lkVegetarianTypesId, pageable);
    }


    @Override
    public PlantDetailsTestingDifferenceExcellenceDto findProveExcellenceByPlantDetailsId(Long appId,Long lkVegetarianTypesId) {
         Long plantDetailsId = plantVarietyService.getPlantVarietyId(appId);
         PlantVarietyDetails plantVarietyDetails = plantVarietyService.findPlantVarietyDetailsById(plantDetailsId);
         List<ProveExcellenceLightDto> proveExcellenceLightDtoList = proveExcellenceRepository.findProveExcellenceByPlantDetailsId(plantDetailsId,lkVegetarianTypesId);
         PlantDetailsTestingDifferenceExcellenceDto plantDetailsTestingDifferenceExcellenceDto = new PlantDetailsTestingDifferenceExcellenceDto();
         plantDetailsTestingDifferenceExcellenceDto.setPlantConditionalTesting(plantVarietyDetails.isPlantConditionalTesting());
         plantDetailsTestingDifferenceExcellenceDto.setAdditionalFeatureDifferentiateVariety(plantVarietyDetails.isAdditionalFeatureDifferentiateVariety());
         if (plantVarietyDetails.getAdditionalFeatureDifferentiateVarietyNote() != null && !plantVarietyDetails.getAdditionalFeatureDifferentiateVarietyNote().isEmpty()){
             plantDetailsTestingDifferenceExcellenceDto.setAdditionalFeatureDifferentiateVarietyNote(plantVarietyDetails.getAdditionalFeatureDifferentiateVarietyNote());
         }
         if (plantVarietyDetails.getPlantConditionalTestingNote() != null && !plantVarietyDetails.getPlantConditionalTestingNote().isEmpty()){
             plantDetailsTestingDifferenceExcellenceDto.setPlantConditionalTestingNote(plantVarietyDetails.getPlantConditionalTestingNote());
         }
         plantDetailsTestingDifferenceExcellenceDto.setProveExcellenceLightDto(proveExcellenceLightDtoList);
         return plantDetailsTestingDifferenceExcellenceDto;
    }

    private  <T> T findByIdOrNull(JpaRepository<T, Long> repository, Long id) {
        // Generic helper method to find entity by ID or return null
        return id == null ? null : repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }


}
