package gov.saip.applicationservice.common.validators;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.common.dto.ApplicantsRequestDto;
import gov.saip.applicationservice.common.dto.patent.PctRequestDto;
import gov.saip.applicationservice.common.service.BPMCallerService;
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
public class PctValidator implements Validator {


    private final PctService pctService;
    private final BPMCallerService bpmCallerService;
    private static final String DESCRIPTION_LETTERS_LIMIT_PAT = "DESCRIPTION_LETTERS_LIMIT_PAT";
    private static final String PCT_VALID_MONTHS_COUNT_PAT = "PCT_VALID_MONTHS_COUNT_PAT";
    private final Logger logger = LoggerFactory.getLogger(PctValidator.class);

    public PctValidator(PctService pctService, BPMCallerService bpmCallerService) {
        this.pctService = pctService;
        this.bpmCallerService = bpmCallerService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(null == target)
            throw new BusinessException(Constants.ErrorKeys.PCT_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        ApplicantsRequestDto dto = (ApplicantsRequestDto) target;
        if(dto.getPltRegisteration() != null && dto.getPltRegisteration()){
            long lettersLimit = bpmCallerService.getRequestTypeConfigValue(DESCRIPTION_LETTERS_LIMIT_PAT);
            if(dto.getPltDocument() == null && (dto.getPltDescription() == null || dto.getPltDescription().isEmpty() || dto.getPltDescription().length() < lettersLimit))
                throw new BusinessException(Constants.ErrorKeys.PATENT_PLT_DATA_INVALID, HttpStatus.BAD_REQUEST, null);
        }else {
            PctRequestDto pct = dto.getPctRequestDto();
            if (pct.getPctCopyDocument() == null || pct.getPctCopyDocument().toString().isEmpty())
                throw new BusinessException(Constants.ErrorKeys.PCT_DOCUMENT_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
            // Subtract 30 months from the current date
            long monthsCount = bpmCallerService.getRequestTypeConfigValue(PCT_VALID_MONTHS_COUNT_PAT);
            LocalDate resultDate = LocalDate.now().minusMonths(monthsCount);
            if (pct.getId() == null && resultDate.isAfter(pct.getFilingDateGr())) {
                if (pct.getPetitionNumber() != null && !pct.getPetitionNumber().isEmpty()) {
                    if (!pctService.validatePetitionNumber(pct.getPetitionNumber()))
                        throw new BusinessException(Constants.ErrorKeys.PCT_PETITION_NOT_CORRECT, HttpStatus.BAD_REQUEST, null);
                } else {
                    throw new BusinessException(Constants.ErrorKeys.PCT_DATE_NOT_CORRECT, HttpStatus.BAD_REQUEST, null);
                }
            }
        }

    }

}


