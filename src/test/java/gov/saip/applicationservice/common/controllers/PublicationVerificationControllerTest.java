package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.service.PublicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class PublicationVerificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PublicationService publicationService;

    private PublicationVerificationController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new PublicationVerificationController(publicationService);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void testGetPatentApplicationPublications() throws Exception {
        when(publicationService.getPatentPublicationsBatches(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(new PaginationDto<>());

       mockMvc.perform(MockMvcRequestBuilders.get("/internal-calling/publications/patent"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testGetTrademarkApplicationPublications() throws Exception {
        when(publicationService.getTrademarkPublicationsBatches(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(new PaginationDto<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/internal-calling/publications/trademark"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testGetIndustrialApplicationPublications() throws Exception {
        when(publicationService.getIndustrialPublicationsBatches(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(new PaginationDto<>());

       mockMvc.perform(MockMvcRequestBuilders.get("/internal-calling/publications/industrial_design"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testViewTrademarkPublications() throws Exception {
        when(publicationService.viewTrademarkPublications(anyInt(), anyInt(), anyString()))
                .thenReturn(new PaginationDto<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/internal-calling/publications-batch-view/trademark"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testViewPatentPublications() throws Exception {
        when(publicationService.viewPatentPublications(anyInt(), anyInt(), anyString()))
                .thenReturn(new PaginationDto<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/internal-calling/publications-batch-view/patent"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testViewIndustrialPublications() throws Exception {
        when(publicationService.viewIndustrialPublications(anyInt(), anyInt(), anyString()))
                .thenReturn(new PaginationDto<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/internal-calling/publications-batch-view/industrial_design"))
                .andExpect(status().isOk())
                .andReturn();
    }
}

