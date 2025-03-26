package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.TaskEqmDto;
import gov.saip.applicationservice.common.enums.certificate.CertificateStatusEnum;
import gov.saip.applicationservice.common.model.LkTaskEqmStatus;
import gov.saip.applicationservice.common.model.TaskEqm;
import gov.saip.applicationservice.common.repository.TaskEqmRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.LkTaskEqmItemService;
import gov.saip.applicationservice.common.service.LkTaskEqmTypeService;
import gov.saip.applicationservice.common.service.TaskEqmService;
import gov.saip.applicationservice.common.service.lookup.LkTaskEqmStatusService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskEqmServiceImpl extends BaseServiceImpl<TaskEqm, Long> implements TaskEqmService {

    private final TaskEqmRepository taskEqmRepository;
    private final LkTaskEqmItemService taskEqmItemService;
    private final ApplicationInfoService applicationInfoService;
    private final LkTaskEqmStatusService lkTaskEqmStatusService;
    private final LkTaskEqmTypeService lkTaskEqmTypeService;

    @Override
    protected BaseRepository<TaskEqm, Long> getRepository() {
        return taskEqmRepository;
    }

    @Override
    public TaskEqm findByTaskId(String taskId) {
        String[] params = {taskId};
        return taskEqmRepository.findByTaskId(taskId).orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, params));

    }

    @Override
    public TaskEqm findByEqmApplicationId(Long applicationId) {
        String[] params = {applicationId.toString()};
        return taskEqmRepository.findByEqmApplicationId(applicationId).orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, params));
    }

    @Override
    public Boolean checkByEqmApplicationId(Long applicationId) {
        return taskEqmRepository.countByEqmApplicationId(applicationId) > 0 ;
    }

    @Override
    public List<TaskEqm> findAllByApplicationId(Long applicationId) {
        return taskEqmRepository.findAllByApplicationInfoId(applicationId);
    }

    @Override
    public List<TaskEqm> getByServiceIdAndType(Long serviceId, String typeCode) {
        return taskEqmRepository.findListByServiceIdAndTypeCode(serviceId, typeCode);
    }

    @Override
    public TaskEqm update(TaskEqm entity) {
        if (entity.getTaskEqmType() != null) {
            entity.setTaskEqmType(lkTaskEqmTypeService.findByCode(entity.getTaskEqmType().getCode()));
        }

        Optional<TaskEqm> taskEqm = getTaskIfExists(entity);

        entity.getTaskEqmRatingItems().forEach(item -> {
            item.setTaskEqm(entity);
            item.setTaskEqmItem(taskEqmItemService.getReferenceById(item.getTaskEqmItem().getId()));
        });
       if(taskEqm.isPresent()) {
           TaskEqm current = taskEqm.get();
           current.setComments(entity.getComments());
           current.setAverage(entity.getAverage());
           current.setEnough(entity.isEnough());
           current.setTaskEqmRatingItems(entity.getTaskEqmRatingItems());
           current.getTaskEqmRatingItems().forEach(item -> {
               item.setTaskEqm(current);
           });
           taskEqmRepository.deleteByTaskEqmId(current.getId());
           return super.update(current);
       }
       else {
           entity.setApplicationInfo(entity.getApplicationInfo() == null ? null : applicationInfoService.findById(entity.getApplicationInfo().getId()));
           LkTaskEqmStatus taskEqmStatus = lkTaskEqmStatusService.findByCode(CertificateStatusEnum.PENDING.name());
           entity.setTaskEqmStatus(taskEqmStatus);
           return super.insert(entity);
       }
    }

    private Optional<TaskEqm> getTaskIfExists(TaskEqm entity) {
        if (entity.getTaskId() != null) {
            return taskEqmRepository.findByTaskId(entity.getTaskId());
        } else if (entity.getServiceId() != null) {
            return taskEqmRepository.findByServiceIdAndTypeCode(entity.getServiceId(), entity.getTaskEqmType().getCode());
        } else if (entity.getApplicationInfo() != null && entity.getApplicationInfo().getId() != null) {
            return taskEqmRepository.findByEqmApplicationId(entity.getApplicationInfo().getId());
        } else {
            return null;
        }

    }
}
