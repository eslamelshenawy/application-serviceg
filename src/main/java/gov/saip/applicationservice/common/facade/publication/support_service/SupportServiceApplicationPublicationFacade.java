package gov.saip.applicationservice.common.facade.publication.support_service;

import gov.saip.applicationservice.common.enums.PublicationType;
import gov.saip.applicationservice.common.enums.support_services_enums.SupportServicesPublicationType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationPublicationService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.LicenceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static gov.saip.applicationservice.common.enums.LicenceTypeEnum.CANCEL_LICENCE;
import static gov.saip.applicationservice.common.enums.PublicationType.LICENCE_CANCELLATION;
import static gov.saip.applicationservice.common.enums.SupportServiceType.*;

@Service
@RequiredArgsConstructor
public class SupportServiceApplicationPublicationFacade {

    private final ApplicationPublicationService applicationPublicationService;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final LicenceRequestService licenceRequestService;
    @Transactional
    public void createSupportServiceApplicationPublication(Long supportServiceId) {
        ApplicationSupportServicesType applicationSupportServicesType =
                applicationSupportServicesTypeService.findById(supportServiceId);
        
        if (applicationSupportServicesType == null) {
            return;
        }
        ApplicationInfo application = applicationSupportServicesType.getApplicationInfo();
        String categoryCode = application.getCategory().getSaipCode();
        String publicationType = determinePublicationType(applicationSupportServicesType, categoryCode);
        String documentType = determineDocumentType(applicationSupportServicesType);
        
        if (publicationType == null) {
            return;
        }
        
        applicationPublicationService.createApplicationPublication(
                application,
                application.getApplicationStatus().getCode(),
                publicationType,
                documentType,
                supportServiceId
        );
   
    }
    
    private String determinePublicationType(ApplicationSupportServicesType supportService, String categoryCode) {
        String supportServiceType = supportService.getLkSupportServices().getCode().name();
        List<PublicationType> publicationTypes =  SupportServicesPublicationType.
                getPublicationTypesForSupportServices(categoryCode, supportServiceType);
        
        if(publicationTypes.isEmpty())
            return null;
        
        if (LICENSING_REGISTRATION.name().equals(supportServiceType) || LICENSING_MODIFICATION.name().equals(supportServiceType)) {
            String licenceRequestType = licenceRequestService.getLicensingRequestType(supportService.getId());
            
            if (CANCEL_LICENCE.name().equals(licenceRequestType)) {
                return publicationTypes.stream().filter(publicationTypeEnum -> LICENCE_CANCELLATION.name().equals(publicationTypeEnum.name()))
                        .findFirst().get().name();
            } else {
                return publicationTypes.stream().filter(publicationTypeEnum -> (publicationTypeEnum.name()).endsWith("LICENCE_USE"))
                        .findFirst().get().name();
            }
        }
        
        return publicationTypes.get(0).name();
    }
    
    
    private String determineDocumentType(ApplicationSupportServicesType supportService) {
        if (OWNERSHIP_CHANGE.name().equals(supportService.getLkSupportServices().getCode().name())) {
            return "OWNERSHIP_APPLICATION_XML";
        }
        else if(LICENSING_REGISTRATION.name().equals(supportService.getLkSupportServices().getCode().name())){
            return "LICENSING_APPLICATION_XML";
        }
        return null;
    }

    @Transactional
    public void createInstallmentApplicationPublication(ApplicationInstallment applicationInstallment, String publicationType) {

        if (applicationInstallment == null) {
            return;
        }

        ApplicationInfo application = applicationInstallment.getApplication();


        if (publicationType == null) {
            return;
        }

        applicationPublicationService.createApplicationPublication(
                application,
                application.getApplicationStatus().getCode(),
                publicationType,
                null,
                null
        );
    }
    
}
