package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.SimilarTrademarkDto;
import gov.saip.applicationservice.common.model.SimilarTrademark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SimilarTrademarkMapper extends BaseMapper<SimilarTrademark, SimilarTrademarkDto> {
    @Mapping(target = "applicationInfoId", source = "applicationInfo.id")
    SimilarTrademarkDto map(SimilarTrademark SimilarTrademark);

    @Override
    @Mapping(source = "applicationInfoId", target = "applicationInfo.id")
    SimilarTrademark unMap(SimilarTrademarkDto dto);


}
