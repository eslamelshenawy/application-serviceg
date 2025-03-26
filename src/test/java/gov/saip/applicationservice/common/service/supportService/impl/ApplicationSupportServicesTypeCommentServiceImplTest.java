package gov.saip.applicationservice.common.service.supportService.impl;

import gov.saip.applicationservice.common.model.supportService.ApplicationSupportServicesTypeComment;
import gov.saip.applicationservice.common.repository.supportService.ApplicationSupportServicesTypeCommentRepository;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ApplicationSupportServicesTypeCommentServiceImplTest {

    @InjectMocks
    private ApplicationSupportServicesTypeCommentServiceImpl commentService;

    @Mock
    private ApplicationSupportServicesTypeService typeService;

    @Mock
    private ApplicationSupportServicesTypeCommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllApplicationSupportServicesTypeCommentsByApplicationSupportServiceId() {
        Long applicationSupportServiceId = 1L;
        List<ApplicationSupportServicesTypeComment> comments = new ArrayList<>();
        comments.add(new ApplicationSupportServicesTypeComment());
        when(commentRepository.findAllByApplicationSupportServicesTypeId(applicationSupportServiceId)).thenReturn(comments);

        List<ApplicationSupportServicesTypeComment> result = commentService.getAllApplicationSupportServicesTypeCommentsByApplicationSupportServiceId(applicationSupportServiceId);

        assertEquals(comments, result);
    }


    @Test
    public void testGetLastApplicationSupportServicesTypeCommentByApplicationSupportServiceId() {
        Long applicationSupportServiceId = 1L;
        List<ApplicationSupportServicesTypeComment> comments = new ArrayList<>();
        comments.add(new ApplicationSupportServicesTypeComment());
        when(commentRepository.findLastByApplicationSupportServicesTypeId(eq(applicationSupportServiceId), any()))
                .thenReturn(comments);

        ApplicationSupportServicesTypeComment result = commentService.getLastApplicationSupportServicesTypeCommentByApplicationSupportServiceId(applicationSupportServiceId);

        assertEquals(comments.get(0), result);
    }

    @Test
    public void testGetLastApplicationSupportServicesTypeCommentByApplicationSupportServiceId_NoCommentsFound() {
        Long applicationSupportServiceId = 1L;
        when(commentRepository.findLastByApplicationSupportServicesTypeId(eq(applicationSupportServiceId), any()))
                .thenReturn(new ArrayList<>());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> commentService.getLastApplicationSupportServicesTypeCommentByApplicationSupportServiceId(applicationSupportServiceId)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

}

