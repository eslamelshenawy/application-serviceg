package gov.saip.applicationservice.common.facade.publication.support_service;

import gov.saip.applicationservice.common.facade.publication.PublicationIssueFacade;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupportServicePublicationIssueFacade {
    
    private final PublicationIssueFacade publicationIssueFacade;
    private final PublicationIssueService publicationIssueService;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final ApplicationInstallmentService applicationInstallmentService;

    @Transactional
    public void AddSupportServiceApplicationPublicationToLatestIssueOrCreateNewIssue(Long supportServiceId) {
        
        ApplicationSupportServicesType applicationSupportServicesType =
                applicationSupportServicesTypeService.findById(supportServiceId);
        
        if (applicationSupportServicesType == null) {
            return;
        }
        ApplicationInfo application = applicationSupportServicesType.getApplicationInfo();
        Long newIssueId = publicationIssueFacade.addApplicationPublicationToLatestIssueOrCreateNewIssue(application);
        if (newIssueId != null)
            publicationIssueService.startPublicationIssueProcess(newIssueId);
        
    }

    @Transactional
    public void AddSupportServiceApplicationPublicationToLatestIssueOrCreateNewIssueInstallment(Long installmentId) {

        ApplicationInstallment applicationInstallment = applicationInstallmentService.getById(installmentId).get();
        log.info(" applicationInstallment with id ==>> {} ", applicationInstallment.getId());
        Long newIssueId = publicationIssueFacade.addApplicationPublicationToLatestIssueOrCreateNewIssue(applicationInstallment.getApplication());

        log.info(" newIssueId with id ==>> {} ", newIssueId);
        if (newIssueId != null)
            publicationIssueService.startPublicationIssueProcess(newIssueId);

    }

    
}
