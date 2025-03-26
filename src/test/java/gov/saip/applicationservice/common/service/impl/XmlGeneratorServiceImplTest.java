package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.service.XmlGeneratorService;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.common.service.patent.PatentDetailsService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class XmlGeneratorServiceImplTest {

    @Mock
    private TrademarkDetailService trademarkDetailService;

    @Mock
    private PatentDetailsService patentDetailsService;

    @Mock
    private IndustrialDesignDetailService industrialDesignDetailService;

    @InjectMocks
    private XmlGeneratorServiceImpl xmlGeneratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateUploadSaveXmlForApplication_Patent() {
        Long applicationId = 1L;
        String saipCode = ApplicationCategoryEnum.PATENT.name();
        String documentType = "DocumentType";

        xmlGeneratorService.generateUploadSaveXmlForApplication(applicationId, saipCode, documentType);

        verify(patentDetailsService, times(1)).generateUploadSaveXmlForApplication(applicationId, documentType);
        verify(trademarkDetailService, never()).generateUploadSaveXmlForApplication(anyLong(), anyString());
        verify(industrialDesignDetailService, never()).generateUploadSaveXmlForApplication(anyLong(), anyString());
    }

    @Test
    void testGenerateUploadSaveXmlForApplication_Trademark() {
        Long applicationId = 2L;
        String saipCode = ApplicationCategoryEnum.TRADEMARK.name();
        String documentType = "DocumentType";

        xmlGeneratorService.generateUploadSaveXmlForApplication(applicationId, saipCode, documentType);

        verify(trademarkDetailService, times(1)).generateUploadSaveXmlForApplication(applicationId, documentType);
        verify(patentDetailsService, never()).generateUploadSaveXmlForApplication(anyLong(), anyString());
        verify(industrialDesignDetailService, never()).generateUploadSaveXmlForApplication(anyLong(), anyString());
    }

    @Test
    void testGenerateUploadSaveXmlForApplication_IndustrialDesign() {
        Long applicationId = 3L;
        String saipCode = ApplicationCategoryEnum.INDUSTRIAL_DESIGN.name();
        String documentType = "DocumentType";

        xmlGeneratorService.generateUploadSaveXmlForApplication(applicationId, saipCode, documentType);

        verify(industrialDesignDetailService, times(1)).generateUploadSaveXmlForApplication(applicationId, documentType);
        verify(patentDetailsService, never()).generateUploadSaveXmlForApplication(anyLong(), anyString());
        verify(trademarkDetailService, never()).generateUploadSaveXmlForApplication(anyLong(), anyString());
    }

}

