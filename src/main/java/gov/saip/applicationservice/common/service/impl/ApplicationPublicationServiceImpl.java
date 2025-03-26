package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.enums.AppStatusChangeLogDescriptionCode;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.DocumentTypeEnum;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ApplicationPublicationRepository;
import gov.saip.applicationservice.common.repository.lookup.LKPublicationTypeRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationStatusRepository;
import gov.saip.applicationservice.common.service.ApplicationPublicationService;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.PUBLISHED_ELECTRONICALLY;

@Service
@RequiredArgsConstructor
public class ApplicationPublicationServiceImpl extends BaseServiceImpl<ApplicationPublication, Long> implements ApplicationPublicationService {
    
    private final ApplicationPublicationRepository applicationPublicationRepository;
    private final DocumentsService documentsService;
    private final LkApplicationStatusRepository lkApplicationStatusRepository;
    private final LKPublicationTypeRepository lkPublicationTypeRepository;
    private final ApplicationInfoRepository applicationInfoRepository;
    private Clock clock;

    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    @Override
    protected BaseRepository<ApplicationPublication, Long> getRepository() {
        return applicationPublicationRepository;
    }
    @Autowired
    public void setClock(Clock clock) {
        this.clock = clock;
    }
    
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    
    @Override
    public ApplicationPublication findByApplicationId(Long applicationId) {
        List<ApplicationPublication> applicationPublications =
                applicationPublicationRepository.findByApplicationId(applicationId).orElseThrow();
        return applicationPublications.get(0);
    }
    
    @Override
    public LocalDateTime getPublicationDateById(Long id) {
        return applicationPublicationRepository.findPublicationDateById(id).orElseThrow();
    }
    
    
    @Override
    @Transactional
    public void createApplicationPublication(Long applicationId, String pubType) {
// todo refactoring
        System.out.println("Start publication ") ;
        System.out.println("  publication " + applicationId + " "+ pubType) ;
        try{
        ApplicationInfo application = applicationInfoRepository.findById(applicationId).orElseThrow();
        if (Objects.nonNull(pubType)) {
            String status = (pubType.equals("PUBLICATION_A") || pubType.equals("TRADEMARK_RENEWAL")) ? application.getApplicationStatus().getCode() : PUBLISHED_ELECTRONICALLY.name();
            createApplicationPublication(application, status,
                    pubType, DocumentTypeEnum.APPLICATION_XML.name(), null);
            
        } else {
            String publicationType = Constants.MAP_CATEGORY_To_REGISTRATION_PUBLICATION_TYPE.get(application.getCategory().getSaipCode());
            System.out.println("  publication  publicationType " + publicationType  ) ;
            createApplicationPublication(application, PUBLISHED_ELECTRONICALLY.name(),
                    publicationType, DocumentTypeEnum.APPLICATION_XML.name(), null);

        }}catch (Exception ex){

            System.out.println(" publication exception ");
            System.out.println(ex.getMessage());
        }
        
    }
    
    
    public void createApplicationPublication(ApplicationInfo application, String appStatusCode, String publicationType,
                                             String documentType, Long serviceId) {
//        LkApplicationStatus applicationStatus = getApplicationStatus(appStatusCode, application.getCategory().getId());
        LKPublicationType lkPublicationType = getPublicationType(publicationType, application.getCategory().getId());
//        application.setApplicationStatus(applicationStatus);
        System.out.println(" publication trace 1 ");
        applicationStatusChangeLogService.changeApplicationStatusAndLog(appStatusCode, AppStatusChangeLogDescriptionCode.CREATE_APPLICATION_PUBLICATION.name(),application.getId());
        System.out.println(" publication trace 2 ");
        Document document = getDocument(application.getId(), documentType);
        System.out.println(" publication trace 3 ");
        ApplicationPublication applicationPublication = applicationPublicationBuilder(application, lkPublicationType, document);
        if (serviceId != null) {
            ApplicationSupportServicesType applicationSupportServicesType = new ApplicationSupportServicesType();
            applicationSupportServicesType.setId(serviceId);
            applicationPublication.setApplicationSupportServicesType(applicationSupportServicesType);
        }

        // set patent publication number same as application number
        if (ApplicationCategoryEnum.PATENT.name().equals(application.getCategory().getSaipCode())) {
            applicationPublication.setPublicationNumber(application.getApplicationNumber());
        }

        applicationPublicationRepository.save(applicationPublication);

        // set other categories publication id same as id of applicaton publication
        if (!ApplicationCategoryEnum.PATENT.name().equals(application.getCategory().getSaipCode())) {
            applicationPublicationRepository.updatePublicationNumber(applicationPublication.getId(), String.valueOf(applicationPublication.getId()));
        }
    }

    private LkApplicationStatus getApplicationStatus(String appStatusCode, Long categoryId) {
        return lkApplicationStatusRepository.findByCodeAndApplicationCategoryId(appStatusCode, categoryId)
                .orElseThrow(() -> new RuntimeException("Application status not found"));
    }
    
    private LKPublicationType getPublicationType(String publicationType, Long categoryId) {
        return lkPublicationTypeRepository.findByCodeAndApplicationCategoryId(publicationType, categoryId)
                .orElseThrow(() -> new RuntimeException("Publication type not found"));
    }
    
    private Document getDocument(Long applicationId, String documentType) {
        if (documentType != null) {
            DocumentDto documentDto = documentsService.findLatestDocumentByApplicationIdAndDocumentType(applicationId, documentType);
            if (documentDto != null) {
                Document document = new Document();
                document.setId(documentDto.getId());
                return document;
            }
        }
        return null;
    }
    
    private ApplicationPublication applicationPublicationBuilder(ApplicationInfo application, LKPublicationType lkPublicationType, Document document) {
        String publicationDateHijri = Utilities.convertDateFromGregorianToHijri(LocalDateTime.now(clock).toLocalDate());
        return ApplicationPublication.builder()
                .applicationInfo(application)
                .publicationType(lkPublicationType)
                .publicationDate(LocalDateTime.now(clock))
                .publicationDateHijri(publicationDateHijri)
                .document(document)
                .isPublished(false)
                .build();
    }
    
    @Override
    public Optional<LocalDate> findApplicationPublicationDateByAppId(Long appId) {
        return applicationPublicationRepository.findApplicationPublicationDateByAppId(appId);
    }

    @Override
    public ApplicationPublicationSummaryProjection getPublicationSummary(Long applicationId, String publicationType) {
        return applicationPublicationRepository.getApplicationPublicationSummaryProjection(applicationId, publicationType);
    }

    @Override
    public String findApplicationPublicationDateByAppIdAndPublicationType(Long appId, Long serviceId, String publicationCode) {
        return applicationPublicationRepository.findApplicationPublicationDateByAppIdAndSupportServiceIdAndType(appId,serviceId,publicationCode);
    }

    @Override
    public Optional<LocalDate> findApplicationPublicationDateByAppIdAndType(Long appId, String publicationType) {
        return applicationPublicationRepository.findApplicationPublicationDateByAppIdAndType(appId,publicationType);
    }
}
