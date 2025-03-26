package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.enums.ApplicationListSortingColumns;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.validators.InventorsValidator;
import gov.saip.applicationservice.common.validators.TradeMarkApplicationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class ApplicationControllerTest {

    @InjectMocks
    private ApplicationController applicationController;
    @Mock
    private ApplicationInfoService  applicationInfoService;
    @Mock
    private TradeMarkApplicationValidator tradeMarkApplicationValidator;
    @Mock
    private InventorsValidator inventorsValidator;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    ApplicationInfoMapper applicationInfoMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    public void testUpdateApplicationsData() throws Exception {
        ApplicationDataUpdateDto applicationDataUpdateDto = new ApplicationDataUpdateDto();
        applicationDataUpdateDto.setTitleEn("title");
        applicationDataUpdateDto.setTitleAr("title");
        applicationDataUpdateDto.setNationalSecurity(true);
        applicationDataUpdateDto.setPartialApplication(true);
        Long id = 1L;
        Mockito.doReturn(id).when(applicationInfoService).updateApplicationMainData(ArgumentMatchers.any(ApplicationDataUpdateDto.class), ArgumentMatchers.anyLong());
        ApiResponse<Long> expectedResponse = ApiResponse.ok(id);

        mockMvc.perform(MockMvcRequestBuilders.post("/kc/applications/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applicationDataUpdateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddInventor() throws Exception {
        InventorRequestsDto inventorRequestsDto = new InventorRequestsDto();
        Mockito.doNothing().when(tradeMarkApplicationValidator).validate(inventorsValidator, null);
        when(applicationInfoService.addInventor(inventorRequestsDto)).thenReturn(1L);
        ApiResponse<Long> expectedResponse = ApiResponse.ok(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/kc/applications/inventor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventorRequestsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteRelvant() throws Exception {
        Long id = 1L;
        ApiResponse<?> expectedResponse = ApiResponse.ok("");

        mockMvc.perform(MockMvcRequestBuilders.delete("/kc/applications/relvantType/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(applicationInfoService, Mockito.times(1)).deleteAppRelvant(id);
    }

    @Test
    public void testGetApplication() throws Exception {
        Long id = 1L;
        ApplicationInfoDto applicationInfoDto = new ApplicationInfoDto();
        ApiResponse<ApplicationInfoDto> expectedResponse = ApiResponse.ok(applicationInfoDto);
        when(applicationInfoService.getApplication(id)).thenReturn(applicationInfoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetApplicationClassificationById() throws Exception {
        Long id = 1L;
        ApplicationClassificationDto applicationClassificationDto = new ApplicationClassificationDto();
        ApiResponse<ApplicationClassificationDto> expectedResponse = ApiResponse.ok(applicationClassificationDto);
        when(applicationInfoService.getApplicationClassificationById(id)).thenReturn(applicationClassificationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/application-classification/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testListApplicants() throws Exception {
        Long id = 1L;
        List<ApplicantsDto> applicantsDtoList = new ArrayList<>();
        ApiResponse<List<ApplicantsDto>> expectedResponse = ApiResponse.ok(applicantsDtoList);
        when(applicationInfoService.listApplicants(id)).thenReturn(applicantsDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/{id}/applicants", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testSaveApplication() throws Exception {
        Long id = 1l;
        ApplicantsRequestDto applicantsRequestDto = new ApplicantsRequestDto();
        Mockito.doNothing().when(tradeMarkApplicationValidator).validate(applicantsRequestDto, null);
        when(applicationInfoService.saveApplication(ArgumentMatchers.any(ApplicantsRequestDto.class))).thenReturn(id);
        ApiResponse<Long> expectedResponse = ApiResponse.ok(id);
        mockMvc.perform(MockMvcRequestBuilders.post("/kc/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applicantsRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateSubstantiveExamination() throws Exception {
        Long id = 1L;
        Boolean substantiveExamination = true;
        ApiResponse<Object> expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.updateSubstantiveExamination(substantiveExamination, id)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/kc/applications/{id}/substantive-examination/{substantiveExamination}", id, substantiveExamination))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetApplicationSubstantiveExamination() throws Exception {
        Long applicationId = 1L;
        ApplicationSubstantiveExaminationRetrieveDto expectedResponse = new ApplicationSubstantiveExaminationRetrieveDto();
        when(applicationInfoService.getApplicationSubstantiveExamination(applicationId)).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/substantive-examination/{applicationId}", applicationId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUserRequestTypes() throws Exception {
        ApiResponse expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.getUserRequestTypes()).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/task/request-type"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetRequestApplication() throws Exception {
        Long applicationId = 1L;
        ApiResponse expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.getApplicationSummary(applicationId, null)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/summary/{appId}", applicationId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetSupportServiceCostBySaipCode() throws Exception {
        String code = "123";
        String applicantCategorySaipCode = "456";
        String applicationCategorySaipCode = "789";
        ApiResponse<Double> expectedResponse = ApiResponse.ok(Double.valueOf(100));
        when(applicationInfoService.getSupportServiceCost(code, applicantCategorySaipCode, applicationCategorySaipCode)).thenReturn(Double.valueOf(100));
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/support-service/cost")
                        .param("code", code)
                        .param("applicantCategorySaipCode", applicantCategorySaipCode)
                        .param("applicationCategorySaipCode", applicationCategorySaipCode))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testChangeStatus() throws Exception {
        Long id = 1L;
        String code = "NEW";
        mockMvc.perform(MockMvcRequestBuilders.put("/kc/applications/{id}/status/{code}", id, code))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetApplicationPaymentPreparationInfo() throws Exception {
        Long applicationId = 1L;
        ApiResponse<ApplicationInfoPaymentDto> expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.getApplicationPaymentPreparationInfo(applicationId)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/{id}/payment-preperation-info", applicationId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testListApplicationsByApplicationCategoryAndUserId() throws Exception {
        String applicationCategory = "cat";
        Long categoryId = 1L;
        String applicationNumber = "123";
        Long appId = 2L;
        Long userId = 3L;
        Integer page = 1;
        Integer limit = 10;
        ApiResponse<PaginationDto> expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.getApplicationListByApplicationCategoryAndUserId(applicationCategory, userId ,page, limit, ApplicationListSortingColumns.id, Sort.Direction.DESC)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/")
                        .param("applicationCategory", applicationCategory)
                        .param("userId", userId.toString())
                        .param("page", page.toString())
                        .param("limit", limit.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGenerateApplicationNumber() throws Exception {
        Long id = 1L;
        ApplicationNumberGenerationDto dto = new ApplicationNumberGenerationDto();
        ApiResponse<Long> expectedResponse = ApiResponse.ok(2L);
        when(applicationInfoService.generateApplicationNumber(dto, id)).thenReturn(2L);
        mockMvc.perform(MockMvcRequestBuilders.post("/kc/applications/{id}/application-number", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetApplicationsByAgentAndUserIds() throws Exception {
        Long agentId = 1L;
        String customerCode = "code001";
        Long categoryId = 3L;
        String applicationNumber = "123";
        String appStatus = "NEW";
        Integer page = 1;
        Integer limit = 10;
        ApiResponse<PaginationDto> expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.listApplicationsAgentId(categoryId, applicationNumber, appStatus, agentId, customerCode, page, limit)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/user/{userId}/agent/{agentId}", customerCode, agentId)
                        .param("categoryId", categoryId.toString())
                        .param("applicationNumber", applicationNumber)
                        .param("appStatus", appStatus)
                        .param("page", page.toString())
                        .param("limit", limit.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetNotAssignedToAgentApplications() throws Exception {
        String customerCode = "code001";
        Long categoryId = 2L;
        String applicationNumber = "123";
        String appStatus = "NEW";
        Integer page = 0;
        Integer limit = 10;
        ApiResponse<PaginationDto> expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.getNotAssignedToAgentApplications(categoryId, applicationNumber, appStatus, customerCode, page, limit)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/without-agent/user/{customerCode}", customerCode)
                        .param("categoryId", categoryId.toString())
                        .param("applicationNumber", applicationNumber)
                        .param("appStatus", appStatus)
                        .param("page", page.toString())
                        .param("limit", limit.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAssignedToAgentApplications() throws Exception {
        String customerCode = "code001";
        Long categoryId = 2L;
        String applicationNumber = "123";
        String appStatus = "NEW";
        Integer page = 0;
        Integer limit = 10;
        ApiResponse<PaginationDto> expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.getAssignedToAgentApplications(categoryId, applicationNumber, appStatus, customerCode, page, limit)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/with-agent/user/{customerCode}", customerCode)
                        .param("categoryId", categoryId.toString())
                        .param("applicationNumber", applicationNumber)
                        .param("appStatus", appStatus)
                        .param("page", page.toString())
                        .param("limit", limit.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetApplicationInfoPayment() throws Exception {
        Long id = 1L;
        ApiResponse<ApplicationInfoPaymentDto> expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.getApplicationInfoPayment(id)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/{id}/payment", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testUpdateAccelerated() throws Exception {
        Long id = 1L;
        Boolean accelerated = true;
        ApiResponse<Object> expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.updateAccelerated(accelerated, id)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/kc/applications/{id}/accelerated/{accelerated}", id, accelerated)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testListApplicationsByApplicationCategoryAndUserId_WithAllParams() throws Exception {
        String applicationCategory = "cat";
        Long categoryId = 1L;
        String applicationNumber = "123";
        Long appId = 2L;
        Long userId = 3L;
        Integer page = 1;
        Integer limit = 10;
        ApiResponse<PaginationDto> expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.getApplicationListByApplicationCategoryAndUserId(applicationCategory, userId,page, limit, ApplicationListSortingColumns.id, Sort.Direction.DESC)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/")
                        .param("applicationCategory", applicationCategory)

                        .param("userId", userId.toString())
                        .param("page", page.toString())
                        .param("limit", limit.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testListApplicationsByApplicationCategoryAndUserId_WithRequiredParams() throws Exception {
        Long userId = 3L;
        ApiResponse<PaginationDto> expectedResponse = ApiResponse.ok(null);
        when(applicationInfoService.getApplicationListByApplicationCategoryAndUserId(null, userId,1, 10, ApplicationListSortingColumns.id, Sort.Direction.DESC)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/")
                        .param("userId", userId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testSubmitApplicationInfoPayment() throws Exception {
        Long id = 1L;
        ApplicationInfoPaymentDto dto = new ApplicationInfoPaymentDto();
        ApiResponse<Long> expectedResponse = ApiResponse.ok(id);
        when(applicationInfoService.submitApplicationInfoPayment(ArgumentMatchers.anyLong(), ArgumentMatchers.any(ApplicationInfoPaymentDto.class))).thenReturn(id);
        mockMvc.perform(MockMvcRequestBuilders.post("/kc/applications/{id}/payment", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateExamination() throws Exception {
        Long applicationId = 1L;
        ApplicationSubstantiveExaminationDto dto = new ApplicationSubstantiveExaminationDto();
        mockMvc.perform(MockMvcRequestBuilders.put("/kc/applications/substantive-examination/{applicationId}", applicationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(applicationInfoService).updateExamination(dto, applicationId);
    }

    @Test
    public void checkIfApplicationNumberExistsTest() throws Exception {
        String appNumber = "123";
        Long partialAppId = 1L;
        when(applicationInfoService.checkMainApplicationExists(appNumber, partialAppId)).thenReturn(new ApplicationInfo());
        when(applicationInfoMapper.mapAppInfoToBaseInfoDto(new ApplicationInfo())).thenReturn(new ApplicationInfoBaseDto());
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/applications/check-exists/{appNumber}", appNumber)
                        .param("partialAppId", partialAppId.toString()))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

}