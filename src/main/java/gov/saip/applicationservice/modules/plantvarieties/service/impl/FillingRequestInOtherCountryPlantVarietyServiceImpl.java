package gov.saip.applicationservice.modules.plantvarieties.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerClient;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.FillingRequestInOtherCountryDto;
import gov.saip.applicationservice.modules.plantvarieties.mapper.FillingRequestInOtherCountryMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.FillingRequestInOtherCountry;
import gov.saip.applicationservice.modules.plantvarieties.repository.FillingRequestInOtherCountryPlantVarietyRepository;
import gov.saip.applicationservice.modules.plantvarieties.service.FillingRequestInOtherCountryPlantVarietyService;
import gov.saip.applicationservice.modules.plantvarieties.service.PlantVarietyService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FillingRequestInOtherCountryPlantVarietyServiceImpl extends BaseServiceImpl<FillingRequestInOtherCountry, Long> implements FillingRequestInOtherCountryPlantVarietyService {
    private final FillingRequestInOtherCountryPlantVarietyRepository fillingRequestInOtherCountryPlantVarietyRepository;
    private final FillingRequestInOtherCountryMapper fillingRequestInOtherCountryMapper;
    private final CustomerClient customerClient;
    @Autowired
    @Lazy
    private  PlantVarietyService plantVarietyService;
    @Override
    protected BaseRepository<FillingRequestInOtherCountry, Long> getRepository() {
        return fillingRequestInOtherCountryPlantVarietyRepository;
    }

    @Override
    @Transactional
    public FillingRequestInOtherCountry insert(FillingRequestInOtherCountry entity) {
        return super.insert(entity);
    }

    @Override
    public Long softFillingRequestInOtherCountryById(Long id) {
        Optional<FillingRequestInOtherCountry> optionalFillingRequestInOtherCountry = getRepository().findById(id);
        if (optionalFillingRequestInOtherCountry.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.DOCUMENT_ID_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        FillingRequestInOtherCountry fillingRequestInOtherCountry = optionalFillingRequestInOtherCountry.get();
        fillingRequestInOtherCountry.setIsDeleted(1);
        return getRepository().save(fillingRequestInOtherCountry).getId();
    }
    @Override
    public List<FillingRequestInOtherCountryDto> findAllByPlantDetailsId(Long plantId){
        List<FillingRequestInOtherCountry> fillingRequestInOtherCountry=  fillingRequestInOtherCountryPlantVarietyRepository.findAllByPlantDetailsId(plantId);
        List<FillingRequestInOtherCountryDto> fillingRequestInOtherCountryDtos = fillingRequestInOtherCountryMapper.mapToListOfFillingRequestInOtherCountryListDto(fillingRequestInOtherCountry);
        fillingRequestInOtherCountryDtos.stream().forEach(val -> {
            CountryDto countryDto = customerClient.getCountryById(val.getCountryId()).getPayload();
            val.setCountryDto(countryDto);
        });
        return fillingRequestInOtherCountryDtos;
    }
}
