package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationOtherDocumentDto;
import gov.saip.applicationservice.common.dto.ApplicationSimilarDocumentDto;
import gov.saip.applicationservice.common.dto.ApplicationWordDto;
import gov.saip.applicationservice.common.model.ApplicationOtherDocument;
import gov.saip.applicationservice.common.model.ApplicationSimilarDocument;
import gov.saip.applicationservice.common.model.ApplicationWord;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper
public interface ApplicationOtherDocumentMapper extends BaseMapper<ApplicationOtherDocument, ApplicationOtherDocumentDto> {


    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationOtherDocument  unMap(ApplicationOtherDocumentDto applicationOtherDocumentDto);


    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ApplicationOtherDocumentDto map(ApplicationOtherDocument applicationOtherDocument);



    @AfterMapping
    default void afterUnMapDtoToEntity(@MappingTarget ApplicationOtherDocument applicationOtherDocument, ApplicationOtherDocumentDto applicationOtherDocumentDto) {
        if (applicationOtherDocumentDto.getApplicationId() == null) {
            applicationOtherDocument.setApplicationInfo(null);
        }

    }


}
