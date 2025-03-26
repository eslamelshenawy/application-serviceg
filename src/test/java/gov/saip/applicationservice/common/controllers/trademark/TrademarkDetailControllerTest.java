package gov.saip.applicationservice.common.controllers.trademark;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.trademark.*;
import gov.saip.applicationservice.common.projection.TradeMarkInfo;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.TrademarkApplicationFacade;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TrademarkDetailControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrademarkDetailService trademarkDetailService;

    @Mock
    private ApplicationInfoService applicationInfoService;

    @Mock
    private  TrademarkApplicationFacade trademarkApplicationFacade;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TrademarkDetailController(applicationInfoService, trademarkDetailService,trademarkApplicationFacade)).build();
    }

    @Test
    public void testCreatePct() throws Exception {

        TrademarkDetailReqDto createdPct = new TrademarkDetailReqDto();
        createdPct.setId(1L);
        createdPct.setTagTypeDescId(1);
        createdPct.setTagLanguageId(1);
        createdPct.setMarkTypeId(1);
        Long res = Long.valueOf(1);
        Long appId = Long.valueOf(1);
        when(trademarkDetailService.create(any(TrademarkDetailReqDto.class), anyLong())).thenReturn(res);
        ApiResponse<Long> response = new ApiResponse<>();
        response.setCode(201);
        response.setSuccess(true);
        response.setPayload(res);

        mockMvc.perform(post("/kc/trademark-detail/application/{id}", appId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createdPct)))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindDtoById() throws Exception {
        TrademarkDetailDto res = new TrademarkDetailDto();
        res.setId(1l);
        Long id = Long.valueOf(1);
        when(trademarkDetailService.findDtoById(id)).thenReturn(res);
        ResponseEntity<TrademarkDetailDto> response = ResponseEntity.ok(res);
        mockMvc.perform(get("/kc/trademark-detail/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindDtoByApplicationId() throws Exception {
        TrademarkDetailDto res = new TrademarkDetailDto();
        res.setId(1l);
        Long id = Long.valueOf(1);
        when(trademarkDetailService.findDtoByApplicationId(id)).thenReturn(res);
        ResponseEntity<TrademarkDetailDto> response = ResponseEntity.ok(res);
        mockMvc.perform(get("/kc/trademark-detail/application/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void getTradeMarkInfoByApplicantCode() throws Exception {
        List<TradeMarkInfo> res = new ArrayList<>();
        String code = "code";
        when(trademarkDetailService.getTradeMarKApplicaionInfo(code)).thenReturn(res);
        ResponseEntity<List<TradeMarkInfo>> response = ResponseEntity.ok(res);
        mockMvc.perform(get("/kc/trademark-detail/applicant/{code}", code))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTradeMarkInfoByApplicantCode() throws Exception {
        TradeMarkInfo res = getTradeMarkInfo();
        Long id = Long.valueOf(1);
        when(trademarkDetailService.getTradeMarkByApplicationId(id)).thenReturn(res);
        ResponseEntity<TradeMarkInfo> response = ResponseEntity.ok(res);
        mockMvc.perform(get("/kc/trademark-detail/app/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetApplicationSubstantiveExamination() throws Exception {
        ApplicationTrademarkDetailDto res = new ApplicationTrademarkDetailDto();
        res.setTagLanguage(new LkTagLanguageDto());
        res.setTagTypeDesc(new LkTagTypeDescDto());
        Long applicationId = Long.valueOf(1);
        when(trademarkDetailService.getApplicationTrademarkDetails(applicationId)).thenReturn(res);
        ApiResponse<ApplicationTrademarkDetailDto> response = new ApiResponse<>();
        response.setCode(200);
        response.setSuccess(true);
        response.setPayload(res);
        mockMvc.perform(get("/kc/trademark-detail/substantive-examination/{applicationId}", applicationId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetApplicationInfoXml() throws Exception {
        // Arrange
        Long applicationId = 1L;
        ByteArrayResource file = new ByteArrayResource(new byte[]{1, 2, 3});
        when(trademarkDetailService.getApplicationInfoXml(applicationId)).thenReturn(file);
        // Act & Assert
        mockMvc.perform(get("/kc/trademark-detail/application-info/{applicationId}/xml-file", applicationId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Trademarks.xml"))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_OCTET_STREAM.toString()))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_LENGTH,
                        String.valueOf(file.contentLength())))
                .andExpect(content().bytes(file.getByteArray()));
    }

    private TradeMarkInfo getTradeMarkInfo() {
        return new TradeMarkInfo() {
            @Override
            public Long getAppId() {
                return null;
            }

            @Override
            public String getAppTitleAr() {
                return null;
            }

            @Override
            public String getAppTitleEn() {
                return null;
            }

            @Override
            public String getStatusAr() {
                return null;
            }

            @Override
            public String getApplicationNumber() {
                return null;
            }

            @Override
            public String getExaminerGrantCondition() {
                return null;
            }

            @Override
            public String getStatusEn() {
                return null;
            }

            @Override
            public String getClassificationAr() {
                return null;
            }

            @Override
            public String getClassificationEn() {
                return null;
            }

            @Override
            public String getTmWorkMark() {
                return null;
            }

            @Override
            public String getMarkDescription() {
                return null;
            }

            @Override
            public String getMarkClaimingColor() {
                return null;
            }

            @Override
            public String getTmTypeAr() {
                return null;
            }

            @Override
            public String getTmTypeEn() {
                return null;
            }

            @Override
            public String getCustomerCode() {
                return null;
            }

        };
    }
}