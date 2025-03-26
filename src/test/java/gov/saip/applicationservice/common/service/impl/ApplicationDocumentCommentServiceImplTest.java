package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.model.ApplicationDocumentComment;
import gov.saip.applicationservice.common.repository.ApplicationDocumentCommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ApplicationDocumentCommentServiceImplTest {

    @Mock
    private ApplicationDocumentCommentRepository applicationDocumentCommentRepository;

    private ApplicationDocumentCommentServiceImpl commentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        commentService = new ApplicationDocumentCommentServiceImpl(applicationDocumentCommentRepository);
    }

    @Test
    public void testFindByDocumentId() {
        // Mock data
        Long documentId = 456L;
        List<ApplicationDocumentComment> expectedComments = new ArrayList<>();
        ApplicationDocumentComment comment1 = new ApplicationDocumentComment();
        ApplicationDocumentComment comment2 = new ApplicationDocumentComment();
        expectedComments.add(comment1);
        expectedComments.add(comment2);

        when(applicationDocumentCommentRepository.findByDocumentId(documentId)).thenReturn(expectedComments);

        List<ApplicationDocumentComment> actualComments = commentService.findByDocumentId(documentId);

        assertEquals(expectedComments, actualComments);
    }
}
