package gov.saip.applicationservice.common.mapper.consultation.trademarkconultationcomment;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationRequestDto;
import gov.saip.applicationservice.common.dto.consultation.trademarkexaminercomment.ExaminerConsultationCommentDto;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.ExaminerConsultation;
import gov.saip.applicationservice.common.model.ExaminerConsultationComment;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;

@Mapper()
public interface ExaminerConsultationCommentMapper extends BaseMapper<ExaminerConsultationComment, ExaminerConsultationCommentDto> {

    @Override
    @Mapping(source ="examinerConsultation.id" ,target = "consultationId")
    @Mapping(source ="createdDate" ,target = "createdCommentDate")
    ExaminerConsultationCommentDto map(ExaminerConsultationComment examinerConsultationComment);

    @Override
    @Mapping(source ="consultationId" ,target = "examinerConsultation.id")
    ExaminerConsultationComment unMap(ExaminerConsultationCommentDto examinerConsultationCommentDto);
}
