package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkExaminationOfficeDto;
import gov.saip.applicationservice.common.model.LkExaminationOffice;
import org.mapstruct.Mapper;

@Mapper
public interface LkExaminationOfficeMapper extends BaseMapper<LkExaminationOffice, LkExaminationOfficeDto> {

}
