package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.LkCertificateTypeDto;
import gov.saip.applicationservice.common.mapper.LkCertificateTypeMapper;
import gov.saip.applicationservice.common.model.LkCertificateType;
import gov.saip.applicationservice.common.repository.LkCertificateTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class LkCertificateTypeServiceImplTest {


    @Mock
    private LkCertificateTypeRepository repository;

    @Mock
    LkCertificateTypeMapper mapper;

    @InjectMocks
    private LkCertificateTypeServiceImpl certificateTypeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findCertificateTypesByCategoryIdTest() {

        // Arrange
        LkCertificateTypeDto dto1 = new LkCertificateTypeDto();
        LkCertificateTypeDto dto2 = new LkCertificateTypeDto();
        List<LkCertificateTypeDto> dtoList = Arrays.asList(dto1, dto2);

        LkCertificateType entity1 = new LkCertificateType();
        LkCertificateType entity2 = new LkCertificateType();
        List<LkCertificateType> entities = Arrays.asList(entity1, entity2);

        when(repository.findCertificateTypesByCategoryId(anyLong()))
                .thenReturn(entities);

        when(mapper.map(entities)).thenReturn(dtoList);

        // Act
        List<LkCertificateTypeDto> result = certificateTypeService.findCertificateTypesByCategoryId(anyLong());

        // Assert
        Assertions.assertThat(result).isEqualTo(dtoList);
    }
}