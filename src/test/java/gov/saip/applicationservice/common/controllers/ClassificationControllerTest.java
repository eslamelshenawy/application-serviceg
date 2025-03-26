package gov.saip.applicationservice.common.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.model.Classification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.service.ClassificationService;

@ExtendWith(MockitoExtension.class)
public class ClassificationControllerTest {

    @Mock
    private ClassificationService classificationService;

    @Mock
    private ClassificationMapper classificationMapper;

    @InjectMocks
    private ClassificationController classificationController;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(classificationController).build();
    }

//    @Test
//    public void testGetClassifications() throws Exception {
//        int page = 0;
//        int limit = 10;
//        String query = null;
//        Integer versionId = null;
//        String saipCode = null;
//
//        PaginationDto paginationDto = new PaginationDto();
//        List<ClassificationDto> content = Arrays.asList(new ClassificationDto(), new ClassificationDto());
//        paginationDto.setContent(content);
//        paginationDto.setTotalElements(2L);
//        paginationDto.setTotalPages(1);
//
//        ApiResponse<PaginationDto> apiResponse = ApiResponse.ok(paginationDto);
//
//        when(classificationService.getAllClassifications(page, limit, query, versionId, saipCode)).thenReturn(paginationDto);
//
//        mockMvc.perform(get("/kc/classification?page=0&limit=10"))
//                .andExpect(status().isOk());
//
//    }

    @Test
    public void testFindByCategory() throws Exception {
        Long categoryId = 1L;
        List<ClassificationDto> classifications = Arrays.asList(new ClassificationDto(), new ClassificationDto());
        ApiResponse<List<ClassificationDto>> apiResponse = ApiResponse.ok(classifications);

        when(classificationService.findByCategoryId(categoryId)).thenReturn(classifications);

        mockMvc.perform(get("/kc/classification/category/{categoryId}", categoryId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAll() throws Exception {
        List<ClassificationLightDto> classifications = Arrays.asList(new ClassificationLightDto(), new ClassificationLightDto());
        ApiResponse<List<ClassificationLightDto>> apiResponse = ApiResponse.ok(classifications);

        when(classificationService.getAllClassifications(any(), any())).thenReturn(classifications);

        mockMvc.perform(get("/kc/classification/all"))
                .andExpect(status().isOk());
    }

//    @Test
    public void testFindAllPaging() throws Exception {
        int page = 1;
        int limit = 10;
        String sortableColumn = "id";

        ClassificationDto classificationDto = new ClassificationDto();
        classificationDto.setId(1L);
        Page<ClassificationDto> pageImpl = new PageImpl<>(Arrays.asList(classificationDto));

        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setContent(Arrays.asList(classificationDto));
        paginationDto.setTotalElements(1L);
        paginationDto.setTotalPages(1);

        ApiResponse<PaginationDto> apiResponse = ApiResponse.ok(paginationDto);

        when(classificationService.findAll(any())).thenReturn(Mockito.any());

        mockMvc.perform(get("/kc/classification/page?page=1&limit=10"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindById() throws Exception {
        Long id = 1L;
        ClassificationDto classificationDto = new ClassificationDto();
        classificationDto.setId(id);
        classificationDto.setCode("12");

        Classification classification = new Classification();
        classification.setId(id);
        classification.setCode("12");

        ApiResponse<ClassificationDto> apiResponse = ApiResponse.ok(classificationDto);

        when(classificationService.findById(id)).thenReturn(classification);
        when(classificationMapper.map(classification)).thenReturn(classificationDto);

        mockMvc.perform(get("/kc/classification/{id}", id))
                .andExpect(status().isOk());

    }

    @Test
    public void testInsert() throws Exception {
        ClassificationDto classificationDto = new ClassificationDto();
        classificationDto.setId(1L);
        classificationDto.setNameAr("sdd");
        classificationDto.setDescriptionAr("sdsd");
        classificationDto.setCode("12");
        classificationDto.setCategoryId(5L);

        Classification classification = new Classification();
        classification.setId(1L);
        classification.setNameAr("sdd");
        classification.setDescriptionAr("sdsd");
        classification.setCode("12");

        mockMvc.perform(post("/kc/classification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(classificationDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        ClassificationDto classificationDto = new ClassificationDto();
        classificationDto.setId(1L);
        classificationDto.setNameAr("sdd");
        classificationDto.setDescriptionAr("sdsd");
        classificationDto.setCode("12");
        classificationDto.setCategoryId(5L);

        Classification classification = new Classification();
        classification.setId(1L);

        mockMvc.perform(put("/kc/classification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(classificationDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteById() throws Exception {
        Long id = 1L;
        ApiResponse<?> apiResponse = ApiResponse.ok(null);

        mockMvc.perform(delete("/kc/classification/{id}", id))
                .andExpect(status().isOk());
    }


}