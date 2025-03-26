package gov.saip.applicationservice.common.controllers.publication.support_service;


import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.facade.publication.support_service.SupportServiceApplicationPublicationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/pb", "/internal-calling"})
@RequiredArgsConstructor
public class SupportServiceApplicationPublicationController {
    
    private final SupportServiceApplicationPublicationFacade supportServiceApplicationPublicationFacade;
    
    
    @PostMapping("/support-service/{id}/application-publication")
    public ApiResponse<Void> publishSupportServiceApplicationInNextIssue(@PathVariable("id") Long supportServiceId) {
        supportServiceApplicationPublicationFacade.createSupportServiceApplicationPublication(supportServiceId);
        return ApiResponse.ok();
        
    }
    
}
