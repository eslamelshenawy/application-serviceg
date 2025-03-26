package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.mapper.ApplicationStatusChangeLogMapper;
import gov.saip.applicationservice.common.model.ApplicationStatusChangeLog;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.repository.ApplicationStatusChangeLogRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApplicationStatusChangeLogServiceImpl extends BaseServiceImpl<ApplicationStatusChangeLog, Long> implements ApplicationStatusChangeLogService {

    private final ApplicationStatusChangeLogRepository applicationStatusChangeLogRepository;
    private final BPMCallerFeignClient bPMCallerFeignClient;

    @Autowired
    @Lazy
    private ApplicationInfoService applicationInfoService;

    private final LkApplicationStatusService applicationStatusService;
    private final ApplicationStatusChangeLogMapper applicationStatusChangeLogMapper;
    @Override
    protected BaseRepository<ApplicationStatusChangeLog, Long> getRepository() {
        return applicationStatusChangeLogRepository;
    }

    @Override
    public ApplicationStatusChangeLog insert(ApplicationStatusChangeLog entity) {
        LkApplicationStatus currentApplicationStatus = applicationStatusService.getStatusByApplicationId(entity.getApplication().getId());
        entity.setPreviousStatus(currentApplicationStatus);
        updateApplicationWithNewStatusAndSetNewStatusInLongEntity(entity, currentApplicationStatus);
        if (entity.getNewStatus() != null && !currentApplicationStatus.getCode().equals(entity.getNewStatus().getCode())) {
            applicationInfoService.changeApplicationStatusId(entity.getApplication().getId(), entity.getNewStatus().getCode());
            log.info("start to insert ApplicationStatusChangeLog ==> {} ", entity);
            return super.insert(entity);
        }
        return null;
    }

    private void updateApplicationWithNewStatusAndSetNewStatusInLongEntity(ApplicationStatusChangeLog entity, LkApplicationStatus currentApplicationStatus) {
        if (entity.getNewStatus() != null && entity.getNewStatus().getCode() != null) {
            LkApplicationStatus newStatus = applicationStatusService.findByCodeAndApplicationCategory(entity.getNewStatus().getCode() ,currentApplicationStatus.getApplicationCategory().getId());
//            applicationInfoService.changeApplicationStatusId(entity.getApplication().getId(), newStatus.getCode());
            entity.setNewStatus(newStatus);
        }
//        else {
//            entity.setNewStatus(currentApplicationStatus);
//        }
    }

    @Override
    public List<BaseStatusChangeLogDto> getByApplicationId(Long appId) {
        List<ApplicationStatusChangeLog> statusChangeLogs = applicationStatusChangeLogRepository.findByApplicationId(appId);
        List<BaseStatusChangeLogDto> baseStatusChangeLogDtos = applicationStatusChangeLogMapper.mapToBaseStatusChangeLogDto(statusChangeLogs);
        return baseStatusChangeLogDtos;
    }

    @Override
    public BaseStatusChangeLogDto getLastLogByApplicationId(Long appId) {
        Pageable pageable = PageRequest.of(0, 1);
        List<ApplicationStatusChangeLog> applicationStatusChangeLog = applicationStatusChangeLogRepository.findLastLogByApplicationId(appId, pageable);
        ApplicationStatusChangeLog lastLog = applicationStatusChangeLog.isEmpty() ? null : applicationStatusChangeLog.get(0);
        return applicationStatusChangeLogMapper.map(lastLog);
    }

    @Override
    public boolean isExistsByAppIdTaskDefinitionKey(Long applicationId, String taskKey) {
        return applicationStatusChangeLogRepository.existsByApplicationIdAndTaskDefinitionKey(applicationId, taskKey);
    }

    @Override
    public void changeApplicationStatusAndLog(String status, String descriptionCode, Long appId) {
        System.out.println("  changeApplicationStatusAndLog publication trace 11 " + appId );
        Long reqId = bPMCallerFeignClient.findRequestIdByRowId(appId).getPayload();
        System.out.println("  changeApplicationStatusAndLog publication trace 12 ");
        RequestTasksDto requestTasksDto = null;
         try{
             requestTasksDto = bPMCallerFeignClient.getCurrentTaskByRequestId(reqId).getPayload();
         }catch (Exception ex ){
             System.out.println(" Exception  getCurrentTaskByRequestId   " + ex.getMessage());
         }
        System.out.println("  changeApplicationStatusAndLog publication trace 13 ");
        ApplicationStatusChangeLogDto changeLogDto = new ApplicationStatusChangeLogDto();
        changeLogDto.setNewStatusCode(status);
        changeLogDto.setApplicationId(appId);
        changeLogDto.setDescriptionCode(descriptionCode);

        if (requestTasksDto != null) {
            changeLogDto.setTaskDefinitionKey(requestTasksDto.getTaskDefinitionKey());
            changeLogDto.setTaskInstanceId(requestTasksDto.getTaskId());
        }

        insert(applicationStatusChangeLogMapper.unMap(changeLogDto));
    }

    @Override
    public void changeApplicationStatusAndLog(String status, String descriptionCode, Long appId, String taskDefinitionKey, String taskId) {
        ApplicationStatusChangeLogDto changeLogDto = new ApplicationStatusChangeLogDto();
        changeLogDto.setNewStatusCode(status);
        changeLogDto.setApplicationId(appId);
        changeLogDto.setDescriptionCode(descriptionCode);
        changeLogDto.setTaskDefinitionKey(taskDefinitionKey);
        changeLogDto.setTaskInstanceId(taskId);
        insert(applicationStatusChangeLogMapper.unMap(changeLogDto));
    }
}
