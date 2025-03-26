package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyCommentsDto;
import gov.saip.applicationservice.common.dto.CustomerExtClassifyDto;
import gov.saip.applicationservice.common.model.CustomerExtClassify;
import gov.saip.applicationservice.common.model.CustomerExtClassifyComments;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomerExtClassifyCommentsMapper.class})
public interface CustomerExtClassifyMapper extends BaseMapper<CustomerExtClassify, CustomerExtClassifyDto> {

    @Override
    @Mappings({
            @Mapping(source = "applicationInfo.id", target = "applicationId")
    })
    CustomerExtClassifyDto map(CustomerExtClassify customerExtClassify);

    @Override
    @Mappings({
            @Mapping(source = "applicationId", target = "applicationInfo.id")
    })
    CustomerExtClassify unMap(CustomerExtClassifyDto customerExtClassifyDto);

}
