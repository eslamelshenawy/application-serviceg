package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationWordDto;
import gov.saip.applicationservice.common.model.ApplicationWord;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper
public interface ApplicationWordMapper extends BaseMapper<ApplicationWord, ApplicationWordDto> {


    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationWord unMap(ApplicationWordDto applicationWordDto);

    @Mapping(target = "applicationId", source = "applicationInfo.id")
    ApplicationWordDto map(ApplicationWord applicationWord);

    @AfterMapping
    default void afterUnMapDtoToEntity(@MappingTarget ApplicationWord applicationWord, ApplicationWordDto applicationWordDto) {
        if (applicationWordDto.getSynonyms() != null && !applicationWordDto.getSynonyms().isEmpty()) {
            applicationWord.setSynonym(String.join(",", applicationWordDto.getSynonyms()));
        }
        if (applicationWordDto.getApplicationId() == null) {
            applicationWord.setApplicationInfo(null);
        }

    }

    @AfterMapping
    default void afterMapDtoToEntity(ApplicationWord applicationWord, @MappingTarget ApplicationWordDto applicationWordDto) {
        if (applicationWord.getSynonym() != null) {
            List<String> list = Stream.of(applicationWord.getSynonym().split(","))
                    .collect(Collectors.toList());
            applicationWordDto.setSynonyms(list);
        }
    }
}
