package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationDatabaseDto;
import gov.saip.applicationservice.common.dto.ApplicationDocumentCommentDto;
import gov.saip.applicationservice.common.dto.ApplicationNotesDto;
import gov.saip.applicationservice.common.mapper.ApplicationDatabaseMapper;
import gov.saip.applicationservice.common.mapper.ApplicationDocumentCommentMapper;
import gov.saip.applicationservice.common.model.ApplicationDatabase;
import gov.saip.applicationservice.common.model.ApplicationDocumentComment;
import gov.saip.applicationservice.common.service.ApplicationDatabaseService;
import gov.saip.applicationservice.common.service.ApplicationDocumentCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApplicationDocumentCommentControllerTest {

    @InjectMocks
    private ApplicationDocumentCommentController applicationDocumentCommentController;
    @Mock
    private ApplicationDocumentCommentMapper applicationDocumentCommentMapper;
    @Mock
    private ApplicationDocumentCommentService applicationDocumentCommentService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationDocumentCommentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllApplicationWords() throws Exception {
        Long documentId = 1L;
        List<ApplicationDocumentComment> applicationDocumentComments = new ArrayList<>();
        ApplicationDocumentComment applicationDocumentComment = new ApplicationDocumentComment();
        applicationDocumentComments.add(applicationDocumentComment);
        List<ApplicationDocumentCommentDto> applicationDocumentCommentDtos = new ArrayList<>();
        ApplicationDocumentCommentDto applicationDocumentCommentDto = new ApplicationDocumentCommentDto();
        applicationDocumentCommentDtos.add(applicationDocumentCommentDto);
        ApiResponse<List<ApplicationDocumentCommentDto>> expectedResponse = ApiResponse.ok(applicationDocumentCommentDtos);
        Mockito.when(applicationDocumentCommentService.findByDocumentId(documentId)).thenReturn(applicationDocumentComments);
        Mockito.when(applicationDocumentCommentMapper.map(applicationDocumentComments)).thenReturn(applicationDocumentCommentDtos);


        mockMvc.perform(MockMvcRequestBuilders.get("/kc/app-document-comment/document/{documentId}", documentId))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}