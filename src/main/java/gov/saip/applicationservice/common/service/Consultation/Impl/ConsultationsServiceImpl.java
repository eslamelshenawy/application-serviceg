package gov.saip.applicationservice.common.service.Consultation.Impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.ProcessRequestDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationRequestDto;
import gov.saip.applicationservice.common.mapper.consultation.ExaminerConsultationMapper;
import gov.saip.applicationservice.common.mapper.consultation.trademarkconultationcomment.ExaminerConsultationCommentMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.consultation.ExaminerConsultationCommentRepository;
import gov.saip.applicationservice.common.repository.consultation.ExaminerConsultationRepository;
import gov.saip.applicationservice.common.service.BPMCallerService;
import gov.saip.applicationservice.common.service.Consultation.ConsultationsService;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static gov.saip.applicationservice.common.enums.RequestTypeEnum.EXAMINER_CONSULTATION;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConsultationsServiceImpl extends BaseServiceImpl<ExaminerConsultation, Long> implements ConsultationsService {
    private final ExaminerConsultationMapper examinerConsultationMapper;
    private final ExaminerConsultationCommentRepository examinerConsultationCommentRepository;
    private final ExaminerConsultationRepository examinerConsultationRepository;
    private final BPMCallerService bpmCallerService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ExaminerConsultationCommentMapper examinerConsultationCommentMapper;
    @Override
    protected BaseRepository<ExaminerConsultation, Long> getRepository() {
        return examinerConsultationRepository;
    }
    @Override
    @Transactional
    public ExaminerConsultation insert(ExaminerConsultation entity) {
        // Save the ExaminerConsultation first
        ExaminerConsultation persistedExaminerConsultation = saveConsultation(entity);

        // Associate and save comments with the persisted ExaminerConsultation
        saveComments(persistedExaminerConsultation);
        startExaminerConsultationProcess(entity);
        return persistedExaminerConsultation;
    }

    @Transactional
    public ExaminerConsultation saveConsultation(ExaminerConsultation entity) {
        // Save the ExaminerConsultation entity and return the persisted instance
        return super.insert(entity);
    }

    @Transactional
    public void saveComments(ExaminerConsultation persistedExaminerConsultation) {
        if (Objects.nonNull(persistedExaminerConsultation.getComments())) {
            if (!persistedExaminerConsultation.getComments().isEmpty()) {
                // Associate the comments with the persisted ExaminerConsultation
                for (ExaminerConsultationComment comment : persistedExaminerConsultation.getComments()) {
                    comment.setExaminerConsultation(persistedExaminerConsultation);
                }
                examinerConsultationCommentRepository.saveAll(persistedExaminerConsultation.getComments());
            }
        }
    }

    @Transactional
    public void saveComments(ExaminerConsultation persistedExaminerConsultation, List<ExaminerConsultationComment> comments) {
        if (Objects.nonNull(comments)) {
            if (!comments.isEmpty()) {
                // Associate the comments with the persisted ExaminerConsultation
                for (ExaminerConsultationComment comment : comments) {
                    comment.setExaminerConsultation(persistedExaminerConsultation);
                }
                examinerConsultationCommentRepository.saveAll(comments);
            }
        }
    }


    public void completeConsultation(Long consultationId, String decision, String comment) {
        RequestTasksDto taskByRowId = bpmCallerService.getTaskByRowId(consultationId,EXAMINER_CONSULTATION.name());
        if (Objects.isNull(taskByRowId) || Objects.isNull(taskByRowId.getTaskId())) {
            log.info("there is no consultations with id {}", consultationId);
            return;
        }
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> acceptApplicantModifications = new HashMap<>();
        acceptApplicantModifications.put("value", decision);
        variables.put("approved", acceptApplicantModifications);
        completeTaskRequestDto.setVariables(variables);
        completeTaskRequestDto.setNotes(comment);
        bpmCallerFeignClient.completeUserTask(taskByRowId.getTaskId(), completeTaskRequestDto);
    }


    public void startExaminerConsultationProcess(ExaminerConsultation entity) {
        ProcessRequestDto dto = new ProcessRequestDto();
        dto.setProcessId("examiner_consultation_process");
        Map<String, Object> vars = new HashMap<>();
        vars.put("EXAMINER_USER_NAME", entity.getUserNameSender());
        vars.put("CONSULTANT_USER_NAME", entity.getUserNameReceiver());
        vars.put("identifier", entity.getUserNameSender());
        vars.put("id", entity.getId().toString());
        vars.put("requestTypeCode", "EXAMINER_CONSULTATION");
        vars.put("REQUESTS_APPLICATION_ID_COLUMN", entity.getApplication().getId().toString());
        dto.setVariables(vars);
        bpmCallerService.startApplicationProcess(dto);
    }


    public Long Replay(ExaminerConsultationRequestDto dto) {
        ExaminerConsultation entity = getConsultationById(dto);
        List<ExaminerConsultationComment> newComments = null;
        if (Objects.nonNull(dto.getComments())) {
            if (!dto.getComments().isEmpty()) {
                newComments = examinerConsultationCommentMapper.unMap(dto.getComments());
            }
        }
        if (Objects.nonNull(dto.getSenderDocumentId()))
            entity.setSenderDocument(new Document(dto.getSenderDocumentId()));
        if (Objects.nonNull(dto.getReceiverDocumentId()))
            entity.setReceiverDocument(new Document(dto.getReceiverDocumentId()));
        ExaminerConsultation savedEntity = saveConsultation(entity);
        if (Objects.nonNull(newComments))
            saveComments(savedEntity, newComments);

        completeConsultation(dto.getId(), "YES", dto.getComments().get(0).getComment());

        return savedEntity.getId();
    }

    public ExaminerConsultation getConsultationById(ExaminerConsultationRequestDto dto) {
        return findById(dto.getId());
    }


    public String getReplayHijriDate() {
        return Utilities.convertDateFromGregorianToHijri(LocalDate.now());
    }

    @Override
    public String refuseConsultation(Long consultationId) {
        completeConsultation(consultationId, "NO", null);
        return "Refuse Consultation Done Well";
    }

    @Override
    public List<ExaminerConsultationDto> listAllConsultationByAppId(Long appId) {
        Optional<List<ExaminerConsultation>>examinerConsultationsDto = examinerConsultationRepository.findByApplicationId(appId);
        if(examinerConsultationsDto.isPresent()){
            if(!examinerConsultationsDto.get().isEmpty()){
                return examinerConsultationMapper.unMapListConsultationTOListConsultationDto(examinerConsultationsDto.get());
            }
        }

        return null ;
    }

    public ExaminerConsultationDto getConsultationById(Long consultationId) {
        return examinerConsultationMapper.unMapListConsultationTOListConsultationDto(findById(consultationId));

    }

}
