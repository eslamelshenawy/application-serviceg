package gov.saip.applicationservice.common.service.activityLog.impl;

import gov.saip.applicationservice.common.dto.TaskHistoryUIDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import gov.saip.applicationservice.common.enums.AppStatusChangeLogDescriptionCode;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyStatusCode;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationStatusChangeLog;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.agency.LKTrademarkAgencyRequestStatus;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyStatusChangeLog;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.agency.LKTrademarkAgencyRequestStatusRepository;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import gov.saip.applicationservice.common.service.activityLog.ActivityLogService;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyChangeLogService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationStatusService;
import gov.saip.applicationservice.common.service.supportService.SupportServiceStatusChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {
    private final SupportServiceStatusChangeLogService supportServiceStatusChangeLogService;
    private final LKTrademarkAgencyRequestStatusRepository agencyRequestStatusRepository;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    private final TrademarkAgencyChangeLogService trademarkAgencyStatusChangeLogService;
    private final LkApplicationStatusService applicationStatusService;

    @Override
    public void insertSupportServicesActivityLogStatus(TaskHistoryUIDto taskHistoryUIDto, ApplicationSupportServicesType supportServicesType) {
        SupportServiceStatusChangeLogDto changeLogDto = new SupportServiceStatusChangeLogDto();
        changeLogDto.setTaskInstanceId(taskHistoryUIDto.getId());
        changeLogDto.setTaskDefinitionKey(taskHistoryUIDto.getTaskDefinitionKey());
        changeLogDto.setNewStatusCode(SupportServiceRequestStatusEnum.UNDER_PROCEDURE.name());
        changeLogDto.setSupportServicesTypeId(supportServicesType.getId());
        supportServiceStatusChangeLogService.insert(changeLogDto);
    }
    @Override
    public void insertTrademarkAgencyActivityLogStatus(TaskHistoryUIDto taskHistoryUIDto, Long agencyId){
        TrademarkAgencyStatusChangeLog trademarkAgencyStatusChangeLog = new TrademarkAgencyStatusChangeLog();
        LKTrademarkAgencyRequestStatus statusByCode = agencyRequestStatusRepository.getLKTrademarkAgencyRequestStatusByCode(TrademarkAgencyStatusCode.UNDER_PROCEDURE.toString());
        trademarkAgencyStatusChangeLog.setTaskInstanceId(taskHistoryUIDto.getId().toString());
        trademarkAgencyStatusChangeLog.setTaskDefinitionKey(taskHistoryUIDto.getTaskDefinitionKey());
        trademarkAgencyStatusChangeLog.setNewStatus(statusByCode);
        trademarkAgencyStatusChangeLog.setPreviousStatus(statusByCode);
        trademarkAgencyStatusChangeLog.setTrademarkAgencyId(agencyId);
        trademarkAgencyStatusChangeLogService.insert(trademarkAgencyStatusChangeLog);
    }
    @Override
    public void insertFileNewApplicationActivityLogStatus(TaskHistoryUIDto taskHistoryUIDto, Long applicationId, String codeStatus, LkApplicationStatus previousStatus){
        ApplicationStatusChangeLog changeLog = new ApplicationStatusChangeLog();
        ApplicationInfo applicationInfo = applicationInfoRepository.findById(applicationId).get();
        if(taskHistoryUIDto != null)
        {
            changeLog.setTaskInstanceId(taskHistoryUIDto.getId());
            changeLog.setTaskDefinitionKey(taskHistoryUIDto.getTaskDefinitionKey());
            changeLog.setDescriptionCode(AppStatusChangeLogDescriptionCode.APPLICATION_FILLED.name());
        }
        else {
            changeLog.setDescriptionCode(AppStatusChangeLogDescriptionCode.APPLICATION_CREATED.name());
        }
        changeLog.setNewStatus(applicationStatusService.findByCodeAndApplicationCategory(codeStatus, applicationInfo.getCategory().getId()));
        changeLog.setPreviousStatus(previousStatus);
        changeLog.setApplication(applicationInfo);
        applicationStatusChangeLogService.persist(changeLog);
    }

    @Override
    public void insertSupportServicesActivityLogStatus(String taskId, String taskDefinitionKey, Long id, String newStatus) {
        SupportServiceStatusChangeLogDto changeLogDto = new SupportServiceStatusChangeLogDto();
        changeLogDto.setTaskInstanceId(taskId);
        changeLogDto.setTaskDefinitionKey(taskDefinitionKey);
        changeLogDto.setNewStatusCode(newStatus);
        changeLogDto.setSupportServicesTypeId(id);
        supportServiceStatusChangeLogService.insert(changeLogDto);
    }
}
