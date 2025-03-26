package gov.saip.applicationservice.common.service.industrial.impl;

import gov.saip.applicationservice.common.dto.industrial.DesignSampleDrawingsReqDto;
import gov.saip.applicationservice.common.dto.industrial.LkShapeTypeReqDto;
import gov.saip.applicationservice.common.model.industrial.DesignSample;
import gov.saip.applicationservice.common.model.industrial.DesignSampleDrawings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class DesignSampleDrawingsServiceImplTest {

    @InjectMocks
    @Spy
    private DesignSampleDrawingsServiceImpl designSampleDrawingsService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDesignSampleDrawings() {
        DesignSampleDrawingsReqDto drawingsReqDto = new DesignSampleDrawingsReqDto();
        drawingsReqDto.setId(1L);
        drawingsReqDto.setMain(true);
        drawingsReqDto.setShape(new LkShapeTypeReqDto());
        drawingsReqDto.setDocId(1l);

        DesignSample designSample = new DesignSample();
        designSample.setId(2L);
        DesignSampleDrawings designSampleDrawings = new DesignSampleDrawings();
        designSampleDrawings.setId(1l);
        doReturn(designSampleDrawings).when(designSampleDrawingsService).findById(anyLong());

        DesignSampleDrawings result = designSampleDrawingsService.createDesignSampleDrawings(drawingsReqDto, designSample);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertTrue(result.isMain());
    }

    @Test
    public void testSave() {
        DesignSampleDrawingsReqDto drawingsReqDto = new DesignSampleDrawingsReqDto();
        drawingsReqDto.setMain(true);
        drawingsReqDto.setShape(new LkShapeTypeReqDto());
        drawingsReqDto.setDocId(1l);

        DesignSample designSample = new DesignSample();

        DesignSampleDrawings drawings = new DesignSampleDrawings();
        drawings.setId(1L);

        when(designSampleDrawingsService.createDesignSampleDrawings(drawingsReqDto, designSample)).thenReturn(drawings);

        doReturn(drawings).when(designSampleDrawingsService).insert(drawings);

        DesignSampleDrawings result = designSampleDrawingsService.save(drawingsReqDto, designSample);

        assertNotNull(result);
        assertSame(drawings, result);
    }
}
