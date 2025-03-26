package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationDatabaseDto;
import gov.saip.applicationservice.common.mapper.lookup.LkDatabaseMapper;
import gov.saip.applicationservice.common.model.ApplicationDatabase;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {LkDatabaseMapper.class})
public interface ApplicationDatabaseMapper extends BaseMapper<ApplicationDatabase, ApplicationDatabaseDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ApplicationDatabaseDto map(ApplicationDatabase applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationDatabase unMap(ApplicationDatabaseDto applicationDatabaseDto);


    @AfterMapping
    default void afterUnMapDtoToEntity(@MappingTarget ApplicationDatabase applicationDatabase, ApplicationDatabaseDto applicationDatabaseDto) {
        if (applicationDatabaseDto.getApplicationId() == null) {
            applicationDatabase.setApplicationInfo(null);
        }

    }

}
