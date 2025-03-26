package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.dto.ListApplicationClassificationDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.model.SubClassification;
import gov.saip.applicationservice.common.repository.ClassificationRepository;
import gov.saip.applicationservice.common.service.LkClassificationVersionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ClassificationServiceImplTest {

    @InjectMocks
    private ClassificationServiceImpl classificationService;

    @Mock
    private ClassificationRepository classificationRepository;

    @Mock
    private ClassificationMapper classificationMapper;

    @Mock
    LkClassificationVersionService lkClassificationVersionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllClassifications() {
        int page = 0;
        int limit = 10;
        String query = "test";
        Integer versionId = 1;
        String saipCode = "SAIP123";
        Long categoryId = 1L;
        Long unitId = 1L;

        Pageable pageable = PageRequest.of(page, limit);
        Page<Classification> classificationPage = mock(Page.class);
        when(classificationRepository.findBySearchAndCategory(query, versionId, saipCode, categoryId, pageable)).thenReturn(classificationPage);

        PaginationDto expectedPaginationDto = new PaginationDto();
        expectedPaginationDto.setTotalElements(0L);
        expectedPaginationDto.setContent(new ArrayList());
        when(classificationMapper.mapListOfClassificationsToListOfDtos(anyList())).thenReturn(new ArrayList<>());

        PaginationDto result = classificationService.getAllClassifications(page, limit, query, versionId, saipCode, categoryId,unitId);

        assertNotNull(result);
        assertEquals(expectedPaginationDto, result);
        verify(classificationRepository, times(1)).findBySearchAndCategory(query, versionId, saipCode, categoryId, pageable);
    }

    @Test
    public void testFindByCategoryId() {
        Long categoryId = 1L;
        Integer versionId = 12;
        List<ClassificationDto> expectedDtoList = new ArrayList<>();
        when(classificationRepository.findByCategoryId(categoryId,versionId)).thenReturn(new ArrayList<>());
        when(lkClassificationVersionService.getLatestVersionIdByCategoryId(anyLong())).thenReturn(versionId);
        List<ClassificationDto> result = classificationService.findByCategoryId(categoryId);

        assertNotNull(result);
        assertEquals(expectedDtoList, result);
        verify(classificationRepository, times(1)).findByCategoryId(categoryId,versionId);
    }

    @Test
    public void testFindByUnitIdIn() {
        List<Long> unitId = new ArrayList<>();
        unitId.add(1L);
        List<Classification> expectedClassificationList = new ArrayList<>();
        expectedClassificationList.add(new Classification());
        List<ClassificationDto> classificationDtoList = new ArrayList<>();
        classificationDtoList.add(new ClassificationDto());
        when(classificationRepository.findByUnitIdIn(unitId)).thenReturn(expectedClassificationList);
        when(classificationMapper.map(expectedClassificationList)).thenReturn(classificationDtoList);

        List<ClassificationDto> result = classificationService.findByUnitIdIn(unitId);

        assertNotNull(result);
        assertEquals(classificationDtoList, result);
        verify(classificationRepository, times(1)).findByUnitIdIn(unitId);
    }

    @Test
    public void testFindByIdIn() {
        List<Long> classificationIds = new ArrayList<>();
        classificationIds.add(1L);
        List<Classification> expectedClassificationList = new ArrayList<>();
        expectedClassificationList.add(new Classification());
        when(classificationRepository.findByIdIn(classificationIds)).thenReturn(expectedClassificationList);
        List<Classification> result = classificationService.findByIdIn(classificationIds);

        assertNotNull(result);
        assertEquals(expectedClassificationList, result);
        verify(classificationRepository, times(1)).findByIdIn(classificationIds);
    }

    @Test
    public void testGetAllClassificationsWithSaipCode() {
        String saipCode = "SAIP123";
        Integer versionId = 12;
        List<Classification> expectedClassificationList = new ArrayList<>();
        expectedClassificationList.add(new Classification());

        List<ClassificationLightDto> classificationLightDtoArrayList = new ArrayList<>();
        ClassificationLightDto classificationLightDto = new ClassificationLightDto();
        classificationLightDto.setId(1l);
        classificationLightDtoArrayList.add(classificationLightDto);
        when(classificationRepository.getAllClassifications(saipCode,versionId)).thenReturn(expectedClassificationList);
        when(lkClassificationVersionService.getLatestVersionIdBySaipCode(any())).thenReturn(versionId);
        List<ClassificationLightDto> result = classificationService.getAllClassifications(saipCode, versionId);

        assertNotNull(result);
        verify(classificationRepository, times(1)).getAllClassifications(saipCode,versionId);
    }

    @Test
    public void testListApplicationClassification() {
        // Arrange
        Long id = 1L;
        List<SubClassification> subClassificationList = new ArrayList<>();
        SubClassification subClassification = new SubClassification();
        subClassification.setId(1l);
        subClassificationList.add(subClassification);
        Classification classification = new Classification();
        classification.setId(1l);
        subClassification.setClassification(classification);
        when(classificationRepository.getSubClassificationList(id)).thenReturn(subClassificationList);

        List<ListApplicationClassificationDto> result = classificationService.listApplicationClassification(id);

        assertNotNull(result);
        verify(classificationRepository, times(1)).getClassificationList(id);
    }


}
