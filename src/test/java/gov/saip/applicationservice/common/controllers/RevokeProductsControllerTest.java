package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.RevokeProductsDto;
import gov.saip.applicationservice.common.mapper.RevokeProductsMapper;
import gov.saip.applicationservice.common.model.RevokeProducts;
import gov.saip.applicationservice.common.service.RevokeProductsService;
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

import static org.mockito.Mockito.when;

public class RevokeProductsControllerTest {

    @Mock
    private RevokeProductsService revokeProductsService;

    @Mock
    private RevokeProductsMapper revokeProductsMapper;

    @InjectMocks
    private RevokeProductsController revokeProductsController;

    @Mock
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(revokeProductsController).build();

    }

    @Test
    public void testGetRevokeProductsByApplicationSupportServiceId() throws Exception {
        Long applicationSupportServiceId = 1L;

        RevokeProducts mockRevokeProducts = new RevokeProducts();

        when(revokeProductsService.findById(applicationSupportServiceId)).thenReturn(mockRevokeProducts);

        RevokeProductsDto mockRevokeProductsDto = new RevokeProductsDto();

        when(revokeProductsMapper.map(mockRevokeProducts)).thenReturn(mockRevokeProductsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/support-service/revoke-products/service/{id}", applicationSupportServiceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

