package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.InitialModificationRequest;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.repository.InitialModificationRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.InitialModificationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional
public class InitialModificationRequestServiceImpl extends SupportServiceRequestServiceImpl<InitialModificationRequest> implements InitialModificationRequestService {

    private final InitialModificationRequestRepository initialModificationRequestRepository;
    private final BPMCallerServiceImpl bpmCallerService;
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    private final ApplicationServiceImpl applicationServiceImpl;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return initialModificationRequestRepository;
    }


    @Override
    public InitialModificationRequest insert(InitialModificationRequest entity) {
        /**
         * @Author Mohmad sabri
         * // TODO WILL get it's Business PreCodtions
         */

        return super.insert(SupportServiceType.INITIAL_MODIFICATION, entity);
    }

    @Override
    public InitialModificationRequest update(InitialModificationRequest entity) {
        InitialModificationRequest initialModificationRequest = findById(entity.getId());
        initialModificationRequest.setLkSupportServiceType(entity.getLkSupportServiceType() != null ? entity.getLkSupportServiceType() : initialModificationRequest.getLkSupportServiceType());
        return super.update(initialModificationRequest);
    }

    @Override
    public SupportedServiceCode getApplicationSupportedServiceType(Long appId) {
        List<SupportedServiceCode> supportedServiceCodeList = initialModificationRequestRepository.findApplicationSupportedServiceType(appId, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")));
        return supportedServiceCodeList!= null && !supportedServiceCodeList.isEmpty()? supportedServiceCodeList.get(0):null;
    }

    @Override
    public void finishInitialModificationRequestByApplicationId(Long appId) {
        Long unPaidCountApplicationRelavantType = applicationRelevantTypeRepository.getUnPaidCountApplicationRelavantType(appId);
        Long initialModificationId = initialModificationRequestRepository.findUnderProcedureByApplicationId(appId);
        this.updateRequestStatusByCode( initialModificationId, SupportServiceRequestStatusEnum.COMPLETED);
        ApplicationInfo applicationInfo = applicationInfoService.findById(appId);
        applicationInfo.setUnPaidApplicationReleventTypeCount(unPaidCountApplicationRelavantType);
        getCurrentInitialModifyTaskAndCompleteIt(applicationInfo, "YES");
    }

    @Override
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
        ApplicationInfo applicationInfo = initialModificationRequestRepository.getApplicationByServiceId(id);
        getCurrentTaskAndCompleteIt(applicationInfo, "UPDATE");

    }

    private void getCurrentTaskAndCompleteIt(ApplicationInfo applicationInfo, String approvedValue) {
        RequestTasksDto requestTasksDto = bpmCallerService.getTaskByRowIdAndType(RequestTypeEnum.valueOf(applicationInfo.getCategory().getSaipCode()), applicationInfo.getId()).getPayload();
        completeTask(requestTasksDto, approvedValue);
    }

    private void getCurrentInitialModifyTaskAndCompleteIt(ApplicationInfo applicationInfo, String approvedValue) {
        RequestTasksDto requestTasksDto = bpmCallerService.getTaskByRowId(applicationInfo.getId(), applicationInfo.getCategory().getSaipCode());
        completeTask(requestTasksDto, approvedValue);
    }

    private void completeTask(RequestTasksDto requestTasksDto, String approvedValue) {
        CompleteTaskRequestDto completeTaskRequestDto = buildCompleteTaskRequestDto(approvedValue);
        bpmCallerService.completeTaskToUser(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }

    private static CompleteTaskRequestDto buildCompleteTaskRequestDto(String approvedValue) {
        Map<String, Object> variables = buildTaskVariableMap(approvedValue);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(variables);
        return completeTaskRequestDto;
    }

    private static Map<String, Object> buildTaskVariableMap(String approvedValue) {
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> completedPetitionRecovery = new HashMap<>();
        completedPetitionRecovery.put("value", approvedValue);
        variables.put("approved", completedPetitionRecovery);
        return variables;
    }


}