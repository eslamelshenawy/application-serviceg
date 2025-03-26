package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.SubClassificationDto;
import gov.saip.applicationservice.common.mapper.SubClassificationMapper;
import gov.saip.applicationservice.common.service.SubClassificationService;
import gov.saip.applicationservice.common.model.SubClassification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SubClassificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SubClassificationService subClassificationServiceMock;
    @Mock
    private SubClassificationMapper subClassificationMapper;

    private SubClassificationController subClassificationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        subClassificationController = new SubClassificationController(subClassificationServiceMock, subClassificationMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(subClassificationController).build();
    }

    @Test
    void testGetCategories() throws Exception {
        // given
        long categoryId = 1L;
        long applicationId = 2L;
        int page = 0;
        int limit = 10;
        String query = "sub";
        Boolean isShortcut = true;
        PaginationDto<List<SubClassificationDto>> paginationDto = new PaginationDto<>();
        paginationDto.setContent(Collections.singletonList(new SubClassificationDto()));

        ApiResponse<PaginationDto> apiResponse = ApiResponse.ok(paginationDto);

        when(subClassificationServiceMock.getAllSubClass(anyInt(), anyInt(), anyString(), anyBoolean(), anyLong(), anyLong()))
                .thenReturn(paginationDto);

        // when and then
        mockMvc.perform(get("/kc/sub-classification/classification/{id}", categoryId)
                        .param("applicationId", String.valueOf(applicationId))
                        .param("page", String.valueOf(page))
                        .param("limit", String.valueOf(limit))
                        .param("query", query)
                        .param("isShortcut", String.valueOf(isShortcut)))
                .andExpect(status().isOk());
    }
}