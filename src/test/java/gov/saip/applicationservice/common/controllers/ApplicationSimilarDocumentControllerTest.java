package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSearchResultDto;
import gov.saip.applicationservice.common.dto.ApplicationSimilarDocumentDto;
import gov.saip.applicationservice.common.mapper.ApplicationSearchResultMapper;
import gov.saip.applicationservice.common.mapper.ApplicationSimilarDocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationSearchResult;
import gov.saip.applicationservice.common.model.ApplicationSimilarDocument;
import gov.saip.applicationservice.common.service.ApplicationSearchResultService;
import gov.saip.applicationservice.common.service.ApplicationSimilarDocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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


public class ApplicationSimilarDocumentControllerTest {

    @InjectMocks
    private ApplicationSimilarDocumentController applicationSimilarDocumentController;
    @Mock
    private ApplicationSimilarDocumentService applicationSimilarDocumentService;
    @Mock
    private ApplicationSimilarDocumentMapper applicationSimilarDocumentMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationSimilarDocumentController).build();
    }

    @Test
    public void testGetAllApplicationWords() throws Exception {
        Long applicationId = 1L;
        List<ApplicationSimilarDocument> similarDocumentList = new ArrayList<>();
        ApplicationSimilarDocument similarDocument1 = new ApplicationSimilarDocument();
        similarDocument1.setId(1L);
        similarDocumentList.add(similarDocument1);

        ApplicationSimilarDocumentDto similarDocumentDto1 = new ApplicationSimilarDocumentDto();
        similarDocumentDto1.setId(1L);
        similarDocumentDto1.setApplicationId(applicationId);

        ApiResponse<List<ApplicationSimilarDocumentDto>> expectedResponse = ApiResponse.ok(List.of(similarDocumentDto1));

        Mockito.when(applicationSimilarDocumentService.getAllByApplicationId(ArgumentMatchers.anyLong())).thenReturn(similarDocumentList);
        Mockito.when(applicationSimilarDocumentMapper.map(similarDocumentList)).thenReturn(List.of(similarDocumentDto1));

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/app-similar-docs/{appId}/application", applicationId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}