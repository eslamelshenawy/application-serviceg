package gov.saip.applicationservice.modules.plantvarieties.validators;

import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceDto;
import gov.saip.applicationservice.util.Constants;
import joptsimple.internal.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProveExcellenceValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (null == target)
            throw new BusinessException(Constants.ErrorKeys.PCT_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        ProveExcellenceDto dto = (ProveExcellenceDto) target;
        if (dto.getApplication().getId() == null || Strings.isNullOrEmpty(dto.getPlantNameSimilarYourPlant())) {
            throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if(dto.getLkpvPropertyId() == null){

            if(Strings.isNullOrEmpty(dto.getAttributePlantDescription()) || Strings.isNullOrEmpty(dto.getAttributePlantDescription()) || Strings.isNullOrEmpty(dto.getDescriptionTraitSimilarCategory())){
                throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
            }
            dto.setLkpvPropertyOptionsId(null);
            dto.setLkpvPropertyOptionsSecondId(null);
        }
    }
}

