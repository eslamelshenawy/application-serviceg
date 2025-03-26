package gov.saip.applicationservice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.controllers.LkTaskEqmItemController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LkTaskEqmItemDto;
import gov.saip.applicationservice.common.mapper.LkTaskEqmItemMapper;
import gov.saip.applicationservice.common.model.LkTaskEqmItem;
import gov.saip.applicationservice.common.service.LkTaskEqmItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LkTaskEqmItemControllerTest {

    @Mock
    private MockMvc mockMvc;


    @Mock
    private LkTaskEqmItemService taskEqmItemService;

    @Mock
    private LkTaskEqmItemMapper taskEqmItemMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    LkTaskEqmItemController lkTaskEqmItemController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lkTaskEqmItemController).build();

    }

    @Test
    public void testFindByApplicationCategorySaipCode() throws Exception {
        String saipCode = "SAIP_CODE";

        List<LkTaskEqmItem> taskEqmItems = new ArrayList<>();
        taskEqmItems.add(new LkTaskEqmItem());
        taskEqmItems.add(new LkTaskEqmItem());

        when(taskEqmItemService.findByTypeCode(saipCode)).thenReturn(taskEqmItems);

        List<LkTaskEqmItemDto> taskEqmItemDtos = new ArrayList<>();
        taskEqmItemDtos.add(new LkTaskEqmItemDto());
        taskEqmItemDtos.add(new LkTaskEqmItemDto());

        when(taskEqmItemMapper.map(taskEqmItems)).thenReturn(taskEqmItemDtos);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/kc/task-eqm-item/category")
                        .param("categoryCode", saipCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        ApiResponse<List<LkTaskEqmItemDto>> response = objectMapper.readValue(responseContent, ApiResponse.class);

        verify(taskEqmItemService, times(1)).findByTypeCode(saipCode);
        verify(taskEqmItemMapper, times(1)).map(taskEqmItems);

    }
}

