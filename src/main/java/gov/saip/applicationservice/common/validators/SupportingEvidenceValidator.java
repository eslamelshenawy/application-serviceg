package gov.saip.applicationservice.common.validators;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.common.dto.SupportingEvidenceDto;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class SupportingEvidenceValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(SupportingEvidenceValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        SupportingEvidenceDto supportingEvidenceDto = (SupportingEvidenceDto) target;

        if(supportingEvidenceDto.getPatentRegisteration() != null && supportingEvidenceDto.getPatentRegisteration() && supportingEvidenceDto.getDocument()!=null && supportingEvidenceDto.getDocument().getId()!=0L){
            if(supportingEvidenceDto.getPatentNumber() == null || supportingEvidenceDto.getLink() == null )
                throw new BusinessException(Constants.ErrorKeys.COMMON_DATA_NOT_FOUND, HttpStatus.NON_AUTHORITATIVE_INFORMATION, null);
        }else{
            if(supportingEvidenceDto.getLink() == null )
                throw new BusinessException(Constants.ErrorKeys.COMMON_DATA_NOT_FOUND, HttpStatus.NON_AUTHORITATIVE_INFORMATION, null);
        }
    }

}


