package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.RevokeVoluntaryRequestDto;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.RevokeVoluntaryRequest;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(uses = {DocumentMapper.class})
public interface RevokeVoluntaryRequestMapper extends BaseMapper<RevokeVoluntaryRequest, RevokeVoluntaryRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    RevokeVoluntaryRequestDto map(RevokeVoluntaryRequest revokeVoluntryRequest);


    @Override
    @Mapping(source = "applicationId", target = "applicationInfo.id")
    RevokeVoluntaryRequest unMap(RevokeVoluntaryRequestDto revokeVoluntryRequestDto);

    @AfterMapping
    default void afterMapEntityToDto(RevokeVoluntaryRequest entity, @MappingTarget RevokeVoluntaryRequestDto dto) {
        if (!Collections.isEmpty(entity.getDocuments())) {
            List<Long> documentIds = entity.getDocuments().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            dto.setDocumentIds(documentIds);
        }
    }
    @AfterMapping
    default void afterMapDtoToEntity(RevokeVoluntaryRequestDto dto, @MappingTarget RevokeVoluntaryRequest entity) {
        if (dto.getDocumentIds() != null && !dto.getDocumentIds().isEmpty()) {
            List<Document> documents = dto.getDocumentIds().stream()
                    .map(id -> {
                        Document doc = new Document();
                        doc.setId(id);
                        return doc;
                    })
                    .collect(Collectors.toList());
            entity.setDocuments(documents);
        }
    }


}
