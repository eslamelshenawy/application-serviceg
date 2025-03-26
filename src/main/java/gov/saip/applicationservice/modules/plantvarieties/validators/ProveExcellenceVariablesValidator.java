package gov.saip.applicationservice.modules.plantvarieties.validators;

import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.PlantProveExcellenceVariablesDto;
import gov.saip.applicationservice.util.Constants;
import joptsimple.internal.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProveExcellenceVariablesValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (null == target)
            throw new BusinessException(Constants.ErrorKeys.PCT_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        PlantProveExcellenceVariablesDto dto = (PlantProveExcellenceVariablesDto) target;
        if(dto.getApplication() == null)
            throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        if (dto.getPlantConditionalTesting()==true) {
            if(Strings.isNullOrEmpty(dto.getPlantConditionalTestingNote()))
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if (dto.getAdditionalFeatureDifferentiateVariety()==true) {
            if(Strings.isNullOrEmpty(dto.getAdditionalFeatureDifferentiateVarietyNote()))
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
    }
}

