package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkAcceleratedTypeDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationServicesDto;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import gov.saip.applicationservice.common.model.LkApplicationService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface LkApplicationServiceMapper extends BaseMapper<LkApplicationService, LkApplicationServicesDto> {

    @Override
    @Mapping(source = "category.applicationCategoryDescAr" , target = "applicationCategoryDescAr")
    @Mapping(source = "category.applicationCategoryDescEn" , target = "applicationCategoryDescEn")
   LkApplicationServicesDto map(LkApplicationService lkApplicationService);


    List<LkApplicationServicesDto> mapRequestToEntity(List<LkApplicationService> lkApplicationServices);

 }
