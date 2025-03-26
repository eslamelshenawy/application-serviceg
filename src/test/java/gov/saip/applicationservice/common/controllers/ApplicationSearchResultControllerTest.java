package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationDatabaseDto;
import gov.saip.applicationservice.common.dto.ApplicationSearchResultDto;
import gov.saip.applicationservice.common.mapper.ApplicationDatabaseMapper;
import gov.saip.applicationservice.common.mapper.ApplicationSearchResultMapper;
import gov.saip.applicationservice.common.model.ApplicationDatabase;
import gov.saip.applicationservice.common.model.ApplicationSearchResult;
import gov.saip.applicationservice.common.service.ApplicationDatabaseService;
import gov.saip.applicationservice.common.service.ApplicationSearchResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApplicationSearchResultControllerTest {

    @InjectMocks
    private ApplicationSearchResultController applicationSearchResultController;
    @Mock
    private ApplicationSearchResultService applicationSearchResultService;
    @Mock
    private ApplicationSearchResultMapper applicationSearchResultMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationSearchResultController).build();
    }

    @Test
    public void testGetAllSearchResults() throws Exception {
        Long applicationId = 1L;
        List<ApplicationSearchResult> resultList = new ArrayList<>();
        ApplicationSearchResult result1 = new ApplicationSearchResult();
        result1.setId(1L);
        result1.setId(1l);
        resultList.add(result1);

        ApplicationSearchResultDto resultDto1 = new ApplicationSearchResultDto();
        resultDto1.setId(1L);
        resultDto1.setApplicationId(applicationId);

        ApiResponse<List<ApplicationSearchResultDto>> expectedResponse = ApiResponse.ok(List.of(resultDto1));

        Mockito.when(applicationSearchResultService.getAllSearchResultsByApplicationId(ArgumentMatchers.anyLong())).thenReturn(resultList);
        Mockito.when(applicationSearchResultMapper.map(resultList)).thenReturn(List.of(resultDto1));

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/search-result/{appId}/application", applicationId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}