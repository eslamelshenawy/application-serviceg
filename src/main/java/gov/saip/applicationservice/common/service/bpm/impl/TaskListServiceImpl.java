package gov.saip.applicationservice.common.service.bpm.impl;


import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.TaskHistoryUIDto;
import gov.saip.applicationservice.common.dto.appeal.AppealDetailsDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.enums.RequestActivityLogEnum;
import gov.saip.applicationservice.common.model.appeal.AppealRequest;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.BPMCallerService;
import gov.saip.applicationservice.common.service.appeal.AppealRequestService;
import gov.saip.applicationservice.common.service.bpm.TaskListService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.util.Constants.tasksToBeIgnoredFromCamundaHistory;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    public static final String NAME_AR = "تحت الدراسة";
    public static final String NAME_EN = "Under Procedure";
    public static final String RETURNED_TO_APPLICANT_AR = "معاد لمقدم الطلب";
    public static final String RETURNED_TO_APPLICANT_EN = "Returned to applicant";
    public static final String EXAMINER_REPORT = "examinerReport";
    public static final String SEND_OFFICE_ACTION_SUB_TM = "SEND_OFFICE_ACTION_SUB_TM";
    public static final String GRANT = "GRANT";

    public static final String REJECTED_AR = "مرفوض";
    public static final String REJECTED_EN = "Rejected";
    public static final String APPROVE_OBJECTION_PAT = "APPROVE_OBJECTION_PAT";
    public static final String APPROVE_OBJECTION_AR = "رفض الطلب";
    public static final String APPROVE_OBJECTION_EN = "Application Rejection";
    public static final String ACCEPTED_AR = "بإنتظار سداد المقابل المالي التسجيل والنشر";
    public static final String ACCEPTED_EN = "Waiting for financial fees of registration and publication payment";
    public static final String CORRECTION_AR = "بانتظار الرد على تقرير الفحص";
    public static final String CORRECTION_EN = "Invitation For Formal Correction";
    private final BPMCallerService bpmCallerService;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final ApplicationInfoService applicationServices;
    private final AppealRequestService appealRequestService;

    @Override
    public String completeTaskToUser(String taskId, CompleteTaskRequestDto dto) {
        return bpmCallerService.completeTaskToUser(taskId, dto);
    }

    @Override
    public TaskHistoryUIDto lastAction(Integer page, Integer limit, Long applicationId, String processInstanceId, String requestId, Long serviceId) {
        List<TaskHistoryUIDto> allTasks = taskHistory(page, limit,applicationId ,processInstanceId , requestId, serviceId).getContent();
        List<TaskHistoryUIDto> filteredTasks = allTasks.stream().filter(task -> !tasksToBeIgnoredFromCamundaHistory.contains(task.getTaskDefinitionKey())).collect(Collectors.toList());
        return filteredTasks.get(0);
    }



    @Override
    public PaginationDto<List<TaskHistoryUIDto>> taskHistory(Integer page, Integer limit, Long applicationId,String processInstanceId, String requestId, Long serviceId) {
        PaginationDto<List<TaskHistoryUIDto>> listPaginationDto = bpmCallerService.taskHistory(page, limit, applicationId, processInstanceId, requestId, serviceId);
        if (listPaginationDto.getContent() == null || listPaginationDto.getContent().isEmpty()) {
            return listPaginationDto;
        }

        List<TaskHistoryUIDto> taskHistoryUIDtos =  listPaginationDto.getContent();

        taskHistoryUIDtos.stream().
                filter(taskHistoryUIDto -> isInternalTask(taskHistoryUIDto))
                .forEach(taskHistoryUIDto -> {
                            taskHistoryUIDto.setNameAr(null);
                            taskHistoryUIDto.setNameEn(null);
                            taskHistoryUIDto.setAssignee(null);
                            nullifyKey(taskHistoryUIDto.getAdditionalData(), EXAMINER_REPORT);
                            if(isGrantTask(taskHistoryUIDto)){
                                taskHistoryUIDto.setShowReportButton(false);
                            }
                            updateAppealTaskWithComments(taskHistoryUIDto, serviceId);
                        }
                );
        taskHistoryUIDtos = filterInternalTasks(taskHistoryUIDtos);
        changeTaskNameAr(taskHistoryUIDtos);
        return  getPaginationDto(page, limit, removeTaskDuplicates(taskHistoryUIDtos));
    }

    private void updateAppealTaskWithComments(TaskHistoryUIDto taskHistoryUIDto, Long serviceId) {
        Optional<AppealRequest> appealRequest = appealRequestService.getById(serviceId);
        AppealDetailsDto appealDetailsDto = null;

        if (appealRequest.isPresent()) {
            appealDetailsDto = appealRequestService.getTradeMarkAppealDetails(serviceId);
        }

        if (appealDetailsDto == null) {
            return;
        }

        String taskDefinitionKey = taskHistoryUIDto.getTaskDefinitionKey();
        String comment = null;

        if ("SAIP_SPECICIALIT_DECISION_APPEAL".equalsIgnoreCase(taskDefinitionKey)) {
            comment = appealDetailsDto.getAppealCommitteeDecisionComment();
        } else if ("APPEAL_REVIEW_REQUEST".equalsIgnoreCase(taskDefinitionKey)) {
            comment = appealDetailsDto.getCheckerFinalNotes();
        }

        if (comment != null) {
            taskHistoryUIDto.setComments(comment);
        }
    }

    private static boolean isGrantTask(TaskHistoryUIDto taskHistoryUIDto) {
        return taskHistoryUIDto.getTaskDefinitionKey().equals(SEND_OFFICE_ACTION_SUB_TM) && taskHistoryUIDto.getAction().equals(GRANT);
    }

    private void nullifyKey(Map<String,Object> additionalData, String key) {
        if (additionalData != null && additionalData.containsKey(key)) {
            additionalData.put(key, null);
        }
    }

    private static boolean isInternalTask(TaskHistoryUIDto taskHistoryUIDto) {
        return taskHistoryUIDto.getAssignee() != null && taskHistoryUIDto.getCreatedByCustomerCode() == null && assigneeContainsCharsAndStartWithSap(taskHistoryUIDto);
    }

    private static boolean assigneeContainsCharsAndStartWithSap(TaskHistoryUIDto taskHistoryUIDto) {
        return !taskHistoryUIDto.getAssignee().chars().allMatch(Character::isDigit) && !taskHistoryUIDto.getAssignee().startsWith("sap");
    }
    private List<TaskHistoryUIDto> filterInternalTasks(List<TaskHistoryUIDto> completeDto) {
        List<TaskHistoryUIDto> filteredList = new ArrayList<>();
        for (TaskHistoryUIDto dto : completeDto) {
            if (isReturnedToApplicant(dto) || !isDefinedIn(dto.getTaskDefinitionKey(), Constants.applicationInternalTasksPrefix)) {
                filteredList.add(dto);
            }
        }
        return filteredList;
    }
    private String startsWithOneOfListElements(String taskDefinitionKey, List<String> stringList){
        for(String internalTask : stringList){
            if(taskDefinitionKey.startsWith(internalTask)){
                return internalTask;
            }
        }
        return null;
    }
    private void changeTaskNameAr(List<TaskHistoryUIDto> taskHistoryUIDtos) {
        List<String> keyList = Constants.mapApplicationTasksNames.keySet().stream().toList();
        for (TaskHistoryUIDto dto : taskHistoryUIDtos) {
            String taskPrefix = startsWithOneOfListElements(dto.getTaskDefinitionKey(), keyList);
            if (Objects.nonNull(taskPrefix)) {     // check if the task needs to change its name
                String replacement = Constants.mapApplicationTasksNames.get(taskPrefix);
                dto.setTaskNameAr(replacement);
            }
            // update task name of cancellation non-renewal from internal name to external name
            if (RequestActivityLogEnum.RENEWAL_APPLICATION_DESERTION.name().equals(dto.getTaskDefinitionKey())) {
                dto.setTaskNameAr(dto.getPreviousStatus().getNameAr());
                dto.setTaskNameEn(dto.getPreviousStatus().getNameEn());
            }
        }
    }
    public List<TaskHistoryUIDto> removeTaskDuplicates(List<TaskHistoryUIDto> taskHistoryUIDtos){
        List<TaskHistoryUIDto> result = new ArrayList<>();
        for(int taskIndex =0 ; taskIndex< taskHistoryUIDtos.size(); taskIndex++)
        {
            String taskDefinitionKey = taskHistoryUIDtos.get(taskIndex).getTaskDefinitionKey();
            if(hasUnderProcedureStatus(taskHistoryUIDtos, taskIndex, taskDefinitionKey)){
                wrapTaskInfo(taskHistoryUIDtos, taskIndex);
                result.add(taskHistoryUIDtos.get(taskIndex));
                taskIndex = removeNextDuplicates(taskHistoryUIDtos, taskIndex, taskDefinitionKey);
            }
            if(taskIndex<taskHistoryUIDtos.size())
            result.add(taskHistoryUIDtos.get(taskIndex));
        }
        return result;
    }

    private boolean hasUnderProcedureStatus(List<TaskHistoryUIDto> taskHistoryUIDtos, int taskIndex, String taskDefinitionKey) {
        return isDefinedIn(taskDefinitionKey, Constants.applicationTasksHasUnderProcedureStatus) && !isReturnedToApplicant(taskHistoryUIDtos.get(taskIndex));
    }

    private int removeNextDuplicates(List<TaskHistoryUIDto> taskHistoryUIDtos, int taskIndex, String taskDefinitionKey) {
        taskIndex++;

        while(taskIndex < taskHistoryUIDtos.size() && isIgnored(taskHistoryUIDtos, taskIndex, taskDefinitionKey)){
            taskIndex++;
        }
        return taskIndex;
    }

    private boolean isIgnored(List<TaskHistoryUIDto> taskHistoryUIDtos, int taskIndex, String taskDefinitionKey) {
        String taskDefinitionKeyToBeTested = taskHistoryUIDtos.get(taskIndex).getTaskDefinitionKey();
        return (isRepeatedTask(taskHistoryUIDtos, taskIndex, taskDefinitionKey) || hasUnderProcedureStatus(taskHistoryUIDtos, taskIndex, taskDefinitionKeyToBeTested)) && !isDefinedIn(taskDefinitionKeyToBeTested, Constants.taskDefinitionKeysOfOurActivityLogs);
    }
    private boolean isDefinedIn(String taskDefinitionKey, List<String> definitions) {
        return Objects.nonNull(startsWithOneOfListElements(taskDefinitionKey, definitions));
    }

    private boolean isRepeatedTask(List<TaskHistoryUIDto> taskHistoryUIDtos, int taskIndex, String taskDefinitionKey) {
        return taskIndex != taskHistoryUIDtos.size() && !isReturnedToApplicant(taskHistoryUIDtos.get(taskIndex)) && hasPreviousTaskDefinitionKey(taskHistoryUIDtos, taskIndex, taskDefinitionKey);
    }

    private static boolean hasPreviousTaskDefinitionKey(List<TaskHistoryUIDto> taskHistoryUIDtos, int taskIndex, String taskDefinitionKey) {
        return taskHistoryUIDtos.get(taskIndex).getTaskDefinitionKey().equals(taskDefinitionKey);
    }

    private boolean isReturnedToApplicant(TaskHistoryUIDto taskHistoryUIDto){
        List<String> keyList = Constants.mapApplicationTasksToItsSendBackActions.keySet().stream().toList();
        String taskPrefix = startsWithOneOfListElements(taskHistoryUIDto.getTaskDefinitionKey(), keyList);
        if(Objects.isNull(taskHistoryUIDto.getAction())||Objects.isNull(taskPrefix)) return false;
        List<String> actions = List.of(Constants.mapApplicationTasksToItsSendBackActions.get(taskPrefix).split(","));
        List<String> iCTasks = List.of("HEAD_OF_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC",
                "SECOND_HEAD_OF_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC", "THIRD_HEAD_OF_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC", "APPROVE_OBJECTION");
        if(actions.contains(taskHistoryUIDto.getAction())){
            if (iCTasks.contains(taskPrefix)) {
                switch (taskHistoryUIDto.getAction()) {
                    case "YES" -> {
                        taskHistoryUIDto.setTaskNameAr(ACCEPTED_AR);
                        taskHistoryUIDto.setTaskNameEn(ACCEPTED_EN);
                    }
                    case "NO" -> handleNoCase(taskHistoryUIDto);
                    case "UPDATE" -> {
                        taskHistoryUIDto.setTaskNameAr(CORRECTION_AR);
                        taskHistoryUIDto.setTaskNameEn(CORRECTION_EN);
                    }
                }
            } else {
                taskHistoryUIDto.setTaskNameAr(RETURNED_TO_APPLICANT_AR);
                taskHistoryUIDto.setTaskNameEn(RETURNED_TO_APPLICANT_EN);
            }
            return true;
        }
        return false;
    }

    private void handleNoCase(TaskHistoryUIDto taskHistoryUIDto) {
        if (taskHistoryUIDto.getTaskDefinitionKey().equalsIgnoreCase(APPROVE_OBJECTION_PAT)) {
            taskHistoryUIDto.setTaskNameAr(APPROVE_OBJECTION_AR);
            taskHistoryUIDto.setTaskNameEn(APPROVE_OBJECTION_EN);
        } else {
            taskHistoryUIDto.setTaskNameAr(REJECTED_AR);
            taskHistoryUIDto.setTaskNameEn(REJECTED_EN);
        }
    }

    private static void wrapTaskInfo(List<TaskHistoryUIDto> taskHistoryUIDtos, int i) {
        taskHistoryUIDtos.get(i).setTaskNameAr(NAME_AR);
        taskHistoryUIDtos.get(i).setTaskNameEn(NAME_EN);
        taskHistoryUIDtos.get(i).setAction(null);
        taskHistoryUIDtos.get(i).setComments(null);
        taskHistoryUIDtos.get(i).setNotes(null);
    }


    private PaginationDto<List<TaskHistoryUIDto>> getPaginationDto(Integer page, Integer limit, List<TaskHistoryUIDto> result) {
        int totalElements = result.size();
        int totalPages = (int) Math.ceil((double) totalElements / limit);

        int startIndex = page * limit;
        int endIndex = Math.min(startIndex + limit, totalElements);

        List<TaskHistoryUIDto> paginatedResult = result.subList(startIndex, endIndex);

        return new PaginationDto<>(paginatedResult, totalPages, (long) totalElements);
    }



    @Override
    public Integer remainingCountOfCallForCorrectionInvitation(Long requestId, String callForCorrectionInvitationType) {
        return bpmCallerService.remainingCountOfCallForCorrectionInvitation(requestId , callForCorrectionInvitationType);
    }



}
