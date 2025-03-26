package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LkClassificationUnitDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.service.lookup.LkClassificationUnitService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SEClassificationUnitControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LkClassificationUnitService lkClassificationUnitServiceMock;

    @InjectMocks
    private SEClassificationUnitController seClassificationUnitController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(seClassificationUnitController).build();
    }

    @Test
    void testGetCategoryUnits() throws Exception {
        // given
        List<String> categories = Arrays.asList("category1", "category2");
        List<LkClassificationUnitDto> lkClassificationUnitDtos = Arrays.asList(
                new LkClassificationUnitDto(),
                new LkClassificationUnitDto()
        );
        ApiResponse<List<LkClassificationUnitDto>> apiResponse = ApiResponse.ok(lkClassificationUnitDtos);

        when(lkClassificationUnitServiceMock.getCategoryUnits(anyList())).thenReturn(lkClassificationUnitDtos);

        // when and then
        mockMvc.perform(get("/se/classification-unit/category")
                        .param("categories", categories.get(0))
                        .param("categories", categories.get(1)))
                .andExpect(status().isOk());
    }

    @Test
    void testFilter() throws Exception {
        // given
        int page = 0;
        int limit = 10;

        String query = "unit";
        List<LkClassificationUnitDto> lkClassificationUnitDtos = Arrays.asList(
                new LkClassificationUnitDto(),
                new LkClassificationUnitDto()
        );

        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setContent(lkClassificationUnitDtos);

        ApiResponse<PaginationDto> apiResponse = ApiResponse.ok(paginationDto);

        when(lkClassificationUnitServiceMock.filter(anyInt(), anyInt(), anyString(), anyLong())).thenReturn(paginationDto);

        // when and then
        mockMvc.perform(get("/se/classification-unit/filter")
                        .param("page", String.valueOf(page))
                        .param("limit", String.valueOf(limit))
                        .param("query", query))
                .andExpect(status().isOk());
    }

    @Test
    void testList() throws Exception {
        List<LkClassificationUnitDto> lkClassificationUnitDtos = Collections.singletonList(
                new LkClassificationUnitDto()
        );
        ApiResponse<List<LkClassificationUnitDto>> apiResponse = ApiResponse.ok(lkClassificationUnitDtos);

        when(lkClassificationUnitServiceMock.getAll()).thenReturn(lkClassificationUnitDtos);

        mockMvc.perform(get("/se/classification-unit"))
                .andExpect(status().isOk());
    }

    @Test
    void testAdd() throws Exception {
        LkClassificationUnitDto dto = new LkClassificationUnitDto();
        ApiResponse apiResponse = ApiResponse.ok(1L);

        when(lkClassificationUnitServiceMock.insert(any(LkClassificationUnitDto.class))).thenReturn(1L);

        mockMvc.perform(post("/se/classification-unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        LkClassificationUnitDto dto = new LkClassificationUnitDto();
        ApiResponse apiResponse = ApiResponse.ok(null);

      //  doNothing().when(lkClassificationUnitServiceMock).update(any(LkClassificationUnitDto.class));

        mockMvc.perform(put("/se/classification-unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        Long id = 1L;
        ApiResponse apiResponse = ApiResponse.ok(null);

        doNothing().when(lkClassificationUnitServiceMock).deleteById(id);

        mockMvc.perform(delete("/se/classification-unit/" + id))
                .andExpect(status().isOk());
    }
}