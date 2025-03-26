package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.ApplicationDocumentLightDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.PublicationProjection;
import gov.saip.applicationservice.common.dto.SortRequestDto;
import gov.saip.applicationservice.common.dto.publication.*;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ApplicationPublicationRepository;
import gov.saip.applicationservice.common.repository.PublicationIssueApplicationRepository;
import gov.saip.applicationservice.common.repository.lookup.LKPublicationTypeRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationStatusRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PublicationServiceImplTest {

    @InjectMocks
    @Spy
    private PublicationServiceImpl publicationServiceImpl;
    @Mock
    private LkApplicationStatusRepository lkApplicationStatusRepository;
    @Mock
    private LKPublicationTypeRepository lkPublicationTypeRepository;
    @Mock
    private ApplicationInfoRepository applicationInfoRepository;
    @Mock
    private ApplicationInfoMapper applicationInfoMapper;
    @Mock
    private DocumentsService documentsService;
    @Mock
    private ApplicationInfoService applicationInfoService;
    @Mock
    private PublicationIssueApplicationRepository publicationIssueApplicationRepository;
    @Mock
    private ApplicationPublicationRepository applicationPublicationRepository;
    @Mock
    private Clock clock;
    @Mock
    private CustomerServiceCaller customerServiceCaller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPatentPublicationsBatches_ValidInputs_ReturnsPaginationDto() {
        // Mock input data
        int page = 0;
        int limit = 10;
        String fromDate = "2023-01-01";
        String toDate = "2023-12-31";

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.getPatentPublicationsBatches(anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(), anyList(), any(Pageable.class))).thenReturn(mockedPage);

        // Call the method under test
        PaginationDto result = publicationServiceImpl.getPatentPublicationsBatches(page, limit, fromDate, toDate);

        // Verify that the repository method was called with the correct arguments
        verify(applicationInfoRepository, times(1)).getPatentPublicationsBatches(eq(RequestTypeEnum.PATENT.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(AWAITING_VERIFICATION.name()),
                eq(PUBLISHED_ELECTRONICALLY.name()), eq(AWAITING_FOR_UPDATE_XML.name()), eq(fromDate), eq(toDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable));

        // Verify that the result is not null
        assertNotNull(result);

    }


    @Test
    void getTradeMarkPublicationsBatches_ValidInputs_ReturnsPaginationDto() {
        // Mock input data
        int page = 0;
        int limit = 10;
        String fromDate = "2023-01-01";
        String toDate = "2023-12-31";

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.getTrademarkPublicationsBatches(anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(), anyList(), any(Pageable.class))).thenReturn(mockedPage);

        // Call the method under test
        PaginationDto result = publicationServiceImpl.getTrademarkPublicationsBatches(page, limit, fromDate, toDate);

        // Verify that the repository method was called with the correct arguments
        verify(applicationInfoRepository, times(1)).getTrademarkPublicationsBatches(eq(RequestTypeEnum.TRADEMARK.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(AWAITING_VERIFICATION.name()),
                eq(PUBLISHED_ELECTRONICALLY.name()), eq(AWAITING_FOR_UPDATE_XML.name()), eq(fromDate), eq(toDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable));

        // Verify that the result is not null
        assertNotNull(result);

    }


    @Test
    void getIndustrialPublicationsBatches_ValidInputs_ReturnsPaginationDto() {
        // Mock input data
        int page = 0;
        int limit = 10;
        String fromDate = "2023-01-01";
        String toDate = "2023-12-31";

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.getIndustrialPublicationsBatches(anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(), anyList(), any(Pageable.class))).thenReturn(mockedPage);

        // Call the method under test
        PaginationDto result = publicationServiceImpl.getIndustrialPublicationsBatches(page, limit, fromDate, toDate);

        // Verify that the repository method was called with the correct arguments
        verify(applicationInfoRepository, times(1)).getIndustrialPublicationsBatches(eq(RequestTypeEnum.INDUSTRIAL_DESIGN.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(AWAITING_VERIFICATION.name()),
                eq(PUBLISHED_ELECTRONICALLY.name()), eq(AWAITING_FOR_UPDATE_XML.name()), eq(fromDate), eq(toDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable));

        // Verify that the result is not null
        assertNotNull(result);

    }

    @Test
    void viewTrademarkPublications_ValidInputs_ReturnsPaginationDto() {
        // Mock input data
        int page = 0;
        int limit = 10;
        String receptionDate = "2023-01-01";

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationBatchViewProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.viewPublications(eq(RequestTypeEnum.TRADEMARK.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable))).thenReturn(mockedPage);

        // Mock mapper behavior
        List<TrademarkPublicationVerificationBatchViewDto> mockedDtos = new ArrayList<>();
        when(applicationInfoMapper.mapTrademarkPublicationBatchView(anyList())).thenReturn(mockedDtos);

        // Mock documentsService behavior
        List<Long> appIds = new ArrayList<>();
        List<ApplicationDocumentLightDto> applicationDocumentLightDtos = new ArrayList<>();
        when(documentsService.getDocumentsByAppIdsAndType(anyList(), anyString()))
                .thenReturn(applicationDocumentLightDtos);

        // Call the method under test
        PaginationDto result = publicationServiceImpl.viewTrademarkPublications(page, limit, receptionDate);

        // Verify that the repository method was called with the correct arguments
        verify(applicationInfoRepository, times(1)).viewPublications(eq(RequestTypeEnum.TRADEMARK.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable));

        // Verify that the mapper method was called with the correct argument
        verify(applicationInfoMapper, times(1)).mapTrademarkPublicationBatchView(eq(mockedPage.getContent()));

        // Verify that the result is not null
        assertNotNull(result);

    }

    @Test
    void viewTrademarkPublications_ValidInputs_ReturnsPaginationDto_trademarkPublicationsBatchViewDtosNotEmpty() {
        // Mock input data
        int page = 0;
        int limit = 10;
        String receptionDate = "2023-01-01";

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationBatchViewProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.viewPublications(eq(RequestTypeEnum.TRADEMARK.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable))).thenReturn(mockedPage);

        // Mock mapper behavior
        List<TrademarkPublicationVerificationBatchViewDto> mockedDtos = new ArrayList<>();
        mockedDtos.add(new TrademarkPublicationVerificationBatchViewDto());
        when(applicationInfoMapper.mapTrademarkPublicationBatchView(anyList())).thenReturn(mockedDtos);

        // Mock documentsService behavior
        List<Long> appIds = new ArrayList<>();
        List<ApplicationDocumentLightDto> applicationDocumentLightDtos = new ArrayList<>();
        when(documentsService.getDocumentsByAppIdsAndType(anyList(), anyString()))
                .thenReturn(applicationDocumentLightDtos);

        // Call the method under test
        PaginationDto result = publicationServiceImpl.viewTrademarkPublications(page, limit, receptionDate);

        // Verify that the repository method was called with the correct arguments
        verify(applicationInfoRepository, times(1)).viewPublications(eq(RequestTypeEnum.TRADEMARK.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable));

        // Verify that the mapper method was called with the correct argument
        verify(applicationInfoMapper, times(1)).mapTrademarkPublicationBatchView(eq(mockedPage.getContent()));

        // Verify that the result is not null
        assertNotNull(result);

    }


    @Test
    void viewPatentPublications_ValidInputs_ReturnsPaginationDto() {
        // Mock input data
        int page = 0;
        int limit = 10;
        String receptionDate = "2023-01-01";

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationBatchViewProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.viewPublications(eq(RequestTypeEnum.PATENT.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable))).thenReturn(mockedPage);

        // Mock mapper behavior
        List<PatentPublicationVerificationBatchViewDto> mockedDtos = new ArrayList<>();
        when(applicationInfoMapper.mapPatentPublicationBatchView(anyList())).thenReturn(mockedDtos);

        // Mock documentsService behavior
        List<Long> appIds = new ArrayList<>();
        List<ApplicationDocumentLightDto> applicationDocumentLightDtos = new ArrayList<>();
        when(documentsService.getDocumentsByAppIdsAndType(anyList(), anyString()))
                .thenReturn(applicationDocumentLightDtos);

        // Call the method under test
        PaginationDto result = publicationServiceImpl.viewPatentPublications(page, limit, receptionDate);

        // Verify that the repository method was called with the correct arguments
        verify(applicationInfoRepository, times(1)).viewPublications(eq(RequestTypeEnum.PATENT.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable));

        // Verify that the mapper method was called with the correct argument
        verify(applicationInfoMapper, times(1)).mapPatentPublicationBatchView(eq(mockedPage.getContent()));

        // Verify that the result is not null
        assertNotNull(result);

    }

    @Test
    void viewPatentPublications_ValidInputs_ReturnsPaginationDto_trademarkPublicationsBatchViewDtosNotEmpty() {
        // Mock input data
        int page = 0;
        int limit = 10;
        String receptionDate = "2023-01-01";

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationBatchViewProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.viewPublications(eq(RequestTypeEnum.PATENT.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable))).thenReturn(mockedPage);

        // Mock mapper behavior
        List<PatentPublicationVerificationBatchViewDto> mockedDtos = new ArrayList<>();
        mockedDtos.add(new PatentPublicationVerificationBatchViewDto());
        when(applicationInfoMapper.mapPatentPublicationBatchView(anyList())).thenReturn(mockedDtos);

        // Mock documentsService behavior
        List<Long> appIds = new ArrayList<>();
        List<ApplicationDocumentLightDto> applicationDocumentLightDtos = new ArrayList<>();
        when(documentsService.getDocumentsByAppIdsAndType(anyList(), anyString()))
                .thenReturn(applicationDocumentLightDtos);

        // Call the method under test
        PaginationDto result = publicationServiceImpl.viewPatentPublications(page, limit, receptionDate);

        // Verify that the repository method was called with the correct arguments
        verify(applicationInfoRepository, times(1)).viewPublications(eq(RequestTypeEnum.PATENT.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable));

        // Verify that the mapper method was called with the correct argument
        verify(applicationInfoMapper, times(1)).mapPatentPublicationBatchView(eq(mockedPage.getContent()));

        // Verify that the result is not null
        assertNotNull(result);

    }

    @Test
    void viewIndustrialPublications_ValidInputs_ReturnsPaginationDto() {
        // Mock input data
        int page = 0;
        int limit = 10;
        String receptionDate = "2023-01-01";

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationBatchViewProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.viewPublications(eq(RequestTypeEnum.INDUSTRIAL_DESIGN.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable))).thenReturn(mockedPage);

        // Mock mapper behavior
        List<IndustrialPublicationVerificationBatchViewDto> mockedDtos = new ArrayList<>();
        when(applicationInfoMapper.mapIndustrialPublicationBatchView(anyList())).thenReturn(mockedDtos);

        // Mock documentsService behavior
        List<Long> appIds = new ArrayList<>();
        List<ApplicationDocumentLightDto> applicationDocumentLightDtos = new ArrayList<>();
        when(documentsService.getDocumentsByAppIdsAndType(anyList(), anyString()))
                .thenReturn(applicationDocumentLightDtos);

        // Call the method under test
        PaginationDto result = publicationServiceImpl.viewIndustrialPublications(page, limit, receptionDate);

        // Verify that the repository method was called with the correct arguments
        verify(applicationInfoRepository, times(1)).viewPublications(eq(RequestTypeEnum.INDUSTRIAL_DESIGN.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable));

        // Verify that the mapper method was called with the correct argument
        verify(applicationInfoMapper, times(1)).mapIndustrialPublicationBatchView(eq(mockedPage.getContent()));

        // Verify that the result is not null
        assertNotNull(result);

    }

    @Test
    void viewIndustrialPublications_ValidInputs_ReturnsPaginationDto_trademarkPublicationsBatchViewDtosNotEmpty() {
        // Mock input data
        int page = 0;
        int limit = 10;
        String receptionDate = "2023-01-01";

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationBatchViewProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.viewPublications(eq(RequestTypeEnum.INDUSTRIAL_DESIGN.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable))).thenReturn(mockedPage);

        // Mock mapper behavior
        List<IndustrialPublicationVerificationBatchViewDto> mockedDtos = new ArrayList<>();
        mockedDtos.add(new IndustrialPublicationVerificationBatchViewDto());
        when(applicationInfoMapper.mapIndustrialPublicationBatchView(anyList())).thenReturn(mockedDtos);

        // Mock documentsService behavior
        List<Long> appIds = new ArrayList<>();
        List<ApplicationDocumentLightDto> applicationDocumentLightDtos = new ArrayList<>();
        when(documentsService.getDocumentsByAppIdsAndType(anyList(), anyString()))
                .thenReturn(applicationDocumentLightDtos);

        // Call the method under test
        PaginationDto result = publicationServiceImpl.viewIndustrialPublications(page, limit, receptionDate);

        // Verify that the repository method was called with the correct arguments
        verify(applicationInfoRepository, times(1)).viewPublications(eq(RequestTypeEnum.INDUSTRIAL_DESIGN.name()),
                eq(ApplicationRequestStatus.PAID.name()), anyString(), eq(receptionDate),
                eq(Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS), eq(pageable));

        // Verify that the mapper method was called with the correct argument
        verify(applicationInfoMapper, times(1)).mapIndustrialPublicationBatchView(eq(mockedPage.getContent()));

        // Verify that the result is not null
        assertNotNull(result);

    }

//    @Test
//    void listGazetteOrPublicationsForTrademark_ValidInputs_ReturnsPaginationDto() {
//        // Mock input data
//        int page = 0;
//        int limit = 10;
//        Long publicationIssueId = 1L;
//        String publicationTarget = "GAZETTE";
//        PublicPublicationSearchParamDto publicPublicationSearchParamDto = new PublicPublicationSearchParamDto();
//        SortRequestDto sortRequestDto = new SortRequestDto();
//
//        // Mock repository behavior
//        Pageable pageable = PageRequest.of(page, limit);
//        Page<PublicationBatchViewProjection> mockedPage = mock(Page.class);
//        when(applicationInfoRepository.listGazetteOrPublicationsForTrademark(anyString(),
//                anyLong(), any(ApplicationRelevantEnum.class), any(ApplicationAgentStatus.class),
//                anyInt(), any(LocalDate.class), any(LocalDate.class), any(LocalDate.class), any(LocalDate.class), anyString(), anyString(),
//                anyString(), anyList(),  anyList(), any(LocalDate.class), any(LocalDate.class), anyInt(), anyString(), anyLong(), anyString(), any(Pageable.class)))
//                .thenReturn(mockedPage);
//
//        // Mock mapper behavior
//        List<TrademarkGazettePublicationListDto> mockedDtos = new ArrayList<>();
//        when(applicationInfoMapper.mapTrademarkGazettePublicationBatchView(anyList())).thenReturn(mockedDtos);
//
//        // Call the method under test
//        PaginationDto result = publicationServiceImpl.listGazetteOrPublicationsForTrademark(page, limit, publicationIssueId,
//                publicationTarget, publicPublicationSearchParamDto, sortRequestDto);
//
//        // Verify that the result is not null
//        assertNotNull(result);
//
//    }

    @Test
    void listGazetteOrPublicationsForTrademark_ValidInputs_ReturnsPaginationDtoWithNoResult() {
        // Mock input data
        int page = 0;
        int limit = 10;
        Long publicationIssueId = 1L;
        String publicationTarget = "GAZETTE";
        PublicPublicationSearchParamDto publicPublicationSearchParamDto = new PublicPublicationSearchParamDto();
        publicPublicationSearchParamDto.setAgentName("name");
        publicPublicationSearchParamDto.setApplicantName("name");
        SortRequestDto sortRequestDto = new SortRequestDto();

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationBatchViewProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.listGazetteOrPublicationsForTrademark(anyString(),
                anyLong(), any(ApplicationRelevantEnum.class), any(ApplicationAgentStatus.class),
                anyInt(), any(LocalDate.class), any(LocalDate.class), any(LocalDate.class), any(LocalDate.class), anyString(), anyString(),
                anyString(), anyList(),  anyList(), any(LocalDate.class), any(LocalDate.class), anyInt(), anyString(), any(), anyString(), eq(false), any(Pageable.class)))
                .thenReturn(mockedPage);

        List<Long> ids = Arrays.asList(1L, 2L);
        List<String> codes = Arrays.asList("1L", "2L");
        when(customerServiceCaller.getCustomersIds(publicPublicationSearchParamDto.getAgentName())).thenReturn(ids);
        when(customerServiceCaller.getCustomersCodes(publicPublicationSearchParamDto.getApplicantName())).thenReturn(codes);

        List<TrademarkGazettePublicationListDto> mockedDtos = new ArrayList<>();
        when(applicationInfoMapper.mapTrademarkGazettePublicationBatchView(anyList())).thenReturn(mockedDtos);


        PaginationDto result = publicationServiceImpl.listGazetteOrPublicationsForTrademark(page, limit, publicationIssueId,
                publicationTarget, publicPublicationSearchParamDto, sortRequestDto);

        // Verify that the result is not null
        assertNotNull(result);

    }



    @Test
    void listGazetteOrPublicationsForPatent_ValidInputs_ReturnsPaginationDto() {
        // Mock input data
        int page = 0;
        int limit = 10;
        Long publicationIssueId = 1L;
        String publicationTarget = "GAZETTE";
        PublicPublicationSearchParamDto publicPublicationSearchParamDto = new PublicPublicationSearchParamDto();
        SortRequestDto sortRequestDto = new SortRequestDto();

        // Mock repository behavior
        Pageable pageable = PageRequest.of(page, limit);
        Page<PublicationBatchViewProjection> mockedPage = mock(Page.class);
        when(applicationInfoRepository.listGazetteOrPublicationsForPatent(
                anyString(), anyLong(), any(ApplicationCustomerType.class), anyInt(),
                any(LocalDate.class), any(LocalDate.class), any(LocalDate.class),
                any(LocalDate.class), anyString(), anyString(), anyString(),
                anyList(), anyList(), any(LocalDate.class), any(LocalDate.class),anyLong(), anyString(), anyBoolean(), any(Pageable.class)))
                .thenReturn(mockedPage);



        // Mock mapper behavior
        List<PatentPublicationGazetteBatchViewDto> mockedDtos = new ArrayList<>();
        when(applicationInfoMapper.mapPatentPublicationIssueAppBatchView(anyList())).thenReturn(mockedDtos);

        // Call the method under test
        PaginationDto result = publicationServiceImpl.listGazetteOrPublicationsForPatent(page, limit, publicationIssueId,
                publicationTarget, publicPublicationSearchParamDto, sortRequestDto);

        // Verify that the result is not null
        assertNotNull(result);

    }

}