package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LicenceRequestDto;
import gov.saip.applicationservice.common.dto.OppositionRevokeLicenceRequestDto;
import gov.saip.applicationservice.common.dto.RevokeByCourtOrderRequestDto;
import gov.saip.applicationservice.common.dto.RevokeLicenceRequestDto;
import gov.saip.applicationservice.common.model.*;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {DocumentMapper.class})
public interface OppositionRevokeLicenceRequestMapper extends BaseMapper<OppositionRevokeLicenceRequest, OppositionRevokeLicenceRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    OppositionRevokeLicenceRequestDto map(OppositionRevokeLicenceRequest applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    OppositionRevokeLicenceRequest unMap(OppositionRevokeLicenceRequestDto applicationDatabaseDto);
    @AfterMapping
    default void afterMapEntityToDto(OppositionRevokeLicenceRequest entity, @MappingTarget OppositionRevokeLicenceRequestDto dto) {
        if (!Collections.isEmpty(entity.getDocuments())) {
            List<Long> documentIds = entity.getDocuments().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            dto.setDocumentIds(documentIds);
        }
        if (!Collections.isEmpty(entity.getRevokeLicenceRequest().getDocuments())) {
            List<Long> documentIds = entity.getRevokeLicenceRequest().getDocuments().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            dto.getRevokeLicenceRequest().setDocumentIds(documentIds);
        }

    }

    @AfterMapping
    default void afterMapDtoToEntity(OppositionRevokeLicenceRequestDto dto, @MappingTarget OppositionRevokeLicenceRequest entity) {
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
