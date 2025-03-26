package gov.saip.applicationservice.common.validators;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.common.dto.ApplicationPriorityDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantRequestsDto;
import gov.saip.applicationservice.common.dto.InventorRequestsDto;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class InventorsValidator implements Validator {


    private final ApplicationInfoService applicationInfoService;
    private final Logger logger = LoggerFactory.getLogger(InventorsValidator.class);

    public InventorsValidator(ApplicationInfoService applicationInfoService) {
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
        InventorRequestsDto dto = (InventorRequestsDto) target;
        if(applicationInfoService.checkPltRegister(dto.getAppInfoId())){
            for (ApplicationRelevantRequestsDto plt: dto.getRelvants()){
                if(plt.getWaiverDocumentId() ==null || plt.getWaiverDocumentId().equals(""))
                    throw new BusinessException(Constants.ErrorKeys.PRIORITIES_DOCUMENTS_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
                if(plt.getIdentifier() == null || plt.getIdentifier().isEmpty())
                    throw new BusinessException(Constants.ErrorKeys.IDENTIFIER_NOT_FOUND_PLT, HttpStatus.BAD_REQUEST, null);
            }


        }
    }

}


