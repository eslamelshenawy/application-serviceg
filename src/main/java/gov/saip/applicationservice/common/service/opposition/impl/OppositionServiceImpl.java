package gov.saip.applicationservice.common.service.opposition.impl;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.dto.opposition.*;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.enums.opposition.OppositionFinalDecision;
import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.mapper.DocumentCommentMapper;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.mapper.opposition.OppositionMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.opposition.Opposition;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationStatusRepository;
import gov.saip.applicationservice.common.repository.opposition.OppositionRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationNiceClassificationService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceImpl;
import gov.saip.applicationservice.common.service.opposition.OppositionService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;



@Service
@Log4j2
@RequiredArgsConstructor
public class OppositionServiceImpl extends SupportServiceRequestServiceImpl<Opposition> implements OppositionService {

    private final OppositionRepository oppositionRepository;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final ApplicationAgentService applicationAgentService;
    private final ApplicationInfoService applicationInfoService;
    private final ClassificationMapper classificationMapper;
    private final DocumentMapper documentMapper;
    private final OppositionMapper oppositionMapper;
    private final DocumentsService documentsService;
    private final DocumentCommentMapper documentCommentMapper;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationNiceClassificationService applicationNiceClassificationService;
    private final LkApplicationStatusRepository lkApplicationStatusRepository;


    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return oppositionRepository;
    }

    public Opposition insert(Opposition entity) {
        ApplicationSupportServicesType insert = super.insert(SupportServiceType.OPPOSITION_REQUEST, entity);
        entity.setId(insert.getId());
        return entity;
    }


    @Override
    @Transactional
    public Long applicantReply(Opposition opposition, String taskId) {
        String isApplicantAskedForSession = "NO";

        if (Objects.isNull(opposition.getDocuments())  || opposition.getDocuments().isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        }

        opposition.getDocuments().forEach(document -> oppositionRepository.addOppositionDocument(opposition.getId(), document.getId()));

        if (Objects.nonNull(opposition.getApplicantHearingSession()) && Objects.nonNull(opposition.getApplicantHearingSession().getDate())) {
            oppositionRepository.updateApplicantHaringSessionDate(opposition.getId(), opposition.getApplicantHearingSession().getDate(), opposition.getApplicantHearingSession().getTime(), false);
            isApplicantAskedForSession = "YES";
        }

        Map<String, Object> varValue = new LinkedHashMap();
        varValue.put("value", isApplicantAskedForSession);

        Map<String, Object> processVars = new LinkedHashMap<>();
//        processVars.put("IS_APPLICANT_ASKED_FOR_SESSION", varValue);
        processVars.put("paySession", varValue);

        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(taskId, completeTaskRequestDto);

        return opposition.getId();
    }


    @Override
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        oppositionRepository.updateComplainerHearingSessionPaymentStatus(id);
        oppositionRepository.updateApplicationStatus(id, lkApplicationStatusRepository.findByCode(ApplicationStatusEnum.OBJECTOR.name()).get());
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    @Override
    @Transactional
    public void processApplicantHearingSessionPayment(Long id) {
        oppositionRepository.updateApplicantPaymentStatus(id);
        log.info("Call back APPLICANT_OPPOSITION_HEARING_REQ with id ==>> {} " , id);
        Map<String, Object> varValue = new LinkedHashMap();
        varValue.put("value", "YES");

        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", varValue);

        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        completeTaskRequestDto.setTaskDefinitionKey("SESSION_PAYMENT_OPP");
        completeTaskRequestDto.setRequestTypeEnum(RequestTypeEnum.OPPOSITION_REQUEST);
        bpmCallerFeignClient.completeTaskByRowId(id, completeTaskRequestDto);

    }

    @Override
    @Transactional
    public Long updateComplainerHearingSession(OppositionDto dto) {
        if (Objects.nonNull(dto.getHearingSession().getResult()))
            oppositionRepository.updateComplainerHaringSessionResult(dto.getId(), dto.getHearingSession().getResult(), dto.getHearingSession().getFileURL());
        else
            oppositionRepository.updateComplainerHaringSessionDate(dto.getId(), dto.getHearingSession().getDate(), dto.getHearingSession().getTime(), dto.getHearingSession().getIsHearingSessionScheduled());

        return dto.getId();
    }

    @Override
    @Transactional
    public Long updateApplicantHearingSession(OppositionDto dto) {
        if (Objects.nonNull(dto.getApplicantHearingSession().getResult()))
            oppositionRepository.updateApplicantHaringSessionResult(dto.getId(), dto.getApplicantHearingSession().getResult(), dto.getApplicantHearingSession().getFileURL());
        else
            oppositionRepository.updateApplicantHaringSessionDate(dto.getId(), dto.getApplicantHearingSession().getDate(), dto.getApplicantHearingSession().getTime(), dto.getApplicantHearingSession().getIsHearingSessionScheduled());

        return dto.getId();
    }

    @Override
    @Transactional
    public Long updateApplicantExaminerNotes(OppositionDto dto) {
        oppositionRepository.updateApplicantExaminerNotes(dto.getId(), dto.getApplicantExaminerNotes());
        return dto.getId();
    }

    @Override
    @Transactional
    public Long examinerFinalDecision(OppositionDto dto) {
        oppositionRepository.examinerFinalDecision(dto.getId(), dto.isAccepted() ? OppositionFinalDecision.ACCEPTED : OppositionFinalDecision.REJECTED, dto.getFinalNotes());
        return dto.getId();
    }

    @Override
    @Transactional
    public Long headExaminerNotesToExaminer(Opposition opposition) {

        if (Objects.nonNull(opposition.getDocuments()) && !opposition.getDocuments().isEmpty())
            oppositionRepository.addOppositionDocument(opposition.getId(), opposition.getDocuments().stream().findFirst().get().getId());

        oppositionRepository.headExaminerNotesToExaminer(opposition.getId(), opposition.getHeadExaminerNotesToExaminer());

        return opposition.getId();
    }


    @Override
    @Transactional
    public void confirmFinalDecisionFromHeadOfExaminer(Long id) {
        oppositionRepository.updateHeadExaminerConfirmation(id);
        LkApplicationStatus newApplicationStatus;
        OppositionFinalDecision finalDecisionById = oppositionRepository.getFinalDecisionById(id);
        SupportServiceRequestStatusEnum statusEnumCode =
                OppositionFinalDecision.ACCEPTED.equals(finalDecisionById) ?
                SupportServiceRequestStatusEnum.APPROVED:
                SupportServiceRequestStatusEnum.REJECTED;

        this.updateRequestStatusByCode(id, statusEnumCode);
        if (OppositionFinalDecision.ACCEPTED.equals(finalDecisionById)) {
            newApplicationStatus = lkApplicationStatusRepository.findByCode(ApplicationStatusEnum.REJECTED_BECAUSE_THE_OPPOSITION_ACCEPTED.name()).get();
            oppositionRepository.updateApplicationStatus(id, newApplicationStatus);
            suspendApplicationProcessByType(oppositionRepository.getApplicationByServiceId(id));
            return;
        }

        Integer otherOpenedOppositionsCount = oppositionRepository.getOtherOpenedOppositionsCount(id);
        if (otherOpenedOppositionsCount > 0)
            return;

        newApplicationStatus = lkApplicationStatusRepository.findByCode(ApplicationStatusEnum.PUBLISHED_ELECTRONICALLY.name()).get();
        oppositionRepository.updateApplicationStatus(id, newApplicationStatus);
        suspendApplicationProcessByType(oppositionRepository.getApplicationByServiceId(id));
    }

    private void suspendApplicationProcessByType(ApplicationInfo applicationInfo) {
        ProcessInstanceDto dto = new ProcessInstanceDto();
        dto.setSuspended(false);
        bpmCallerFeignClient.suspendApplicationProcessByType(dto, applicationInfo.getId(), applicationInfo.getCategory().getSaipCode());
    }
    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public OppositionDetailsDto getTradeMarkOppositionDetails(Long oppositionId, boolean gate) {
        Opposition opposition = oppositionRepository.findById(oppositionId).get();
        CustomerSampleInfoDto complainerInfo = customerServiceFeignClient.getAnyCustomerById(opposition.getComplainerCustomerId()).getPayload();
        ApplicationAgentSummaryDto mainApplicantAgent =applicationAgentService.getCurrentApplicationAgentSummary(opposition.getApplication().getId());
        ApplicantsDto mainApplicantInfo= applicationInfoService.listMainApplicant(opposition.getApplication().getId());
        List<ClassificationDto> selectedSubClassifications   = applicationNiceClassificationService.listSelectedApplicationNiceClassifications(opposition.getApplication().getId());
        List<ClassificationDto> oppClassifications = classificationMapper.map(opposition.getClassifications());
        List<DocumentDto>  allDocuments = documentMapper.mapToDtoss(opposition.getDocuments());
        String typeName = "Trademark Image/voice";
        DocumentDto tradeMarkApplicationDocument = documentsService.getTradeMarkApplicationDocument(opposition.getApplication().getId(), typeName);
        if (tradeMarkApplicationDocument != null)
            allDocuments.add(tradeMarkApplicationDocument);
        List<DocumentDto> oppDocs= new ArrayList<>();
        for (DocumentDto document:allDocuments) {
            if(!document.getDocumentType().getName().equals("Opposition Waiver Document")&&!document.getDocumentType().getName().equals("Opposition Reply Document"))
                oppDocs.add(document);
        }
        OppositionDetailsDto oppositionTradeMarkDetails = OppositionDetailsDto.builder()
                .oppositionUserFullNameAr(complainerInfo.getNameAr())
                .oppositionUserFullNameEn(complainerInfo.getNameEn())
                .oppositionUserCustomerCode(complainerInfo.getCode())
                .requestNumber(opposition.getRequestNumber())
                .requestStatus(opposition.getRequestStatus())
                .createdDate(opposition.getCreatedDate())
                .mainApplicationNumber(opposition.getApplication().getApplicationNumber())
                .mainApplicationStatusAr(opposition.getApplication().getApplicationStatus().getIpsStatusDescAr())
                .mainApplicationStatusEn(opposition.getApplication().getApplicationStatus().getIpsStatusDescEn())
                .mainApplicationTitleAr(opposition.getApplication().getTitleAr())
                .mainApplicationTitleEn(opposition.getApplication().getTitleEn())
                .mainApplicantMobile(mainApplicantInfo.getMobile())
                .applicantAgentNameAr(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameAr() : "")
                .applicantAgentNameEn(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameEn() : "")
                .applicantNameEn(mainApplicantInfo.getNameEn())
                .statusCode(opposition.getApplication().getApplicationStatus().getCode())
                .headExaminerNotesToExaminer(opposition.getHeadExaminerNotesToExaminer())
                .applicantNameAr(mainApplicantInfo.getNameAr())
                .applicantAddress(mainApplicantInfo.getAddress())
                .hearingSession(oppositionMapper.mapHearingSessionDto(opposition.getHearingSession()))
                .oppositionApplicationClassifications(oppClassifications)
                .selectedApplicationClassifications(selectedSubClassifications)
                .oppositionFinalDecision(opposition.getFinalDecision()==null?null:opposition.getFinalDecision().name())
                .applicantExaminerNotes(opposition.getApplicantExaminerNotes())
                .finalNotes(opposition.getFinalNotes())
                .complainerLegalRepresentativeName(opposition.getOppositionLegalRepresentative()==null?null:opposition.getOppositionLegalRepresentative().getName())
                .oppositionReason(opposition.getOppositionReason())
                .build();

        if(gate==true){
            oppositionTradeMarkDetails.setDocuments(allDocuments);
            oppositionTradeMarkDetails.setApplicantHearingSession(oppositionMapper.mapHearingSessionDto(opposition.getHearingSession()));

            if(opposition.getOppositionLegalRepresentative()!=null)
                oppositionTradeMarkDetails.setComplainerLegalRepresentativeName(opposition.getOppositionLegalRepresentative().getName());
        }
        else{
            oppositionTradeMarkDetails.setDocuments(oppDocs);


        }

        return oppositionTradeMarkDetails;
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public OppositionDetailsDto getPatentOppositionDetails(Long oppositionId,boolean gate) {

        Opposition opposition = oppositionRepository.findById(oppositionId).get();
        CustomerSampleInfoDto complainerInfo = customerServiceFeignClient.getAnyCustomerById(opposition.getComplainerCustomerId()).getPayload();
        ApplicationAgentSummaryDto mainApplicantAgent =applicationAgentService.getCurrentApplicationAgentSummary(opposition.getApplication().getId());
        ApplicantsDto mainApplicantInfo= applicationInfoService.listMainApplicant(opposition.getApplication().getId());
        List<ClassificationDto> selectedSubClassifications   = applicationNiceClassificationService.listSelectedApplicationNiceClassifications(opposition.getApplication().getId());
        List<ClassificationDto> oppClassifications = classificationMapper.map(opposition.getClassifications());
        List< DocumentDto>  allDocuments = documentMapper.mapToDtoss(opposition.getDocuments());
        List<DocumentDto> oppDocs= new ArrayList<>();

        for (DocumentDto document:allDocuments) {
            if(!document.getDocumentType().getName().equals("Opposition Waiver Document")&&!document.getDocumentType().getName().equals("Opposition Reply Document"))
                oppDocs.add(document);
        }
        OppositionDetailsDto oppositionPatentDetails = OppositionDetailsDto.builder()
                .oppositionUserFullNameAr(complainerInfo.getNameAr())
                .oppositionUserFullNameEn(complainerInfo.getNameEn())
                .oppositionUserCustomerCode(complainerInfo.getCode())
                .requestNumber(opposition.getRequestNumber())
                .requestStatus(opposition.getRequestStatus())
                .createdDate(opposition.getCreatedDate())
                .mainApplicationNumber(opposition.getApplication().getApplicationNumber())
                .mainApplicationStatusAr(opposition.getApplication().getApplicationStatus().getIpsStatusDescAr())
                .mainApplicationStatusEn(opposition.getApplication().getApplicationStatus().getIpsStatusDescEn())
                .mainApplicationTitleAr(opposition.getApplication().getTitleAr())
                .mainApplicationTitleEn(opposition.getApplication().getTitleEn())
                .mainApplicantMobile(mainApplicantInfo.getMobile())
                .applicantAgentNameAr(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameAr() : "")
                .applicantAgentNameEn(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameEn() : "")
                .statusCode(opposition.getApplication().getApplicationStatus().getCode())
                .finalNotes(opposition.getFinalNotes())

                .applicantNameEn(mainApplicantInfo.getNameEn())
                .applicantNameAr(mainApplicantInfo.getNameAr())
                .applicantAddress(mainApplicantInfo.getAddress())
                .hearingSession(oppositionMapper.mapHearingSessionDto(opposition.getHearingSession()))
                .oppositionApplicationClassifications(oppClassifications)
                .oppositionReason(opposition.getOppositionReason())
                .applicantExaminerNotes(opposition.getApplicantExaminerNotes())
                .oppositionFinalDecision(opposition.getFinalDecision()==null?null:opposition.getFinalDecision().name())

                .complainerLegalRepresentativeName(opposition.getOppositionLegalRepresentative()==null?null:opposition.getOppositionLegalRepresentative().getName())
                .selectedApplicationClassifications(selectedSubClassifications)
                .build();



        if(gate==true){
            oppositionPatentDetails.setDocuments(allDocuments);
            oppositionPatentDetails.setApplicantHearingSession(oppositionMapper.mapHearingSessionDto(opposition.getHearingSession()));
            if(opposition.getOppositionLegalRepresentative()!=null)
                oppositionPatentDetails.setComplainerLegalRepresentativeName(opposition.getOppositionLegalRepresentative().getName());
        }
        else{
            oppositionPatentDetails.setDocuments(oppDocs);


        }
        return oppositionPatentDetails;
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public OppositionDetailsDto getIndustrialOppositionDetails(Long oppositionId,boolean gate) {
        Opposition opposition = oppositionRepository.findById(oppositionId).get();
        CustomerSampleInfoDto complainerInfo = customerServiceFeignClient.getAnyCustomerById(opposition.getComplainerCustomerId()).getPayload();
        ApplicationAgentSummaryDto mainApplicantAgent =applicationAgentService.getCurrentApplicationAgentSummary(opposition.getApplication().getId());
        ApplicantsDto mainApplicantInfo= applicationInfoService.listMainApplicant(opposition.getApplication().getId());
        List<ClassificationDto> selectedSubClassifications   = applicationNiceClassificationService.listSelectedApplicationNiceClassifications(opposition.getApplication().getId());
        List<ClassificationDto> oppClassifications = classificationMapper.map(opposition.getClassifications());
        List< DocumentDto>  allDocuments = documentMapper.mapToDtoss(opposition.getDocuments());
        String typeName = "Drawing of the sample";
        List<DocumentDto> oppDocs= new ArrayList<>();
        allDocuments.add(documentsService.getTradeMarkApplicationDocument(opposition.getApplication().getId(),typeName));

        for (DocumentDto document:allDocuments) {
            if(!document.getDocumentType().getName().equals("Opposition Waiver Document")&&!document.getDocumentType().getName().equals("Opposition Reply Document"))
                oppDocs.add(document);
        }

        OppositionDetailsDto oppositionIndustrialDetails = OppositionDetailsDto.builder()
                .oppositionUserFullNameAr(complainerInfo.getNameAr())
                .oppositionUserFullNameEn(complainerInfo.getNameEn())
                .oppositionUserCustomerCode(complainerInfo.getCode())
                .requestNumber(opposition.getRequestNumber())
                .requestStatus(opposition.getRequestStatus())
                .createdDate(opposition.getCreatedDate())
                .mainApplicationNumber(opposition.getApplication().getApplicationNumber())
                .mainApplicationStatusAr(opposition.getApplication().getApplicationStatus().getIpsStatusDescAr())
                .mainApplicationStatusEn(opposition.getApplication().getApplicationStatus().getIpsStatusDescEn())
                .statusCode(opposition.getApplication().getApplicationStatus().getCode())
                .mainApplicationTitleAr(opposition.getApplication().getTitleAr())
                .mainApplicationTitleEn(opposition.getApplication().getTitleEn())
                .mainApplicantMobile(mainApplicantInfo.getMobile())
                .applicantAgentNameAr(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameAr() : "")
                .applicantAgentNameEn(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameEn() : "")
                .oppositionReason(opposition.getOppositionReason())
                .finalNotes(opposition.getFinalNotes())
                .applicantNameEn(mainApplicantInfo.getNameEn())
                .applicantNameAr(mainApplicantInfo.getNameAr())
                .applicantAddress(mainApplicantInfo.getAddress())
                .hearingSession(oppositionMapper.mapHearingSessionDto(opposition.getHearingSession()))
                .oppositionApplicationClassifications(oppClassifications)
                .selectedApplicationClassifications(selectedSubClassifications)
                .oppositionReason(opposition.getOppositionReason())
                .applicantExaminerNotes(opposition.getApplicantExaminerNotes())
                .oppositionFinalDecision(opposition.getFinalDecision()==null?null:opposition.getFinalDecision().name())
                .build();
        if(gate==true){
            oppositionIndustrialDetails.setDocuments(allDocuments);
            oppositionIndustrialDetails.setApplicantHearingSession(oppositionMapper.mapHearingSessionDto(opposition.getHearingSession()));
            if(opposition.getOppositionLegalRepresentative()!=null)
                oppositionIndustrialDetails.setComplainerLegalRepresentativeName(opposition.getOppositionLegalRepresentative().getName());

        }
        else{
            oppositionIndustrialDetails.setDocuments(oppDocs);


        }
        return oppositionIndustrialDetails;
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public List<DocumentWithCommentDto> getAllApplicationOppositionDocumentsWithComments(Long oppositionId) {
        Opposition opposition = oppositionRepository.findById(oppositionId).get();
        List<DocumentWithCommentDto> allApplicationAndOppositionDocs = documentsService.findDocumentsWithCommentsByApplicationIdAndCategory(opposition.getApplication().getId(),"APPLICATION");
        allApplicationAndOppositionDocs.addAll(documentCommentMapper.map(opposition.getDocuments()));
        return allApplicationAndOppositionDocs;
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public ApplicantOppositionViewDto getApplicationApplicantOppositionDetails(Long oppositionId) {
        Opposition opposition = oppositionRepository.findById(oppositionId).get();

        ApplicationAgentSummaryDto mainApplicantAgent =applicationAgentService.getCurrentApplicationAgentSummary(opposition.getApplication().getId());
        ApplicantsDto mainApplicantInfo= applicationInfoService.listMainApplicant(opposition.getApplication().getId());
        List<ClassificationDto> selectedSubClassifications   = applicationNiceClassificationService.listSelectedApplicationNiceClassifications(opposition.getApplication().getId());
        List<ClassificationDto> oppClassifications = classificationMapper.map(opposition.getClassifications());
        List< DocumentDto>  documents = new ArrayList<>();
        for (DocumentDto document:documentMapper.mapToDtoss(opposition.getDocuments())) {
            if(!document.getDocumentType().getName().equals("Opposition Waiver Document")&&!document.getDocumentType().getName().equals("Opposition Reply Document")&&!document.getDocumentType().getName().equals("Opposition POA"))
                documents.add(document);
        }

        ApplicantOppositionViewDto applicantOppositionViewDto =ApplicantOppositionViewDto.builder()


                .createdDate(opposition.getCreatedDate())
                .mainApplicationNumber(opposition.getApplication().getApplicationNumber())
                .mainApplicationStatusAr(opposition.getApplication().getApplicationStatus().getIpsStatusDescAr())
                .mainApplicationStatusEn(opposition.getApplication().getApplicationStatus().getIpsStatusDescEn())
                .mainApplicationTitleAr(opposition.getApplication().getTitleAr())
                .mainApplicationTitleEn(opposition.getApplication().getTitleEn())
                .mainApplicantMobile(mainApplicantInfo.getMobile())
                .applicantAgentNameAr(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameAr() : "")
                .applicantAgentNameEn(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameEn() : "")
                .applicantNameEn(mainApplicantInfo.getNameEn())
                .applicantNameAr(mainApplicantInfo.getNameAr())
                .applicantAddress(mainApplicantInfo.getAddress())
                .oppositionReason(opposition.getOppositionReason())
                .applicantExaminerNotes(opposition.getApplicantExaminerNotes())
                .finalDecision(opposition.getFinalDecision()==null?null:opposition.getFinalDecision().name())
                .finalNotes(opposition.getFinalNotes())
                .applicationStatusCode(opposition.getApplication().getApplicationStatus().getCode())

                .hearingSession(oppositionMapper.mapHearingSessionDto(opposition.getHearingSession()))
                .oppositionApplicationClassifications(oppClassifications)
                .selectedApplicationClassifications(selectedSubClassifications)
                .requestNumber(opposition.getRequestNumber())
                .requestStatus(opposition.getRequestStatus())


                .build();
        if(opposition.getApplication().getCategory().getSaipCode().equals("TRADEMARK"))
        {
            String tradeMarkImageFileName = "Trademark Image/voice";
            documents.add(documentsService.getTradeMarkApplicationDocument(opposition.getApplication().getId(),tradeMarkImageFileName));

        }
        if (opposition.getApplication().getCategory().getSaipCode().equals("INDUSTRIAL_DESIGN")) {
            String drawingSampleFileName = "Drawing of the sample";
            documents.add(documentsService.getTradeMarkApplicationDocument(opposition.getApplication().getId(),drawingSampleFileName));

        }
        applicantOppositionViewDto.setDocuments(documents);
        return applicantOppositionViewDto;
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public OppositionTradeMarkReportDetailsDto getOppositionTradeMarkApplicationReport(Long oppositionId) {
        Opposition opposition = oppositionRepository.findById(oppositionId).get();
        CustomerSampleInfoDto complainerInfo = customerServiceFeignClient.getAnyCustomerById(opposition.getComplainerCustomerId()).getPayload();
        ApplicantsDto mainApplicantInfo= applicationInfoService.listMainApplicant(opposition.getApplication().getId());
        ApplicationAgentSummaryDto mainApplicantAgent =applicationAgentService.getCurrentApplicationAgentSummary(opposition.getApplication().getId());
        List<ClassificationDto> selectedClassifications   = applicationNiceClassificationService.listSelectedApplicationNiceClassifications(opposition.getApplication().getId());

        OppositionTradeMarkReportDetailsDto reportDetailsDto = OppositionTradeMarkReportDetailsDto.builder()
                .tradeMarkTitleAr(opposition.getApplication().getTitleAr())
                .tradeMarkTitleEn(opposition.getApplication().getTitleEn())
                .tradeMarkNumber(opposition.getApplication().getApplicationNumber())
                .createdDate(opposition.getCreatedDate())

                .applicantNameEn(mainApplicantInfo.getNameEn())
                .applicantNameAr(mainApplicantInfo.getNameAr())
                .applicantAddress(mainApplicantInfo.getAddress())
                .applicantAgentNameEn(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameEn() : "")
                .applicantAgentNameAr(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameAr() : "")
                .agentAddress(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getAddress() : null)
                .complainerNameAr(complainerInfo.getNameAr())
                .complainerNameEn(complainerInfo.getNameEn())
                .tradeMarkCode(selectedClassifications)
                .oppositionFinalDecision(opposition.getFinalDecision()==null?null:opposition.getFinalDecision().name())

                .finalNotes(opposition.getFinalNotes())
                .complainerAddress(complainerInfo.getAddress())
                .requestNumber(opposition.getRequestNumber())
                .requestStatus(opposition.getRequestStatus())
// TODO must know how to obtaine tradeMark Code


                .build();


        return reportDetailsDto;
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public OppositionIndustrialReportDetailsDto getOppositionIndustrialApplicationReport(Long oppositionId){
        Opposition opposition = oppositionRepository.findById(oppositionId).get();
        CustomerSampleInfoDto complainerInfo = customerServiceFeignClient.getAnyCustomerById(opposition.getComplainerCustomerId()).getPayload();
        ApplicantsDto mainApplicantInfo= applicationInfoService.listMainApplicant(opposition.getApplication().getId());
        ApplicationAgentSummaryDto mainApplicantAgent =applicationAgentService.getCurrentApplicationAgentSummary(opposition.getApplication().getId());
        List<ClassificationDto> selectedClassifications   = applicationNiceClassificationService.listSelectedApplicationNiceClassifications(opposition.getApplication().getId());

        OppositionIndustrialReportDetailsDto reportDetailsDto =   OppositionIndustrialReportDetailsDto.builder()
                .industrialTitleAr(opposition.getApplication().getTitleAr())
                .industrialTitleEn(opposition.getApplication().getTitleEn())
                .industrialNumber(opposition.getApplication().getApplicationNumber())
                .createdDate(opposition.getCreatedDate())

                .applicantNameEn(mainApplicantInfo.getNameEn())
                .applicantNameAr(mainApplicantInfo.getNameAr())
                .applicantAddress(mainApplicantInfo.getAddress())
                .applicantAgentNameEn(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameEn() : "")
                .applicantAgentNameAr(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameAr() : "")
                .agentAddress(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getAddress() : null)
                .complainerNameAr(complainerInfo.getNameAr())
                .complainerNameEn(complainerInfo.getNameEn())
                .complainerAddress(complainerInfo.getAddress())
                .requestNumber(opposition.getRequestNumber())
                .requestStatus(opposition.getRequestStatus())
                .oppositionFinalDecision(opposition.getFinalDecision()==null?null:opposition.getFinalDecision().name())

                .finalNotes(opposition.getFinalNotes())
                .industrialCode(selectedClassifications)



                .build();
        if(opposition.getOppositionLegalRepresentative()!=null)
            reportDetailsDto.setComplainerAgentNameEn(opposition.getOppositionLegalRepresentative().getName());
return reportDetailsDto;
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public OppositionPatentReportDetailsDto getOppositionPatentApplicationReport(Long oppositionId)  {




        Opposition opposition = oppositionRepository.findById(oppositionId).get();
        CustomerSampleInfoDto complainerInfo = customerServiceFeignClient.getAnyCustomerById(opposition.getComplainerCustomerId()).getPayload();
        ApplicantsDto mainApplicantInfo= applicationInfoService.listMainApplicant(opposition.getApplication().getId());
        ApplicationAgentSummaryDto mainApplicantAgent =applicationAgentService.getCurrentApplicationAgentSummary(opposition.getApplication().getId());


        OppositionPatentReportDetailsDto reportDetailsDto = OppositionPatentReportDetailsDto.builder()
                .patentTitleAr(opposition.getApplication().getTitleAr())
                .patentTitleEn(opposition.getApplication().getTitleEn())
                .patentNumber(opposition.getApplication().getApplicationNumber())
                .createdDate(opposition.getCreatedDate())
                .applicantNameEn(mainApplicantInfo.getNameEn())
                .applicantNameAr(mainApplicantInfo.getNameAr())
                .applicantAddress(mainApplicantInfo.getAddress())
                .applicantAgentNameEn(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameEn() : "")
                .applicantAgentNameAr(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameAr() : "")
                .agentAddress(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getAddress() : null)
                .complainerNameAr(complainerInfo.getNameAr())
                .complainerNameEn(complainerInfo.getNameEn())
                .complainerAddress(complainerInfo.getAddress())
                .oppositionFinalDecision(opposition.getFinalDecision()==null?null:opposition.getFinalDecision().name())

                .finalNotes(opposition.getFinalNotes())
                .requestNumber(opposition.getRequestNumber())


// TODO must know how to obtaine patent Code


                .build();

        if(opposition.getApplication().getIpcNumber()!=null)
            reportDetailsDto.setPatentCode(opposition.getApplication().getIpcNumber());
        if(opposition.getOppositionLegalRepresentative()!=null)
            reportDetailsDto.setComplainerAgentNameEn(opposition.getOppositionLegalRepresentative().getName());

        return reportDetailsDto;
    }



}
