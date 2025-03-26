package gov.saip.applicationservice.common.mapper.opposition;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.opposition.HearingSessionDto;
import gov.saip.applicationservice.common.dto.opposition.OppositionDto;
import gov.saip.applicationservice.common.dto.opposition.OppositionSupportServiceDto;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.opposition.HearingSession;
import gov.saip.applicationservice.common.model.opposition.Opposition;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(uses = {DocumentMapper.class})
public interface OppositionMapper extends BaseMapper<Opposition, OppositionDto> {

    HearingSession unMapHearingSessionDto(HearingSessionDto dto);
    HearingSessionDto mapHearingSessionDto(HearingSession  hearingSession);


    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget Opposition entity, OppositionDto dto) {

        // map list of docs id to document objects
        if (!Collections.isEmpty(dto.getDocumentIds())) {
            List<Document> documents = dto.getDocumentIds().stream().map(id -> new Document(id)).collect(Collectors.toList());
            entity.setDocuments(documents);
        }

        // map list of classification ids to document objects
        if (!Collections.isEmpty(dto.getClassificationIds())) {
            List<Classification> classifications = dto.getClassificationIds().stream().map(id -> new Classification(id)).collect(Collectors.toList());
            entity.setClassifications(classifications);
        }

        if (Objects.nonNull(dto.getApplicationId())) {
            entity.setApplication(new ApplicationInfo(dto.getApplicationId()));
            entity.setApplicationInfo(new ApplicationInfo(dto.getApplicationId()));
        }
    }

    @Mapping(target = "applicationId", source = "application.id")
    @Mapping(target = "requestNumber", source = "requestNumber")
    OppositionSupportServiceDto mapOppositionSupportServiceDto(Opposition appealRequest);
}
