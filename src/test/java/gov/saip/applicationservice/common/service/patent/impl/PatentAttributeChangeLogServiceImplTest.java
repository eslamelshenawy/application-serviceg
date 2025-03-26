package gov.saip.applicationservice.common.service.patent.impl;

import gov.saip.applicationservice.common.dto.patent.PatentAttributeChangeLogDto;
import gov.saip.applicationservice.common.dto.patent.PatentDetailsRequestDto;
import gov.saip.applicationservice.common.mapper.patent.PatentAttributeChangeLogMapper;
import gov.saip.applicationservice.common.model.patent.PatentAttributeChangeLog;
import gov.saip.applicationservice.common.model.patent.PatentDetails;
import gov.saip.applicationservice.common.repository.patent.PatentAttributeChangeLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PatentAttributeChangeLogServiceImplTest {

    @Mock
    private PatentAttributeChangeLogRepository patentAttributeChangeLogRepository;

    @Mock
    private PatentAttributeChangeLogMapper patentAttributeChangeLogMapper;
    @InjectMocks
    private PatentAttributeChangeLogServiceImpl patentAttributeChangeLogService;

    List<PatentAttributeChangeLog> changeLogList = createChangeLogList();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        patentAttributeChangeLogService = new PatentAttributeChangeLogServiceImpl(patentAttributeChangeLogRepository, patentAttributeChangeLogMapper);
    }

    @Test
    void testGetPatentAttributeChangeLogByPatentId() {
        Long patentId = 1L;
        when(patentAttributeChangeLogRepository.findAllByPatentId(patentId))
                .thenReturn(changeLogList);
        when(patentAttributeChangeLogMapper.map(any(PatentAttributeChangeLog.class)))
                .thenAnswer(invocation -> {
                    PatentAttributeChangeLog log = invocation.getArgument(0);
                    PatentAttributeChangeLogDto dto = new PatentAttributeChangeLogDto();
                    dto.setAttributeName("test");
                    dto.setAttributeValue(log.getAttributeValue());
                    return dto;
                });

        Map<String, List<PatentAttributeChangeLogDto>> result = patentAttributeChangeLogService.getPatentAttributeChangeLogByPatentId(patentId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(patentAttributeChangeLogRepository).findAllByPatentId(patentId);
    }

    @Test
    void testGetPatentAttributeLatestChangeLogByPatentId() {
        Long patentId = 1L;
        List<PatentAttributeChangeLog> changeLogList = createChangeLogList();
        when(patentAttributeChangeLogRepository.findLatestByPatentId(patentId))
                .thenReturn(changeLogList);
        when(patentAttributeChangeLogMapper.map(any(PatentAttributeChangeLog.class)))
                .thenAnswer(invocation -> {
                    PatentAttributeChangeLog log = invocation.getArgument(0);
                    PatentAttributeChangeLogDto dto = new PatentAttributeChangeLogDto();
                    dto.setAttributeName("test");
                    dto.setAttributeValue(log.getAttributeValue());
                    return dto;
                });

        Map<String, List<PatentAttributeChangeLogDto>> result = patentAttributeChangeLogService.getPatentAttributeLatestChangeLogByPatentId(patentId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(patentAttributeChangeLogRepository).findLatestByPatentId(patentId);
    }


    @Test
    void testSavePatentAttributeChangeLog() {
        PatentDetails patentDetails = new PatentDetails();
        PatentDetailsRequestDto requestDto = new PatentDetailsRequestDto();
        requestDto.setAttributeChangeLogs(createAttributeChangeLogAndDetailsDtos());

        when(patentAttributeChangeLogRepository.getMostRecentAttributeByPatentIdAndName(any(), any()))
                .thenReturn(Optional.empty());
        when(patentAttributeChangeLogMapper.unMap(any(PatentAttributeChangeLogDto.class)))
                .thenAnswer(invocation -> {
                    PatentAttributeChangeLogDto log = invocation.getArgument(0);
                    PatentAttributeChangeLog changeLog = new PatentAttributeChangeLog();
                    changeLog.setAttributeName("test");
                    changeLog.setAttributeValue(log.getAttributeValue());
                    return changeLog;
                });

        when(patentAttributeChangeLogRepository.saveAll(anyList()))
                .thenReturn(new ArrayList<>());

        patentAttributeChangeLogService.savePatentAttributeChangeLog(patentDetails, requestDto);

        verify(patentAttributeChangeLogRepository, times(1)).getMostRecentAttributeByPatentIdAndName(any(),any());
    }

    private List<PatentAttributeChangeLog> createChangeLogList() {
        List<PatentAttributeChangeLog> changeLogList = new ArrayList<>();
        PatentAttributeChangeLog patentAttributeChangeLog = new PatentAttributeChangeLog();
        patentAttributeChangeLog.setAttributeName("test");
        changeLogList.add(patentAttributeChangeLog);
        return changeLogList;
    }

    private List<PatentAttributeChangeLogDto> createAttributeChangeLogAndDetailsDtos() {
        List<PatentAttributeChangeLogDto> dtos = new ArrayList<>();
        PatentAttributeChangeLogDto patentAttributeChangeLog = new PatentAttributeChangeLogDto();
        patentAttributeChangeLog.setAttributeName("test");
        dtos.add(patentAttributeChangeLog);
        return dtos;
    }
}
