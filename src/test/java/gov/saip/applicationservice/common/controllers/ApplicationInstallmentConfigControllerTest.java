package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApplicationInstallmentConfigDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.mapper.ApplicationInstallmentConfigMapper;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ApplicationInstallmentConfigControllerTest {

    @Mock
    private ApplicationInstallmentConfigService configService;

    @Mock
    private ApplicationInstallmentConfigMapper configMapper;

    @InjectMocks
    private ApplicationInstallmentConfigController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findByApplicationCategory() throws Exception {
        String category = "PATENT";
        ApplicationInstallmentConfig config = new ApplicationInstallmentConfig();
        config.setId(1L);
        config.setApplicationCategory(ApplicationCategoryEnum.PATENT);

        ApplicationInstallmentConfigDto configDto = new ApplicationInstallmentConfigDto();
        configDto.setId(1L);
        config.setApplicationCategory(ApplicationCategoryEnum.PATENT);

        when(configService.findByApplicationCategory(category)).thenReturn(config);
        when(configMapper.map(config)).thenReturn(configDto);

        mockMvc.perform(get("/kc/installment-config/category")
                        .param("category", category)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").exists())
                .andExpect(jsonPath("$.payload.id").value(1L));

        verify(configService, times(1)).findByApplicationCategory(category);
        verify(configMapper, times(1)).map(config);
    }

}

