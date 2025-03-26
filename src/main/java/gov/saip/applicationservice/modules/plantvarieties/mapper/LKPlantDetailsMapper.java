package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPlantDetailsDto;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPlantDetails;
import org.mapstruct.Mapper;


@Mapper
public interface LKPlantDetailsMapper extends BaseMapper<LKPlantDetails, LKPlantDetailsDto> {
}
