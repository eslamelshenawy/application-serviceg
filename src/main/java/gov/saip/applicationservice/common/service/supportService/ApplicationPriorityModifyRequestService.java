package gov.saip.applicationservice.common.service.supportService;

import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.supportService.ApplicationPriorityModifyRequestLightDto;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityModifyRequest;
import gov.saip.applicationservice.common.service.SupportServiceRequestService;

public interface ApplicationPriorityModifyRequestService extends SupportServiceRequestService<ApplicationPriorityModifyRequest> {

    void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto);

    void updateApplicationPriority(ApplicationPriorityModifyRequestLightDto applicationPriorityModifyRequestLightDto);
}
