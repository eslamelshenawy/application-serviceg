package gov.saip.applicationservice.common.controllers.opposition;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.opposition.OppositionDto;
import gov.saip.applicationservice.common.mapper.opposition.OppositionMapper;
import gov.saip.applicationservice.common.model.opposition.Opposition;
import gov.saip.applicationservice.common.service.opposition.OppositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OppositionControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private OppositionService oppositionService;

    @Mock
    private OppositionMapper oppositionMapper;

    @InjectMocks
    OppositionController oppositionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(oppositionController).build();
    }

    @Test
    public void testApplicantReply() throws Exception {
        OppositionDto dto = new OppositionDto();
        dto.setFinalNotes("test");

        Opposition opposition = new Opposition();
        opposition.setFinalNotes(dto.getFinalNotes());

        mockMvc.perform(put("/kc/opposition/applicant/reply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))
                        .param("taskId", "12345"))
                .andExpect(status().isOk());
        verify(oppositionService, times(1)).applicantReply(
                argThat(actualDto  -> {
                    return dto.getFinalNotes().equals(dto.getFinalNotes());
                }),
                eq("12345")
        );

    }

    @Test
    public void testUpdateComplainerHearingSession() throws Exception {
        OppositionDto dto = new OppositionDto();
        dto.setFinalNotes("test");
        when(oppositionService.updateComplainerHearingSession(dto)).thenReturn(1L);
        mockMvc.perform(put("/kc/opposition/complainer-hearing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk());
        verify(oppositionService, times(1)).updateComplainerHearingSession(argThat(actualDto -> {
            return actualDto.getFinalNotes().equals(dto.getFinalNotes());
        }));
    }


    @Test
    public void testUpdateApplicantHaringSession() throws Exception {
        OppositionDto dto = new OppositionDto();
        dto.setFinalNotes("test");
        when(oppositionService.updateApplicantHearingSession(dto)).thenReturn(1L);

        mockMvc.perform(put("/kc/opposition/applicant-hearing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk());

        verify(oppositionService, times(1)).updateApplicantHearingSession(
                argThat(actualDto -> {
                    return actualDto.getFinalNotes().equals(dto.getFinalNotes());
                }));
    }
    @Test
    public void testUpdateApplicantExaminerNotes() throws Exception {
        OppositionDto dto = new OppositionDto();
        dto.setFinalNotes("test");
        when(oppositionService.updateApplicantExaminerNotes(dto)).thenReturn(1L);

        mockMvc.perform(put("/kc/opposition/applicant-examiner-notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk());
               

        verify(oppositionService, times(1)).updateApplicantExaminerNotes((
                argThat(actualDto -> {
                    return actualDto.getFinalNotes().equals(dto.getFinalNotes());
                })));
    }

    @Test
    public void testExaminerFinalDecision() throws Exception {
        OppositionDto dto = new OppositionDto();
        dto.setFinalNotes("test");
        when(oppositionService.examinerFinalDecision(dto)).thenReturn(1L);

        mockMvc.perform(put("/kc/opposition/examiner-final-decision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk());

        verify(oppositionService, times(1)).examinerFinalDecision( argThat(actualDto -> {
            return actualDto.getFinalNotes().equals(dto.getFinalNotes());
        }));
    }

    @Test
    public void testHeadExaminerNotesToExaminer() throws Exception {
        Opposition opposition = new Opposition();
        OppositionDto dto = new OppositionDto();

        when(oppositionMapper.unMap(dto)).thenReturn(opposition);
        when(oppositionService.headExaminerNotesToExaminer(eq(opposition))).thenReturn(1L);

        mockMvc.perform(put("/kc/opposition/head-examiner-notes-to-examiner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(opposition)))
                .andExpect(status().isOk());

    }


    @Test
    public void testConfirmFinalDecisionFromHeadOfExaminer() throws Exception {
        OppositionDto dto = new OppositionDto();
        dto.setId(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        String dtoJson = objectMapper.writeValueAsString(dto);

        doNothing().when(oppositionService).confirmFinalDecisionFromHeadOfExaminer(dto.getId());

        mockMvc.perform(put("/kc/opposition/head-examiner-confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)) // Set the JSON content here
                .andExpect(status().isOk());

        verify(oppositionService, times(1)).confirmFinalDecisionFromHeadOfExaminer(dto.getId());
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

