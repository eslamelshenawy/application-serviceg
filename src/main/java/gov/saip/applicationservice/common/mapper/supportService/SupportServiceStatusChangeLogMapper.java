package gov.saip.applicationservice.common.mapper.supportService;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.supportService.SupportServiceStatusChangeLog;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface SupportServiceStatusChangeLogMapper extends BaseMapper<SupportServiceStatusChangeLog, SupportServiceStatusChangeLogDto> {

    List<BaseStatusChangeLogDto> mapToBaseStatusChangeLogDto(List<SupportServiceStatusChangeLog> t);

    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget SupportServiceStatusChangeLog entity, SupportServiceStatusChangeLogDto dto) {
        if (dto.getSupportServicesTypeId() != null) {
            entity.setSupportServicesType(new ApplicationSupportServicesType(dto.getSupportServicesTypeId()));
        }

        if (dto.getNewStatusCode() != null) {
            entity.setNewStatusByCode(dto.getNewStatusCode());
        }
    }
}
