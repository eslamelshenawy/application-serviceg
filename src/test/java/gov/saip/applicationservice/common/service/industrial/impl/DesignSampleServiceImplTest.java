package gov.saip.applicationservice.common.service.industrial.impl;

import gov.saip.applicationservice.common.dto.industrial.*;
import gov.saip.applicationservice.common.model.industrial.DesignSample;
import gov.saip.applicationservice.common.model.industrial.IndustrialDesignDetail;
import gov.saip.applicationservice.common.model.industrial.RequestTypeEnum;
import gov.saip.applicationservice.common.repository.industrial.DesignSampleRepository;
import gov.saip.applicationservice.common.service.industrial.DesignSampleDrawingsService;
import gov.saip.applicationservice.common.service.industrial.DesignSampleService;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.common.validators.DesignSampleDrawingsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DesignSampleServiceImplTest {

    @InjectMocks
    @Spy
    private DesignSampleServiceImpl designSampleServiceImpl;

    @Mock
    private DesignSampleRepository designSampleRepository;

    @Mock
    private IndustrialDesignDetailService industrialDesignDetailService;

    @Mock
    private DesignSampleDrawingsValidator designSampleDrawingsValidator;

    @Mock
    private DesignSampleDrawingsService designSampleDrawingsService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testCreate() {
        CreateDesignSampleDto createDesignSampleDto = new CreateDesignSampleDto();
        createDesignSampleDto.setIndustrialDesignId(1L);
        createDesignSampleDto.setRequestType(RequestTypeEnum.SINGLE);
        createDesignSampleDto.setDesignSamplesIdsToBeDeleted(Arrays.asList("1"));

        List<DesignSampleReqDto> designSamples = new LinkedList<>();

        DesignSampleReqDto designSampleReqDto1 = new DesignSampleReqDto();
        designSampleReqDto1.setName("Sample Design 1");

        List<DesignSampleDrawingsReqDto> designSampleDrawingsReqDtoList1 = new LinkedList<>();

        DesignSampleDrawingsReqDto designSampleDrawingsReqDto1 = new DesignSampleDrawingsReqDto();
        designSampleDrawingsReqDto1.setId(1L);
        designSampleDrawingsReqDto1.setMain(true);
        designSampleDrawingsReqDto1.setShape(new LkShapeTypeReqDto());
        designSampleDrawingsReqDto1.setDocId(1l);

        designSampleDrawingsReqDtoList1.add(designSampleDrawingsReqDto1);

        designSampleReqDto1.setDesignSampleDrawings(designSampleDrawingsReqDtoList1);

        designSamples.add(designSampleReqDto1);

        createDesignSampleDto.setDesignSamples(designSamples);

        DesignSample designSample = new DesignSample();

        designSample.setId(1l);

        designSample.setDesignSampleDrawings(new ArrayList<>());

        IndustrialDesignDetailDto industrialDesignDetailDto = new IndustrialDesignDetailDto();
        industrialDesignDetailDto.setId(1l);

        doReturn(designSample).when(designSampleServiceImpl).findById(Long.valueOf("1"));

        when(industrialDesignDetailService.findIndustrialDesignById(1L)).thenReturn(Optional.of(new IndustrialDesignDetail()));
        when(industrialDesignDetailService.findDtoById(any())).thenReturn(industrialDesignDetailDto);
        when(designSampleRepository.save(designSample)).thenReturn(designSample);

        doNothing().when(designSampleDrawingsValidator).validateDesignSampleDrawings(any(DesignSampleReqDto.class));

        when(designSampleRepository.saveAndFlush(any(DesignSample.class))).thenReturn(new DesignSample());

        when(industrialDesignDetailService.insert(any(IndustrialDesignDetail.class))).thenReturn(new IndustrialDesignDetail());

        IndustrialDesignDetailDto result = designSampleServiceImpl.create(createDesignSampleDto);

        assertNotNull(result);
    }

    @Test
    public void testCount() {
        when(designSampleRepository.countByIndustrialDesignId(1L)).thenReturn(5L);

        Long count = designSampleServiceImpl.count(1L);

        assertEquals(5L, count);
    }
}
