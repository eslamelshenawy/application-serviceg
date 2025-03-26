package gov.saip.applicationservice.common.validators;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.common.dto.CustomerCodeListDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.impl.ApplicationServiceImpl;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomerCodeValidator {
    private final static Logger logger = LoggerFactory.getLogger(CustomerCodeValidator.class);

    private CustomerServiceCaller customerServiceCaller;

    public CustomerCodeValidator(CustomerServiceCaller customerServiceCaller) {
        this.customerServiceCaller = customerServiceCaller;
    }

    public void customerCodeValidator(List<String> customerCodes) {
        if (CollectionUtils.isEmpty(customerCodes)) {
            return;
        }
        try {
            CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
            customerCodeListDto.setCustomerCode(customerCodes);
            List<CustomerSampleInfoDto> customerSampleInfoDto = customerServiceCaller.getCustomerByListOfCode(customerCodeListDto).getPayload();
            Set<String> codeSets = new HashSet<>(customerCodeListDto.getCustomerCode());
            Set<String> receivedCodesSet = new HashSet();
            for (int i = 0; i < customerSampleInfoDto.size(); i++) {
                receivedCodesSet.add(customerSampleInfoDto.get(i).getCode());
            }
            if (codeSets.size() != receivedCodesSet.size()) {
                throw new BusinessException(Constants.ErrorKeys.SOME_OF_CODES_NOT_RETURNED, HttpStatus.BAD_REQUEST, null);
            }
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }

    }
}
