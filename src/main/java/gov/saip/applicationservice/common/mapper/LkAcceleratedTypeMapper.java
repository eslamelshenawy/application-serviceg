package gov.saip.applicationservice.common.mapper;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkAcceleratedTypeDto;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper()
public interface LkAcceleratedTypeMapper extends BaseMapper<LkAcceleratedType, LkAcceleratedTypeDto> {


    @Override
    @Mapping(source = "applicationCategory.applicationCategoryDescAr" , target = "applicationCategoryDescAr")
    @Mapping(source = "applicationCategory.applicationCategoryDescEn" , target = "applicationCategoryDescEn")
    LkAcceleratedTypeDto map(LkAcceleratedType lkAcceleratedType);


    List<LkAcceleratedTypeDto> mapRequestToEntity(List<LkAcceleratedType> lkAcceleratedType);
}