package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApplicationDrawingDto;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.mapper.ApplicationDrawingMapper;
import gov.saip.applicationservice.common.response.ApplicationDrawingResponse;
import gov.saip.applicationservice.common.service.ApplicationDrawingService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ApplicationDrawingControllerTest {

    @Mock
    private ApplicationDrawingService applicationDrawingService;

    @Mock
    private ApplicationDrawingMapper applicationDrawingMapper;

    @InjectMocks
    private ApplicationDrawingController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAppDrawing() throws Exception {
        long appId = 1L;
        List<ApplicationDrawingDto> dtos = Arrays.asList(
                new ApplicationDrawingDto(1L, new DocumentLightDto(), "Shape1", true),
                new ApplicationDrawingDto(2L, new DocumentLightDto(), "Shape2", false)
        );
        ApplicationDrawingResponse response = new ApplicationDrawingResponse();
        response.setDrawings(dtos.subList(1, 2)); // notDefaultDrawings
        response.setMainDraw(dtos.get(0)); // Main Draw

        when(applicationDrawingService.getAppDrawing(appId)).thenReturn(Collections.emptyList());
        when(applicationDrawingMapper.map(anyList())).thenReturn(dtos);

        mockMvc.perform(get("/kc/app-drawing/shapes/{appId}", appId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").exists())
                .andExpect(jsonPath("$.payload.mainDraw").exists())
                .andExpect(jsonPath("$.payload.drawings").isArray())
                .andExpect(jsonPath("$.payload.drawings[0]").exists());

        verify(applicationDrawingService, times(1)).getAppDrawing(appId);
        verify(applicationDrawingMapper, times(1)).map(anyList());
    }

    @Test
    void deleteByAppAndDocumentId() throws Exception {
        long appId = 1L;
        long documentId = 1L;

        mockMvc.perform(delete("/kc/app-drawing/shapes/{appId}/{documentId}", appId, documentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(applicationDrawingService, times(1)).deleteByAppAndDocumentId(appId, documentId);
    }

    @Test
    void handleAppDrawingResponse() {
        List<ApplicationDrawingDto> dtos = Arrays.asList(
                new ApplicationDrawingDto(1L, new DocumentLightDto(), "Shape1", true),
                new ApplicationDrawingDto(2L, new DocumentLightDto(), "Shape2", false)
        );

        ApplicationDrawingResponse response = controller.handleAppDrawingResponse(dtos);

        assertNotNull(response);
        assertNotNull(response.getMainDraw());
        assertNotNull(response.getDrawings());
        assertEquals(1, response.getDrawings().size());
    }

}

