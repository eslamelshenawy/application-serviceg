package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.service.PublicationIssueService;
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

class PublicationIssueExternalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PublicationIssueService publicationIssueService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PublicationIssueExternalController(publicationIssueService)).build();
    }

    @Test
    public void testGetTrademarkApplicationsInfoXmlByIssueId() throws Exception {
        // Arrange
        Long issueId = 1L;
        ByteArrayResource file = new ByteArrayResource(new byte[]{1, 2, 3});
        when(publicationIssueService.getTrademarkApplicationsInfoXmlByIssueId(issueId)).thenReturn(file);
        // Act & Assert
        mockMvc.perform(get("/kc/publication-issue/application-info/trademark/{issueId}/xml-file", issueId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Trademarks.xml"))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_OCTET_STREAM.toString()))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_LENGTH,
                        String.valueOf(file.contentLength())))
                .andExpect(content().bytes(file.getByteArray()));
    }

    @Test
    public void testGetPatentApplicationsInfoXmlByIssueId() throws Exception {
        // Arrange
        Long issueId = 1L;
        ByteArrayResource file = new ByteArrayResource(new byte[]{1, 2, 3});
        when(publicationIssueService.getPatentApplicationsInfoXmlByIssueId(issueId)).thenReturn(file);
        // Act & Assert
        mockMvc.perform(get("/kc/publication-issue/application-info/patent/{issueId}/xml-file", issueId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Patents.xml"))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_OCTET_STREAM.toString()))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_LENGTH,
                        String.valueOf(file.contentLength())))
                .andExpect(content().bytes(file.getByteArray()));
    }

    @Test
    public void testGetIndustrialDesignApplicationsInfoXmlByIssueId() throws Exception {
        // Arrange
        Long issueId = 1L;
        ByteArrayResource file = new ByteArrayResource(new byte[]{1, 2, 3});
        when(publicationIssueService.getIndustrialDesignApplicationsInfoXmlByIssueId(issueId)).thenReturn(file);
        // Act & Assert
        mockMvc.perform(get("/kc/publication-issue/application-info/industrial-design/{issueId}/xml-file", issueId))
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