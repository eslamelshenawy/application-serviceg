package gov.saip.applicationservice.common.mapper.applicationSnapShot;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationSnapShotDto.ApplicationSnapShotDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSnapShot;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Objects;

@Mapper()
public interface ApplicationSnapShotMapper extends BaseMapper<ApplicationSnapShot, ApplicationSnapShotDto> {


    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget ApplicationSnapShot entity, ApplicationSnapShotDto dto) {

        if (Objects.nonNull(dto.getApplicationId())) {
            entity.setApplication(new ApplicationInfo(dto.getApplicationId()));
        }
    }


    @AfterMapping
    default void afterMapEntityToDto(ApplicationSnapShot entity, @MappingTarget ApplicationSnapShotDto dto) {


        if (Objects.nonNull(entity.getApplication())) {
            dto.setApplicationId(entity.getApplication().getId());
        }


    }
}
