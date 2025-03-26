package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSupportServicesTypeDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.search.SearchDto;
import gov.saip.applicationservice.common.mapper.ApplicationSupportServicesTypeMapper;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


public class ApplicationSupportServicesTypeControllerTest {

    @InjectMocks
    private ApplicationSupportServicesTypeController applicationSupportServicesTypeController;
    @Mock
    private ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    @Mock
    private ApplicationSupportServicesTypeMapper applicationSupportServicesTypeMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationSupportServicesTypeController).build();
    }

    @Test
    public void testGetAllSupportServicesByApplication() throws Exception {
        Long appId = 1L;
        List<ApplicationSupportServicesType> similarDocumentList = new ArrayList<>();
        ApplicationSupportServicesType similarDocument1 = new ApplicationSupportServicesType();
        similarDocument1.setId(1L);
        similarDocumentList.add(similarDocument1);

        ApplicationSupportServicesTypeDto similarDocumentDto1 = new ApplicationSupportServicesTypeDto();
        similarDocumentDto1.setId(1L);

        ApiResponse<List<ApplicationSupportServicesTypeDto>> expectedResponse = ApiResponse.ok(List.of(similarDocumentDto1));

        Mockito.when(applicationSupportServicesTypeService.getAllByApplicationId(ArgumentMatchers.anyLong())).thenReturn(similarDocumentList);
        Mockito.when(applicationSupportServicesTypeMapper.map(similarDocumentList)).thenReturn(List.of(similarDocumentDto1));

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/support-service/previous-requests/{appId}/application", appId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testListApplicationsByApplicationCategoryAndUserId() throws Exception {
        Long appId = 1L;
        Integer page = 1;
        Integer limit = 1;
        String query = "query";
        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;
        Long userId = 1l;
        Long applicationId = 1l;
        Long supportServiceTypeId = 1l;
        PaginationDto paginationDto = new PaginationDto();
        ApiResponse<PaginationDto> expectedResponse = ApiResponse.ok(paginationDto);
        SearchDto searchDto = new SearchDto();

        Mockito.when(applicationSupportServicesTypeService.getPreviousRequestsByFilter(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyString(), searchDto,
                ArgumentMatchers.anyBoolean())).thenReturn(paginationDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/support-service/previous-requests/filter")
                        .param("appId", String.valueOf(appId))
                        .param("page", String.valueOf(page))
                        .param("limit", String.valueOf(limit))
                        .param("query", query)
                        .param("fromDate", String.valueOf(fromDate))
                        .param("toDate", String.valueOf(toDate))
                        .param("userId", String.valueOf(userId))
                        .param("applicationId", String.valueOf(applicationId))
                        .param("supportServiceTypeId", String.valueOf(supportServiceTypeId))
                        .header("X-Customer-Id", "1")
                        .header("accept", MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test
    public void testUpdateRequestStatusById() throws Exception {
        Long id = 1L;
        Integer newStatusId = 1;
        Mockito.doNothing().when(applicationSupportServicesTypeService).updateRequestStatusById(id, newStatusId);
        mockMvc.perform(MockMvcRequestBuilders.put("/internal-calling/support-service/previous-requests/{id}/status/{newStatusId}", id, newStatusId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("SUCCESS"));;
    }

}