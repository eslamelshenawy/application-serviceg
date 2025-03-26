package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.mapper.lookup.LkClassificationVersionMapper;
import gov.saip.applicationservice.common.repository.LkClassificationVersionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LkClassificationVersionServiceImplTest {
    @Mock
    private LkClassificationVersionRepository classificationVersionRepository;
    @Mock
    private LkClassificationVersionMapper classificationVersionMapper;

    @InjectMocks
    private LkClassificationVersionServiceImpl lkClassificationVersionServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getLatestVersionIdBySaipCode_test() {
        //Arrange
        String saipCode = "TRADEMARK";
        Integer versionId = 12;
        Mockito.when(classificationVersionRepository.getLatestVersionIdBySaipCode(saipCode)).thenReturn(versionId);
        //Act
        Integer expectedVersionId = lkClassificationVersionServiceImpl.getLatestVersionIdBySaipCode(saipCode);
        //Assert
        Assertions.assertEquals(versionId,expectedVersionId);
    }

    @Test
    public void getLatestVersionIdByCategoryId_test() {
        //Arrange
        Long categoryId = 5L;
        Integer versionId = 12;
        Mockito.when(classificationVersionRepository.getLatestVersionIdByCategoryId(categoryId)).thenReturn(versionId);
        //Act
        Integer expectedVersionId = lkClassificationVersionServiceImpl.getLatestVersionIdByCategoryId(categoryId);
        //Assert
        Assertions.assertEquals(versionId,expectedVersionId);
    }
}