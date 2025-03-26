package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.service.ClassificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SEClassificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClassificationService classificationServiceMock;

    @InjectMocks
    private SEClassificationControllerTest seClassificationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(seClassificationController).build();
    }

    @Test
    void testGetClassifications() throws Exception {
        int page = 0;
        int limit = 10;
        String query = "classification";
        Integer versionId = 1;
        String saipCode = "code";
        PaginationDto paginationDto = new PaginationDto();
        ApiResponse<PaginationDto> apiResponse = ApiResponse.ok(paginationDto);

        when(classificationServiceMock.getAllClassifications(anyInt(), anyInt(), anyString(), any(), anyString(), anyLong(),anyLong())).thenReturn(paginationDto);

        mockMvc.perform(get("/se/classification")
                        .param("page", String.valueOf(page))
                        .param("limit", String.valueOf(limit))
                        .param("query", query)
                        .param("versionId", String.valueOf(versionId))
                        .param("saipCode", saipCode))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByCategory() throws Exception {
        Long categoryId = 1L;
        List<ClassificationDto> classificationDtos = List.of(new ClassificationDto());

        ApiResponse apiResponse = ApiResponse.ok(classificationDtos);

        when(classificationServiceMock.findByCategoryId(categoryId)).thenReturn(classificationDtos);

        mockMvc.perform(get("/se/classification/category/" + categoryId))
                .andExpect(status().isOk());
    }
}