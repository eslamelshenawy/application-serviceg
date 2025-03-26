package gov.saip.applicationservice.modules.plantvarieties.validators;

import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.ExaminationDataPlantVarietyDto;
import gov.saip.applicationservice.util.Constants;
import joptsimple.internal.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ExaminationDataPlantValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (null == target)
            throw new BusinessException(Constants.ErrorKeys.PCT_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        ExaminationDataPlantVarietyDto dto = (ExaminationDataPlantVarietyDto) target;
        if(dto.getApplication() == null)
            throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        if (dto.getMicrobiology()==true) {
            if(Strings.isNullOrEmpty(dto.getMicrobiologyNote()))
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if (dto.getOtherFactors()==true) {
            if(Strings.isNullOrEmpty(dto.getOtherFactorsNote()))
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if (dto.getTissueCulture()==true) {
            if(Strings.isNullOrEmpty(dto.getTissueCultureNote()))
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if (dto.getChemicalEdit()==true) {
            if(Strings.isNullOrEmpty(dto.getChemicalEditNote()))
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
    }
}

