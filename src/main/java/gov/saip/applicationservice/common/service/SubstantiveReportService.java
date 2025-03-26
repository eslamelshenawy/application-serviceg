package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.SubstantiveReportDto;
import gov.saip.applicationservice.common.enums.ExaminerReportType;
import gov.saip.applicationservice.common.model.SubstantiveExaminationReport;

import java.util.List;

public interface SubstantiveReportService extends BaseService<SubstantiveExaminationReport, Long> {

    SubstantiveExaminationReport update(SubstantiveReportDto substantiveReportDto);

    List<SubstantiveReportDto> getAllByApplicationId(Long appId, ExaminerReportType type);

    SubstantiveReportDto getLastAcceptWithConditionReportByApplicationId(Long appId);
}
