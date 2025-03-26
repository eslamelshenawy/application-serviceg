package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.TaskEqmDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LkTaskEqmType;
import gov.saip.applicationservice.common.model.TaskEqm;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface TaskEqmMapper extends BaseMapper<TaskEqm, TaskEqmDto> {

//    @Mapping(source = "applicationId", target = "applicationInfo.id")
    TaskEqm unMap(TaskEqmDto TaskEqmDto);

    @Mapping(target = "applicationId", source = "applicationInfo.id")
    TaskEqmDto map(TaskEqm TaskEqm);


    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget TaskEqm entity, TaskEqmDto dto) {
        if (dto.getApplicationId() != null) {
            entity.setApplicationInfo(new ApplicationInfo(dto.getApplicationId()));
        }

        if (dto.getTaskEqmTypeCode() != null) {
            LkTaskEqmType type = new LkTaskEqmType();
            type.setCode(dto.getTaskEqmTypeCode());
            entity.setTaskEqmType(type);
        }
    }






}
