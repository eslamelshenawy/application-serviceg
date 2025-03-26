package gov.saip.applicationservice.common.controllers.industrial;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.controllers.industrial.LookupController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignLookupResDto;
import gov.saip.applicationservice.common.dto.industrial.LkShapeDto;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignLookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LookupControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IndustrialDesignLookupService industrialDesignLookupService;

    private LookupController lookupController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        lookupController = new LookupController(industrialDesignLookupService);
        mockMvc = MockMvcBuilders.standaloneSetup(lookupController).build();
    }

    @Test
    public void testGetIndustrialDesignLookup() throws Exception {
        IndustrialDesignLookupResDto lookupData = new IndustrialDesignLookupResDto();
        lookupData.setShapes(List.of(new LkShapeDto()));
        when(industrialDesignLookupService.getIndustrialDesignLookup()).thenReturn(lookupData);

        mockMvc.perform(get("/internal-calling/lookup"))
                .andExpect(status().isOk());
    }
}
