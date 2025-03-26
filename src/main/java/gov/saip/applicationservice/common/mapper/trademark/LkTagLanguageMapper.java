package gov.saip.applicationservice.common.mapper.trademark;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.trademark.LkTagLanguageDto;
import gov.saip.applicationservice.common.model.trademark.LkTagLanguage;
import org.mapstruct.Mapper;

@Mapper
public interface LkTagLanguageMapper extends BaseMapper<LkTagLanguage, LkTagLanguageDto> {
}
