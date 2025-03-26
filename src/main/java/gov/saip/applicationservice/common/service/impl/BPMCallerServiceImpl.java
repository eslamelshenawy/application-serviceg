package gov.saip.applicationservice.common.service.impl;

import com.google.common.base.CharMatcher;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.enums.CallForCorrectionInvitationType;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.service.BPMCallerService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Util;
import gov.saip.applicationservice.util.Utilities;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.RequestTypeEnum.REVOKE_LICENSE_REQUEST;

@Service
@Slf4j
@AllArgsConstructor
public class BPMCallerServiceImpl implements BPMCallerService {
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final Util util;

    @Override
    public StartProcessResponseDto startApplicationProcess(ProcessRequestDto dto) {
        return bpmCallerFeignClient.startApplicationProcess(dto);
    }

    @Override
    public RequestTasksDto getTaskByRowId(Long rowId, String processRequestTypeCode) {
        return bpmCallerFeignClient.getTaskByRowId(rowId,processRequestTypeCode);
    }

    public RequestTasksDto getTaskByRowIdAndUserName(Long rowId, String userName,String requestTypeCode) {
        return bpmCallerFeignClient.getTaskByRowIdAndUserName(rowId, userName,requestTypeCode);
    }
    @Override
    public List<RequestTasksDto> getTasksByRowId(Long rowId) {
        return bpmCallerFeignClient.getTasksByRowId(rowId);
    }

    @Override
    public List<RequestTasksDto> getTaskByRowsIds(List<Long> rowIds) {
        return bpmCallerFeignClient.getTaskByRowsIds(rowIds);
    }

    @Override
    public ApiResponse<RequestTasksDto> getTaskByRowIdAndType(RequestTypeEnum type, Long rowId) {
        ApiResponse<RequestTasksDto> taskByRowIdAndType = bpmCallerFeignClient.getTaskByRowIdAndType(type, rowId);
        isValidCustomerToTakeAction(type, taskByRowIdAndType);
        return taskByRowIdAndType;
    }

    @Override
    public ApiResponse<RequestTasksDto> getTaskByRowIdAndTypefromHistory(RequestTypeEnum type, Long rowId) {
        ApiResponse<RequestTasksDto> taskByRowIdAndType = bpmCallerFeignClient.getTaskByRowIdAndTypefromHistory(type, rowId);
        isValidCustomerToTakeAction(type, taskByRowIdAndType);
        return taskByRowIdAndType;
    }




    private static void isValidCustomerToTakeAction(RequestTypeEnum type, ApiResponse<RequestTasksDto> taskByRowIdAndType) {
        String currentUserCustomerCode = Utilities.getCustomerCodeFromHeaders();
        if(currentUserCustomerCode!= null && type == REVOKE_LICENSE_REQUEST){
            if(!currentUserCustomerCode.equals(taskByRowIdAndType.getPayload().getAssignee())){
                throw new BusinessException(Constants.ErrorKeys.GENERAL_DO_NOT_HAVE_PERMISSION, HttpStatus.FORBIDDEN, null);
            }
        }
    }

    @Override
    public RequestTasksDto getTaskByRowIdAndTypeIfExists(RequestTypeEnum type, Long rowId) {
        return bpmCallerFeignClient.getTaskByRowIdAndTypeIfExissts(type, rowId).getPayload();
    }

    @Override
    public ApiResponse<PaginationDto<List<TasksUiDto>>> getAllUserTasks(Integer page, Integer limit, String query, String type, String fromDate, String toDate, String expiryDate
            , LocalDate fromFilingDate, LocalDate toFilingDate, String applicationNumber) {
        String userName = (String) util.getFromBasicUserinfo("userName");
        log.info("the external logged in user is -> {}", userName);
        ApiResponse<PaginationDto<List<TasksUiDto>>> userTasks = bpmCallerFeignClient.getUserTasks(page, limit, query, type, fromDate, toDate, userName,fromFilingDate, toFilingDate, applicationNumber ,expiryDate);

        return userTasks;
    }


    @Override
    public Map<String, Object> getTaskRemainingTimeByApplicationId(Long applicationId,String processRequestTypeCode) {
        return bpmCallerFeignClient.getTaskRemainingTimeByApplicationId(applicationId,processRequestTypeCode);
    }

    public void extendDueDate(RequestTasksDto task) {
        bpmCallerFeignClient.extendDueDate(task);
    }

    @Override
    public Long getRequestTypeConfigValue(String code) {
        String value = bpmCallerFeignClient.getRequestTypeConfigValue(code);
        return Long.parseLong(CharMatcher.digit().retainFrom(value));
    }

    @Override
    public Long getRequestTypeConfigValue(String code, Long rowId,String requestTypeCode) {
        String value = bpmCallerFeignClient.getRequestTypeConfigValue(code, rowId,requestTypeCode).getPayload();
        if (value == null)
            return null;
        return extractConfigValue(value);
    }

    @Override
    public List<ApplicationRequestTypeConfig> getRequestTypeConfigValue(String code, List<Long> rowsIds) {
        List<ApplicationRequestTypeConfig> applicationRequestTypeConfigs =
                bpmCallerFeignClient.getRequestTypeConfigValue(code, rowsIds).getPayload();
        if (applicationRequestTypeConfigs.isEmpty())
            return new ArrayList<>();

        applicationRequestTypeConfigs.forEach(applicationRequestTypeConfig -> {
            Long configValue = extractConfigValue(applicationRequestTypeConfig.getConfigValueExpression());
            applicationRequestTypeConfig.setConfigValue(configValue);
        });

        return applicationRequestTypeConfigs.stream()
                .filter(applicationRequestTypeConfig -> applicationRequestTypeConfig.getConfigValueExpression() != null)
                .collect(Collectors.toList());
    }

    public static Long extractConfigValue(String configValueExpression) {
        if (configValueExpression != null) {
            String expression = CharMatcher.digit().retainFrom(configValueExpression);
            return Long.valueOf(expression.isEmpty() ? "0" : expression);
        }
        return 0L;
    }

    @Override
    public String completeTaskToUser(String taskId, CompleteTaskRequestDto dto) {
        return bpmCallerFeignClient.completeUserTask(taskId, dto).getPayload();
    }

//    @Override
//    public void suspendApplicationProcessByType(ProcessInstanceDto processInstance,
//                                                Long applicationId,
//                                                String saipCode) {
//        bpmCallerFeignClient.suspendApplicationProcessByType(processInstance, applicationId, saipCode);
//    }

    @Override
    public RequestIdAndNotesDto getRequestIdAndLastNotes(long id, String saipCode) {
        return bpmCallerFeignClient.getRequestIdAndLastNotes(id, saipCode).getPayload();
    }

    @Override
    public RequestTasksDto getCurrentTaskByRequestId(Long requestId) {
        return bpmCallerFeignClient.getCurrentTaskByRequestId(requestId).getPayload();
    }

    @Override
    public PaginationDto<List<TaskHistoryUIDto>> taskHistory(Integer page, Integer limit, Long applicationId, String processInstanceId, String requestId, Long serviceId) {
        return bpmCallerFeignClient.taskHistory(page, limit, requestId, applicationId, processInstanceId, serviceId).getPayload();
    }

    @Override
    public Integer remainingCountOfCallForCorrectionInvitation(Long requestId, String callForCorrectionInvitationType) {
        return bpmCallerFeignClient.getMaxNumberForCallForCorrectionInvitation(requestId , CallForCorrectionInvitationType.valueOf(callForCorrectionInvitationType)).getPayload();
    }

//    @Override
//    public String getProcessInstanceByRowIdIfExist(Long rowId) {
//        return bpmCallerFeignClient.getProcessInstanceByRowIdIfExist(rowId).getPayload();
//    }

//    @Override
//    public Map<String, Object> getProcessVariableMap(String processInstanceId) {
//        return bpmCallerFeignClient.getProcessVariableMap(processInstanceId).getBody();
//    }


    @Override
    public List<RequestTasksDto> getTasksByRowIdAndAssignee(Long rowId) {
        return bpmCallerFeignClient.getTasksByActualRowIdAndAssignee(rowId, Utilities.getCustomerCodeFromHeaders());
    }


}
