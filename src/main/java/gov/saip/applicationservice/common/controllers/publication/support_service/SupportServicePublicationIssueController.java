package gov.saip.applicationservice.common.controllers.publication.support_service;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.facade.publication.support_service.SupportServicePublicationIssueFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/pb", "/internal-calling"})
@RequiredArgsConstructor
public class SupportServicePublicationIssueController {
    
    private final SupportServicePublicationIssueFacade supportServicePublicationIssueFacade;
    
    @PostMapping("/support-service/{id}/publish-in-next-issue")
    public ApiResponse<Void> publishSupportServiceApplicationInNextIssue(@PathVariable("id") Long supportServiceId) {
        supportServicePublicationIssueFacade.AddSupportServiceApplicationPublicationToLatestIssueOrCreateNewIssue(supportServiceId);
        return ApiResponse.ok();
        
    }
    @PostMapping("/installment-rennewal-trademark-service/{id}/publish-in-next-issue")
    public ApiResponse<Void> publishInstallmentRenewalTrademarkApplicationInNextIssue(@PathVariable("id") Long installmentId) {
        supportServicePublicationIssueFacade.AddSupportServiceApplicationPublicationToLatestIssueOrCreateNewIssueInstallment(installmentId);
        return ApiResponse.ok();

    }
    
}
