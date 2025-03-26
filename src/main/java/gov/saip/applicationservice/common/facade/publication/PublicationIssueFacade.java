package gov.saip.applicationservice.common.facade.publication;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerConfigParameterClient;
import gov.saip.applicationservice.common.dto.ConfigParameterDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.CustomerConfigParameterEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationPublication;
import gov.saip.applicationservice.common.model.LKPublicationType;
import gov.saip.applicationservice.common.model.PublicationIssue;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentConfigService;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.common.service.lookup.LKPublicationTypeService;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.PATENT;
import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.TRADEMARK;

@Service
@RequiredArgsConstructor
public class PublicationIssueFacade {
    private final PublicationIssueNumberService publicationIssueNumberService;
    private final PublicationSchedulingConfigService publicationSchedulingConfigService;
    private PublicationIssueService publicationIssueService;
    private final CustomerConfigParameterClient customerConfigParameterClient;
    private  ApplicationInfoService applicationInfoService;
    private final Clock clock;
    private final ApplicationPublicationService applicationPublicationService;
    private final LKPublicationTypeService publicationTypeService;
    @Autowired
    private ApplicationInstallmentConfigService applicationInstallmentConfigService;
    @Autowired
    private ApplicationInstallmentService applicationInstallmentService;


    @Autowired
    public void setPublicationIssueService(PublicationIssueService publicationIssueService) {
        this.publicationIssueService = publicationIssueService;
    }

    @Autowired
    public void setApplicationInfoService(ApplicationInfoService applicationInfoService) {
        this.applicationInfoService = applicationInfoService;
    }


    @Transactional
    public void updateLatestIssueOrCreateNewIssue(ApplicationCategoryEnum applicationCategoryEnum) {
        int issueCutOffInDays = getIssueCutOffInDays();
        LocalDateTime nextIssueDate = publicationSchedulingConfigService.calculateNextIssueDate(applicationCategoryEnum,
                issueCutOffInDays,
                clock);
        long nextIssueNumber = publicationIssueNumberService.calculateNextIssueNumber(applicationCategoryEnum);
        List<ApplicationInfo> applications = applicationInfoService.getApplicationsThatAreReadyToBeAddedToNextPublicationIssue(applicationCategoryEnum);
        PublicationIssue newIssue = publicationIssueService.updateLatestIssueOrCreateNewIssue(applications,
                                    applicationCategoryEnum,
                                    nextIssueDate,
                                    nextIssueNumber,
                                    issueCutOffInDays,
                                    clock);
        if(newIssue != null && newIssue.getId() != null)
            publicationIssueService.startPublicationIssueProcess(newIssue.getId());
    }

    private int getIssueCutOffInDays() {
        ConfigParameterDto payload = customerConfigParameterClient.getConfig(CustomerConfigParameterEnum.PUBLICATION_ISSUE_CUTOFF.name()).getPayload();
        return Integer.parseInt(payload.getValue());
    }
    
    @Transactional
    public void addGrantedApplicationPublicationToLatestIssueOrCreateNewIssue(Long applicationId) {
        ApplicationInfo application = applicationInfoService.findById(applicationId);
        Long newIssueId = addApplicationPublicationToLatestIssueOrCreateNewIssue(application);

        // protection date + installments
        if (application.getCategory().getSaipCode().equals(TRADEMARK.name())) {
            applicationInfoService.updateStatusAndProtectionDate(applicationId, ApplicationStatusEnum.THE_TRADEMARK_IS_REGISTERED, getEndOfProtection(application));
        } else {
            applicationInfoService.updateStatusAndProtectionDate(applicationId, ApplicationStatusEnum.ACCEPTANCE, getEndOfProtection(application));
        }
        applicationInstallmentService.insertFirstInstallmentForGivenApplications(application);
        updateApplicationRegistrationDate(application);

        if (newIssueId != null) {
            publicationIssueService.startPublicationIssueProcess(newIssueId);
        }

    }
    
    private void updateApplicationRegistrationDate(ApplicationInfo application) {
        application.setRegistrationDate(LocalDateTime.now(clock));
        application.setRegistrationDateHijri(Utilities.convertDateFromGregorianToHijri((LocalDateTime.now(clock).toLocalDate())));
        applicationInfoService.update(application);
    }
    
    
    private LocalDateTime getEndOfProtection(ApplicationInfo application) {
        if (PATENT.name().equals(application.getCategory().getSaipCode())) {
            return application.getEndOfProtectionDate();
        }

        ApplicationInstallmentConfig config = applicationInstallmentConfigService.findByApplicationCategory(application.getCategory().getSaipCode());
        LocalDateTime endOfProtection = LocalDateTime.now();
        if (ApplicationCategoryEnum.TRADEMARK.name().equals(application.getCategory().getSaipCode())) {
            endOfProtection = application.getFilingDate();
        }
        Integer paymentIntervalYears = config.getPaymentIntervalYears();
        return Utilities.getLocalDateTimeAfterAddingYearsToHijri(Long.valueOf(paymentIntervalYears),endOfProtection);
    }

    /**@apiNote
     *
     * @param applicationId
     * @param publicationTypeCode : it will be CROSSED_OUT_MARK in VOLUNTARY_TRADEMARK_DELETION
     */
    
    @Transactional
    public void addApplicationPublicationToLatestIssueOrCreateNewIssue(Long applicationId, String publicationTypeCode) {
        
        ApplicationInfo application = applicationInfoService.findById(applicationId);
        ApplicationPublication applicationPublication = applicationPublicationService.findByApplicationId(applicationId);
        LKPublicationType publicationType = publicationTypeService.findByCode(publicationTypeCode);
        applicationPublication.setPublicationType(publicationType);
        Long newIssueId = addApplicationPublicationToLatestIssueOrCreateNewIssue(application);
        if(newIssueId != null)
            publicationIssueService.startPublicationIssueProcess(newIssueId);
    }
    
    public Long addApplicationPublicationToLatestIssueOrCreateNewIssue(ApplicationInfo application) {
        int issueCutOffInDays = getIssueCutOffInDays();
        ApplicationCategoryEnum applicationCategoryEnum = ApplicationCategoryEnum.valueOf(application.getCategory().getSaipCode());
        
        LocalDateTime nextIssueDate = publicationSchedulingConfigService.calculateNextIssueDate(applicationCategoryEnum,
                issueCutOffInDays,
                clock);
        long nextIssueNumber = publicationIssueNumberService.calculateNextIssueNumber(applicationCategoryEnum);
        return publicationIssueService.addApplicationPublicationToLatestIssueOrCreateNewIssue(application,
                        applicationCategoryEnum,
                        nextIssueDate,
                        nextIssueNumber,
                        issueCutOffInDays,
                        clock);
    }

}
