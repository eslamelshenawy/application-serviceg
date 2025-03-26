package gov.saip.applicationservice.common.mapper;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.dto.DocumentWithCommentDto;
import gov.saip.applicationservice.common.dto.DocumentWithTypeDto;
import gov.saip.applicationservice.common.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface DocumentCommentMapper extends BaseMapper<Document, DocumentWithCommentDto> {
    @Override
    @Mapping(source = "documentComments", target = "comments")
    DocumentWithCommentDto map(Document document);

    List<DocumentDto> mapToDocumentDto(List<Document> documents);
    List<DocumentWithTypeDto> mapToDocumentWithTypeDto(List<Document> documents);
}