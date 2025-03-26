package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.service.ApplicationNotesService;
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


public class ApplicationNotesControllerTest {

    @InjectMocks
    private ApplicationNotesController applicationNotesController;
    @Mock
    private ApplicationNotesService applicationNotesService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationNotesController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllApplicationNotes() throws Exception {

        Long applicationId = 1l;
        Integer stepId = 1;
        Integer sectionId = 1;
        String taskDefinitionKey = "1";
        NotesTypeEnum notesType = NotesTypeEnum.APPLICANT ;
        List<SectionApplicationNotesResponseDto> list = new ArrayList<>();
        ApiResponse<List<SectionApplicationNotesResponseDto>> expectedResponse = ApiResponse.ok(list);
        Mockito.when(applicationNotesService.getAllApplicationNotes(applicationId, stepId, sectionId, taskDefinitionKey, notesType)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/application-notes")
                        .param("applicationId", String.valueOf(applicationId))
                        .param("stepId", String.valueOf(stepId))
                        .param("sectionId", String.valueOf(sectionId))
                        .param("taskDefinitionKey", taskDefinitionKey))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindAppNotes() throws Exception {
        Long applicationId = 1L;
        String sectionCode = "testCode";
        String attributeCode = "testAttribute";
        String taskDefinitionKey = "testKey";
        String stageKey = "stageKey";
        NotesTypeEnum notesType = NotesTypeEnum.APPLICANT;
        List<ApplicationNotesDto> applicationNotesDtoList = new ArrayList<>();
        ApplicationNotesDto applicationNotesDto = new ApplicationNotesDto();
        applicationNotesDto.setId(1l);
        applicationNotesDtoList.add(applicationNotesDto);
        ApiResponse<List<ApplicationNotesDto>> expectedResponse = ApiResponse.ok(applicationNotesDtoList);
        Mockito.when(applicationNotesService.findAppNotes(applicationId, sectionCode, attributeCode, taskDefinitionKey,stageKey, notesType)).thenReturn(applicationNotesDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/application-notes/{applicationId}", applicationId)
                        .param("sectionCode", sectionCode)
                        .param("attributeCode", attributeCode)
                        .param("taskDefinitionKey", taskDefinitionKey)
                        .param("stageKey", stageKey)
                        .param("notesType", String.valueOf(notesType)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(applicationNotesService).findAppNotes(applicationId, sectionCode, attributeCode, taskDefinitionKey,stageKey, notesType);
    }

    @Test
    public void testUpdateApplicationNotesDoneStatus() throws Exception {
        Long applicationNoteId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.put("/kc/application-notes/done/{applicationNoteId}", applicationNoteId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(applicationNotesService).updateApplicationNotesDoneStatus(applicationNoteId);
    }

    @Test
    public void testAddAppNote() throws Exception {
        ApplicationNotesReqDto applicationNotesReqDto = new ApplicationNotesReqDto();
        applicationNotesReqDto.setId(1l);
        Long id = 1l;
        ApiResponse<Long> expectedResponse =ApiResponse.ok(id);
        Mockito.when(applicationNotesService.addOrUpdateAppNotes(ArgumentMatchers.any(ApplicationNotesReqDto.class))).thenReturn(id);

        mockMvc.perform(MockMvcRequestBuilders.post("/kc/application-notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(applicationNotesReqDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testUpdateAppNote() throws Exception {
        ApplicationNotesReqDto applicationNotesReqDto = new ApplicationNotesReqDto();
        applicationNotesReqDto.setId(1l);
        Long id = 1l;
        ApiResponse<Long> expectedResponse = ApiResponse.ok(id);
        Mockito.when(applicationNotesService.updateAppNote(ArgumentMatchers.any(ApplicationNotesReqDto.class))).thenReturn(id);
        mockMvc.perform(MockMvcRequestBuilders.put("/kc/application-notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(applicationNotesReqDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}