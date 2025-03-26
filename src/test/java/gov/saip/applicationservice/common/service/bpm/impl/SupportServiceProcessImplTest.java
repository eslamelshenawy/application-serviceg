package gov.saip.applicationservice.common.service.bpm.impl;

import gov.saip.applicationservice.common.dto.ProcessRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.service.BPMCallerService;
import gov.saip.applicationservice.common.service.bpm.impl.SupportServiceProcessImpl;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class SupportServiceProcessImplTest {

    @InjectMocks
    private SupportServiceProcessImpl supportServiceProcess;

    @Mock
    private BPMCallerService bpmCallerService;
    @Mock
    private LkApplicationCategoryService lkApplicationCategoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStarSupportServiceProcess() {
        StartProcessDto processDto = StartProcessDto.builder()
                .id("1")
                .fullNameAr("John Doe")
                .fullNameEn("Jane Doe")
                .email("john.doe@example.com")
                .mobile("123456789")
                .requestTypeCode("REQUEST123")
                .identifier("IDENTIFIER123")
                .applicationCategory("CATEGORY123")
                .processName("SampleProcess")
                .requestType("SampleRequest")
                .applicantUserName("johndoe")
                .supportServiceCode("SERVICECODE123")
                .supportServiceTypeCode("SERVICETYPE123")
                .applicationIdColumn("ApplicationIdColumnValue")
                .build();

        Map<String, Object> expectedVars = new HashMap<>();
        expectedVars.put("fullNameAr", "John Doe");
        expectedVars.put("fullNameEn", "Jane Doe");
        expectedVars.put("APPLICANT_USER_NAME", "johndoe");
        expectedVars.put("email", "john.doe@example.com");
        expectedVars.put("mobile", "123456789");
        expectedVars.put("id", "1");
        expectedVars.put("requestTypeCode", "REQUEST123");
        expectedVars.put("identifier", "IDENTIFIER123");
        expectedVars.put("applicationCategory", "CATEGORY123");
        expectedVars.put("supportServiceCode", "SERVICECODE123");
        expectedVars.put("supportServiceTypeCode", "SERVICETYPE123");
        expectedVars.put("REQUESTS_APPLICATION_ID_COLUMN", "ApplicationIdColumnValue");

        LkApplicationCategory lkApplicationCategory = new LkApplicationCategory();
        lkApplicationCategory.setId(1L);

        when(lkApplicationCategoryService.findBySaipCode(processDto.getApplicationCategory())).thenReturn(lkApplicationCategory);
        supportServiceProcess.starSupportServiceProcess(processDto);

        verify(bpmCallerService).startApplicationProcess(any(ProcessRequestDto.class));

    }
}
