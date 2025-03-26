package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.SubstantiveReportDto;
import gov.saip.applicationservice.common.enums.ExaminerReportType;
import gov.saip.applicationservice.common.mapper.SubstantiveReportMapper;
import gov.saip.applicationservice.common.model.SubstantiveExaminationReport;
import gov.saip.applicationservice.common.service.ApplicationNotesService;
import gov.saip.applicationservice.common.service.SubstantiveReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"/kc/substantive-report", "/internal-calling/substantive-report"})
@RequiredArgsConstructor
@Slf4j
public class SubstantiveReportController extends BaseController<SubstantiveExaminationReport, SubstantiveReportDto, Long> {

    private final SubstantiveReportService substantiveReportService;
    private final SubstantiveReportMapper substantiveReportMapper;
    private final ApplicationNotesService applicationNotesService;

    @Override
    protected BaseService<SubstantiveExaminationReport, Long> getService() {
        return  substantiveReportService;
    }

    @Override
    protected BaseMapper<SubstantiveExaminationReport, SubstantiveReportDto> getMapper() {
        return substantiveReportMapper;
    }
    @Override
    public ApiResponse<Long> insert(@Valid @RequestBody SubstantiveReportDto substantiveReportDto) {
        return ApiResponse.ok(substantiveReportService.update(substantiveReportDto).getId());
    }

    @GetMapping("/{appId}/application")
    public ApiResponse<List<SubstantiveReportDto>> getAllReports(@PathVariable(name = "appId") Long appId,
                                                                 @RequestParam(value = "type", required = false) ExaminerReportType type) {
        List<SubstantiveReportDto> substantiveReportDtoList = substantiveReportService.getAllByApplicationId(appId, type);

        for(SubstantiveReportDto dto : substantiveReportDtoList){
            dto.setSectionNotes(applicationNotesService.findAppNotesByDecisionAndNotesType(dto,"SEND_BACK","EXAMINAR"));
        }

        return ApiResponse.ok(substantiveReportDtoList);
    }

    @GetMapping("/acceptWithCondition/application/{appId}")
    public ApiResponse<SubstantiveReportDto> getLastAcceptWithConditionReportByApplicationId(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(substantiveReportService.getLastAcceptWithConditionReportByApplicationId(appId));
    }

}
