package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.common.service.patent.PatentDetailsService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class XmlGeneratorControllerTest {

    @Mock
    private TrademarkDetailService trademarkDetailService;

    @Mock
    private PatentDetailsService patentDetailsService;

    @Mock
    private IndustrialDesignDetailService industrialDesignDetailService;

    @InjectMocks
    private XmlGeneratorController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGenerateUploadSaveXmlForTrademarkApplication() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("documentType", "TRADEMARK");
        doNothing().when(trademarkDetailService).generateUploadSaveXmlForApplication(anyLong(), anyString());

        mockMvc.perform(post("/internal-calling/application-info/{applicationId}/TRADEMARK/generate-upload-save-xml", 123)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS"));
    }

    @Test
    void testGenerateUploadSaveXmlForPatentApplication() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("documentType", "PATENT");
        doNothing().when(patentDetailsService).generateUploadSaveXmlForApplication(anyLong(), anyString());

        mockMvc.perform(post("/internal-calling/application-info/{applicationId}/PATENT/generate-upload-save-xml", 456)
                        .contentType(MediaType.APPLICATION_JSON)
                .params(params))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS"));
    }

    @Test
    void testGenerateUploadSaveXmlForIndustrialApplication() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("documentType", "INDUSTRIAL_DESIGN");
        doNothing().when(industrialDesignDetailService).generateUploadSaveXmlForApplication(anyLong(), anyString());

        mockMvc.perform(post("/internal-calling/application-info/{applicationId}/INDUSTRIAL_DESIGN/generate-upload-save-xml", 789)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS"));
    }
}

