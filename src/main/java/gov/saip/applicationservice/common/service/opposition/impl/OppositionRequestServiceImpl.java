package gov.saip.applicationservice.common.service.opposition.impl;


import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.dto.notifications.AppNotificationDto;
import gov.saip.applicationservice.common.dto.notifications.NotificationLanguage;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.dto.opposition.OppositionDetailsProjection;
import gov.saip.applicationservice.common.dto.opposition.OppositionRequestDto;
import gov.saip.applicationservice.common.dto.timeslots.TimeSlotDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.mapper.opposition.OppositionRequestMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationStatusChangeLog;
import gov.saip.applicationservice.common.model.opposition.OppositionRequest;
import gov.saip.applicationservice.common.model.opposition.OppositionRequestHearingSession;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.opposition.OppositionRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationPublicationService;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceImpl;
import gov.saip.applicationservice.common.service.opposition.OppositionRequestService;
import gov.saip.applicationservice.common.service.timeslots.TimeSlotService;
import gov.saip.applicationservice.common.util.SupportServiceActivityLogHelper;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode.*;
import static gov.saip.applicationservice.common.enums.SupportServiceType.OPPOSITION_REQUEST;

@Service
@Transactional
@AllArgsConstructor
public class OppositionRequestServiceImpl extends SupportServiceRequestServiceImpl<OppositionRequest>
  implements OppositionRequestService {
    
    @Value("${link.contactus}")
    String contactUs;
    
    private final OppositionRequestRepository oppositionRequestRepository;
    private final ApplicationInfoService applicationInfoService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final TimeSlotService timeSlotService;
    private final ApplicationPublicationService applicationPublicationService;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    private final OppositionRequestMapper oppositionRequestMapper;
    private final SupportServiceActivityLogHelper supportServiceActivityLogHelper;
    private final NotificationCaller notificationCaller;

    public static final String OPPOSITION_COMPLETED_LOG_TASK_NAME_AR = "مرفوض بسبب قبول الاعتراض";
    public static final String OPPOSITION_COMPLETED_TASK_NAME_EN = "Rejected because the objection was accepted";



    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return oppositionRequestRepository;
    }

    @Override
    public OppositionRequestDto getRequestById(Long id) {
        OppositionRequestDto dto =oppositionRequestMapper.map(findById(id));
        dto.setApplicantDetails(customerServiceFeignClient.getAnyCustomerByCustomerCode(dto.getCreatedByCustomerCode()).getPayload());
        return dto;
    }

    private void addComplainerDateAndTimeOfSession(OppositionRequest oppositionRequest){
        if (oppositionRequest.getComplainerHearingSession() != null){
            TimeSlotDto timeSlotDto = timeSlotService.findById(oppositionRequest.getComplainerHearingSession().getComplainerSessionSlotId());
            oppositionRequest.getComplainerHearingSession().setComplainerSessionDate(LocalDate.parse(timeSlotDto.getSlotDate()));
            oppositionRequest.getComplainerHearingSession().setComplainerSessionTime(timeSlotDto.getStartTime());
            timeSlotDto.setReserved(true);
            timeSlotService.update(timeSlotDto);
        }
    }

    @Override
    public OppositionRequest insert(OppositionRequest entity) {
        addComplainerDateAndTimeOfSession(entity);
        return super.insert(OPPOSITION_REQUEST, entity);
    }

    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.UNDER_PROCEDURE;
    }

    @Override
    @Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        oppositionRequestStartProcess(id);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
        updateApplicationInfoStatus(id);
    }



    private void updateApplicationInfoStatus(Long oppositionRequestId){
        OppositionRequest oppositionRequest = super.findById(oppositionRequestId);
        applicationStatusChangeLogService.changeApplicationStatusAndLog(ApplicationStatusEnum.OBJECTOR.name(), AppStatusChangeLogDescriptionCode.OPPOSITION_REQUEST_REQUESTED.name(), oppositionRequest.getApplicationInfo().getId());
        //applicationInfoService.changeApplicationStatusId(oppositionRequest.getApplicationInfo().getId(),ApplicationStatusEnum.OBJECTOR.name());
    }




    public void completeApplicationOwnerAskForSessionUserTask(OppositionRequest oppositionRequest){
        OppositionRequest applicationOwnerReply = super.findById(oppositionRequest.getId());
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.OPPOSITION_REQUEST, oppositionRequest.getId()).getPayload();
        Map<String, Object> approved = new LinkedHashMap();
        String isApplicantAskedForSession = "NO";
        if (applicationOwnerReply.getApplicationOwnerHearingSession().getComplainerSessionSlotId() != null) {
            isApplicantAskedForSession = "YES";
            oppositionRequest.getApplicationOwnerHearingSession().setComplainerSessionIsPaid(true);
        }
        approved.put("value", isApplicantAskedForSession);
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }

    @Override
    @Transactional
    public Long updateComplainerOpposition(OppositionRequest oppositionRequest) {
        OppositionRequest updatedOppositionRequest = findById(oppositionRequest.getId());
        updatedOppositionRequest.setOppositionReason(oppositionRequest.getOppositionReason());
        updatedOppositionRequest.setOppositionDocuments(oppositionRequest.getOppositionDocuments());
        updatedOppositionRequest.setComplainerHearingSession(oppositionRequest.getComplainerHearingSession());
        updatedOppositionRequest.setOppositionApplicationSimilars(oppositionRequest.getOppositionApplicationSimilars());
        super.update(updatedOppositionRequest);
        return updatedOppositionRequest.getId();
    }




    private OppositionRequestHearingSession addApplicationOwnerHearingSession(OppositionRequest oppositionRequest) {
        OppositionRequestHearingSession applicationOwnerRequestHearingSession = new OppositionRequestHearingSession();
        if (oppositionRequest.getApplicationOwnerHearingSession() != null) {
            TimeSlotDto timeSlotDto = timeSlotService.findById(oppositionRequest.getApplicationOwnerHearingSession().getComplainerSessionSlotId());
            applicationOwnerRequestHearingSession.setComplainerSessionSlotId(timeSlotDto.getId());
            applicationOwnerRequestHearingSession.setComplainerSessionTime(timeSlotDto.getStartTime());
            applicationOwnerRequestHearingSession.setComplainerSessionDate(LocalDate.parse(timeSlotDto.getSlotDate()));
            timeSlotDto.setReserved(true);
            timeSlotService.update(timeSlotDto);
        }

        return applicationOwnerRequestHearingSession;
    }
    private void sendNotificationToApplicationOwner(OppositionRequest request, NotificationTemplateCode notificationTemplateCode,Map<String , Object> notificationParams,ApplicationInfo app,CustomerSampleInfoDto appOwnerDetails){

        notificationParams.put("requestNumber",app.getApplicationRequestNumber());
        notificationParams.put("titleAr",app.getTitleAr());
        notificationParams.put("titleEn",app.getTitleEn());
        notificationParams.put("link",contactUs);

        NotificationDto emailDto = NotificationDto
                .builder()
                .to(appOwnerDetails.getEmail())
                .messageType(Constants.MessageType.EMAIL_TYPE_MESSAGE)
                .build();
        NotificationDto smsDto = NotificationDto
                .builder()
                .to(appOwnerDetails.getMobile())
                .build();
        AppNotificationDto appDto = AppNotificationDto.builder()
                .requestId(String.valueOf(request.getProcessRequestId()))
                .serviceId(String.valueOf(request.getId()))
                .serviceCode(OPPOSITION_REQUEST.name())
                .routing( ApplicationCategoryEnum.valueOf(app.getCategory().getSaipCode()).name()).date(LocalDateTime.now())
                .userNames(List.of((String)appOwnerDetails.getCode())).build();

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .lang(NotificationLanguage.AR)
                .code(notificationTemplateCode)
                .templateParams(notificationParams)
                .email(emailDto)
                .sms(smsDto)
                .app(appDto)
                .build();
        notificationCaller.sendAllToSpecificUser(notificationRequest);
    }


    @Transactional
    @Override
    public Long addApplicationOwnerReply(OppositionRequest oppositionRequest) {
        OppositionRequest updatedOppositionRequest = findById(oppositionRequest.getId());
        updatedOppositionRequest.setApplicationOwnerReply(oppositionRequest.getApplicationOwnerReply());
        OppositionRequestHearingSession applicationOwnerRequestHearingSession = addApplicationOwnerHearingSession(oppositionRequest);
        if (applicationOwnerRequestHearingSession != null){
            updatedOppositionRequest.setApplicationOwnerHearingSession(applicationOwnerRequestHearingSession);
        }
        if (oppositionRequest.getOppositionDocuments() != null && !oppositionRequest.getOppositionDocuments().isEmpty()){
            updatedOppositionRequest.getOppositionDocuments().addAll(oppositionRequest.getOppositionDocuments());
        }
        super.update(updatedOppositionRequest);
        completeApplicationOwnerAskForSessionUserTask(updatedOppositionRequest);

        sendNotificationToOwnerWhenHeReply(updatedOppositionRequest);


        return updatedOppositionRequest.getId();
    }

    private void sendNotificationToOwnerWhenHeReply(OppositionRequest updatedOppositionRequest) {
        ApplicationInfo app = updatedOppositionRequest.getApplicationInfo();
        if (Objects.nonNull(app)) {

                CustomerSampleInfoDto appOwnerDetails = customerServiceFeignClient.getAnyCustomerById(updatedOppositionRequest.getApplicationInfo().getCreatedByCustomerId()).getPayload();


                    if (updatedOppositionRequest.withoutSession()) {
                        Map<String, Object> notificationParams = getComplainerRelatedInfo(updatedOppositionRequest);
                        sendNotificationToApplicationOwner(updatedOppositionRequest, OWNER_OPPOSITION_REPLY_WITHOUT_HEARING_SESSION, notificationParams, app,appOwnerDetails);
                    } else {
                        Map<String, Object> notificationParams = getHearingSessionDetails(updatedOppositionRequest);
                        notificationParams.putAll(getComplainerRelatedInfo(updatedOppositionRequest));
                        sendNotificationToApplicationOwner(updatedOppositionRequest, OWNER_OPPOSITION_REPLY_WITH_HEARING_SESSION, notificationParams, app,appOwnerDetails);
                    }

            }
    }

    private Map<String, Object> getComplainerRelatedInfo(OppositionRequest updatedOppositionRequest) {
        CustomerSampleInfoDto complainerDetails = customerServiceFeignClient.getAnyCustomerByCustomerCode(updatedOppositionRequest.getCreatedByCustomerCode()).getPayload();
        Map<String , Object> notificationParams = new HashMap<>();
        notificationParams.put("nameAr",complainerDetails.getNameAr());
        notificationParams.put("nameEn",complainerDetails.getNameEn());
        return notificationParams;
    }

    private Map<String, Object> getHearingSessionDetails(OppositionRequest updatedOppositionRequest) {
        Map<String , Object> notificationParams = new HashMap<>();
        OppositionRequestHearingSession ownerHearingSession = updatedOppositionRequest.getApplicationOwnerHearingSession();
        LocalDate  ownerSessionDateGeorigian =  ownerHearingSession.getComplainerSessionDate();
        String format = "dd/MM/yyyy";
        String  ownerSessionDateHijri = Utilities.convertDateFromGregorianToHijriWithFormat(ownerSessionDateGeorigian,format);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
        String ownerSessionDateGeorgian = ownerSessionDateGeorigian.format(dateFormatter);
        TimeSlotDto timeSlotDto = timeSlotService.findById(ownerHearingSession.getComplainerSessionSlotId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
        LocalTime timeAfterCalculations = timeSlotDto.getStartTime().getHour()%12==0 ?
                timeSlotDto.getStartTime():
                LocalTime.of(timeSlotDto.getStartTime().getHour()%12,timeSlotDto.getStartTime().getMinute());
        String formattedTime = timeAfterCalculations.format(formatter);
        String periodAr = timeSlotDto.getStartTime().isBefore(LocalTime.NOON) ? "صباحاً" :"مساءً" ;
        String periodEn = timeSlotDto.getStartTime().isBefore(LocalTime.NOON) ? "am" : "pm";
        String formattedTimeInArabic = formattedTime + " " + periodAr;
        String formattedTimeInEnglish = formattedTime + " " + periodEn;
        notificationParams.put("formattedTimeInArabic",formattedTimeInArabic);
        notificationParams.put("formattedTimeInEnglish",formattedTimeInEnglish);
        notificationParams.put("ownerSessionDateGeorgian",ownerSessionDateGeorgian);
        notificationParams.put("ownerSessionDateHijri",ownerSessionDateHijri);
        return notificationParams;
    }

    @Override
    @Transactional
    public Long updateComplainerHearingSession(OppositionRequest oppositionRequest) {
        OppositionRequest updatedComplainerHearingSession = super.findById(oppositionRequest.getId());
        TimeSlotDto timeSlotDto = timeSlotService.findById(oppositionRequest.getComplainerHearingSession().getComplainerSessionSlotId());
        if (timeSlotDto != null && updatedComplainerHearingSession.getComplainerHearingSession().getComplainerSessionSlotId() != null){
            updatedComplainerHearingSession.getComplainerHearingSession().setComplainerSessionSlotId(timeSlotDto.getId());
            updatedComplainerHearingSession.getComplainerHearingSession().setComplainerSessionDate(LocalDate.parse(timeSlotDto.getSlotDate()));
            updatedComplainerHearingSession.getComplainerHearingSession().setComplainerSessionTime(timeSlotDto.getStartTime());
            timeSlotDto.setReserved(true);
            timeSlotService.update(timeSlotDto);
        }
        super.update(updatedComplainerHearingSession);
        return updatedComplainerHearingSession.getId();
    }

    @Override
    @Transactional
    public Long updateApplicationOwnerHearingSession(OppositionRequest oppositionRequest) {
        OppositionRequest updatedApplicationOwnerHearingSession = super.findById(oppositionRequest.getId());
        TimeSlotDto timeSlotDto = timeSlotService.findById(oppositionRequest.getApplicationOwnerHearingSession().getComplainerSessionSlotId());
        if (timeSlotDto.getId() != null && updatedApplicationOwnerHearingSession.getApplicationOwnerHearingSession().getComplainerSessionSlotId() != null){
            updatedApplicationOwnerHearingSession.getApplicationOwnerHearingSession().setComplainerSessionSlotId(timeSlotDto.getId());
            updatedApplicationOwnerHearingSession.getApplicationOwnerHearingSession().setComplainerSessionTime(timeSlotDto.getStartTime());
            updatedApplicationOwnerHearingSession.getApplicationOwnerHearingSession().setComplainerSessionDate(LocalDate.parse(timeSlotDto.getSlotDate()));
            timeSlotDto.setReserved(true);
            timeSlotService.update(timeSlotDto);
        }
        super.update(updatedApplicationOwnerHearingSession);
        return updatedApplicationOwnerHearingSession.getId();
    }




    private void oppositionRequestStartProcess(Long oppositionId) {
        OppositionRequest oppositionRequest = super.findById(oppositionId);
        ApplicationInfo applicationInfo =oppositionRequest.getApplicationInfo();
        ApplicantsDto applicantsDto = applicationInfoService.listMainApplicant(applicationInfo.getId());
        Map<String, Object> vars = prepareProcessVariables(applicantsDto, applicationInfo, oppositionRequest);
        CustomerSampleInfoDto complainerDetails = customerServiceFeignClient.getAnyCustomerByCustomerCode(oppositionRequest.getCreatedByCustomerCode()).getPayload();
        StartProcessDto startProcessDto =  StartProcessDto.builder()
                .id(oppositionRequest.getId().toString())
                .applicantUserName(oppositionRequest.getCreatedByUser())
                .fullNameAr(complainerDetails.getNameAr())
                .fullNameEn(complainerDetails.getNameEn())
                .mobile(complainerDetails.getMobile())
                .email(complainerDetails.getEmail())
                .applicationCategory(oppositionRequest.getApplicationInfo().getCategory().getSaipCode())
                .processName("application_opposition_process")
                .requestTypeCode(RequestTypeEnum.OPPOSITION_REQUEST.name())
                .supportServiceCode(oppositionRequest.getLkSupportServices().getCode().name())
                .applicationIdColumn(oppositionRequest.getApplicationInfo().getId().toString())
                .identifier(complainerDetails.getIdentifier())
                .requestNumber(oppositionRequest.getRequestNumber())
                .variables(vars)
                .build();
        startSupportServiceProcess(oppositionRequest, startProcessDto);
    }


    private Map<String, Object> prepareProcessVariables(ApplicantsDto applicantsDto, ApplicationInfo applicationInfo, OppositionRequest oppositionRequest) {
        Map<String, Object> vars = new HashMap<>();
        String mainApplicationApplicantCustomerCode = customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(applicationInfo.getCreatedByCustomerId())).getPayload();
        vars.put("APPLICANT_NAME_AR", applicantsDto.getNameAr());
        vars.put("APPLICANT_NAME_EN", applicantsDto.getNameEn());
        vars.put("APPLICANT_EMAIL", applicantsDto.getEmail());
        vars.put("APPLICANT_MOBILE", applicantsDto.getMobile());
        vars.put("APPLICANT_USER_NAME", applicantsDto.getUsername());
        vars.put("APPLICATION_NUMBER", applicationInfo.getApplicationNumber());
        vars.put("CREATED_BY_CUSTOMER_CODE", oppositionRequest.getCreatedByCustomerCode());
        vars.put("APPLICATION_REQUEST_NUMBER", applicationInfo.getApplicationRequestNumber());
        vars.put("MAIN_APPLICATION_APPLICANT_CUSTOMER_CODE" , mainApplicationApplicantCustomerCode);
        return vars;
    }


    @Override
    public PaginationDto<List<OppositionDetailsProjection>> getRequestsDetails(String requestNumber, LocalDate sessionDate, LocalDate from, LocalDate to, Integer limit, Integer page){
        List<OppositionDetailsProjection> details = ListUtils.union(oppositionRequestRepository.getRequestsDetailsFirst(requestNumber, sessionDate, to, from), oppositionRequestRepository.getRequestsDetailsSecond(requestNumber, sessionDate, to, from));

        int totalElements = details.size();
        int totalPages = (int) Math.ceil((double) totalElements / limit);

        int startIndex = page * limit;
        int endIndex = Math.min(startIndex + limit, totalElements);

        List<OppositionDetailsProjection> paginatedDetails = details.subList(startIndex, endIndex);

        return new PaginationDto<>(paginatedDetails, totalPages, (long) totalElements);
    }

    @Override
    public Long addComplainerHearingSessionResult(OppositionRequest oppositionRequest) {
        OppositionRequest addComplainerHearingSessionResult = super.findById(oppositionRequest.getId());
        addComplainerHearingSessionResult.getComplainerHearingSession().setComplainerSessionResult(oppositionRequest.getComplainerHearingSession().getComplainerSessionResult());
        super.update(addComplainerHearingSessionResult);

        NotifyOppositionApplicantAndOwner(
                addComplainerHearingSessionResult,
                OPPOSITION_INTERNAL_ADD_HEARING_SESSION_FOR_OPPOSITION_APPLICANT_TO_OWNER,
                OPPOSITION_INTERNAL_ADD_HEARING_SESSION_FOR_OPPOSITION_APPLICANT_TO_OPPOSITION_APPLICANT
        );
        return addComplainerHearingSessionResult.getId();

    }

    private void NotifyOppositionApplicantAndOwner(OppositionRequest addComplainerHearingSessionResult, NotificationTemplateCode oppositionInternalAddHearingSessionForOppositionApplicantToOwner, NotificationTemplateCode oppositionInternalAddHearingSessionForOppositionApplicantToOppositionApplicant) {
        ApplicationInfo app = addComplainerHearingSessionResult.getApplicationInfo();
        CustomerSampleInfoDto appOwnerDetails = customerServiceFeignClient.getAnyCustomerById(addComplainerHearingSessionResult.getApplicationInfo().getCreatedByCustomerId()).getPayload();
        CustomerSampleInfoDto complainerDetails = customerServiceFeignClient.getAnyCustomerByCustomerCode(addComplainerHearingSessionResult.getCreatedByCustomerCode()).getPayload();
        sendNotificationToApplicationOwner(addComplainerHearingSessionResult, oppositionInternalAddHearingSessionForOppositionApplicantToOwner, new HashMap<>(), app, appOwnerDetails);
        sendNotificationToApplicationOwner(addComplainerHearingSessionResult, oppositionInternalAddHearingSessionForOppositionApplicantToOppositionApplicant, new HashMap<>(), app, complainerDetails);
    }

    @Override
    public Long addApplicationOwnerHearingSessionResult(OppositionRequest oppositionRequest) {
        OppositionRequest addApplicationOwnerHearingSessionResult = super.findById(oppositionRequest.getId());
        addApplicationOwnerHearingSessionResult.getApplicationOwnerHearingSession().setComplainerSessionResult(oppositionRequest.getApplicationOwnerHearingSession().getComplainerSessionResult());
        super.update(addApplicationOwnerHearingSessionResult);

        NotifyOppositionApplicantAndOwner(addApplicationOwnerHearingSessionResult, OPPOSITION_INTERNAL_ADD_HEARING_SESSION_FOR_OWNER_TO_OWNER, OPPOSITION_INTERNAL_ADD_HEARING_SESSION_FOR_OWNER_TO_OPPOSITION_APPLICANT);

        return addApplicationOwnerHearingSessionResult.getId();


    }

    @Override
    public LocalDate getMaxDateOfOpposition(Long applicationId) {

        Optional<LocalDate> applicationPublicationDate = applicationPublicationService.findApplicationPublicationDateByAppId(applicationId);
        if (applicationPublicationDate.isPresent()){
            LocalDate maxDateOfOpposition = applicationPublicationDate.get().plusDays(61);
            return maxDateOfOpposition;
        }else {
            return applicationPublicationDate.get();
        }

    }


    @Override
    @Transactional
    public void changeOppositionRequestStatusAfterFinalDecision(Long id) {
        OppositionRequest oppositionRequest = super.findById(id);
        if (SupportServiceRequestStatusEnum.COMPLETED.name().equals(oppositionRequest.getRequestStatus().getCode())) {
            changeApplicationStatusAfterAcceptOppositionRequest(oppositionRequest);
        } else if (SupportServiceRequestStatusEnum.REJECTED.name().equals(oppositionRequest.getRequestStatus().getCode())) {
            changeApplicationStatusAfterRejectOppositionRequest(oppositionRequest);
        }
    }

    private void changeApplicationStatusAfterRejectOppositionRequest(OppositionRequest oppositionRequest) {
        if (isThereOppositionStillUnderProcedureForThisApplication(oppositionRequest) || isApplicationStatusInvalidToBePublished(oppositionRequest)) {
            return;
        }

        applicationStatusChangeLogService.changeApplicationStatusAndLog(ApplicationStatusEnum.PUBLISHED_ELECTRONICALLY.name(), AppStatusChangeLogDescriptionCode.OPPOSITION_REQUEST_REJECTED.name(), oppositionRequest.getApplicationInfo().getId());
    }

    private void changeApplicationStatusAfterAcceptOppositionRequest(OppositionRequest oppositionRequest) {
        if (isApplicationStatusInvalidToBePublished(oppositionRequest)) {
            return;
        }
        String taskId = supportServiceActivityLogHelper.addActivityLogForSupportService(oppositionRequest.getId(), RequestActivityLogEnum.OPPOSITION_COMPLETED,
                "DONE", OPPOSITION_COMPLETED_LOG_TASK_NAME_AR, OPPOSITION_COMPLETED_TASK_NAME_EN, null);
        applicationStatusChangeLogService.changeApplicationStatusAndLog(ApplicationStatusEnum.REJECTED_BECAUSE_THE_OPPOSITION_ACCEPTED.name(),
                AppStatusChangeLogDescriptionCode.OPPOSITION_REQUEST_ACCEPTED.name(), oppositionRequest.getApplicationInfo().getId(),
                RequestActivityLogEnum.OPPOSITION_COMPLETED.name() ,taskId);
    }

    private boolean isApplicationStatusInvalidToBePublished(OppositionRequest oppositionRequest) {
        String applicationStatus = applicationInfoService.getApplicationStatus(oppositionRequest.getApplicationInfo().getId());
        return ApplicationStatusEnum.REJECTED_BECAUSE_THE_OPPOSITION_ACCEPTED.name().equals(applicationStatus)
                ||
                ApplicationStatusEnum.WAIVED.name().equals(applicationStatus);

    }

    private boolean isThereOppositionStillUnderProcedureForThisApplication(OppositionRequest oppositionRequest) {
        Integer countOfOpendOppositionsForApplication = oppositionRequestRepository.getOtherOpenedOppositionsCount(oppositionRequest.getApplicationInfo().getId(), List.of(SupportServiceRequestStatusEnum.UNDER_PROCEDURE.name()));
        return countOfOpendOppositionsForApplication > 0;
    }


    private ApplicationStatusChangeLog createApplicationLog(Long applicationId, String descriptionCode, String status) {
        ApplicationStatusChangeLog changeLog = new ApplicationStatusChangeLog();
        changeLog.setNewStatusByCode(status);
        changeLog.setApplicationById(applicationId);
        changeLog.setDescriptionCode(descriptionCode);
        return changeLog;
    }


    @Override
    public OppositionRequest update(OppositionRequest entity) {
        OppositionRequest oppositionRequest = findById(entity.getId());
        oppositionRequest.setOppositionReason(entity.getOppositionReason());
        oppositionRequest.setOppositionApplicationSimilars(entity.getOppositionApplicationSimilars());
        oppositionRequest.setOppositionDocuments(entity.getOppositionDocuments());
        return super.update(oppositionRequest);
    }
}
