package gov.saip.applicationservice.common.validators;

import gov.saip.applicationservice.common.model.ApplicationAccelerated;
import gov.saip.applicationservice.common.service.ApplicationAcceleratedService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ApplicationAcceleratedValidator {

    private final ApplicationAcceleratedService applicationAcceleratedService;

    @Autowired
    public ApplicationAcceleratedValidator(@Lazy ApplicationAcceleratedService applicationAcceleratedService) {
        this.applicationAcceleratedService = applicationAcceleratedService;
    }

    public void validateApplicationAccelerated(ApplicationAccelerated applicationAccelerated) {
        Optional<ApplicationAccelerated> applicationAcceleratedExists = applicationAcceleratedService.getByApplicationInfo(applicationAccelerated.getApplicationInfo());
        if (applicationAcceleratedExists.isPresent())
            throw new BusinessException(Constants.ErrorKeys.APP_INFO_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, null);
    }
}


