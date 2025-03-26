package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationRelevantmapDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.DocumentsTemplateDto;
import gov.saip.applicationservice.common.dto.lookup.LkAcceleratedTypeDto;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.DocumentsTemplate;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper()
public interface DocumentsTemplateMapper extends BaseMapper<DocumentsTemplate, DocumentsTemplateDto> {

    @Override
    @Mapping(source = "lkNexuoUser.name" , target = "name")
    @Mapping(source = "lkNexuoUser.type" , target = "type")
    @Mapping(source = "category.applicationCategoryDescAr" , target = "applicationCategoryDescAr")
    @Mapping(source = "category.applicationCategoryDescEn" , target = "applicationCategoryDescEn")
    DocumentsTemplateDto map(DocumentsTemplate documentsTemplate);

    List<DocumentsTemplateDto> mapRequestToEntity(List<DocumentsTemplate> documentsTemplates);
}
