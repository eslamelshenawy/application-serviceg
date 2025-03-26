package gov.saip.applicationservice.common.controllers.consultations;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationRequestDto;
import gov.saip.applicationservice.common.mapper.consultation.ExaminerConsultationMapper;
import gov.saip.applicationservice.common.model.ExaminerConsultation;
import gov.saip.applicationservice.common.service.Consultation.ConsultationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/examiner-consultation", "/internal-calling/examiner-consultation"})
@RequiredArgsConstructor
@Slf4j
public class ExaminerConsultationController extends BaseController<ExaminerConsultation, ExaminerConsultationRequestDto, Long> {
    private final ConsultationsService consultationsService;
    private final ExaminerConsultationMapper examinerConsultationMapper;
    @Override
    protected BaseService<ExaminerConsultation, Long> getService() {
        return  consultationsService;
    }

    @Override
    protected BaseMapper<ExaminerConsultation, ExaminerConsultationRequestDto> getMapper() {
        return examinerConsultationMapper;
    }
    @GetMapping("/{consultationId}/retrieve")
    ApiResponse<ExaminerConsultationDto> getConsultationById(@PathVariable("consultationId") String consultationId) {
        return ApiResponse.ok(consultationsService.getConsultationById(Long.valueOf(consultationId)));
    }
    @PostMapping("/replay")
    ApiResponse<Long> replayOnConsultation(@RequestBody ExaminerConsultationRequestDto dto){
      return  ApiResponse.ok(consultationsService.Replay(dto));
    }
    @PostMapping("/refuse/{consultationId}")
    ApiResponse<String> refuseConsultation(@PathVariable("consultationId") String consultationId){
      return  ApiResponse.ok(consultationsService.refuseConsultation(Long.valueOf(consultationId)));
    }


    @GetMapping("/application/{appId}")

    ApiResponse<List<ExaminerConsultationDto>> listAllConsultationsByApplicationId(@PathVariable(name="appId")String appId){

        return ApiResponse.ok(consultationsService.listAllConsultationByAppId(Long.valueOf(appId)));
    }





}
