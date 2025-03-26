package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignApplicationInfoXmlDto;
import gov.saip.applicationservice.common.dto.patent.PatentApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.dto.patent.PatentApplicationInfoXmlDto;
import gov.saip.applicationservice.common.dto.trademark.TrademarkApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.dto.trademark.TrademarkApplicationInfoXmlDto;
import gov.saip.applicationservice.common.model.PublicationIssue;
import gov.saip.applicationservice.common.repository.PublicationIssueRepository;
import gov.saip.applicationservice.common.service.impl.PublicationIssueServiceImpl;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.generics.ApplicationInfoGenericService;
import gov.saip.applicationservice.util.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PublicationIssueServiceUnitTest {

    @InjectMocks
    @Spy
    private PublicationIssueServiceImpl publicationIssueService;

    @Mock
    private PublicationIssueRepository publicationIssueRepository;
    @Autowired
    @Spy
    private ApplicationInfoGenericService applicationInfoGenericService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateIssueExistByIssueId() {
        // Arrange
        Long issueId = 1L;
        PublicationIssue expectedPublicationIssue = new PublicationIssue();
        expectedPublicationIssue.setIssueNumber(issueId);
        when(publicationIssueRepository.findById(issueId)).thenReturn(Optional.of(expectedPublicationIssue));
        // Act
        PublicationIssue actualPublicationIssue = publicationIssueService.validateIssueExistByIssueId(issueId);
        // Assert
        Assertions.assertThat(expectedPublicationIssue).isNotNull();
        Assertions.assertThat(actualPublicationIssue.getIssueNumber())
                .isEqualTo(expectedPublicationIssue.getIssueNumber());
    }

    @Test
    public void testValidateIssueExistByIssueId_issueIdNotFound() {
        // Arrange
        Long issueId = 1L;
        when(publicationIssueRepository.findById(issueId)).thenReturn(Optional.empty());
        // Act & Assert
        BusinessException ex = assertThrows(BusinessException.class,
                () -> publicationIssueService.validateIssueExistByIssueId(issueId));
        Assertions.assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND);
    }

    private TrademarkApplicationInfoXmlDto convertToTrademarkApplicationInfoXmlDto(ByteArrayResource file)
            throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TrademarkApplicationInfoXmlDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (TrademarkApplicationInfoXmlDto) jaxbUnmarshaller.unmarshal(file.getInputStream());
    }

    @Test
    public void testGetTrademarkApplicationsInfoXmlByIssueId() throws JAXBException, IOException {
        // Arrange
        Long issueId = 1L;
        TrademarkApplicationInfoXmlDataDto expectedDto1 = TrademarkApplicationInfoXmlDataDto.builder()
                .applicationId(1L)
                .email("mahmoud.aboelsoud@wscdev.net")
                .build();
        TrademarkApplicationInfoXmlDataDto expectedDto2 = TrademarkApplicationInfoXmlDataDto.builder()
                .applicationId(2L)
                .email("mahmoud.aboelsoud.2@wscdev.net")
                .build();
        TrademarkApplicationInfoXmlDto expectedResult = TrademarkApplicationInfoXmlDto.builder()
                .trademarkApplicationInfoXmlDataDtoList(Arrays.asList(expectedDto1, expectedDto2))
                .build();
        when(publicationIssueRepository.findById(issueId)).thenReturn(Optional.of(Mockito.mock(PublicationIssue.class)));
        when(publicationIssueRepository.getTrademarkApplicationsInfoXmlDtoByIssueId(issueId))
                .thenReturn(expectedResult.getTrademarkApplicationInfoXmlDataDtoList());
        // Act
        ByteArrayResource file = publicationIssueService.getTrademarkApplicationsInfoXmlByIssueId(issueId);
        TrademarkApplicationInfoXmlDto actualResult = convertToTrademarkApplicationInfoXmlDto(file);
        // Assert
        Assertions.assertThat(file).isNotNull();
        Assertions.assertThat(actualResult.getTrademarkApplicationInfoXmlDataDtoList().size())
                .isEqualTo(expectedResult.getTrademarkApplicationInfoXmlDataDtoList().size());
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }

    private PatentApplicationInfoXmlDto convertToPatentApplicationInfoXmlDto(ByteArrayResource file)
            throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PatentApplicationInfoXmlDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (PatentApplicationInfoXmlDto) jaxbUnmarshaller.unmarshal(file.getInputStream());
    }

    @Test
    public void testGetTrademarkApplicationsInfoXmlDtoByIssueId() throws JAXBException, IOException {
        // Arrange
        Long issueId = 1L;
        PatentApplicationInfoXmlDataDto expectedDto1 = PatentApplicationInfoXmlDataDto.builder()
                .applicationId(1L)
                .email("mahmoud.aboelsoud@wscdev.net")
                .build();
        PatentApplicationInfoXmlDataDto expectedDto2 = PatentApplicationInfoXmlDataDto.builder()
                .applicationId(2L)
                .email("mahmoud.aboelsoud.2@wscdev.net")
                .build();
        PatentApplicationInfoXmlDto expectedResult = PatentApplicationInfoXmlDto.builder()
                .patentApplicationInfoXmlDataDtoList(Arrays.asList(expectedDto1, expectedDto2))
                .build();
        when(publicationIssueRepository.findById(issueId)).thenReturn(Optional.of(Mockito.mock(PublicationIssue.class)));
        when(publicationIssueRepository.getPatentApplicationsInfoXmlDtoByIssueId(issueId))
                .thenReturn(expectedResult.getPatentApplicationInfoXmlDataDtoList());
        // Act
        ByteArrayResource file = publicationIssueService.getPatentApplicationsInfoXmlByIssueId(issueId);
        PatentApplicationInfoXmlDto actualResult = convertToPatentApplicationInfoXmlDto(file);
        // Assert
        Assertions.assertThat(file).isNotNull();
        Assertions.assertThat(actualResult.getPatentApplicationInfoXmlDataDtoList().size())
                .isEqualTo(expectedResult.getPatentApplicationInfoXmlDataDtoList().size());
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }

    private IndustrialDesignApplicationInfoXmlDto convertToIndustrialDesignApplicationInfoXmlDto(ByteArrayResource file)
            throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(IndustrialDesignApplicationInfoXmlDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (IndustrialDesignApplicationInfoXmlDto) jaxbUnmarshaller.unmarshal(file.getInputStream());
    }

    @Test
    public void testGetIndustrialDesignApplicationsInfoXmlByIssueId() throws JAXBException, IOException {
        // Arrange
        Long issueId = 1L;
        IndustrialDesignApplicationInfoXmlDataDto expectedDto1 = IndustrialDesignApplicationInfoXmlDataDto.builder()
                .applicationId(1L)
                .email("mahmoud.aboelsoud@wscdev.net")
                .build();
        IndustrialDesignApplicationInfoXmlDataDto expectedDto2 = IndustrialDesignApplicationInfoXmlDataDto.builder()
                .applicationId(2L)
                .email("mahmoud.aboelsoud.2@wscdev.net")
                .build();
        IndustrialDesignApplicationInfoXmlDto expectedResult = IndustrialDesignApplicationInfoXmlDto.builder()
                .industrialDesignApplicationInfoXmlDataDtoList(Arrays.asList(expectedDto1, expectedDto2))
                .build();
        when(publicationIssueRepository.findById(issueId)).thenReturn(Optional.of(Mockito.mock(PublicationIssue.class)));
        when(publicationIssueRepository.getIndustrialDesignApplicationsInfoXmlDtoByIssueId(issueId))
                .thenReturn(expectedResult.getIndustrialDesignApplicationInfoXmlDataDtoList());
        // Act
        ByteArrayResource file = publicationIssueService.getIndustrialDesignApplicationsInfoXmlByIssueId(issueId);
        IndustrialDesignApplicationInfoXmlDto actualResult = convertToIndustrialDesignApplicationInfoXmlDto(file);
        // Assert
        Assertions.assertThat(file).isNotNull();
        Assertions.assertThat(actualResult.getIndustrialDesignApplicationInfoXmlDataDtoList().size())
                .isEqualTo(expectedResult.getIndustrialDesignApplicationInfoXmlDataDtoList().size());
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }

}