package gov.saip.applicationservice.common.mapper.patent;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.patent.PatentAttributeChangeLogDto;
import gov.saip.applicationservice.common.model.patent.PatentAttributeChangeLog;
import org.mapstruct.Mapper;

@Mapper
public interface PatentAttributeChangeLogMapper extends BaseMapper<PatentAttributeChangeLog, PatentAttributeChangeLogDto> {}