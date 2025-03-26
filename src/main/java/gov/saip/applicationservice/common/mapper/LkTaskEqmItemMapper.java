package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LkTaskEqmItemDto;
import gov.saip.applicationservice.common.dto.opposition.OppositionRequestDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LkTaskEqmItem;
import gov.saip.applicationservice.common.model.LkTaskEqmType;
import gov.saip.applicationservice.common.model.opposition.OppositionRequest;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface LkTaskEqmItemMapper extends BaseMapper<LkTaskEqmItem, LkTaskEqmItemDto> {

    @AfterMapping
    default void mapDtoToEntity(@MappingTarget LkTaskEqmItem entity, LkTaskEqmItemDto dto) {
        LkTaskEqmType type = new LkTaskEqmType();
        type.setCode(dto.getSaipCode());
        entity.setTypes(List.of(type));
    }

    @AfterMapping
    default void afterMapEntityToDto(LkTaskEqmItem entity, @MappingTarget LkTaskEqmItemDto dto) {
        if (!Collections.isEmpty(entity.getTypes())) {
            List<String> typeCodes  = entity.getTypes().stream()
                    .map(LkTaskEqmType::getCode)
                    .collect(Collectors.toList());
            dto.setSaipCode(String.join(",", typeCodes));
        }
    }
}
