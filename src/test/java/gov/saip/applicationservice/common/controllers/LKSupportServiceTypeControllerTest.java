package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LKSupportServiceTypeDto;
import gov.saip.applicationservice.common.mapper.LKSupportServiceTypeMapper;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import gov.saip.applicationservice.common.service.LKSupportServiceTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LKSupportServiceTypeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LKSupportServiceTypeService lkSupportServiceTypeServiceMock;

    @InjectMocks
    private LKSupportServiceTypeController lkSupportServiceTypeController;

    @Mock
    private  LKSupportServiceTypeMapper lkSupportServiceTypeMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lkSupportServiceTypeController).build();
    }

    @Test
    void testGetAllReports() throws Exception {
        String requestType = "test";
        LKSupportServiceTypeDto dto1 = new LKSupportServiceTypeDto();
        LKSupportServiceTypeDto dto2 = new LKSupportServiceTypeDto();
        List<LKSupportServiceTypeDto> dtoList = Arrays.asList(dto1, dto2);

        LKSupportServiceType entity1 = new LKSupportServiceType();
        LKSupportServiceType entity2 = new LKSupportServiceType();
        List<LKSupportServiceType> entityList = Arrays.asList(entity1, entity2);
        ApiResponse<List<LKSupportServiceTypeDto>> apiResponse = ApiResponse.ok(dtoList);

        when(lkSupportServiceTypeServiceMock.getAllByRequestType(anyString(), anyLong())).thenReturn(entityList);
        when(lkSupportServiceTypeMapper.map(Mockito.anyList())).thenReturn(dtoList);

        mockMvc.perform(get("/kc/support-service-type/" + requestType + "/type"))
                .andExpect(status().isOk());
    }
}