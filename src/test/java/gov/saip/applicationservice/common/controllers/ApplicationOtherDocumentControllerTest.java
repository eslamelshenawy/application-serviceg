package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationOtherDocumentDto;
import gov.saip.applicationservice.common.mapper.ApplicationOtherDocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationOtherDocument;
import gov.saip.applicationservice.common.service.ApplicationOtherDocumentService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApplicationOtherDocumentControllerTest {

    @InjectMocks
    private ApplicationOtherDocumentController applicationOtherDocumentController;
    @Mock
    private ApplicationOtherDocumentService applicationOtherDocumentService;
    @Mock
    private ApplicationOtherDocumentMapper applicationOtherDocumentMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationOtherDocumentController).build();
    }

    @Test
    public void testGetAllApplicationOtherDocuments() throws Exception {
        Long appId = 1L;
        List<ApplicationOtherDocument> applicationOtherDocumentList = new ArrayList<>();
        ApplicationOtherDocument applicationOtherDocument = new ApplicationOtherDocument();
        applicationOtherDocumentList.add(applicationOtherDocument);
        List<ApplicationOtherDocumentDto> applicationOtherDocumentDtoList = new ArrayList<>();
        ApplicationOtherDocumentDto applicationOtherDocumentDto = new ApplicationOtherDocumentDto();
        applicationOtherDocumentDtoList.add(applicationOtherDocumentDto);
        ApiResponse<List<ApplicationOtherDocumentDto>> expectedResponse = ApiResponse.ok(applicationOtherDocumentDtoList);
        Mockito.when(applicationOtherDocumentService.getAllAppOtherDocumentsByApplicationId(appId)).thenReturn(applicationOtherDocumentList);
        Mockito.when(applicationOtherDocumentMapper.map(applicationOtherDocumentList)).thenReturn(applicationOtherDocumentDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/other-document/{appId}/application", appId))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}