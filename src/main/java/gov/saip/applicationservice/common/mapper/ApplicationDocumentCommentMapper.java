package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationDocumentCommentDto;
import gov.saip.applicationservice.common.model.ApplicationDocumentComment;
import gov.saip.applicationservice.common.model.Document;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {DocumentMapper.class})
public interface ApplicationDocumentCommentMapper extends BaseMapper<ApplicationDocumentComment, ApplicationDocumentCommentDto> {

    @Override
    ApplicationDocumentComment unMap(ApplicationDocumentCommentDto applicationDocumentCommentDto);

    @AfterMapping
    default void afterUnMapDtoToEntity(@MappingTarget ApplicationDocumentComment t, ApplicationDocumentCommentDto dto) {
        if (dto.getCommentDocumentIds() != null && !dto.getCommentDocumentIds().isEmpty()) {
            List<Document> documents = dto.getCommentDocumentIds()
                    .stream()
                    .map(Document::new)
                    .collect(Collectors.toList());
            t.setCommentDocuments(documents);
        }
    }
}
