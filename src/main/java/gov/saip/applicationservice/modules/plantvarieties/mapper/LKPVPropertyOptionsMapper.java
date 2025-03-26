package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyOptionsDto;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVPropertyOptions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface LKPVPropertyOptionsMapper extends BaseMapper<LKPVPropertyOptions, LKPVPropertyOptionsDto> {


    @Override
    @Mapping(source = "LKPVProperty.id",target = "lkPVPropertyId")
    LKPVPropertyOptionsDto map(LKPVPropertyOptions lkpvPropertyOptions);

    @Override
    @Mapping(target = "LKPVProperty.id", source = "lkPVPropertyId")
    LKPVPropertyOptions unMap(LKPVPropertyOptionsDto lkpvPropertyOptionsDto);
}
