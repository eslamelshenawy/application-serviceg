package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.service.DocumentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DocumentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DocumentsService documentsService;

    @InjectMocks
    private DocumentsController documentsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(documentsController).build();
    }

    @Test
    public void testAddDocuments() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile("files", "test1.txt", MediaType.TEXT_PLAIN_VALUE, "test content".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "test2.txt", MediaType.TEXT_PLAIN_VALUE, "test content".getBytes());
        List<MultipartFile> files = List.of(file1, file2);
        String docTypeName = "TestDocType";
        String applicationType = "TestAppType";
        String applicationId = "1";

        DocumentDto documentDto1 = new DocumentDto();
        documentDto1.setId(1L);
        documentDto1.setFileName(file1.getOriginalFilename());

        DocumentDto documentDto2 = new DocumentDto();
        documentDto2.setId(2L);
        documentDto2.setFileName(file2.getOriginalFilename());

        List<DocumentDto> documents = List.of(documentDto1, documentDto2);

        when(documentsService.addDocuments(files, docTypeName, applicationType, Long.valueOf(applicationId)))
                .thenReturn(documents);

        mockMvc.perform(multipart("/kc/documents")
                        .file(file1)
                        .file(file2)
                        .header("Doc-Type-Name", docTypeName)
                        .header("App-Type", applicationType)
                        .header("Application-Id", applicationId))
                .andExpect(status().isOk());

    }

    @Test
    public void testFindDocumentById() throws Exception {
        Long documentId = 1L;

        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(documentId);
        documentDto.setFileName("TestDoc");

        when(documentsService.findDocumentById(documentId))
                .thenReturn(documentDto);

        mockMvc.perform(get("/kc/documents/{id}", documentId))
                .andExpect(status().isOk());

    }

    @Test
    public void testFindDocumentByIds() throws Exception {
        List<Long> documentIds = List.of(1L, 2L);

        DocumentDto documentDto1 = new DocumentDto();
        documentDto1.setId(1L);
        documentDto1.setFileName("TestDoc1");

        DocumentDto documentDto2 = new DocumentDto();
        documentDto2.setId(2L);
        documentDto2.setFileName("TestDoc2");

        List<DocumentDto> documents = List.of(documentDto1, documentDto2);

        when(documentsService.findDocumentByIds(documentIds))
                .thenReturn(documents);

        mockMvc.perform(post("/kc/documents/findByIds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(documentIds)))
                .andExpect(status().isOk());

    }

    @Test
    public void testSoftDeleteDocumentById() throws Exception {
        Long documentId = 1L;

        when(documentsService.softDeleteDocumentById(documentId))
                .thenReturn(documentId);

        mockMvc.perform(delete("/kc/documents/{id}", documentId))
                .andExpect(status().isOk());
    }

}