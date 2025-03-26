package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.DocumentsTemplateDto;
import gov.saip.applicationservice.common.mapper.DocumentsTemplateMapper;
import gov.saip.applicationservice.common.model.DocumentsTemplate;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.repository.DocumentsTemplateRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DocumentsTemplateServiceImplTest {

    @Mock
    private LkApplicationCategoryRepository applicationCategoryRepository;

    @Mock
    private DocumentsTemplateRepository documentsTemplateRepository;

    @Mock
    private DocumentsTemplateMapper documentsTemplateMapper;

    @InjectMocks
    private DocumentsTemplateServiceImpl documentsTemplateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindDocumentsTemplates() {
        String saipCode = "SAIP123";

        LkApplicationCategory lkApplicationCategory = new LkApplicationCategory();
        when(applicationCategoryRepository.findBySaipCode(saipCode)).thenReturn(Optional.of(lkApplicationCategory));

        DocumentsTemplate documentsTemplate = new DocumentsTemplate();
        when(documentsTemplateRepository.findByCategory(lkApplicationCategory)).thenReturn(Collections.singletonList(documentsTemplate));

        DocumentsTemplateDto documentsTemplateDto = new DocumentsTemplateDto();
        when(documentsTemplateMapper.map(Collections.singletonList(documentsTemplate))).thenReturn(Collections.singletonList(documentsTemplateDto));

        List<DocumentsTemplateDto> result = documentsTemplateService.findDocumentsTemplates(saipCode);

        assertEquals(1, result.size());
        assertEquals(documentsTemplateDto, result.get(0));

        verify(applicationCategoryRepository, times(1)).findBySaipCode(saipCode);
        verify(documentsTemplateRepository, times(1)).findByCategory(lkApplicationCategory);
        verify(documentsTemplateMapper, times(1)).map(Collections.singletonList(documentsTemplate));
    }
}
