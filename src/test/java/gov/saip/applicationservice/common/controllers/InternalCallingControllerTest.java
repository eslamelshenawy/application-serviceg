package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import gov.saip.applicationservice.common.service.lookup.LkFastTrackExaminationTargetAreaService;
import gov.saip.applicationservice.common.service.lookup.LkNotesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InternalCallingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ApplicationPriorityService applicationPriorityService;

    @Mock
    private DocumentsService documentsService;

    @Mock
    private ApplicationInfoService applicationInfoService;

    @Mock
    private ApplicationAcceleratedService applicationAcceleratedService;

    @Mock
    private ClassificationService classificationService;

    @Mock
    private LkFastTrackExaminationTargetAreaService fastTrackExaminationTargetAreaService;

    @Mock
    private LkApplicationCategoryService applicationCategoryService;

    @Mock
    private LkNotesService lkNotesService;

    @Mock
    private ApplicationNotesService applicationNotesService;

    @InjectMocks
    private InternalCallingController internalCallingController;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(internalCallingController).build();
    }

    @Test
    public void testGenerateApplicationNumber() throws Exception {

        Long id = 1L;
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();

        when(applicationInfoService.generateApplicationNumber(any(ApplicationNumberGenerationDto.class), anyLong()))
                .thenReturn(1L);


        mockMvc.perform(post("/internal-calling/applications/{id}/application-number", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applicationNumberGenerationDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(1L))));
    }

    @Test
    public void testFindClassificationByUnitId() throws Exception {

        List<Long> unitIds = List.of(1L);

        List<ClassificationDto> result = List.of(new ClassificationDto());
        when(classificationService.findByUnitIdIn(unitIds))
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/classification/unitId/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testGetApplication() throws Exception {

        Long id = 1L;

        ApplicationInfoDto result = new ApplicationInfoDto();
        when(applicationInfoService.getApplication(id))
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/applications/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testGetAllFastTrackExaminationTargetAreas() throws Exception {

        List<LkFastTrackExaminationTargetAreaDto> result = List.of(new LkFastTrackExaminationTargetAreaDto());
        when(fastTrackExaminationTargetAreaService.getAllFastTrackExaminationTargetAreas())
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/fastTrackExaminationTargetAreas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    public void testGetAllCategories() throws Exception {

        List<LKApplicationCategoryDto> result = List.of(new LKApplicationCategoryDto());
        when(applicationCategoryService.getAll())
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testGetApplicationInfoPayment() throws Exception {

        Long id = 1L;

        ApplicationInfoPaymentDto result = new ApplicationInfoPaymentDto();
        when(applicationInfoService.getApplicationInfoPayment(id))
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/{id}/payment", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testSubmitApplicationInfoPayment() throws Exception {

        Long id = 1L;
        ApplicationInfoPaymentDto applicationInfoPaymentDto = new ApplicationInfoPaymentDto();
        applicationInfoPaymentDto.setTotalCheckingFee(100L);

        when(applicationInfoService.submitApplicationInfoPayment(anyLong(), any(ApplicationInfoPaymentDto.class)))
                .thenReturn(1L);


        mockMvc.perform(post("/internal-calling/{id}/payment", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applicationInfoPaymentDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(1L))));
    }

    @Test
    public void testGetApplicationPayment() throws Exception {

        Long id = 1L;

        ApplicationInfoPaymentDto result = new ApplicationInfoPaymentDto();
        when(applicationInfoService.getApplicationPayment(anyLong()))
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/{id}/payment/current", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    public void testGetAllApplicationNotes() throws Exception {

        Long applicationId = 1L;

        List<SectionApplicationNotesResponseDto> result = List.of(new SectionApplicationNotesResponseDto());
        when(applicationNotesService.getAllApplicationNotes(1L, null, null, null, null))
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/application-notes")
                        .param("applicationId", applicationId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testFindAppNotes() throws Exception {

        Long applicationId = 1L;
        String sectionCode = "section";
        String attributeCode = "attribute";
        String taskDefinitionKey = "taskKey";
        String stageKey = "stageKey";
        NotesTypeEnum notesType = NotesTypeEnum.EXAMINAR;

        List<ApplicationNotesDto> notesList = Collections.singletonList(new ApplicationNotesDto());

        when(applicationNotesService.findAppNotes(applicationId, sectionCode, attributeCode, taskDefinitionKey, stageKey, notesType))
                .thenReturn(notesList);

        mockMvc.perform(get("/internal-calling/application-notes/{applicationId}", applicationId)
                        .param("sectionCode", sectionCode)
                        .param("attributeCode", attributeCode)
                        .param("taskDefinitionKey", taskDefinitionKey)
                        .param("stageKey", stageKey)
                        .param("notesType", notesType.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(notesList))));
    }


    @Test
    public void testUpdateApplicationNotesDoneStatus() throws Exception {

        Long applicationNoteId = 1L;
        doNothing().when(applicationNotesService).updateApplicationNotesDoneStatus(anyLong());


        mockMvc.perform(put("/internal-calling/application-notes/done/{applicationNoteId}", applicationNoteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddAppNote() throws Exception {

        ApplicationNotesReqDto dto = new ApplicationNotesReqDto();
        dto.setApplicationId(1L);
        dto.setSectionCode("SEC");
        dto.setAttributeCode("ATTR");
        dto.setTaskDefinitionKey("TASK");

        when(applicationNotesService.addOrUpdateAppNotes(any(ApplicationNotesReqDto.class)))
                .thenReturn(1L);


        mockMvc.perform(post("/internal-calling/application-notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(1L))));
    }

    @Test
    public void testUpdateAppNote() throws Exception {

        ApplicationNotesReqDto dto = new ApplicationNotesReqDto();
        dto.setId(1L);
        dto.setApplicationId(1L);
        dto.setSectionCode("SEC");
        dto.setAttributeCode("ATTR");
        dto.setTaskDefinitionKey("TASK");

        when(applicationNotesService.updateAppNote(any(ApplicationNotesReqDto.class)))
                .thenReturn(1L);


        mockMvc.perform(put("/internal-calling/application-notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(1L))));
    }

    @Test
    public void testFindDocumentByApplicationId() throws Exception {

        Long id = 1L;
        String typeName = "TYPE";

        DocumentDto result = new DocumentDto();
        when(documentsService.findDocumentByApplicationIdAndDocumentType(anyLong(), anyString()))
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/document/applications/{id}", id)
                        .param("typeName", typeName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testCheckIfApplicationAccelrated() throws Exception {

        Long id = 1L;

        when(applicationAcceleratedService.checkIfApplicationAccelrated(anyLong()))
                .thenReturn(true);


        mockMvc.perform(get("/internal-calling/check/accelrated/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testGetApplicationDocuments() throws Exception {

        DocumentApplicationTypesDto dto = new DocumentApplicationTypesDto();
        dto.setId(1L);
        dto.setTypes(List.of("TYPE"));

        List<DocumentWithCommentDto> result = List.of(new DocumentWithCommentDto());
        when(documentsService.findDocumentByApplicationID(any(DocumentApplicationTypesDto.class)))
                .thenReturn(result);


        mockMvc.perform(post("/internal-calling/documents/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testGetApplicationPriorites() throws Exception {

        Long appId = 1L;

        List<ApplicationPriorityListDto> result = List.of(new ApplicationPriorityListDto());
        when(applicationPriorityService.listApplicationPriorites(anyLong()))
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/priorities/application/{appId}", appId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testUpdatePriorityDocs() throws Exception {

        Long id = 1L;
        ApplicationPriorityUpdateStatusAndCommentsDto dto = new ApplicationPriorityUpdateStatusAndCommentsDto();
        dto.setCode("code");
        dto.setComment("COMMENT");

        when(applicationPriorityService.updatePriorityStatusAndComment(anyLong(), any(ApplicationPriorityUpdateStatusAndCommentsDto.class)))
                .thenReturn(1L);


        mockMvc.perform(put("/internal-calling/priorities/{id}/update-priority-status-comment", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(1L))));
    }

    @Test
    public void testGetApplicationByApplicationPartialNumber() throws Exception {

        String partialNumber = "PARTIAL";

        List<PartialApplicationInfoDto> result = List.of(new PartialApplicationInfoDto());
        when(applicationInfoService.getApplicationByApplicationPartialNumber(anyString()))
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/applications")
                        .param("partialNumber", partialNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testGetApplicationClassificationsByApplicationIds() throws Exception {

        List<Long> ids = List.of(1L);

        List<ApplicationClassificationLightDto> result = List.of(new ApplicationClassificationLightDto());
        when(applicationInfoService.getApplicationClassification(anyList()))
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/application/classifications")
                        .param("ids", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testGetApplicationStatusByApplicationIds() throws Exception {

        List<Long> applicationIds = Arrays.asList(1L, 2L, 3L);

        Map<Long, ApplicationInfoTaskDto> applicationStatusMap = createApplicationStatusMap();

        when(applicationInfoService.getApplicationStatusByApplicationIds(applicationIds))
                .thenReturn(applicationStatusMap);


        mockMvc.perform(get("/internal-calling/application/task-data")
                        .param("ids", "1,2,3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(applicationStatusMap))));
    }

    @Test
    public void testGetApplicationAccelerated() throws Exception {

        Long id = 1L;

        ApplicationAcceleratedLightDto result = new ApplicationAcceleratedLightDto();
        when(applicationAcceleratedService.getByApplicationId(anyLong()))
                .thenReturn(result);

        mockMvc.perform(get("/internal-calling/application/{id}/application-accelerated", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    @Test
    public void testUpdateApplicationAcceleratedStatus() throws Exception {

        Long id = 1L;
        doNothing().when(applicationAcceleratedService).updateApplicationAcceleratedStatus(anyLong(), anyBoolean());


        mockMvc.perform(put("/internal-calling/application/{id}/application-accelerated/refused", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetApplicationClassificationUnitIdsByAppId() throws Exception {

        Long id = 1L;

        List<Long> result = List.of(1L);
        when(applicationInfoService.getApplicationClassificationUnitIdsByAppId(anyLong()))
                .thenReturn(result);


        mockMvc.perform(get("/internal-calling/application/{id}/classifications/unit-ids", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.ok(result))));
    }

    private Map<Long, ApplicationInfoTaskDto> createApplicationStatusMap() {
        // Create a sample map with application status data
        Map<Long, ApplicationInfoTaskDto> applicationStatusMap = Map.of(
                1L, new ApplicationInfoTaskDto(),
                2L, new ApplicationInfoTaskDto(),
                3L, new ApplicationInfoTaskDto()
        );
        return applicationStatusMap;
    }
}