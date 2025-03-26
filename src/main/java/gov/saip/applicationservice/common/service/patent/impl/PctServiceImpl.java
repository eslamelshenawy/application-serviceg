package gov.saip.applicationservice.common.service.patent.impl;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.PctIValidateDto;
import gov.saip.applicationservice.common.dto.patent.PatentFillingDateDto;
import gov.saip.applicationservice.common.dto.patent.PctDto;
import gov.saip.applicationservice.common.dto.patent.PctRequestDto;
import gov.saip.applicationservice.common.mapper.patent.PctMapper;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.patent.PatentDetails;
import gov.saip.applicationservice.common.model.patent.Pct;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.repository.patent.PatentDetailsRepository;
import gov.saip.applicationservice.common.repository.patent.PctRepository;
import gov.saip.applicationservice.common.service.PetitionRequestNationalStageService;
import gov.saip.applicationservice.common.service.patent.PctService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

/**
 * The {@code PctServiceImpl} class provides an implementation of the {@link PctService} interface
 * that manages international patent applications (PCT).
 */
@Service
@RequiredArgsConstructor
public class PctServiceImpl extends BaseServiceImpl<Pct, Long> implements PctService {

    private static final Logger logger = LoggerFactory.getLogger(PctServiceImpl.class);

    private final PctRepository pctRepository;

    private final PctMapper pctMapper;

    private final PatentDetailsRepository patentDetailsRepository;
    private final DocumentRepository documentRepository;
    @Override
    protected BaseRepository<Pct, Long> getRepository() {
        return pctRepository;
    }
    @Autowired
    @Lazy
    private PetitionRequestNationalStageService petitionRequestNationalStageService;

    /**
     * Creates a new PCT with the specified details, or updates an existing PCT if an ID is provided.
     *
     * @param dto the PCT request DTO
     * @return the saved/updated PCT entity
     * @throws BusinessException if the PCT or     * patent details are not found, or if the patent ID is duplicated
     */
    @Override
    public Pct createPct(PctRequestDto dto) {
        try {
            Long pctId = dto.getId();
            Pct pct;
            boolean newPct = true;
            if (pctId != null) {
                newPct = false;
                Optional<Pct> optionalPct = pctRepository.findById(pctId);
                if (optionalPct.isPresent()) {
                    pct = optionalPct.get();
                    pctMapper.updatePctFromRequestDto(dto, pct);
                } else {
                    throw new BusinessException(Constants.ErrorKeys.PCT_NOT_FOUND, HttpStatus.NOT_FOUND, null);
                }
            } else {
                pct = pctMapper.mapRequestToEntity(dto);

            }

            Long patentDetailsId = null;
            Optional<PatentDetails> patentDetails1 = patentDetailsRepository.findByApplicationId(dto.getApplicationId());
            if (patentDetails1.isPresent()) {
                patentDetailsId = patentDetails1.get().getId();
            }
            if (patentDetailsId != null) {
                if (pctRepository.findByPatentDetailsId(patentDetailsId) != null && newPct) {
                    throw new BusinessException(Constants.ErrorKeys.PATENT_ID_DUPLICATED, HttpStatus.NOT_FOUND, null);
                }
                Optional<PatentDetails> optionalPatentDetails = patentDetailsRepository.findById(patentDetailsId);
                if (optionalPatentDetails.isPresent()) {
                    pct.setPatentDetails(optionalPatentDetails.get());
                } else {
                    throw new BusinessException(Constants.ErrorKeys.PATENT_DETAILS_NOT_FOUND, HttpStatus.NOT_FOUND, null);
                }
            } else {
                PatentDetails patentDetails = new PatentDetails();
                patentDetails.setApplicationId(dto.getApplicationId());
                patentDetails = patentDetailsRepository.save(patentDetails);

                pct.setPatentDetails(patentDetails);

            }

            pct.setIsDeleted(dto.isActive() ? 0 : 1);
            pct.setActive(dto.isActive());
            pct = pctRepository.save(pct);
            // update pct document
            Document haveAppId= documentRepository.findById(pct.getPctCopyDocument().getId()).get();
            if(haveAppId.getApplicationInfo() == null)
            documentRepository.linkApplicationToDocument(pct.getPctCopyDocument().getId(),dto.getApplicationId());
            return pct;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    /**
     * Retrieves a PCT with the specified ID and maps it to a {@link PctDto} object.
     *
     * @param id the PCT ID
     * @return the PCT entity mapped to a PCT DTO
     * @throws BusinessException if the PCT is not found
     */
    @Override
    public PctDto getPctById(Long id) {
        try {
            Pct pct = findById(id);
            PctDto pctDto = pctMapper.map(pct);
            pctDto.setActive(pct.getIsDeleted() == 1 ? false : true);
            pctDto.setPatentId(pct.getPatentDetails().getId());
            return pctDto;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    /**
     * Retrieves a PCT with the specified patent application ID.
     *
     * @param applicationId the patent application ID
     * @return the PCT entity
     * @throws BusinessException if the patent details or PCT are not found
     */
    @Override
    public Pct findByApplicationId(Long applicationId) {
        Optional<PatentDetails> patentDetails = patentDetailsRepository.findByApplicationId(applicationId);
        if (!patentDetails.isPresent()) {
            return null;
        }
        Pct pct = pctRepository.findByPatentDetailsId(patentDetails.get().getId());
        if (Objects.isNull(pct)) {
            return null;
        }
        pct.setActive(pct.getIsDeleted() == 1 ? false : true);
        return pct;

    }

    @Override
    public Optional<Pct> findPctByApplicationId(Long applicationId) {
        return pctRepository.findPctByApplicationId(applicationId);
    }

    @Override
    public LocalDateTime getFillingDateOrInternationalDate(Long applicationId) {

        Optional<PatentFillingDateDto> appFilingDateAndPublicationDate =
                pctRepository.findApplicationFilinggDateAndPctPublicationDate(applicationId);
        if(appFilingDateAndPublicationDate.isPresent()){
            if(Objects.nonNull(appFilingDateAndPublicationDate.get().getInternationalPublicationDate()))
            {
            return  LocalDateTime.of(
                    appFilingDateAndPublicationDate.get().getInternationalPublicationDate(),
                    LocalTime.now());
            }
            else if(Objects.nonNull(appFilingDateAndPublicationDate.get().getApplicationFilingDate())){

                return appFilingDateAndPublicationDate.get().getApplicationFilingDate();
            }
        }
        return LocalDateTime.now();
    }
    @Override
    public Boolean validatePetitionNumber(String petitionNumber){
        if(petitionRequestNationalStageService.checkPetitionRequestNumberToUseInPct(petitionNumber)){
            if(pctRepository.validatePetitionNumber(petitionNumber))
                throw new BusinessException(Constants.ErrorKeys.PITITION_NUMBER_EXISTING, HttpStatus.NOT_FOUND, null);
        }else{
                throw new BusinessException(Constants.ErrorKeys.PITITION_NUMBER_NOT_EXISTING, HttpStatus.NOT_FOUND, null);
        }
            return true ;
    }

    @Override
    public Boolean validatePCT(Long applicationId){
        return pctRepository.validatePCT(applicationId);
    }
    public PctIValidateDto validatePetitionNumberAndGetPctNumber(String petitionNumber){

        PctIValidateDto petitionNumberPctInfos = petitionRequestNationalStageService.getPctForPetitionRequestNumber(petitionNumber);
        if(Objects.nonNull(petitionNumberPctInfos)){
            if(petitionNumberPctInfos.getValid()) {
                if (pctRepository.validatePetitionNumber(petitionNumber))
                    throw new BusinessException(Constants.ErrorKeys.PITITION_NUMBER_EXISTING, HttpStatus.NOT_FOUND, null);
            }
        }else{
            throw new BusinessException(Constants.ErrorKeys.PITITION_NUMBER_NOT_EXISTING, HttpStatus.NOT_FOUND, null);
        }
        return petitionNumberPctInfos;
    }

    @Override
    public PctDto findDTOByApplicationId(Long applicationId) {
        Pct entity = findByApplicationId(applicationId);
        return pctMapper.map(entity);
    }

    @Override
    public LocalDate getFilingDateByApplicationNumber(String applicationNumber) {
        return pctRepository.getFilingDateByApplicationNumber(applicationNumber);
    }

    @Override
    public Pct findByPatentDetailsId(Long patentDetailsId) {
        return pctRepository.findByPatentDetailsId(patentDetailsId);
    }
}
