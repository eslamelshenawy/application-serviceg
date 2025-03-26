package gov.saip.applicationservice.common.validators;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.ApplicantsRequestDto;
import gov.saip.applicationservice.common.enums.customers.UserGroup;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class TradeMarkApplicationValidator  implements Validator {

    private CustomerServiceFeignClient customerServiceFeignClient;

    public TradeMarkApplicationValidator(CustomerServiceFeignClient customerServiceFeignClient) {
        this.customerServiceFeignClient = customerServiceFeignClient ;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.info("TradeMarkApplicationValidator : validate : started");
        ApplicantsRequestDto applicantsRequestDto = (ApplicantsRequestDto) target;
        log.info("TradeMarkApplicationValidator : validate : applicantsRequestDto is {} :"+ applicantsRequestDto);
        if (applicantsRequestDto.getCustomerCode() != null) {
//            String userGroupCode = this.customerServiceFeignClient.getUserGroupCodeByCustomerCode(Utilities.getCustomerCodeFromHeaders()).getPayload();

//            if (!isCurrentCustomerHasPermissionToCreateApplications(userGroupCode)) {
//                logger.error().message("Error message").log();
//                throw new BusinessException(Constants.ErrorKeys.GENERAL_DO_NOT_HAVE_PERMISSION, HttpStatus.NON_AUTHORITATIVE_INFORMATION, null);
//            }
        }
    }

    private static boolean isCurrentCustomerHasPermissionToCreateApplications(String userGroupCode) {
        if (userGroupCode == null) {
            return false;
        }

        return userGroupCode.equals(UserGroup.AGENT.getValue()) || userGroupCode.equals(UserGroup.NATURAL_PERSON_WITH_NATIONALITY.getValue());
    }

}


