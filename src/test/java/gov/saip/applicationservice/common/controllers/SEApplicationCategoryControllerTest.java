package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SEApplicationCategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LkApplicationCategoryService applicationCategoryServiceMock;

    @InjectMocks
    private SEApplicationCategoryControllerTest seApplicationCategoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(seApplicationCategoryController).build();
    }

    @Test
    void testGetAll() throws Exception {
        List<String> categoryList = Arrays.asList("category1", "category2");
        List<LKApplicationCategoryDto> lkApplicationCategoryDtos = List.of(new LKApplicationCategoryDto());
        ApiResponse<List<LKApplicationCategoryDto>> apiResponse = ApiResponse.ok(lkApplicationCategoryDtos);

        when(applicationCategoryServiceMock.getAll()).thenReturn(lkApplicationCategoryDtos);

        mockMvc.perform(get("/se/category"))
                .andExpect(status().isOk());
    }
}