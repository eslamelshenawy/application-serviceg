package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.GenericLookupDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationStatusChangeLog;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface ApplicationStatusChangeLogMapper extends BaseMapper<ApplicationStatusChangeLog, ApplicationStatusChangeLogDto> {


    List<BaseStatusChangeLogDto> mapToBaseStatusChangeLogDto(List<ApplicationStatusChangeLog> t);

    @Mapping(target = "nameEn", source = "ipsStatusDescEn")
    @Mapping(target = "nameAr", source = "ipsStatusDescAr")
    GenericLookupDto mapLkApplicationStatusToDto(LkApplicationStatus status);


    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget ApplicationStatusChangeLog entity, ApplicationStatusChangeLogDto dto) {
        if (dto.getApplicationId() != null) {
            entity.setApplication(new ApplicationInfo(dto.getApplicationId()));
        }

        if (dto.getNewStatusCode() != null) {
            entity.setNewStatusByCode(dto.getNewStatusCode());
        }
    }
}
