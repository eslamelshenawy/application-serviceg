package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyCommentsDto;
import gov.saip.applicationservice.common.model.CustomerExtClassifyComments;
import gov.saip.applicationservice.common.service.impl.CustomerExtClassifyCommentsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerExtClassifyCommentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerExtClassifyCommentsServiceImpl customerExtClassifyCommentsService;

    @InjectMocks
    private CustomerExtClassifyCommentsController customerExtClassifyCommentsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerExtClassifyCommentsController).build();
    }

    @Test
    public void testGetAll() throws Exception {
        CustomerExtClassifyCommentsDto commentDto1 = new CustomerExtClassifyCommentsDto();
        commentDto1.setId(1L);
        commentDto1.setComment("Test comment 1");

        CustomerExtClassifyCommentsDto commentDto2 = new CustomerExtClassifyCommentsDto();
        commentDto2.setId(2L);
        commentDto2.setComment("Test comment 2");

        CustomerExtClassifyComments customerExtClassifyComments = new CustomerExtClassifyComments();
        customerExtClassifyComments.setComment("Test comment 1");

        CustomerExtClassifyComments customerExtClassifyComments2 = new CustomerExtClassifyComments();
        customerExtClassifyComments2.setComment("Test comment 2");


        List<CustomerExtClassifyComments> value = Arrays.asList(customerExtClassifyComments, customerExtClassifyComments2);
        when(customerExtClassifyCommentsService.findAll())
                .thenReturn(value);

        mockMvc.perform(get("/kc/v1/customer-ext-classify-comments"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateCustomerExtClassifyComment() throws Exception {
        CustomerExtClassifyCommentsDto commentDto = new CustomerExtClassifyCommentsDto();
        commentDto.setComment("Test comment");
        CustomerExtClassifyComments customerExtClassifyComments = new CustomerExtClassifyComments();
        customerExtClassifyComments.setComment("Test comment");

        when(customerExtClassifyCommentsService.createCustomerExtClassifyComment(any(CustomerExtClassifyCommentsDto.class)))
                .thenReturn(1L);

        mockMvc.perform(post("/kc/v1/customer-ext-classify-comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentDto)))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateCustomerExtClassifyComment() throws Exception {
        CustomerExtClassifyCommentsDto commentDto = new CustomerExtClassifyCommentsDto();
        commentDto.setId(1L);
        commentDto.setComment("Test comment");

        when(customerExtClassifyCommentsService.updateCustomerExtClassifyComment(any(CustomerExtClassifyCommentsDto.class)))
                .thenReturn(1L);

        mockMvc.perform(put("/kc/v1/customer-ext-classify-comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentDto)))
                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteCustomerExtClassifyComment() throws Exception {
        Long commentId = 1L;

        doNothing().when(customerExtClassifyCommentsService).deleteCustomerExtClassifyComment(commentId);

        mockMvc.perform(delete("/kc/v1/customer-ext-classify-comments/{commentId}", commentId))
                .andExpect(status().isOk());

    }

}