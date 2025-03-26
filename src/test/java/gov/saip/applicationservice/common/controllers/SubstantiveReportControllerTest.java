package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.SubstantiveReportDto;
import gov.saip.applicationservice.common.enums.ExaminerReportType;
import gov.saip.applicationservice.common.mapper.ApplicationNotesMapper;
import gov.saip.applicationservice.common.mapper.SubstantiveReportMapper;
import gov.saip.applicationservice.common.model.SubstantiveExaminationReport;
import gov.saip.applicationservice.common.service.ApplicationNotesService;
import gov.saip.applicationservice.common.service.SubstantiveReportService;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SubstantiveReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SubstantiveReportService substantiveReportServiceMock;

    @Mock

    private SubstantiveReportMapper substantiveReportMapperMock;

    @Mock
    private ApplicationNotesService applicationNotesService;

    @Mock
    private SubstantiveReportMapper substantiveReportMapper;
    private SubstantiveReportController substantiveReportController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        substantiveReportController = new SubstantiveReportController(substantiveReportServiceMock, substantiveReportMapper, applicationNotesService);
        mockMvc = MockMvcBuilders.standaloneSetup(substantiveReportController).build();
    }

    @Test
    void testGetAllReports() throws Exception {
        // given
        Long appId = 1L;
        ExaminerReportType type = ExaminerReportType.SUBSTANTIVE_AGENT;
        SubstantiveReportDto reportDto = new SubstantiveReportDto();
        reportDto.setId(1L);
        reportDto.setApplicationId(appId);
        reportDto.setType(type);
        ApiResponse<List<SubstantiveReportDto>> apiResponse = ApiResponse.ok(Collections.singletonList(reportDto));

        when(substantiveReportServiceMock.getAllByApplicationId(anyLong(), any()))
                .thenReturn(Collections.singletonList(reportDto));

        // when and then
        mockMvc.perform(get("/kc/substantive-report/{appId}/application", appId)
                        .param("type", type.name()))
                .andExpect(status().isOk());
    }


    @Test
    void givenCorrectApplicationId_whenGetLastAcceptWithConditionReportByApplicationId_thenCorrect() throws Exception {
        SubstantiveReportDto substantiveReportDto = new SubstantiveReportDto();
        when(substantiveReportServiceMock.getLastAcceptWithConditionReportByApplicationId(any())).thenReturn(substantiveReportDto);
        mockMvc.perform(get("/kc/substantive-report/acceptWithCondition/application/1"))
                .andExpect(status().isOk());
    }

    @Test
    void givenInCorrectApplicationId_whenGetLastAcceptWithConditionReportByApplicationId_thenThrowException() throws Exception {
        when(substantiveReportServiceMock.getLastAcceptWithConditionReportByApplicationId(any())).thenThrow(BusinessException.class);
        assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/kc/substantive-report/acceptWithCondition/application/1"));
        });
    }
}