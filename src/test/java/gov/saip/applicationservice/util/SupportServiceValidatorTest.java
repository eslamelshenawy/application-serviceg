package gov.saip.applicationservice.util;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerConfigParameterClient;
import gov.saip.applicationservice.common.dto.ApplicationListDto;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.service.ApplicationUserService;
import gov.saip.applicationservice.common.service.ConfigParameterService;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.SupportServiceValidator;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static gov.saip.applicationservice.util.Constants.ErrorKeys.*;
import static gov.saip.applicationservice.util.Constants.PASSED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupportServiceValidatorTest {

    @Mock
    private ConfigParameterService configParameterService;
    @Mock
    private ApplicationInstallmentService applicationInstallmentService;
    @Mock
    private ApplicationUserService applicationUserService;
    @Mock
    private BPMCallerServiceImpl bpmCallerService;
    @Mock
    private CustomerConfigParameterClient customerConfigParameterClient;

    @InjectMocks
    @Spy
    SupportServiceValidator supportServiceValidator;

    @DisplayName("checking if app status matches the renewal service pre conditions")
    @Test
    public void test_checkRenewalStatus_when_givenAcceptanceStatusTradeMarkCategory_returnPassed()
    {
        //Arrange
        ApplicationInfo app = new ApplicationInfo(55L);
        LkApplicationStatus applicationStatus = new LkApplicationStatus(13L,"ممنوح","ACCEPTANCE","ACCEPTANCE");
        LkApplicationCategory applicationCategory = new LkApplicationCategory(1L,"","TRADEMARK","TRADEMARK");
        app.setApplicationStatus(applicationStatus);
        app.setCategory(applicationCategory);
        // Act
        String result = supportServiceValidator.checkRenewalStatus(app);
        //assert
        assertEquals(result,PASSED);
    }@DisplayName("checking if app status does not matches the renewal service pre conditions")
    @Test
    public void test_checkRenewalStatus_when_givenRejectionStatusTradeMarkCategory_throwBusinessException()
    {
        try (MockedStatic<Utilities> utilitiesMockedStatic = mockStatic(Utilities.class);){
            //Arrange
            ApplicationInfo app = new ApplicationInfo(55L);
            LkApplicationStatus applicationStatus = new LkApplicationStatus(13L,"ممنوح","REJECTION","REJECTION");
            LkApplicationCategory applicationCategory = new LkApplicationCategory(5L,"علامة تجارية","TRADEMARK","TRADEMARK");
            app.setApplicationStatus(applicationStatus);
            app.setCategory(applicationCategory);

            utilitiesMockedStatic.when(Utilities::isExternal).thenReturn(true);
            //Act & Assert
            RuntimeException exception = assertThrows(BusinessException.class, () -> supportServiceValidator.checkRenewalStatus(app) );
            assertEquals(EXCEPTION_APPLICATION_STATUS_CHANGED,exception.getMessage());
        }
    }
    @DisplayName("checking if app category does not matches the renewal service pre conditions")
    @Test
    public void test_checkRenewalStatus_when_givenAcceptanceStatusPatentCategory_throwBusinessException()
    {
        //Arrange
        ApplicationInfo app = new ApplicationInfo(55L);
        LkApplicationStatus applicationStatus = new LkApplicationStatus(28L,"مقبول","ACCEPTANCE","ACCEPTANCE");
        LkApplicationCategory applicationCategory = new LkApplicationCategory(2L,"براءات","TRADEMARK","TRADEMARK");
        app.setApplicationStatus(applicationStatus);
        app.setCategory(applicationCategory);

        String res = supportServiceValidator.checkRenewalStatus(app);

        assertEquals(res, PASSED);


    }
    @DisplayName("checking renewal service condition when current date is valid for TradeMark")
    @Test
    public void test_checkRenewalFeesPayConditions_when_currentDateIsValidPaymentDateAndAppCategoryIsTrademark_returnsPassed()
    {
        //Arrange
        ApplicationInfo app = new ApplicationInfo(55L);
        LkApplicationStatus applicationStatus = new LkApplicationStatus(28L, "مقبول", "ACCEPTANCE", "ACCEPTANCE");
        LkApplicationCategory applicationCategory = new LkApplicationCategory(5L, "علامة تجارية", "TRADEMARK", "TRADEMARK");
        app.setApplicationStatus(applicationStatus);
        app.setCategory(applicationCategory);
        LocalDate localDate = LocalDate.of(2014, 8, 30);
        LocalTime timeOfDay = LocalTime.of(12, 0, 0); // 12:00:00
        LocalDateTime date = localDate.atTime(timeOfDay);
        LocalDateTime filingDate = date;
        app.setFilingDate(filingDate);
        doReturn(PASSED).when(supportServiceValidator).checkRenewalStatus(app);
        when(configParameterService.getLongByKey("RENEWAL_TM_END_YEAR")).thenReturn(10L);
        when(configParameterService.getLongByKey("RENEWAL_TM_END_MONTHS")).thenReturn(6L);
        when(configParameterService.getLongByKey("RENEWAL_TM_START_YEAR")).thenReturn(9L);
        when(applicationInstallmentService.getLastDueDate(55L, InstallmentStatus.NEW)).thenReturn(null);
        // Act

        String result = supportServiceValidator.checkRenewalFeesPayConditions(app);
        //Assert
        assertEquals(result,PASSED);

    }
    @DisplayName("checking renewal service condition when current date is before allowed payment valid date for TradeMark")
    @Test
    public void test_checkRenewalFeesPayConditions_when_currentDateIsBeforeValidPaymentDateAndAppCategoryIsTrademark_returnsPassed()
    {
        //Arrange
        ApplicationInfo app = new ApplicationInfo(55L);
        LkApplicationStatus applicationStatus = new LkApplicationStatus(28L, "مقبول", "ACCEPTANCE", "ACCEPTANCE");
        LkApplicationCategory applicationCategory = new LkApplicationCategory(5L, "علامة تجارية", "TRADEMARK", "TRADEMARK");
        app.setApplicationStatus(applicationStatus);
        app.setCategory(applicationCategory);
        LocalDate localDate = LocalDate.of(2016, 8, 30);
        LocalTime timeOfDay = LocalTime.of(12, 0, 0); // 12:00:00
        LocalDateTime date = localDate.atTime(timeOfDay);
        LocalDateTime filingDate = date;
        app.setFilingDate(filingDate);
        doReturn(PASSED).when(supportServiceValidator).checkRenewalStatus(app);
        when(configParameterService.getLongByKey("RENEWAL_TM_END_YEAR")).thenReturn(10L);
        when(configParameterService.getLongByKey("RENEWAL_TM_END_MONTHS")).thenReturn(6L);
        when(configParameterService.getLongByKey("RENEWAL_TM_START_YEAR")).thenReturn(9L);
        when(applicationInstallmentService.getLastDueDate(55L,InstallmentStatus.NEW)).thenReturn(LocalDateTime.now());
        //Act & Assert
        RuntimeException exception = assertThrows(BusinessException.class, () -> supportServiceValidator.checkRenewalFeesPayConditions(app) );
        assertEquals(EXCEPTION_EXPLAIN_BLOCK_REASON_ِRENEWAL,exception.getMessage());

    }
    @DisplayName("checking renewal service condition when current date is after allowed payment valid date for TradeMark")
    @Test
    public void test_checkRenewalFeesPayConditions_when_currentDateIsAfterValidPaymentDateAndAppCategoryIsTrademark_returnsPassed()
    {
        //Arrange
        ApplicationInfo app = new ApplicationInfo(55L);
        LkApplicationStatus applicationStatus = new LkApplicationStatus(28L, "مقبول", "ACCEPTANCE", "ACCEPTANCE");
        LkApplicationCategory applicationCategory = new LkApplicationCategory(5L, "علامة تجارية", "TRADEMARK", "TRADEMARK");
        app.setApplicationStatus(applicationStatus);
        app.setCategory(applicationCategory);
        LocalDate localDate = LocalDate.of(2011, 8, 30);
        LocalTime timeOfDay = LocalTime.of(12, 0, 0); // 12:00:00
        LocalDateTime date = localDate.atTime(timeOfDay);
        LocalDateTime filingDate = date;
        app.setFilingDate(filingDate);
        doReturn(PASSED).when(supportServiceValidator).checkRenewalStatus(app);
        when(configParameterService.getLongByKey("RENEWAL_TM_END_YEAR")).thenReturn(10L);
        when(configParameterService.getLongByKey("RENEWAL_TM_END_MONTHS")).thenReturn(6L);
        when(configParameterService.getLongByKey("RENEWAL_TM_START_YEAR")).thenReturn(9L);
        when(applicationInstallmentService.getLastDueDate(55L,InstallmentStatus.NEW)).thenReturn(null);
        //Act & Assert
        RuntimeException exception = assertThrows(BusinessException.class, () -> supportServiceValidator.checkRenewalFeesPayConditions(app) );
        assertEquals(EXCEPTION_EXPLAIN_BLOCK_ِRENEWAL,exception.getMessage());

    }
}
