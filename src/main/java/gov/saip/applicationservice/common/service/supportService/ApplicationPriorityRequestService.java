package gov.saip.applicationservice.common.service.supportService;

import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityRequest;
import gov.saip.applicationservice.common.service.SupportServiceRequestService;
import org.springframework.data.repository.query.Param;

public interface ApplicationPriorityRequestService extends SupportServiceRequestService<ApplicationPriorityRequest> {

    void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto);

    boolean applicationSupportServicesTypePeriorityEditOnlyExists(Long appId);
}
