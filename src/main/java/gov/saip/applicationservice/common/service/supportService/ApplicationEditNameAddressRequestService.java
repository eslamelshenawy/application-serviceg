package gov.saip.applicationservice.common.service.supportService;

import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.model.supportService.application_edit_name_address.ApplicationEditNameAddressRequest;
import gov.saip.applicationservice.common.service.SupportServiceRequestService;

public interface ApplicationEditNameAddressRequestService extends SupportServiceRequestService<ApplicationEditNameAddressRequest> {
    void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto);
}
