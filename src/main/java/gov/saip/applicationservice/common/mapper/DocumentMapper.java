package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper()
public interface DocumentMapper extends BaseMapper<Document, ApplicationRelevantmapDto> {


    Document mapRequestToEntity(DocumentDto dto);

    List<Document> mapRequestToEntity(List<DocumentDto> dto);
    @Mapping(target ="appId" ,source = "applicationInfo.id")
    DocumentDto mapToDto(Document document);

    List<DocumentDto> mapToDtoss(List<Document> document);
    
    DocumentLightDto mapToLightDto(Document document);
    
    @Mapping(target ="applicationId" ,source = "applicationInfo.id")
    ApplicationDocumentLightDto mapToAppDocLightDto(Document document);
    
    List<ApplicationDocumentLightDto> mapToAppDocLightDto(List<Document> document);
    
    DocumentWithCommentDto mapToDocWithComment(Document document);
    List<DocumentWithCommentDto> mapToDocWithComment(List<Document> document);
}
