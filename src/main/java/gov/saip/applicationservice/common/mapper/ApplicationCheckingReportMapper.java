package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationCheckingReportDto;
import gov.saip.applicationservice.common.model.ApplicationCheckingReport;
import org.mapstruct.Mapper;

@Mapper
public interface ApplicationCheckingReportMapper extends BaseMapper<ApplicationCheckingReport, ApplicationCheckingReportDto> {
}
