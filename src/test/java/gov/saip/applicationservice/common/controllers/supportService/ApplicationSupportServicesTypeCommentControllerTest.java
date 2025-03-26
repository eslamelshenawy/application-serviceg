package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.common.dto.supportService.ApplicationSupportServicesTypeCommentDto;
import gov.saip.applicationservice.common.mapper.supportService.ApplicationSupportServicesTypeCommentMapper;
import gov.saip.applicationservice.common.model.supportService.ApplicationSupportServicesTypeComment;
import gov.saip.applicationservice.common.service.supportService.ApplicationSupportServicesTypeCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class ApplicationSupportServicesTypeCommentControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private ApplicationSupportServicesTypeCommentService commentService;

    @Mock
    private ApplicationSupportServicesTypeCommentMapper commentMapper;

    @InjectMocks
    private ApplicationSupportServicesTypeCommentController commentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

    }

    @Test
    public void testGetAllApplicationSupportServicesTypeCommentsByApplicationSupportServiceId() throws Exception {
        Long applicationSupportServiceId = 1L;
        List<ApplicationSupportServicesTypeComment> commentList = new ArrayList<>();
        when(commentService.getAllApplicationSupportServicesTypeCommentsByApplicationSupportServiceId(applicationSupportServiceId)).thenReturn(commentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/support-service-comment/application-service/{applicationSupportServiceId}/comment", applicationSupportServiceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testInsertAddNewApplicationSupportServicesTypeComment() throws Exception {
        ApplicationSupportServicesTypeCommentDto commentDto = new ApplicationSupportServicesTypeCommentDto();

        ApplicationSupportServicesTypeComment comment = new ApplicationSupportServicesTypeComment();

        when(commentMapper.unMap(commentDto)).thenReturn(comment);
        when(commentService.insert(comment)).thenReturn(comment);

        mockMvc.perform(MockMvcRequestBuilders.post("/kc/support-service-comment/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commentDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetLastApplicationSupportServicesTypeCommentByApplicationSupportServiceId() throws Exception {
        Long applicationSupportServiceId = 1L;
        ApplicationSupportServicesTypeComment comment = new ApplicationSupportServicesTypeComment();

        when(commentService.getLastApplicationSupportServicesTypeCommentByApplicationSupportServiceId(applicationSupportServiceId)).thenReturn(comment);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/support-service-comment/application-service/{applicationSupportServiceId}/lastComment", applicationSupportServiceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

