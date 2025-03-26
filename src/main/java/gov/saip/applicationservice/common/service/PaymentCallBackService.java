package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;

public interface PaymentCallBackService {
    void paymentCallBack(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id);
}

