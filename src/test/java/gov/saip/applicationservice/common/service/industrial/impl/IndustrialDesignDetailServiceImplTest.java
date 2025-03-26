package gov.saip.applicationservice.common.service.industrial.impl;


import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignApplicationInfoXmlDto;
import gov.saip.applicationservice.common.repository.industrial.IndustrialDesignDetailsRepository;
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

public class IndustrialDesignDetailServiceImplTest {

    @InjectMocks
    @Spy
    private IndustrialDesignDetailServiceImpl industrialDesignDetailService;
    @Mock
    private IndustrialDesignDetailsRepository industrialDesignDetailsRepository;
    @Autowired
    @Spy
    private ApplicationInfoGenericService applicationInfoGenericService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private IndustrialDesignApplicationInfoXmlDto convertToApplicationInfoXmlDto(ByteArrayResource file)
            throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(IndustrialDesignApplicationInfoXmlDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (IndustrialDesignApplicationInfoXmlDto) jaxbUnmarshaller
                .unmarshal(file.getInputStream());
    }

    @Test
    public void testGetApplicationInfoXml() throws JAXBException, IOException {
        // Arrange
        Long applicationId = 1L;
        IndustrialDesignApplicationInfoXmlDataDto dataDto = IndustrialDesignApplicationInfoXmlDataDto.builder()
                .email("mahmoud.aboelsoud@wscdev.net")
                .explanationEn("Good explanation ...")
                .build();
        IndustrialDesignApplicationInfoXmlDto expectedDto = IndustrialDesignApplicationInfoXmlDto.builder()
                .industrialDesignApplicationInfoXmlDataDtoList(Collections.singletonList(dataDto))
                .build();
        when(industrialDesignDetailsRepository.getApplicationInfoXmlDataDto(applicationId))
                .thenReturn(Optional.ofNullable(dataDto));
        // Act
        ByteArrayResource file = industrialDesignDetailService.getApplicationInfoXml(applicationId);
        IndustrialDesignApplicationInfoXmlDto actualDto = convertToApplicationInfoXmlDto(file);
        // Assert
        Assertions.assertThat(file).isNotNull();
        Assertions.assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    public void testGetApplicationInfoXml_applicationIdNotFound() {
        // Arrange
        Long applicationId = 1L;
        when(industrialDesignDetailsRepository.getApplicationInfoXmlDataDto(applicationId))
                .thenReturn(Optional.empty());
        // Act & Assert
        BusinessException ex = assertThrows(BusinessException.class,
                () -> industrialDesignDetailService.getApplicationInfoXml(applicationId));
        Assertions.assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND);
    }

}