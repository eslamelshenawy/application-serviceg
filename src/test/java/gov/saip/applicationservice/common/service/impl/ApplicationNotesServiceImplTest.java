package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.SectionApplicationNotesResponseDto;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.mapper.ApplicationNotesMapper;
import gov.saip.applicationservice.common.model.ApplicationNotes;
import gov.saip.applicationservice.common.model.LkSection;
import gov.saip.applicationservice.common.repository.ApplicationNotesRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.LkAttributeService;
import gov.saip.applicationservice.common.service.lookup.LkNotesService;
import gov.saip.applicationservice.common.service.lookup.LkSectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicationNotesServiceImplTest {

    @Mock
    private ApplicationNotesRepository applicationNotesRepository;

    @Mock
    private ApplicationNotesMapper applicationNotesMapper;

    @Mock
    private LkSectionService lkSectionService;

    @Mock
    private LkAttributeService lkAttributeService;

    @Mock
    private ApplicationInfoService applicationInfoService;

    @Mock
    private LkNotesService notesService;

    @Autowired
    @InjectMocks
    private ApplicationNotesServiceImpl applicationNotesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllApplicationNotes() {
        Long applicationId = 1L;
        Integer stepId = 2;
        Integer attributeId = 3;
        String taskDefinitionKey = "taskKey";
        NotesTypeEnum notesType = NotesTypeEnum.APPLICANT ;

        LkSection lkSection = new LkSection();
        lkSection.setId(1);

        List<LkSection> mockSections = new ArrayList<>();
        mockSections.add(lkSection);

        List<ApplicationNotes> mockApplicationNotes = new ArrayList<>();

        when(lkSectionService.findAll()).thenReturn(mockSections);
        when(applicationNotesRepository.findAllByApplicationInfoIdAndSectionId(
                applicationId, mockSections.get(0).getId(), attributeId, taskDefinitionKey,  notesType))
                .thenReturn(mockApplicationNotes);

        List<SectionApplicationNotesResponseDto> result = applicationNotesService.getAllApplicationNotes(
                applicationId, stepId, attributeId, taskDefinitionKey,  notesType);

        assertEquals(mockSections.size(), result.size());

        verify(applicationNotesRepository, times(mockSections.size())).findByApplicationInfoIdAndDefinitionKeyWithSectionNotes(
                anyLong(),anyString(),anyInt());
    }
}
