package gov.saip.applicationservice.common.service.applicantModifications.Impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.applicantModifications.TradeMarkApplicationModificationDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.mapper.applicantModifications.TradeMarkApplicationModificationMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationStatusChangeLog;
import gov.saip.applicationservice.common.model.TrademarkApplicationModification;
import gov.saip.applicationservice.common.model.trademark.LkMarkType;
import gov.saip.applicationservice.common.model.trademark.LkTagLanguage;
import gov.saip.applicationservice.common.model.trademark.LkTagTypeDesc;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.applicantModifications.TradeMarkApplicationModificationRepository;
import gov.saip.applicationservice.common.repository.trademark.LkMarkTypeRepository;
import gov.saip.applicationservice.common.repository.trademark.LkTagLanguageRepository;
import gov.saip.applicationservice.common.repository.trademark.LkTagTypeDescRepository;
import gov.saip.applicationservice.common.repository.trademark.TrademarkDetailRepository;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import gov.saip.applicationservice.common.service.applicantModifications.TradeMarkApplicationModificationService;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static gov.saip.applicationservice.common.enums.TrademarkType.VISUAL;
import static gov.saip.applicationservice.util.Constants.TradeMarkApplicationModificationAction.ACTION;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TradeMarkApplicationModificationServiceImpl extends BaseServiceImpl<TrademarkApplicationModification, Long> implements TradeMarkApplicationModificationService {

    private final TradeMarkApplicationModificationRepository tradeMarkApplicationModificationRepository;
    private final TradeMarkApplicationModificationMapper applicationModificationsMapper;
    private final LkMarkTypeRepository lkMarkTypeRepository;
    private final LkTagTypeDescRepository lkTagTypeDescRepository;
    private final LkTagLanguageRepository lkTagLanguageRepository;
    private final BPMCallerServiceImpl bpmCallerService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final TrademarkDetailService trademarkDetailService;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    private final TrademarkDetailRepository trademarkDetailRepository;
    @Override
    protected BaseRepository<TrademarkApplicationModification, Long> getRepository() {
        return tradeMarkApplicationModificationRepository;
    }
    @Override
    public TrademarkApplicationModification addApplicantModifications(TradeMarkApplicationModificationDto dto, Long appId) {
//        TrademarkDetail trademarkDetail = Objects.isNull(getApplicationTradeMarkDetails(appId))?new TrademarkDetail():getApplicationTradeMarkDetails(appId);

        long trademarkDetailId = getApplicationTM(appId);

        TrademarkDetail trademarkDetail = trademarkDetailRepository.getReferenceById(trademarkDetailId);

        TrademarkApplicationModification applicantChanges = initializeModifications(trademarkDetail, dto);

        if (Objects.nonNull(applicantChanges)) {
            applicantChanges.setModificationFilingDate(LocalDateTime.now());

            applicantChanges.setModificationsHijriDate(Utilities.convertDateFromGregorianToHijri(LocalDateTime.now().toLocalDate()));

            TrademarkApplicationModification insert = super.insert(applicantChanges);

            CompleteApplicantOrCheckerTask(appId, "EDIT", ApplicationStatusEnum.THE_APPLICANT_IS_INVITED_TO_ACCEPT_FORMAL_AMENDMENTS.name());

            return insert;
        }

        return new TrademarkApplicationModification();
    }




    public TrademarkApplicationModification initializeModifications(TrademarkDetail trademarkDetail, TradeMarkApplicationModificationDto applicantNewChanges) {

        TrademarkApplicationModification tempChanges = initializeApplicantChanges(applicantNewChanges);
        tempChanges = applicationModificationsMapper.mapOldTradeMarkValues(tempChanges, trademarkDetail);
        return tempChanges;
    }


    private TrademarkApplicationModification initializeApplicantChanges(TradeMarkApplicationModificationDto applicantNewChanges) {
        return applicationModificationsMapper.map(applicantNewChanges);
    }


    public TrademarkDetail getApplicationTradeMarkDetails(Long appId) {
        TrademarkDetail trademarkDetail = trademarkDetailService.getByApplicationId(appId);
        return trademarkDetail;
    }


    public Long getApplicationTM(Long appId) {
        return trademarkDetailService.getTMByApplicationId(appId);
    }



    public Map<String, Object> getAuditModificationsDifferences(Long appId) {
        Optional<List<TrademarkApplicationModification>> applicantModificationTracking =
                tradeMarkApplicationModificationRepository.getByApplicationIdOrderByModificationFilingDateDesc(appId);
        if (applicantModificationTracking.isPresent()) {
            if (!applicantModificationTracking.get().isEmpty()) {
                Map<String, Object> changesAudit = new HashMap<>();
                tradeMarkNameEnAudit(applicantModificationTracking, changesAudit);

                tradeMarkNameArAudit(applicantModificationTracking, changesAudit);

                tradeMarkDescriptionAudit(applicantModificationTracking, changesAudit);


                tradeMarktypeAudit(applicantModificationTracking, changesAudit);

                tradeMarkLanguageAudit(applicantModificationTracking, changesAudit);

                tradeMarkTypeDescAudit(applicantModificationTracking, changesAudit);


                tradeMarkModificationDate(applicantModificationTracking, changesAudit);
                RequestTasksDto taskByRowId = bpmCallerService.getTaskByRowIdAndType(RequestTypeEnum.TRADEMARK,appId).getPayload();
                if (Objects.nonNull(taskByRowId) && Objects.nonNull(taskByRowId.getTaskId())) {
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    LocalDateTime taskDueDate = parseDateTime(taskByRowId.getDue());
                    if (Objects.nonNull(taskDueDate)) {
                        Duration duration = calculateDuration(currentDateTime, taskDueDate);
                        changesAudit.put("duration-Days", duration.toHours() >= 0 && duration.toHours() < 24
                                ? 0
                                : duration.toHours() / 24);
                        changesAudit.put("duration-Hours", duration.toHours() % 24
                                < 0?0:duration.toHours() % 24);
                    }
                }

                changesAudit.put("lastActionAr", ACTION.getNameAr());
                changesAudit.put("lastActionEn", ACTION.getNameEn());

                return changesAudit;
            }
        }
        return new HashMap<>();
    }

    private static void tradeMarkModificationDate(Optional<List<TrademarkApplicationModification>> applicantModificationTracking, Map<String, Object> changesAudit) {
        if (Objects.nonNull(applicantModificationTracking.get().get(0).getModificationFilingDate())) {
            LocalDateTime filingDate = applicantModificationTracking.get().get(0).getModificationFilingDate();
            changesAudit.put("timeExpression", filingDate.getHour() + ":" + filingDate.getMinute() + ":" + filingDate.getSecond());
            changesAudit.put("hijriModificationDate", applicantModificationTracking.get().get(0).getModificationsHijriDate());
        }
    }

    private void tradeMarktypeAudit(Optional<List<TrademarkApplicationModification>> applicantModificationTracking, Map<String, Object> changesAudit) {
        if (Objects.nonNull(applicantModificationTracking.get().get(0).getOldMarkType()) && Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkType())) {
            if (applicantModificationTracking.get().get(0).getOldMarkType() != applicantModificationTracking.get().get(0).getNewMarkType()) {
                Optional<LkMarkType> oldMarkType = lkMarkTypeRepository.findById(applicantModificationTracking.get().get(0).getOldMarkType());
                if (oldMarkType.isPresent())
                    changesAudit.put("oldMarkType", oldMarkType.get());
                Optional<LkMarkType> newMarkType = lkMarkTypeRepository.findById(applicantModificationTracking.get().get(0).getNewMarkType());
                if (newMarkType.isPresent())
                    changesAudit.put("newMarkType", newMarkType.get());
            }
        }
    }

    private void tradeMarkLanguageAudit(Optional<List<TrademarkApplicationModification>> applicantModificationTracking, Map<String, Object> changesAudit) {
        if (Objects.nonNull(applicantModificationTracking.get().get(0).getOldMarkType()) && Objects.nonNull(applicantModificationTracking.get().get(0).getNewTagLanguageId())) {
            if (applicantModificationTracking.get().get(0).getOldTagLanguageId() != null && applicantModificationTracking.get().get(0).getOldTagLanguageId() != applicantModificationTracking.get().get(0).getNewTagLanguageId()) {
                Optional<LkTagLanguage> oldTagLanguage = lkTagLanguageRepository.findById(applicantModificationTracking.get().get(0).getOldTagLanguageId());
                if (oldTagLanguage.isPresent())
                    changesAudit.put("oldTagLanguage", oldTagLanguage.get());
                Optional<LkTagLanguage> newTagLanguage = lkTagLanguageRepository.findById(applicantModificationTracking.get().get(0).getNewTagLanguageId());
                if (newTagLanguage.isPresent())
                    changesAudit.put("newTagLanguage", newTagLanguage.get());
            }
        }
    }

    private void tradeMarkTypeDescAudit(Optional<List<TrademarkApplicationModification>> applicantModificationTracking, Map<String, Object> changesAudit) {
        if (Objects.nonNull(applicantModificationTracking.get().get(0).getOldMarkTypeDesc()) && Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkTypeDesc())) {
            if (applicantModificationTracking.get().get(0).getOldMarkTypeDesc() != applicantModificationTracking.get().get(0).getNewMarkTypeDesc()) {

                Optional<LkTagTypeDesc> oldMarkTypeDesc = lkTagTypeDescRepository.findById(applicantModificationTracking.get().get(0).getOldMarkTypeDesc());
                if (oldMarkTypeDesc.isPresent())
                    changesAudit.put("oldMarkTypeDesc", oldMarkTypeDesc.get());
                Optional<LkTagTypeDesc> newMarkTypeDesc = lkTagTypeDescRepository.findById(applicantModificationTracking.get().get(0).getNewMarkTypeDesc());
                if (oldMarkTypeDesc.isPresent())
                    changesAudit.put("newMarkTypeDesc", newMarkTypeDesc.get());
            }
        }
    }

    private void tradeMarkDescriptionAudit(Optional<List<TrademarkApplicationModification>> applicantModificationTracking, Map<String, Object> changesAudit) {
        if (Objects.nonNull(applicantModificationTracking.get().get(0).getOldMarkDesc()) && Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkDesc())) {
            if (!isValueChanged(applicantModificationTracking.get().get(0).getOldMarkDesc(), applicantModificationTracking.get().get(0).getNewMarkDesc())) {
                changesAudit.put("oldMarkDesc", applicantModificationTracking.get().get(0).getOldMarkDesc());
                changesAudit.put("newMarkDesc", applicantModificationTracking.get().get(0).getNewMarkDesc());
            }
        }
        if (Objects.isNull(applicantModificationTracking.get().get(0).getOldMarkDesc()) && Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkDesc())) {
            changesAudit.put("oldMarkDesc", null);
            changesAudit.put("newMarkDesc", applicantModificationTracking.get().get(0).getNewMarkDesc());

        }
    }

    private void tradeMarkNameArAudit(Optional<List<TrademarkApplicationModification>> applicantModificationTracking, Map<String, Object> changesAudit) {
        if (Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkNameAr()) && Objects.nonNull(applicantModificationTracking.get().get(0).getOldMarkNameAr())) {
            if (!isValueChanged(applicantModificationTracking.get().get(0).getNewMarkNameAr(), applicantModificationTracking.get().get(0).getOldMarkNameAr())) {
                changesAudit.put("oldNameAr", applicantModificationTracking.get().get(0).getOldMarkNameAr());
                changesAudit.put("newNameAr", applicantModificationTracking.get().get(0).getNewMarkNameAr());
            }
        }
        if (Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkNameAr()) && Objects.isNull(applicantModificationTracking.get().get(0).getOldMarkNameAr())) {
            changesAudit.put("oldNameAr", null);
            changesAudit.put("newNameAr", applicantModificationTracking.get().get(0).getNewMarkNameAr());

        }
    }

    private void tradeMarkNameEnAudit(Optional<List<TrademarkApplicationModification>> applicantModificationTracking, Map<String, Object> changesAudit) {
        if (Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkNameEn()) && Objects.nonNull(applicantModificationTracking.get().get(0).getOldMarkNameEn())) {
            if (!isValueChanged(applicantModificationTracking.get().get(0).getNewMarkNameEn(), applicantModificationTracking.get().get(0).getOldMarkNameEn())) {
                changesAudit.put("oldNameEn", applicantModificationTracking.get().get(0).getOldMarkNameEn());
                changesAudit.put("newNameEn", applicantModificationTracking.get().get(0).getNewMarkNameEn());
            }

        }
        if (Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkNameEn()) && Objects.isNull(applicantModificationTracking.get().get(0).getOldMarkNameEn())) {
            changesAudit.put("oldNameEn", null);
            changesAudit.put("newNameEn", applicantModificationTracking.get().get(0).getNewMarkNameEn());


        }
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    private Duration calculateDuration(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime);
    }

    public void persistNewTradeMarkModificationsAndCompleteApplicantTask(Long appId) {
        peristTradeMarkModifications(appId);
        CompleteApplicantOrCheckerTask(appId, "YES", ApplicationStatusEnum.UNDER_FORMAL_PROCESS.name());
    }

    private void CompleteApplicantOrCheckerTask(Long applicationId, String decision, String status) {
        RequestTasksDto taskDto = bpmCallerService.getTaskByRowIdAndType(RequestTypeEnum.TRADEMARK,applicationId).getPayload();
        if (Objects.isNull(taskDto) || Objects.isNull(taskDto.getTaskId())) {
            log.info("there is no current task in Tm application for application id {}", applicationId);
            return;
        }
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> acceptApplicantModifications = new HashMap<>();
        acceptApplicantModifications.put("value", decision);
        variables.put("approved", acceptApplicantModifications);
        Map<String, Object> appId = new HashMap<>();
        appId.put("value", applicationId.toString());
        variables.put("id", appId);
        completeTaskRequestDto.setVariables(variables);
//        if(decision.equals(Constants.tradeMarkRelease.EDIT.name()))
//        completeTaskRequestDto.setNotes(Constants.tradeMarkRelease.EDIT.getActionNote());

//        ApplicationStatusChangeLog changeLog = createApplicationLog(applicationId, taskDto, status);
//        applicationStatusChangeLogService.insert(changeLog);
        bpmCallerFeignClient.completeUserTask(taskDto.getTaskId(), completeTaskRequestDto);
    }

    private ApplicationStatusChangeLog createApplicationLog(Long applicationId, RequestTasksDto taskDto, String status) {
        ApplicationStatusChangeLog changeLog = new ApplicationStatusChangeLog();
        changeLog.setTaskInstanceId(taskDto.getTaskId());
        changeLog.setTaskDefinitionKey(taskDto.getTaskDefinitionKey());
        changeLog.setNewStatusByCode(status);
        changeLog.setApplicationById(applicationId);
        return changeLog;
    }

    private void peristTradeMarkModifications(Long appId) {
        Optional<List<TrademarkApplicationModification>> applicantModificationTracking =
                tradeMarkApplicationModificationRepository.getByApplicationIdOrderByModificationFilingDateDesc(appId);
        Optional<ApplicationInfo> applicationInfo = applicationInfoRepository.findById(appId);
        if (applicationInfo.isPresent()) {
            if (applicantModificationTracking.isPresent()) {
                if (!applicantModificationTracking.get().isEmpty()) {
                    TrademarkDetail trademarkDetail = getApplicationTradeMarkDetails(appId);
                    trademarkDetail = Objects.isNull(trademarkDetail) ? new TrademarkDetail() : trademarkDetail;
                    trademarkDetail.setApplicationId(appId);
                    if (Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkNameAr())) {
                        trademarkDetail.setNameAr(applicantModificationTracking.get().get(0).getNewMarkNameAr());
                        applicationInfo.get().setTitleAr(applicantModificationTracking.get().get(0).getNewMarkNameAr());
                    }
                    if (Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkNameEn())) {
                        trademarkDetail.setNameEn(applicantModificationTracking.get().get(0).getNewMarkNameEn());
                        applicationInfo.get().setTitleEn(applicantModificationTracking.get().get(0).getNewMarkNameEn());
                    }
                    if (Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkDesc())) {
                        trademarkDetail.setMarkDescription(applicantModificationTracking.get().get(0).getNewMarkDesc());
                    }
                    if (Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkType())) {
                        Optional<LkMarkType> markType = lkMarkTypeRepository.findById(applicantModificationTracking.get().get(0).getNewMarkType());
                        if (markType.isPresent())
                            trademarkDetail.setMarkType(markType.get());
                    }
                    if (Objects.nonNull(applicantModificationTracking.get().get(0).getNewTagLanguageId())) {
                        Optional<LkTagLanguage> tagLanguage = lkTagLanguageRepository.findById(applicantModificationTracking.get().get(0).getNewTagLanguageId());
                        if (tagLanguage.isPresent())
                            trademarkDetail.setTagLanguage(tagLanguage.get());
                    }
                    if (Objects.nonNull(applicantModificationTracking.get().get(0).getNewMarkTypeDesc())) {
                        Optional<LkTagTypeDesc> tagTypeDesc = lkTagTypeDescRepository.findById(applicantModificationTracking.get().get(0).getNewMarkTypeDesc());
                        if (tagTypeDesc.isPresent()) {
                            trademarkDetail.setTagTypeDesc(tagTypeDesc.get());
                            if (tagTypeDesc.get().getCode().equals(VISUAL.name())) {
                                applicationInfo.get().setTitleEn(null);
                                applicationInfo.get().setTitleAr(null);
                                trademarkDetail.setNameAr(null);
                                trademarkDetail.setNameEn(null);

                            }


                        }

                    }
                    trademarkDetailService.insert(trademarkDetail);
                    applicantModificationTracking.get().get(0).setUpdated(true);
                    super.update(applicantModificationTracking.get().get(0));
                    applicationInfoRepository.save(applicationInfo.get());
                }
            }
        }
    }

    public <T> boolean isValueChanged(T oldVal, T newVal) {
        if (oldVal == null && newVal == null) {
            return true;
        }
        if (oldVal == null || newVal == null) {
            return true;
        }
        return oldVal.equals(newVal);
    }

}
