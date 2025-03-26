package gov.saip.applicationservice.common.mapper;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationSearchSimilarsDto;
import gov.saip.applicationservice.common.model.ApplicationSearchSimilars;
import org.mapstruct.Mapper;

@Mapper
public interface ApplicationSearchSimilarsMapper extends BaseMapper<ApplicationSearchSimilars , ApplicationSearchSimilarsDto> {


}
