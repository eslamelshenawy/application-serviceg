package gov.saip.applicationservice.common.controllers.industrial;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.industrial.CreateDesignSampleDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailDto;
import gov.saip.applicationservice.common.service.industrial.DesignSampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

public class DesignSampleControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private DesignSampleService designSampleService;

    @InjectMocks
    private DesignSampleController designSampleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(designSampleController)
                .build();
    }

    @Test
    public void testCreateDesignSamples() throws Exception {
        CreateDesignSampleDto createDto = new CreateDesignSampleDto();

        IndustrialDesignDetailDto industrialDesignDetailDto = new IndustrialDesignDetailDto();

        when(designSampleService.create(createDto)).thenReturn(industrialDesignDetailDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/kc/design-samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCountDesignSamples() throws Exception {
        String designId = "1";
        Long count = 5L;

        when(designSampleService.count(Long.valueOf(designId))).thenReturn(count);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/design-samples/{designId}", designId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
