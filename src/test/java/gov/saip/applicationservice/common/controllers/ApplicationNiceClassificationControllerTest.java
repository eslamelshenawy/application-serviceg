package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.model.ApplicationNiceClassification;
import gov.saip.applicationservice.common.service.ApplicationNiceClassificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ApplicationNiceClassificationControllerTest {
    @Mock
    private ApplicationNiceClassificationService applicationNiceClassificationService;
    @InjectMocks
    private ApplicationNiceClassificationController applicationNiceClassificationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Set up a mock HTTP request context
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testGetAllApplicationNiceClassifications() {
        Long appId = 1L;
        List<ClassificationDto> classificationDtos = new ArrayList<>();
        when(applicationNiceClassificationService.listSelectedApplicationNiceClassifications(appId)).thenReturn(classificationDtos);

        ApiResponse<List<ClassificationDto>> response = applicationNiceClassificationController.getAllApplicationOtherDocuments(appId);

        assertEquals(ApiResponse.ok(classificationDtos), response);
    }

    @Test
    public void testGetAppNiceClassification() {
        Long id = 1L;
        List<ClassificationLightDto> lightNiceClassifications = new ArrayList<>();
        when(applicationNiceClassificationService.getLightNiceClassificationsByAppId(id)).thenReturn(lightNiceClassifications);

        ApiResponse<List<ClassificationLightDto>> response = applicationNiceClassificationController.getAppNiceClassification(id);

        assertEquals(ApiResponse.ok(lightNiceClassifications), response);
    }
}

