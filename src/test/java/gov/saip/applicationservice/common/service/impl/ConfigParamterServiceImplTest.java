package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerConfigParameterClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ConfigParameterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConfigParamterServiceImplTest {

    @Mock
    private CustomerConfigParameterClient customerConfigParameterClient;

    private ConfigParamterServiceImpl configParamterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        configParamterService = new ConfigParamterServiceImpl(customerConfigParameterClient);
    }

    @Test
    void testGetByKey() {
        String key = "testKey";

        ConfigParameterDto configParameterDto = ConfigParameterDto.builder().id(10L).value("testValue").build();
        ApiResponse apiResponse = ApiResponse.ok(configParameterDto);
        when(customerConfigParameterClient.getConfig(key)).thenReturn(apiResponse);

        String result = configParamterService.getByKey(key);

        assertEquals("testValue", result);
    }

    @Test
    void testGetLongByKey() {
        String key = "testKey";

        ConfigParameterDto configParameterDto = ConfigParameterDto.builder().id(10L).value("123").build();
        ApiResponse apiResponse = ApiResponse.ok(configParameterDto);
        when(customerConfigParameterClient.getConfig(key)).thenReturn(apiResponse);

        Long result = configParamterService.getLongByKey(key);

        assertEquals(123L, result);
    }
}
