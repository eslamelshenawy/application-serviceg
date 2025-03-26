package gov.saip.applicationservice.common.validators;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerClient;
import gov.saip.applicationservice.common.dto.ApplicationRelevantRequestsDto;
import gov.saip.applicationservice.common.enums.IdentifierTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationCustomer;
import gov.saip.applicationservice.common.repository.ApplicationCustomerRepository;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class ApplicationRelevantTypeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
    @Autowired
    private ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    @Autowired
    private ApplicationCustomerRepository applicationCustomerRepository;
    @Autowired
    private  CustomerClient customerClient;

    @Override
    public void validate(Object target, Errors errors) {
        ApplicationRelevantRequestsDto dto = (ApplicationRelevantRequestsDto) target;
      ApplicationCustomer customerCodeFromApplicationCustomer = applicationCustomerRepository.getCustomerCodeFromApplicationCustomer(dto.getAppInfoId());
      if(customerCodeFromApplicationCustomer != null && dto.getIdentifier().equalsIgnoreCase(customerCodeFromApplicationCustomer.getCustomerCode())){
          throw new BusinessException(Constants.ErrorKeys.ADD_EXIST_CUSTOMER_CODE, HttpStatus.BAD_REQUEST, null);
      }

        if (dto.getIdentifierType().equals(IdentifierTypeEnum.CUSTOMER_CODE)){
            Boolean isExist =  customerClient.checkCustomerCodeExistsOnCustomer(dto.getIdentifier()).getPayload();
            if (isExist==false) {
                throw new BusinessException(Constants.ErrorKeys.VALIDATION_USER_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
            }
        }
        if (dto == null || dto.getIdentifierType() == null ||(!dto.getIdentifierType().equals(IdentifierTypeEnum.CUSTOMER_CODE) && (Strings.isNullOrEmpty(dto.getIdentifier()) || Strings.isNullOrEmpty(dto.getFullNameAr())
                || Strings.isNullOrEmpty(dto.getFullNameEn()) || Strings.isNullOrEmpty(dto.getGender()) || dto.getNationalCountryId() == null
                || dto.getCountryId() == null || Strings.isNullOrEmpty(dto.getCity()))))
            throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);

        if (dto.getIdentifierType().equals(IdentifierTypeEnum.CUSTOMER_CODE)) {
            Boolean isExist = applicationRelevantTypeRepository.checkCustomerCodeExistsOnApplication(dto.getAppInfoId(), dto.getIdentifier());
            if (isExist) {
                throw new BusinessException(Constants.ErrorKeys.ADD_EXIST_CUSTOMER_CODE, HttpStatus.INTERNAL_SERVER_ERROR, null);
            }
        }
    }

}


