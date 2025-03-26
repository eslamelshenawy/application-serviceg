package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApplicationSearchDto;
import gov.saip.applicationservice.common.mapper.ApplicationSearchMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.service.ApplicationSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ApplicationSearchControllerTest {

    @Mock
    private ApplicationSearchService applicationSearchService;

    @Mock
    private ApplicationSearchMapper applicationSearchMapper;

    @InjectMocks
    private ApplicationSearchController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getApplicationSearchByApplicationSupportServiceId() throws Exception {
        long supportServiceId = 1L;

        ApplicationSearch applicationSearch = new ApplicationSearch();
        applicationSearch.setId(supportServiceId);
        applicationSearch.setTitle("Example Title");
        applicationSearch.setDescription("Example Description");
        applicationSearch.setNotes("Example Notes");

        Classification classification = new Classification();

        Document document = new Document();
        // Set document properties as needed

        applicationSearch.setClassification(classification);
        applicationSearch.setApplicationSearchDocument(document);

        List<ApplicationSearchSimilars> applicationSearchSimilars = Arrays.asList(new ApplicationSearchSimilars(), new ApplicationSearchSimilars());
        applicationSearch.setApplicationSearchSimilars(applicationSearchSimilars);

        ApplicationSearchDto applicationSearchDto = new ApplicationSearchDto();
        applicationSearchDto.setId(supportServiceId);
        applicationSearchDto.setTitle("Example Title");
        applicationSearchDto.setDescription("Example Description");
        applicationSearchDto.setNotes("Example Notes");

        when(applicationSearchService.findById(supportServiceId)).thenReturn(applicationSearch);
        when(applicationSearchMapper.map(applicationSearch)).thenReturn(applicationSearchDto);

        mockMvc.perform(get("/kc/support-service/application-search/service/{id}", supportServiceId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").exists())
                .andExpect(jsonPath("$.payload.id").value(supportServiceId))
                .andExpect(jsonPath("$.payload.title").value("Example Title"))
                .andExpect(jsonPath("$.payload.description").value("Example Description"))
                .andExpect(jsonPath("$.payload.notes").value("Example Notes"));

        verify(applicationSearchService, times(1)).findById(supportServiceId);
        verify(applicationSearchMapper, times(1)).map(applicationSearch);
    }
}
