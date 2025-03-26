package gov.saip.applicationservice.common.service.supportService.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.supportService.SupportServiceStatusChangeLogMapper;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.supportService.SupportServiceStatusChangeLog;
import gov.saip.applicationservice.common.repository.supportService.SupportServiceStatusChangeLogRepository;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import gov.saip.applicationservice.common.service.supportService.SupportServiceStatusChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SupportServiceStatusChangeLogServiceImpl extends BaseServiceImpl<SupportServiceStatusChangeLog, Long> implements SupportServiceStatusChangeLogService {

    private final SupportServiceStatusChangeLogRepository SupportServiceStatusChangeLogRepository;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final LKSupportServiceRequestStatusService supportServiceRequestStatusService;
    private final SupportServiceStatusChangeLogMapper supportServiceStatusChangeLogMapper;

    @Override
    protected BaseRepository<SupportServiceStatusChangeLog, Long> getRepository() {
        return SupportServiceStatusChangeLogRepository;
    }
    @Override
    public SupportServiceStatusChangeLog insert(SupportServiceStatusChangeLog entity) {
        log.info("start to insert SupportServiceStatusChangeLog ==> {} ", entity);
        LKSupportServiceRequestStatus currentStatus = applicationSupportServicesTypeService.getStatusBySupportServiceId(entity.getSupportServicesType().getId());
        SupportServiceType supportServiceType = applicationSupportServicesTypeService.getServiceTypeByServiceId(entity.getSupportServicesType().getId());
        if(supportServiceType.equals(SupportServiceType.LICENSING_MODIFICATION) && entity.getNewStatus().getCode().equals(SupportServiceRequestStatusEnum.APPROVED.name()))
            entity.getNewStatus().setCode(SupportServiceRequestStatusEnum.LICENSED.name());
        entity.setPreviousStatus(currentStatus);
        updateApplicationWithNewStatusAndSetNewStatusInLongEntity(entity, currentStatus);
        return super.insert(entity);
    }

    private void updateApplicationWithNewStatusAndSetNewStatusInLongEntity(SupportServiceStatusChangeLog entity, LKSupportServiceRequestStatus currentApplicationStatus) {
        if (entity.getNewStatus() != null && entity.getNewStatus().getCode() != null) {
            LKSupportServiceRequestStatus newStatus = supportServiceRequestStatusService.findByCode(entity.getNewStatus().getCode());
            applicationSupportServicesTypeService.updateRequestStatusById(entity.getSupportServicesType().getId(), newStatus.getId());
            entity.setNewStatus(newStatus);
        } else {
            entity.setNewStatus(currentApplicationStatus);
        }
    }

    @Override
    public List<BaseStatusChangeLogDto> getBySupportServiceId(Long appId) {
        List<SupportServiceStatusChangeLog> statusChangeLogs = SupportServiceStatusChangeLogRepository.findBySupportServicesTypeId(appId);
        return supportServiceStatusChangeLogMapper.mapToBaseStatusChangeLogDto(statusChangeLogs);
    }

    @Override
    public SupportServiceStatusChangeLog insert(SupportServiceStatusChangeLogDto dto) {
        SupportServiceStatusChangeLog supportServiceStatusChangeLog = supportServiceStatusChangeLogMapper.unMap(dto);
        return insert(supportServiceStatusChangeLog);
    }
}
