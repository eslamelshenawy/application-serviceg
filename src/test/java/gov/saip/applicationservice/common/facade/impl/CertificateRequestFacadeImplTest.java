package gov.saip.applicationservice.common.facade.impl;

import gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusLightDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.service.impl.ApplicationServiceImpl;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


public class CertificateRequestFacadeImplTest {

    @Mock
    ApplicationServiceImpl applicationServiceImpl;

    @InjectMocks
    CertificateRequestFacadeImpl certificateRequestFacade;

    @Mock
    protected HttpServletRequest request;

    @Mock
    ApplicationInfoRepository applicationInfoRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(request.getHeader("X-Customer-Id")).thenReturn("456722");

    }


    @Test
    public void getApplicationsAccordingCertificatesPreConditionsTest() {
        String type = "ISSUE_CERTIFICATE";
        Long categoryId = 1L;
        String applicationNumber = "123";
        ApplicationInfoBaseDto applicationInfoBase1 = new ApplicationInfoBaseDto();
        List<ApplicationRelevantTypeDto> applicationRelevantTypeDtos = new ArrayList<>();
        ApplicationRelevantTypeDto applicationRelevantTypeDto = new ApplicationRelevantTypeDto();
        applicationRelevantTypeDto.setCustomerCode("np-123");
        applicationRelevantTypeDtos.add(applicationRelevantTypeDto);
        applicationInfoBase1.setApplicationId(123L);
        applicationInfoBase1.setApplicationRelevantTypes(applicationRelevantTypeDtos);
        ApplicationInfoBaseDto applicationInfoBase2 = new ApplicationInfoBaseDto();
        applicationInfoBase2.setApplicationId(1234L);
        List<ApplicationInfoBaseDto> applicationInfoBaseDtos = new ArrayList<>();
        ApplicationInfoBaseDto applicationInfoBaseDto = new ApplicationInfoBaseDto();
        LkApplicationStatusLightDto applicationStatusLightDto = new LkApplicationStatusLightDto();
        applicationStatusLightDto.setCode("ACCEPTANCE");
        applicationInfoBaseDto.setApplicationStatus(applicationStatusLightDto);
        applicationInfoBaseDtos.add(applicationInfoBaseDto);
        PaginationDto<List<ApplicationInfoBaseDto>> page = new PaginationDto<>();
        page.setContent(applicationInfoBaseDtos);

        List<ApplicationInfo> applicationInfos = new ArrayList<>();
        ApplicationInfo applicationInfo1 = new ApplicationInfo();
        applicationInfo1.setId(1L);
        applicationInfo1.setCategory(new LkApplicationCategory());
        applicationInfo1.setApplicationStatus(new LkApplicationStatus());
        ApplicationRelevantType applicationRelevantTypeDto1 = new ApplicationRelevantType();
        applicationRelevantTypeDto1.setCustomerCode("ABC");
        List<ApplicationRelevantType> applicationRelevantTypes1 = new ArrayList<>();
        applicationRelevantTypes1.add(applicationRelevantTypeDto1);
        applicationInfo1.setApplicationRelevantTypes(applicationRelevantTypes1);
        applicationInfo1.setApplicationNumber("123");
        applicationInfo1.setTitleEn("Title EN");
        applicationInfo1.setTitleAr("Title AR");
        applicationInfo1.setFilingDate(LocalDateTime.now());
        applicationInfo1.setPartialApplication(false);
        LkApplicationStatus applicationStatus1 = new LkApplicationStatus();
        applicationStatus1.setCode("code");
        applicationStatus1.setIpsStatusDescEn("IPS EN");
        applicationStatus1.setIpsStatusDescAr("IPS AR");
        applicationInfo1.setApplicationStatus(applicationStatus1);
        applicationInfo1.setModifiedDate(LocalDateTime.now().minusDays(1));
        applicationInfos.add(applicationInfo1);
        Page<ApplicationInfo> applicationInfoPages = new PageImpl<>(applicationInfos);


        when(applicationInfoRepository.getApplicationListByApplicationCategoryAndUserIdsForSupportServices(
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any()
        )).thenReturn(applicationInfoPages);

        List<ApplicationCustomerType> customerTypes = new ArrayList<>();
        customerTypes.add(ApplicationCustomerType.AGENT);

        when(applicationServiceImpl.findApplicationsBaseInfoDto(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.anyInt(),
                Mockito.anyInt()
        )).thenReturn(page);

        doNothing().when(applicationServiceImpl).assignDataOfCustomersCode(applicationRelevantTypeDtos);


        BusinessException exception = assertThrows(BusinessException.class, () -> {
            ApplicationInfoBaseDto applicationInfoBase = certificateRequestFacade.getApplicationsAccordingCertificatesPreConditions(type, categoryId, applicationNumber);
        });

        assertEquals(Constants.ErrorKeys.INVALID_CERTIFICATE, exception.getMessage());
    }
}


