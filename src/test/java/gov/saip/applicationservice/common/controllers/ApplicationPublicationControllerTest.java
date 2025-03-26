package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.controllers.publication.ApplicationPublicationController;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.facade.publication.PublicationIssueFacade;
import gov.saip.applicationservice.common.model.ApplicationCategoryToPublicationCountProjection;
import gov.saip.applicationservice.common.service.ApplicationPublicationService;
import gov.saip.applicationservice.common.service.PublicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApplicationPublicationControllerTest {

    @InjectMocks
    private ApplicationPublicationController applicationPublicationController;

    @Mock
    private PublicationIssueFacade publicationIssueFacade;
    
    @Mock
    private ApplicationPublicationService applicationPublicationService;

    @Mock
    private PublicationService publicationService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationPublicationController).build();
    }

    @Test
    public void testListGazettePublicationsForTradeMark() throws Exception {
        PaginationDto sampleData = new PaginationDto();
        sampleData.setTotalPages(1);
        sampleData.setContent(new Object());
        sampleData.setTotalElements(50L);

        doReturn(sampleData).when(publicationService).listGazetteOrPublicationsForTrademark(anyInt(), anyInt(), Mockito.anyLong(), anyString(), any(), any());


        mockMvc.perform(get("/pb/publications-batch-list/trademark/GAZETTE")
                        .param("page", "1")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(200));
        verify(publicationService, times(1))
                .listGazetteOrPublicationsForTrademark(eq(1), eq(10), eq(null), eq("GAZETTE"), any(), any());
    }

    @Test
    public void testListGazettePublicationsForPatent() throws Exception {
        PaginationDto sampleData = new PaginationDto();
        sampleData.setTotalPages(1);
        sampleData.setContent(new Object());
        sampleData.setTotalElements(50L);

        doReturn(sampleData).when(publicationService).listGazetteOrPublicationsForPatent(anyInt(), anyInt(), Mockito.anyLong(), anyString(), any(), any());


        mockMvc.perform(get("/pb/publications-batch-list/patent/GAZETTE")
                        .param("page", "1")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(200));
        verify(publicationService, times(1))
                .listGazetteOrPublicationsForPatent(eq(1), eq(10), eq(null), eq("GAZETTE"), any(), any());
    }

    @Test
    public void testListGazettePublicationsForIndustrial() throws Exception {
        PaginationDto sampleData = new PaginationDto();
        sampleData.setTotalPages(1);
        sampleData.setContent(new Object());
        sampleData.setTotalElements(50L);

        doReturn(sampleData).when(publicationService).listGazetteOrPublicationsForIndustrial(anyInt(), anyInt(), Mockito.anyLong(), anyString(), any(), any());


        mockMvc.perform(get("/pb//publications-batch-list/industrial_design/GAZETTE")
                        .param("page", "1")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(200));
        verify(publicationService, times(1))
                .listGazetteOrPublicationsForIndustrial(eq(1), eq(10), eq(null), eq("GAZETTE"), any(), any());
    }

    @Test
    public void testCountPublicationsByApplicationCategory() throws Exception {
        List<ApplicationCategoryToPublicationCountProjection> sampleList = new ArrayList<>();
        sampleList.add(new ApplicationCategoryToPublicationCountProjection(1L, "Code1", "Desc1", "Desc1", 10L));
        sampleList.add(new ApplicationCategoryToPublicationCountProjection(2L, "Code2", "Desc2", "Desc2", 20L));

        when(publicationService.countPublicationsByApplicationCategory(any())).thenReturn(sampleList);

        mockMvc.perform(get("/internal-calling/application-publication/count-by-application-category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.payload").isArray())
                .andExpect(jsonPath("$.payload[0].applicationCategoryId").value(1))
                .andExpect(jsonPath("$.payload[1].applicationCategoryId").value(2))
                .andExpect(jsonPath("$.payload[0].count").value(10))
                .andExpect(jsonPath("$.payload[1].count").value(20));

        verify(publicationService, times(1)).countPublicationsByApplicationCategory(any());
    }

    @Test
    public void testCreateApplicationPublication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pb/application-publication/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(applicationPublicationService, times(1)).createApplicationPublication(123l,null);


    }

    @Test
    public void testPublishApplicationPublicationInNextIssue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pb/application-publication/456/publish-in-next-issue/granted")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(publicationIssueFacade, times(1)).addGrantedApplicationPublicationToLatestIssueOrCreateNewIssue(456L);

    }

}