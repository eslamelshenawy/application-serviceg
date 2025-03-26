package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryLightDto;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface LkApplicationCategoryMapper extends BaseMapper<LkApplicationCategory, LKApplicationCategoryDto> {
    
    LKApplicationCategoryLightDto mapEntityToLightDto(LkApplicationCategory entity);
    List<LKApplicationCategoryLightDto> mapEntityToLightDto(List<LkApplicationCategory> entity);

 }
