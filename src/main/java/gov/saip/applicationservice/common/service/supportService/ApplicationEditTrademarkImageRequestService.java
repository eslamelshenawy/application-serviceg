package gov.saip.applicationservice.common.service.supportService;

import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.supportService.ApplicationEditTrademarkImageRequestDto;
import gov.saip.applicationservice.common.model.supportService.application_edit_trademark_image_request.ApplicationEditTrademarkImageRequest;
import gov.saip.applicationservice.common.service.SupportServiceRequestService;

public interface ApplicationEditTrademarkImageRequestService extends SupportServiceRequestService<ApplicationEditTrademarkImageRequest> {
    
    void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto);
    
    ApplicationEditTrademarkImageRequestDto getDetailsBySupportServiceId(Long serviceId);
}
