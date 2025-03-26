package gov.saip.applicationservice.modules.plantvarieties.validators;

import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.TechnicalSurveyPlantVarietyDto;
import gov.saip.applicationservice.util.Constants;
import joptsimple.internal.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TechnicalSurveyPlantVarietyValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (null == target)
            throw new BusinessException(Constants.ErrorKeys.PCT_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        TechnicalSurveyPlantVarietyDto dto = (TechnicalSurveyPlantVarietyDto) target;
        if (dto.getApplication() == null || Strings.isNullOrEmpty(dto.getPlantVarietyNameEN()) ||
                Strings.isNullOrEmpty(dto.getPlantVarietyNameAr()) || Strings.isNullOrEmpty(dto.getCommercialNameEn())
        || Strings.isNullOrEmpty(dto.getCommercialNameAr()) ||Strings.isNullOrEmpty(dto.getDetailedSurvey())
        || Strings.isNullOrEmpty(dto.getDescriptionVarietyDevelopment())) {
            throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if(dto.getHybridizationTypeFlag()==true){
            if(dto.getHybridizationTypeId() ==null || dto.getReproductionMethodId() == null || dto.getVegetarianTypeUseId()==null)
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if(dto.getLeapFlag()==true){
            if(dto.getVegetarianTypeUseId() ==null || dto.getReproductionMethodId() == null )
            throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if(dto.getDiscoveryFlag()==true){
            if(dto.getVegetarianTypeUseId() == null||Strings.isNullOrEmpty(dto.getDiscoveryPlace()) || dto.getReproductionMethodId() == null || dto.getDiscoveryDate()==null )
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if(dto.getOtherFlag()==true){
            if( dto.getReproductionMethodId() == null || Strings.isNullOrEmpty(dto.getPlantOrigination()) || dto.getVegetarianTypeUseId()==null)
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if(dto.getHybridizationTypeCode() != null && dto.getHybridizationTypeCode().equals("COMPLETELY_CONTROLLED_HYBRIDIZATION")){
            if( Strings.isNullOrEmpty(dto.getHybridizationFatherName()) || Strings.isNullOrEmpty(dto.getHybridizationMotherName()))
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if(dto.getReproductionMethodCode() !=null && dto.getReproductionMethodCode().equals("OTHER")){
            if(Strings.isNullOrEmpty(dto.getReproductionMethodClarify()))
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if(dto.getVegetarianTypeUseCode()!= null && dto.getVegetarianTypeUseCode().equals("OTHER")){
            if(Strings.isNullOrEmpty(dto.getVegetarianTypeUseClarify()))
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
    }
}

