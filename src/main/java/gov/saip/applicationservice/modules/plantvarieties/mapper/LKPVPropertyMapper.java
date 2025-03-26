package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyDto;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LKPVPropertyMapper extends BaseMapper<LKPVProperty, LKPVPropertyDto> {

    @Override
    @Mapping(source = "lkVegetarianType.id", target = "lkVegetarianTypeId")
    LKPVPropertyDto map(LKPVProperty LKPVProperty);

    @Override
    @Mapping(source = "lkVegetarianTypeId", target = "lkVegetarianType.id")
    LKPVProperty unMap(LKPVPropertyDto LKPVPropertyDTO);
}
