package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.plantvarieties.dto.LkVegetarianTypeDto;
import gov.saip.applicationservice.modules.plantvarieties.model.LkVegetarianTypes;
import org.mapstruct.Mapper;

@Mapper
public interface LkVegetarianTypeMapper extends BaseMapper<LkVegetarianTypes, LkVegetarianTypeDto> {

}
