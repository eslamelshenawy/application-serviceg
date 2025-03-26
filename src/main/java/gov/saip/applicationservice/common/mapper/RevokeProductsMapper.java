package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.RevokeProductsDto;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.RevokeProducts;
import gov.saip.applicationservice.common.model.SubClassification;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {DocumentMapper.class})
public interface RevokeProductsMapper extends BaseMapper<RevokeProducts, RevokeProductsDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    RevokeProductsDto map(RevokeProducts revokeProducts);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    RevokeProducts unMap(RevokeProductsDto revokeProductsDto);

    @AfterMapping
    default void afterMapEntityToDto(RevokeProducts entity, @MappingTarget RevokeProductsDto dto) {
        if (!Collections.isEmpty(entity.getDocuments())) {
            List<Long> documentIds = entity.getDocuments().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            dto.setDocumentIds(documentIds);
        }
        if (!Collections.isEmpty(entity.getSubClassifications())) {
            List<Long> subClassificationIds = entity.getSubClassifications().stream()
                    .map(SubClassification::getId)
                    .collect(Collectors.toList());
            dto.setSubClassificationsIds(subClassificationIds);
        }
    }
    @AfterMapping
    default void afterMapDtoToEntity(RevokeProductsDto dto, @MappingTarget RevokeProducts entity) {
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
        if (dto.getSubClassificationsIds() != null && !dto.getSubClassificationsIds().isEmpty()) {
            List<SubClassification> subClassifications = dto.getSubClassificationsIds().stream()
                    .map(id -> {
                        SubClassification doc = new SubClassification();
                        doc.setId(id);
                        return doc;
                    })
                    .collect(Collectors.toList());
            entity.setSubClassifications(subClassifications);
        }
    }

}
