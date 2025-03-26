package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LkClassificationVersionDto;
import gov.saip.applicationservice.common.model.LkClassificationVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LkClassificationVersionMapper extends BaseMapper<LkClassificationVersion, LkClassificationVersionDto> {


    @Override
    @Mapping(source = "category.id" , target = "categoryId")
    LkClassificationVersionDto map(LkClassificationVersion lkClassificationVersion);


    @Override
    @Mapping(source = "categoryId" , target = "category.id")
    LkClassificationVersion unMap(LkClassificationVersionDto lkClassificationVersionDto);

}
