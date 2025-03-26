package gov.saip.applicationservice.util;

import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@AllArgsConstructor
public class ApplicationValidator {

    private final ApplicationCustomerService applicationCustomerService;
    public void checkAccessForCustomerCode(Long applicationId){
        String currentUserCustomerCode = Utilities.getCustomerCodeFromHeaders();
        if(currentUserCustomerCode == null) return;
        List<String> applicationVerifiedCustomerCodes = applicationCustomerService.getApplicationVerifiedCustomerCodes(applicationId, Constants.NON_OWNER_SUPPORT_SERVICE_STRING_CODES);
        if(!applicationVerifiedCustomerCodes.contains(currentUserCustomerCode)){
            throw new BusinessException(Constants.ErrorKeys.GENERAL_DO_NOT_HAVE_PERMISSION, HttpStatus.FORBIDDEN, null);
        }
    }


}
