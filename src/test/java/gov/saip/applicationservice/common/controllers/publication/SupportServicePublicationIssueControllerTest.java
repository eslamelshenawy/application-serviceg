package gov.saip.applicationservice.common.controllers.publication;

import gov.saip.applicationservice.common.controllers.publication.support_service.SupportServicePublicationIssueController;
import gov.saip.applicationservice.common.facade.publication.support_service.SupportServicePublicationIssueFacade;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SupportServicePublicationIssueControllerTest {
    
    @InjectMocks
    private SupportServicePublicationIssueController supportServicePublicationIssueController;
    
    @Mock
    private SupportServicePublicationIssueFacade supportServicePublicationIssueFacade;
    
    @Mock
    private ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    
    private MockMvc mockMvc;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(supportServicePublicationIssueController).build();
    }
    
    @Test
    public void testPublishSupportServiceApplicationInNextIssue() throws Exception {
        when(applicationSupportServicesTypeService.findById(123l)).thenReturn(new ApplicationSupportServicesType());
        mockMvc.perform(post("/pb/support-service/123/publish-in-next-issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(supportServicePublicationIssueFacade, times(1))
                .AddSupportServiceApplicationPublicationToLatestIssueOrCreateNewIssue(123l);
        
        
    }
}