package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceDto;
import gov.saip.applicationservice.modules.plantvarieties.model.ProveExcellence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProveExcellenceMapper extends BaseMapper<ProveExcellence, ProveExcellenceDto> {
    @Override
    ProveExcellenceDto map(ProveExcellence proveExcellence);
    @Override
    @Mapping(source = "lkpvPropertyId", target = "lkpvProperty.id")
    @Mapping(source = "lkpvPropertyOptionsId", target = "lkpvPropertyOptions.id")
    @Mapping(source = "lkpvPropertyOptionsSecondId", target = "lkpvPropertyOptionsSecond.id")
    @Mapping(source = "lkVegetarianTypesId", target = "lkVegetarianTypes.id")
    ProveExcellence unMap(ProveExcellenceDto proveExcellenceDto);
}
