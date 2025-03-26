package gov.saip.applicationservice.common.mapper.consultation;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationRequestDto;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.mapper.consultation.trademarkconultationcomment.ExaminerConsultationCommentMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ExaminerConsultation;
import gov.saip.applicationservice.common.model.Document;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

@Mapper(uses = {DocumentMapper.class , ExaminerConsultationCommentMapper.class})
public interface ExaminerConsultationMapper extends BaseMapper<ExaminerConsultation, ExaminerConsultationRequestDto> {


    ExaminerConsultation mapConsultationsTOConsultationsDto(ExaminerConsultationDto dto);

    List<ExaminerConsultation> mapListConsultationsTOListConsultationsDto(List<ExaminerConsultationDto> dto);

    ExaminerConsultationDto unMapListConsultationTOListConsultationDto(ExaminerConsultation entity);

    List<ExaminerConsultationDto> unMapListConsultationTOListConsultationDto(List<ExaminerConsultation> entities);

    @AfterMapping

    default void afterMapDtoToEntity(@MappingTarget ExaminerConsultation entity, ExaminerConsultationRequestDto dto) {

        if (Objects.nonNull(dto.getReceiverDocumentId())) {
            Document document = new Document(dto.getReceiverDocumentId());
            entity.setReceiverDocument(document);

        }
//        if(Objects.nonNull(dto.getComments()))
//        {
//            if(!dto.getComments().isEmpty()){
//                ExaminerConsultationCommentMapper examinerConsultationCommentMapper = Mappers.getMapper(ExaminerConsultationCommentMapper.class);
//                entity.setComments(examinerConsultationCommentMapper.unMap(dto.getComments()));
//            }
//        }
        if (Objects.nonNull(dto.getSenderDocumentId())) {
            Document document = new Document(dto.getSenderDocumentId());
            entity.setSenderDocument(document);
        }
        if (Objects.nonNull(dto.getApplicationId())) {
            entity.setApplication(new ApplicationInfo(dto.getApplicationId()));
        }
    }

    @AfterMapping
    default void afterMapDtoToEntity(@MappingTarget ExaminerConsultation entity, ExaminerConsultationDto dto) {
//        if(Objects.nonNull(dto.getComments()))
//        {
//            if(!dto.getComments().isEmpty()){
//                ExaminerConsultationCommentMapper examinerConsultationCommentMapper = Mappers.getMapper(ExaminerConsultationCommentMapper.class);
//                entity.setComments(examinerConsultationCommentMapper.unMap(dto.getComments()));
//            }
//        }

        if (Objects.nonNull(dto.getApplicationId())) {
            entity.setApplication(new ApplicationInfo(dto.getApplicationId()));
        }
    }

    @AfterMapping
    default void afterMapEntityToDto(ExaminerConsultation entity, @MappingTarget ExaminerConsultationDto dto ) {


        if (Objects.nonNull(entity.getApplication())) {
            dto.setApplicationId(entity.getApplication().getId());
        }

//        if(Objects.nonNull(entity.getComments()))
//        {
//            if(!entity.getComments().isEmpty()){
//                ExaminerConsultationCommentMapper examinerConsultationCommentMapper = Mappers.getMapper(ExaminerConsultationCommentMapper.class);
//                dto.setComments(examinerConsultationCommentMapper.map(entity.getComments()));
//            }
//        }
    }
}
