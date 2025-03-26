package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.RevokeLicenceRequestDto;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.RevokeLicenceRequest;
import gov.saip.applicationservice.util.CustomerServiceMapperUtil;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {DocumentMapper.class, CustomerServiceMapperUtil.class})
public interface RevokeLicenceRequestMapper extends BaseMapper<RevokeLicenceRequest, RevokeLicenceRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    @Mapping(source = "licenceRequest.customerId", target = "licenceRequest.customer", qualifiedByName = "customerInfoFromCustomerId")
    RevokeLicenceRequestDto map(RevokeLicenceRequest applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    RevokeLicenceRequest unMap(RevokeLicenceRequestDto applicationDatabaseDto);
    @AfterMapping
    default void afterMapEntityToDto(RevokeLicenceRequest entity, @MappingTarget RevokeLicenceRequestDto dto) {
        if (!Collections.isEmpty(entity.getDocuments())) {
            List<Long> documentIds = entity.getDocuments().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            dto.setDocumentIds(documentIds);
        }

        if (!Collections.isEmpty(entity.getLicenceRequest().getDocuments())) {
            List<Long> documentIds = entity.getLicenceRequest().getDocuments().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            dto.getLicenceRequest().setDocumentIds(documentIds);
        }
    }

    @AfterMapping
    default void afterMapDtoToEntity(RevokeLicenceRequestDto dto, @MappingTarget RevokeLicenceRequest entity) {
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
