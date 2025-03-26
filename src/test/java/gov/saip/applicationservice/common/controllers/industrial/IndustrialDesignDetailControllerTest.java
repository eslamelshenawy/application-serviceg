package gov.saip.applicationservice.common.controllers.industrial;

import gov.saip.applicationservice.common.service.industrial.impl.IndustrialDesignDetailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IndustrialDesignDetailControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IndustrialDesignDetailServiceImpl industrialDesignDetailService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new IndustrialDesignDetailController(industrialDesignDetailService))
                .build();
    }


    @Test
    public void testGetApplicationInfoXml() throws Exception {
        // Arrange
        Long applicationId = 1L;
        ByteArrayResource file = new ByteArrayResource(new byte[]{1, 2, 3});
        when(industrialDesignDetailService.getApplicationInfoXml(applicationId)).thenReturn(file);
        // Act & Assert
        mockMvc.perform(get("/kc/industrial-Design-detail/application-info/{applicationId}/xml-file", applicationId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=IndustrialDesigns.xml"))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_OCTET_STREAM.toString()))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_LENGTH,
                        String.valueOf(file.contentLength())))
                .andExpect(content().bytes(file.getByteArray()));
    }


}