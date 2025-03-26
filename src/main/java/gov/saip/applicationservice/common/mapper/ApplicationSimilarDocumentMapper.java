package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationSimilarDocumentDto;
import gov.saip.applicationservice.common.model.ApplicationSimilarDocument;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {DocumentMapper.class})
public interface ApplicationSimilarDocumentMapper extends BaseMapper<ApplicationSimilarDocument, ApplicationSimilarDocumentDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ApplicationSimilarDocumentDto map(ApplicationSimilarDocument applicationSimilarDocument);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationSimilarDocument unMap(ApplicationSimilarDocumentDto applicationSimilarDocumentDto);


    @AfterMapping
    default void afterUnMapDtoToEntity(@MappingTarget ApplicationSimilarDocument applicationSimilarDocument, ApplicationSimilarDocumentDto applicationSimilarDocumentDto) {
        if (applicationSimilarDocumentDto.getApplicationId() == null) {
            applicationSimilarDocument.setApplicationInfo(null);
        }

    }

}
