package gov.saip.applicationservice.common.controllers.industrial;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignLookupResDto;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignLookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class IndustrialDesignLookupControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private IndustrialDesignLookupService industrialDesignLookupService;

    @InjectMocks
    private IndustrialDesignLookupController industrialDesignLookupController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(industrialDesignLookupController)
                .build();
    }

    @Test
    public void testGetIndustrialDesignLookup() throws Exception {
        IndustrialDesignLookupResDto expectedDto = new IndustrialDesignLookupResDto(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/industrial/lookup")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
