package gov.saip.applicationservice.common.service.patent.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.patent.PatentAttributeChangeLogDto;
import gov.saip.applicationservice.common.dto.patent.PatentDetailsRequestDto;
import gov.saip.applicationservice.common.mapper.patent.PatentAttributeChangeLogMapper;
import gov.saip.applicationservice.common.model.patent.PatentAttributeChangeLog;
import gov.saip.applicationservice.common.model.patent.PatentDetails;
import gov.saip.applicationservice.common.repository.patent.PatentAttributeChangeLogRepository;
import gov.saip.applicationservice.common.service.patent.PatentAttributeChangeLogService;
import gov.saip.applicationservice.common.util.PatentDescriptiveUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class PatentAttributeChangeLogServiceImpl extends BaseServiceImpl<PatentAttributeChangeLog, Long> implements PatentAttributeChangeLogService {
    private final PatentAttributeChangeLogRepository patentAttributeChangeLogRepository;
    private final PatentAttributeChangeLogMapper patentAttributeChangeLogMapper;
    @Override
    protected BaseRepository<PatentAttributeChangeLog, Long> getRepository() {
        return patentAttributeChangeLogRepository;
    }
    public Map<String, List<PatentAttributeChangeLogDto>> getPatentAttributeChangeLogByPatentId(Long patentId){
        List<PatentAttributeChangeLog> PatentAttributeChangeLogList = patentAttributeChangeLogRepository.findAllByPatentId(patentId);
        return convertToPatentAttributeChangeLogDtoMap(PatentAttributeChangeLogList);
    }

    @Override
    public Map<String, List<PatentAttributeChangeLogDto>> getPatentAttributeLatestChangeLogByPatentId(Long patentId) {
        List<PatentAttributeChangeLog> PatentAttributeChangeLogList = patentAttributeChangeLogRepository.findLatestByPatentId(patentId);
        return convertToPatentAttributeChangeLogDtoMap(PatentAttributeChangeLogList);
    }

    public Map<String, List<PatentAttributeChangeLogDto>> getPatentAttributesLatestChangeLogByPatentId(List<String> attributeNames, Long patentId) {
        List<PatentAttributeChangeLog> PatentAttributeChangeLogList = patentAttributeChangeLogRepository.getMostRecentAttributeByPatentIdAndNames(attributeNames, patentId);
        return convertToPatentAttributeChangeLogDtoMap(PatentAttributeChangeLogList);
    }

    private Map<String, List<PatentAttributeChangeLogDto>> convertToPatentAttributeChangeLogDtoMap(List<PatentAttributeChangeLog> PatentAttributeChangeLogList) {
       return PatentAttributeChangeLogList.stream().map(log -> patentAttributeChangeLogMapper.map(log)).sorted(Comparator.comparing(PatentAttributeChangeLogDto::getVersion).reversed()).collect(Collectors.groupingBy(log -> log.getAttributeName()));
    }

    @Override
    public void savePatentAttributeChangeLog(PatentDetails patentDetails, PatentDetailsRequestDto patentDetailsRequestDto) {

        if (patentDetailsRequestDto.getAttributeChangeLogs() == null ||  patentDetailsRequestDto.getAttributeChangeLogs().isEmpty()) {
            return;
        }

        final List<PatentAttributeChangeLog> changeLogs = new ArrayList<>();
        for (PatentAttributeChangeLogDto attributeRequest : patentDetailsRequestDto.getAttributeChangeLogs()) {
            // get from db by patent id
            Optional<PatentAttributeChangeLog> optDBAttribute = patentAttributeChangeLogRepository.getMostRecentAttributeByPatentIdAndName(attributeRequest.getAttributeName(), patentDetails.getId());

            if (optDBAttribute.isEmpty()) { // that mean this is a new attributeRequest
                changeLogs.add(mapToEntityList(patentDetails, attributeRequest));
                continue;
            }

            PatentAttributeChangeLog dbAttribute = optDBAttribute.get();
            if (isTaskIdsEqual(dbAttribute, attributeRequest)) { // same task so update value only
                dbAttribute.setAttributeValue(PatentDescriptiveUtil.removeSomeStylingFromHTML(attributeRequest.getAttributeValue()));
                changeLogs.add(dbAttribute);
            } else { // different task so insert a new row and increment the version
                PatentAttributeChangeLog changeLog = mapToEntityList(patentDetails, attributeRequest);
                changeLog.setVersion(dbAttribute.getVersion() + 1);
                changeLogs.add(changeLog);
            }
        }

        patentAttributeChangeLogRepository.saveAll(changeLogs);
        removeMissingAttributes(patentDetails, patentDetailsRequestDto);
    }

    private void removeMissingAttributes(PatentDetails patentDetails, PatentDetailsRequestDto patentDetailsRequestDto) {
        List<String> updatedAttributes = patentDetailsRequestDto.getAttributeChangeLogs().stream().map(i -> i.getAttributeName()).toList();
        patentAttributeChangeLogRepository.removeMissingAttributes(updatedAttributes, patentDetails.getId());
    }

    private boolean isTaskIdsEqual(PatentAttributeChangeLog dbAttribute, PatentAttributeChangeLogDto attributeRequest) {
        
        if (dbAttribute.getTaskId() == null && attributeRequest.getTaskId() == null) {
            return true;
        }
        if (dbAttribute.getTaskId() == null || attributeRequest.getTaskId() == null) {
            return false;
        }
        return dbAttribute.getTaskId().equals(attributeRequest.getTaskId());
    }


    private PatentAttributeChangeLog  mapToEntityList(PatentDetails patentDetails, PatentAttributeChangeLogDto dto) {
        PatentAttributeChangeLog patentAttributeChangeLog = new PatentAttributeChangeLog();
        if ( dto == null ) {
            return null;
        }
        patentAttributeChangeLog.setId( dto.getId() );
        patentAttributeChangeLog.setIsDeleted( dto.getIsDeleted() );
        patentAttributeChangeLog.setAttributeName( dto.getAttributeName() );
        patentAttributeChangeLog.setAttributeValue( PatentDescriptiveUtil.removeSomeStylingFromHTML( dto.getAttributeValue()));
        patentAttributeChangeLog.setTaskId( dto.getTaskId() );
        patentAttributeChangeLog.setTaskDefinitionKey( dto.getTaskDefinitionKey() );
        patentAttributeChangeLog.setVersion( dto.getVersion() );
        patentAttributeChangeLog.setPatentDetails(patentDetails);
        return patentAttributeChangeLog;
    }

    @Override
    public String getAttributeValueByApplicationId(Long applicationId, String attributeName) {
        return patentAttributeChangeLogRepository.getAttributeValueByApplicationId(applicationId, attributeName);
    }

}
