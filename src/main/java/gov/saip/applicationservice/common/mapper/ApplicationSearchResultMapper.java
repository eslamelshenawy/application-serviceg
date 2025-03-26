package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationOtherDocumentDto;
import gov.saip.applicationservice.common.dto.ApplicationSearchResultDto;
import gov.saip.applicationservice.common.model.ApplicationOtherDocument;
import gov.saip.applicationservice.common.model.ApplicationSearchResult;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ApplicationSearchResultMapper extends BaseMapper<ApplicationSearchResult, ApplicationSearchResultDto> {


    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationSearchResult  unMap(ApplicationSearchResultDto applicationSearchResultDto);

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ApplicationSearchResultDto map(ApplicationSearchResult applicationSearchResult);



    @AfterMapping
    default void afterUnMapDtoToEntity(@MappingTarget ApplicationSearchResult applicationSearchResult, ApplicationSearchResultDto applicationSearchResultDto) {
        if (applicationSearchResultDto.getApplicationId() == null) {
            applicationSearchResult.setApplicationInfo(null);
        }

    }

}
