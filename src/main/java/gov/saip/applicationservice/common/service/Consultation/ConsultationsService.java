package gov.saip.applicationservice.common.service.Consultation;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationRequestDto;
import gov.saip.applicationservice.common.model.ExaminerConsultation;

import java.util.List;


public interface ConsultationsService extends BaseService<ExaminerConsultation, Long> {
    ExaminerConsultationDto getConsultationById(Long consultationId);

    Long Replay(ExaminerConsultationRequestDto dto);

    String refuseConsultation(Long consultationId);
    List<ExaminerConsultationDto> listAllConsultationByAppId(Long appId);
}
