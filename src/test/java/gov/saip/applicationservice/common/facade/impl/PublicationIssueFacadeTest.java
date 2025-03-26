package gov.saip.applicationservice.common.facade.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerConfigParameterClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ConfigParameterDto;
import gov.saip.applicationservice.common.enums.CustomerConfigParameterEnum;
import gov.saip.applicationservice.common.facade.publication.PublicationIssueFacade;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.lookup.LKPublicationTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Clock;

import static gov.saip.applicationservice.common.enums.LicenceTypeEnum.CANCEL_LICENCE;
import static gov.saip.applicationservice.common.enums.SupportServiceType.LICENSING_REGISTRATION;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
public class PublicationIssueFacadeTest {

    @InjectMocks
    private PublicationIssueFacade publicationIssueFacade;

    @Mock
    private PublicationIssueNumberService publicationIssueNumberService;

    @Mock
    private PublicationSchedulingConfigService publicationSchedulingConfigService;

    @Mock
    private PublicationIssueService publicationIssueService;

    @Mock
    private CustomerConfigParameterClient customerConfigParameterClient;

    @Mock
    private ApplicationInfoService applicationInfoService;

    @Mock
    private Clock clock;

    @Mock
    private CertificateRequestService certificateRequestService;

    @Mock
    private ApplicationPublicationService applicationPublicationService;

    @Mock
    private ApplicationSupportServicesTypeService applicationSupportServicesTypeService;

    @Mock
    private LicenceRequestService licenceRequestService;

    @Mock
    private XmlGeneratorService xmlGeneratorService;

    @Mock
    private LKPublicationTypeService publicationTypeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        publicationIssueFacade.setPublicationIssueService(publicationIssueService);
    }

    @DisplayName("test create add application")
    @Test
    public void testCreateAndAddApplicationPublicationToLatestIssueOrCreateNewIssue() {

        // Arrange
        Long id = 862815L;
        ApplicationSupportServicesType applicationSupportServicesType = new ApplicationSupportServicesType();
        applicationSupportServicesType.setId(id);
        LKSupportServices supportServices = new LKSupportServices();
        supportServices.setCode(LICENSING_REGISTRATION);
        applicationSupportServicesType.setLkSupportServices(supportServices);
        when(applicationSupportServicesTypeService.findById(id)).thenReturn(applicationSupportServicesType);

        when(licenceRequestService.getLicensingRequestType(any())).thenReturn(CANCEL_LICENCE.name());


        Long appId = 688654L;
        String saipCode = "TRADEMARK";
        String documentType = "LICENSING_APPLICATION_XML";
        doNothing().when(xmlGeneratorService).generateUploadSaveXmlForApplication(appId, saipCode, documentType);

        ApplicationInfo application = new ApplicationInfo();
        application.setId(appId);
        LkApplicationCategory category = new LkApplicationCategory();
        category.setSaipCode(saipCode);
        application.setCategory(category);
        applicationSupportServicesType.setApplicationInfo(application);
        LkApplicationStatus applicationStatus = new LkApplicationStatus();
        applicationStatus.setCode("ACCEPTANCE");
        application.setApplicationStatus(applicationStatus);

        ApiResponse<ConfigParameterDto> response = new ApiResponse<ConfigParameterDto>();
        ConfigParameterDto payload = ConfigParameterDto.builder().build();
        payload.setId(123L);
        payload.setValue("123");
        payload.setName("123");
        response.setPayload(payload);

        when(customerConfigParameterClient.getConfig(CustomerConfigParameterEnum.PUBLICATION_ISSUE_CUTOFF.name())).thenReturn(response);

        CertificateRequest certificateRequest = new CertificateRequest();
        certificateRequest.setId(123L);
        when(certificateRequestService.prepareAndSaveCertificateRequest(appId, "ISSUE_CERTIFICATE")).thenReturn(certificateRequest);
        doNothing().when(certificateRequestService).processCertificateRequest(anyLong(), anyString());
        when(publicationIssueNumberService.calculateNextIssueNumber(any())).thenReturn(id);
//        publicationIssueFacade.publishSupportServiceApplicationInNextIssue(id);


    }


}
