package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.repository.ApplicationCustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static gov.saip.applicationservice.common.enums.ApplicationCustomerType.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationCustomerServiceImplTest {

    @InjectMocks
    ApplicationCustomerServiceImpl applicationCustomerService;
    @Mock
    ApplicationCustomerRepository applicationCustomerRepository;

    @Test
    @DisplayName("delete by app id and customer id")
    void deleteByAppAndCustomerId_givenAppAndCustIds_removeEntity() {
        // arrange
        doNothing().when(applicationCustomerRepository).deleteByAppAndCustomerId(anyLong(), anyLong());
        // acct
        applicationCustomerService.deleteByAppAndCustomerId(anyLong(), anyLong());
        // assert
        verify(applicationCustomerRepository).deleteByAppAndCustomerId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("delete by app and customer code ")
    void deleteByAppAndCustomerCode_givenAppAndCustomer_removeEntity() {
        // arrange
        doNothing().when(applicationCustomerRepository).deleteByAppAndCustomerCode(anyString(), anyLong());
        // acct
        applicationCustomerService.deleteByAppAndCustomerCode(anyString(), anyLong());
        // assert
        verify(applicationCustomerRepository).deleteByAppAndCustomerCode(anyString(), anyLong());
    }

    @Test
    @DisplayName("delete by customer code and application ids")
    void deleteApplicationCustomersByType_givenCustomerTypeAndApp_removeEntity() {
            // Arrange
            ApplicationCustomerType customerType = AGENT;
            List<Long> appIds = Collections.singletonList(123L);

            // Act
            applicationCustomerService.deleteApplicationCustomersByTypeAndAppIds(customerType, appIds);

            // Assert
            verify(applicationCustomerRepository).deleteApplicationCustomersByTypeAndAppIds(customerType, appIds);
    }

    @Test
    @DisplayName("delete by application id ")
    void deleteByApplicationId_givenAppId_removeEntity() {
        // arrange
        doNothing().when(applicationCustomerRepository).deleteByApplicationId(anyLong());
        // acct
        applicationCustomerService.deleteByApplicationId(anyLong());
        // assert
        verify(applicationCustomerRepository).deleteByApplicationId(anyLong());
    }


    @Test
    @DisplayName("update type by app id and old type")
    void updateApplicationCustomerTypeByApplication_givenNewAndOldStatus_updateEntity() {
        // Arrange
        ApplicationCustomerType oldType = MAIN_OWNER;
        ApplicationCustomerType newType = PREVIOUS_MAIN_OWNER;
        Long appId = 123L;
        doNothing().when(applicationCustomerRepository).updateApplicationCustomerTypeByApplication(newType, oldType, appId);
        // Act
        applicationCustomerService.updateApplicationCustomerTypeByApplication(newType, oldType, appId);

        // Assert
        verify(applicationCustomerRepository).updateApplicationCustomerTypeByApplication(newType, oldType, appId);
    }
}