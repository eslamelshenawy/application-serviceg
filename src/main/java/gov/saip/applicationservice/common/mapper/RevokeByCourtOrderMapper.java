package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.RevokeByCourtOrderInternalDto;
import gov.saip.applicationservice.common.dto.RevokeByCourtOrderRequestDto;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.RevokeByCourtOrder;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(uses = {DocumentMapper.class})
public interface RevokeByCourtOrderMapper extends BaseMapper<RevokeByCourtOrder, RevokeByCourtOrderRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    RevokeByCourtOrderRequestDto map(RevokeByCourtOrder revokeByCourtOrder);


    @Override
    @Mapping(source = "applicationId", target = "applicationInfo.id")
    RevokeByCourtOrder unMap(RevokeByCourtOrderRequestDto revokeByCourtOrderRequestDto);

    RevokeByCourtOrder unMapFromRevokeByCourtOrderInternalDto(@MappingTarget RevokeByCourtOrder revokeByCourtOrder, RevokeByCourtOrderInternalDto revokeByCourtOrderInternalDto);

    @AfterMapping
    default void afterMapEntityToDto(RevokeByCourtOrder entity, @MappingTarget RevokeByCourtOrderRequestDto dto) {
        if (!Collections.isEmpty(entity.getDocuments())) {
            List<Long> documentIds = entity.getDocuments().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            dto.setDocumentIds(documentIds);
        }
    }

    @AfterMapping
    default void afterMapDtoToEntity(RevokeByCourtOrderRequestDto dto, @MappingTarget RevokeByCourtOrder entity) {
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
