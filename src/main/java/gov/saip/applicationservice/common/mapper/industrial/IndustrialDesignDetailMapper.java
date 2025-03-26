package gov.saip.applicationservice.common.mapper.industrial;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationSubstantiveExaminationRetrieveDto;
import gov.saip.applicationservice.common.dto.industrial.ApplicationIndustrialDesignDetailDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailReqDto;
import gov.saip.applicationservice.common.model.industrial.IndustrialDesignDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper()
public interface IndustrialDesignDetailMapper extends BaseMapper<IndustrialDesignDetail, IndustrialDesignDetailDto> {


    @Mapping(target = "id", ignore = true)
    IndustrialDesignDetail mapDetReq(IndustrialDesignDetailReqDto req);
    @Mapping(target = "id", ignore = true)
    IndustrialDesignDetail mapDetReq(IndustrialDesignDetailReqDto req, @MappingTarget IndustrialDesignDetail entity);

    ApplicationIndustrialDesignDetailDto mapAppSubExRetrieveDtoToIndustDesignSubExDetailDto(ApplicationSubstantiveExaminationRetrieveDto req, @MappingTarget ApplicationIndustrialDesignDetailDto dto);
    ApplicationIndustrialDesignDetailDto mapApplicationDet(IndustrialDesignDetail e);

}
