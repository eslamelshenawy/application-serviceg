package gov.saip.applicationservice.common.service.patent.impl;


import gov.saip.applicationservice.common.dto.patent.PatentApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.dto.patent.PatentApplicationInfoXmlDto;
import gov.saip.applicationservice.common.repository.patent.PatentDetailsRepository;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.generics.ApplicationInfoGenericService;
import gov.saip.applicationservice.util.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class PatentDetailsServiceImplTest {

    @InjectMocks
    @Spy
    private PatentDetailsServiceImpl patentDetailsService;
    @Mock
    private PatentDetailsRepository patentDetailsRepository;
    @Autowired
    @Spy
    private ApplicationInfoGenericService applicationInfoGenericService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private PatentApplicationInfoXmlDto convertToApplicationInfoXmlDto(ByteArrayResource file) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PatentApplicationInfoXmlDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (PatentApplicationInfoXmlDto) jaxbUnmarshaller
                .unmarshal(file.getInputStream());
    }

    @Test
    public void testGetApplicationInfoXml() throws JAXBException, IOException {
        // Arrange
        Long applicationId = 1L;
        PatentApplicationInfoXmlDataDto dataDto = PatentApplicationInfoXmlDataDto.builder()
                .email("mahmoud.aboelsoud@wscdev.net")
                .ipdSummaryEn("Good patent")
                .build();
        PatentApplicationInfoXmlDto expectedDto = PatentApplicationInfoXmlDto.builder()
                .patentApplicationInfoXmlDataDtoList(Collections.singletonList(dataDto))
                .build();
        when(patentDetailsRepository.getApplicationInfoXmlDataDto(applicationId))
                .thenReturn(Optional.ofNullable(dataDto));
        // Act
        ByteArrayResource file = patentDetailsService.getApplicationInfoXml(applicationId);
        PatentApplicationInfoXmlDto actualDto = convertToApplicationInfoXmlDto(file);
        // Assert
        Assertions.assertThat(file).isNotNull();
        Assertions.assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    public void testGetApplicationInfoXml_applicationIdNotFound() {
        // Arrange
        Long applicationId = 1L;
        when(patentDetailsRepository.getApplicationInfoXmlDataDto(applicationId)).thenReturn(Optional.empty());
        // Act & Assert
        BusinessException ex = assertThrows(BusinessException.class,
                () -> patentDetailsService.getApplicationInfoXml(applicationId));
        Assertions.assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND);
    }

}