package gov.saip.applicationservice.common.validators;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.common.dto.ApplicationPriorityBulkDto;
import gov.saip.applicationservice.common.dto.ApplicationPriorityDto;
import gov.saip.applicationservice.common.dto.patent.PctRequestDto;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.patent.PctService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
@Slf4j
public class PrioritiesValidator implements Validator {


    private final ApplicationInfoService applicationInfoService;
    private final Logger logger = LoggerFactory.getLogger(PrioritiesValidator.class);

    public PrioritiesValidator(ApplicationInfoService applicationInfoService) {
        this.applicationInfoService = applicationInfoService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(null == target)
            throw new BusinessException(Constants.ErrorKeys.PCT_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        ApplicationPriorityDto dto = (ApplicationPriorityDto) target;
        if(applicationInfoService.checkPltRegister(dto.getApplicationId())){
                Long priorityDocumentNumber = dto.getPriorityDocument();
                if(priorityDocumentNumber ==null || priorityDocumentNumber.equals(""))
                    throw new BusinessException(Constants.ErrorKeys.PRIORITIES_DOCUMENTS_NOT_FOUND, HttpStatus.BAD_REQUEST, null);

        }
    }

}


