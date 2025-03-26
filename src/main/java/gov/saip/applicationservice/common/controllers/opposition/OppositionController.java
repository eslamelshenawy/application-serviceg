package gov.saip.applicationservice.common.controllers.opposition;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.DocumentWithCommentDto;
import gov.saip.applicationservice.common.dto.opposition.*;
import gov.saip.applicationservice.common.mapper.opposition.OppositionMapper;
import gov.saip.applicationservice.common.model.opposition.Opposition;
import gov.saip.applicationservice.common.service.opposition.OppositionService;
import gov.saip.applicationservice.util.SupportServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = {"/kc/opposition", "/internal-calling/opposition"})
@RequiredArgsConstructor
public class OppositionController extends BaseController<Opposition, OppositionDto, Long> {

    private final OppositionService oppositionService;
    private final OppositionMapper oppositionMapper;
    private final SupportServiceValidator supportServiceValidator;
    @Override
    protected BaseService<Opposition, Long> getService() {
        return oppositionService;
    }

    @Override
    protected BaseMapper<Opposition, OppositionDto> getMapper() {
        return oppositionMapper;
    }
    @PutMapping("/applicant/reply")
    public ApiResponse<Long> applicantReply(@RequestBody OppositionDto dto, @RequestParam(name = "taskId", required = true) String taskId) {
        Opposition entity = oppositionMapper.unMap(dto);
        Long result = oppositionService.applicantReply(entity, taskId);
        return ApiResponse.ok(result);
    }


    @PutMapping("/complainer-hearing")
    public ApiResponse<Long> updateComplainerHearingSession(@RequestBody OppositionDto dto) {
        Long result = oppositionService.updateComplainerHearingSession(dto);
        return ApiResponse.ok(result);
    }

    @PutMapping("/applicant-hearing")
    public ApiResponse<Long> updateApplicantHaringSession(@RequestBody OppositionDto dto) {
        Long result = oppositionService.updateApplicantHearingSession(dto);
        return ApiResponse.ok(result);
    }


    @PutMapping("/applicant-examiner-notes")
    public ApiResponse<Long> updateApplicantExaminerNotes(@RequestBody OppositionDto dto) {
        Long result = oppositionService.updateApplicantExaminerNotes(dto);
        return ApiResponse.ok(result);
    }

    @PutMapping("/examiner-final-decision")
    public ApiResponse<Long> examinerFinalDecision(@RequestBody OppositionDto dto) {
        Long result = oppositionService.examinerFinalDecision(dto);
        return ApiResponse.ok(result);
    }

    @PutMapping("/head-examiner-notes-to-examiner")
    public ApiResponse<Long> headExaminerNotesToExaminer(@RequestBody OppositionDto dto) {
        Opposition entity = oppositionMapper.unMap(dto);
        Long result = oppositionService.headExaminerNotesToExaminer(entity);
        return ApiResponse.ok(result);
    }

    @PutMapping("/head-examiner-confirm")
    public ApiResponse<Long> confirmFinalDecisionFromHeadOfExaminer(@RequestBody OppositionDto dto) {
        oppositionService.confirmFinalDecisionFromHeadOfExaminer(dto.getId());
        return ApiResponse.ok(dto.getId());
    }


    @GetMapping("/trade-mark/{oppositionId}")
    public ApiResponse<OppositionDetailsDto> getOppositionTradeMarkApplicationDetails(@PathVariable(name="oppositionId")String oppositionId ,@RequestParam(value="gate")boolean gate) {
        // ToDo for internal usage
        return ApiResponse.ok(oppositionService.getTradeMarkOppositionDetails(Long.valueOf(oppositionId),gate));
    }


    @GetMapping("/patent/{oppositionId}")
    public ApiResponse<OppositionDetailsDto> getOppositionPatentApplicationDetails(@PathVariable(name="oppositionId")String oppositionId,@RequestParam(value="gate")boolean gate ) {
        // ToDo for internal usage
        return ApiResponse.ok(oppositionService.getPatentOppositionDetails(Long.valueOf(oppositionId),gate));
    }


    @GetMapping("/industrial-design/{oppositionId}")
    public ApiResponse<OppositionDetailsDto> getOppositionIndustrialApplicationDetails(@PathVariable(name="oppositionId")String oppositionId,@RequestParam(value="gate")boolean gate) {
        // ToDo for internal usage
        return ApiResponse.ok(oppositionService.getIndustrialOppositionDetails(Long.valueOf(oppositionId),gate));
    }


    // ToDo this List All Documents For Application And It's Opposition
    @GetMapping("/documents/{oppositionId}")
    ApiResponse<List<DocumentWithCommentDto>>getOppositionApplicationDocumentsWithComments(@PathVariable(name="oppositionId")String oppositionId){
        return ApiResponse.ok(oppositionService.getAllApplicationOppositionDocumentsWithComments( Long.valueOf(oppositionId)));
    }

    @GetMapping("/applicant/{oppositionId}")
    public ApiResponse<ApplicantOppositionViewDto> getApplicationApplicantOppositionDetails(@PathVariable(name="oppositionId")String oppositionId) {
        // ToDo for External usage
        return ApiResponse.ok(oppositionService.getApplicationApplicantOppositionDetails(Long.valueOf(oppositionId)));
    }

    @GetMapping("/report/trade-mark/{oppositionId}")
    public ApiResponse<OppositionTradeMarkReportDetailsDto> getOppositionTradeMarkApplicationReport(@PathVariable(name="oppositionId")String oppositionId ) {
        // ToDo for internal usage
        return ApiResponse.ok(oppositionService.getOppositionTradeMarkApplicationReport(Long.valueOf(oppositionId)));
    }
    @GetMapping("/report/patent/{oppositionId}")
    public ApiResponse<OppositionPatentReportDetailsDto> getOppositionPatentApplicationReport(@PathVariable(name="oppositionId")String oppositionId ) {
        // ToDo for internal usage
        return ApiResponse.ok(oppositionService.getOppositionPatentApplicationReport(Long.valueOf(oppositionId)));
    }
    @GetMapping("/report/Industrial/{oppositionId}")
    public ApiResponse<OppositionIndustrialReportDetailsDto> getOppositionIndustrialApplicationReport(@PathVariable(name="oppositionId")String oppositionId ) {
        // ToDo for internal usage
        return ApiResponse.ok(oppositionService.getOppositionIndustrialApplicationReport(Long.valueOf(oppositionId)));
    }

    @GetMapping("/service/{serviceId}")
    public ApiResponse<OppositionSupportServiceDto> getDetailsBySupportServiceId(@PathVariable(name="serviceId")Long serviceId){
        Opposition opposition = oppositionService.findById(serviceId);
        OppositionSupportServiceDto requestDto = oppositionMapper.mapOppositionSupportServiceDto(opposition);
        return ApiResponse.ok(requestDto);
    }
}
