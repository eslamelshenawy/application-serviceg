package gov.saip.applicationservice.common.mapper.trademark;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.trademark.LkTagTypeDescDto;
import gov.saip.applicationservice.common.model.trademark.LkTagTypeDesc;
import org.mapstruct.Mapper;

@Mapper
public interface LkTagTypeDescMapper extends BaseMapper<LkTagTypeDesc, LkTagTypeDescDto> {
}
