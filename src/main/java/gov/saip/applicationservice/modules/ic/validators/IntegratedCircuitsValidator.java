package gov.saip.applicationservice.modules.ic.validators;

import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitDto;
import gov.saip.applicationservice.util.Constants;
import joptsimple.internal.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class IntegratedCircuitsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (null == target)
            throw new BusinessException(Constants.ErrorKeys.PCT_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        IntegratedCircuitDto dto = (IntegratedCircuitDto) target;
        if (dto.getApplication() == null || Strings.isNullOrEmpty(dto.getDesignNameAr()) || Strings.isNullOrEmpty(dto.getDesignNameEn())
                || Strings.isNullOrEmpty(dto.getDesignDescription()) || dto.getDesignDate() == null || dto.getCommercialExploited() == null) {
            throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
        }
        if (dto.getCommercialExploited() != null && dto.getCommercialExploited().equals(Boolean.TRUE)) {
            if (dto.getCommercialExploitationDate() == null)
                throw new BusinessException(Constants.ErrorKeys.COMMERCIAL_EXPLOITATION_DATE, HttpStatus.BAD_REQUEST, null);
            if (dto.getCountryId() == null)
                throw new BusinessException(Constants.ErrorKeys.NULL_COUNTRY_ID, HttpStatus.BAD_REQUEST, null);
        }
    }
}


