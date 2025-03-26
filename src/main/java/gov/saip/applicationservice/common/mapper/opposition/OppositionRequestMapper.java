package gov.saip.applicationservice.common.mapper.opposition;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.opposition.OppositionRequestDto;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.opposition.OppositionRequest;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(uses = {DocumentMapper.class})
public interface OppositionRequestMapper extends BaseMapper<OppositionRequest, OppositionRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    @Mapping(source = "applicationInfo.ownerNameAr",target = "applicationOwnerNameAr")
    @Mapping(source = "applicationInfo.ownerNameEn",target = "applicationOwnerNameEn")
    OppositionRequestDto map(OppositionRequest OppositionRequest);


    @Override
    @Mapping(source = "applicationId", target = "applicationInfo.id")
    @Mapping(source = "applicationOwnerNameAr" , target = "applicationInfo.ownerNameAr")
    @Mapping(source = "applicationOwnerNameEn",target = "applicationInfo.ownerNameEn")
    OppositionRequest unMap(OppositionRequestDto OppositionRequestDto);



    @AfterMapping
    default void afterMapEntityToDto(OppositionRequest entity, @MappingTarget OppositionRequestDto dto) {
        if (!Collections.isEmpty(entity.getOppositionApplicationSimilars())) {
            List<Long> documentIds = entity.getOppositionDocuments().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            dto.setDocumentIds(documentIds);
        }
        if (!Collections.isEmpty(entity.getOppositionApplicationSimilars())) {
            List<Long> applicationIds = entity.getOppositionApplicationSimilars().stream()
                    .map(ApplicationInfo::getId)
                    .collect(Collectors.toList());
            dto.setApplicationIds(applicationIds);
        }
    }
    @AfterMapping
    default void afterMapDtoToEntity(OppositionRequestDto dto, @MappingTarget OppositionRequest entity) {
        if (dto.getDocumentIds() != null && !dto.getDocumentIds().isEmpty()) {
            List<Document> documents = dto.getDocumentIds().stream()
                    .map(id -> {
                        Document doc = new Document();
                        doc.setId(id);
                        return doc;
                    })
                    .collect(Collectors.toList());
            entity.setOppositionDocuments(documents);
        }
        if (dto.getApplicationIds() != null && !dto.getApplicationIds().isEmpty()) {
            List<ApplicationInfo> applicationInfoList = dto.getApplicationIds().stream()
                    .map(id -> {
                        ApplicationInfo applicationInfo = new ApplicationInfo();
                        applicationInfo.setId(id);
                        return applicationInfo;
                    })
                    .collect(Collectors.toList());
            entity.setOppositionApplicationSimilars(applicationInfoList);
        }
    }
}
