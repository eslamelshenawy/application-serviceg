package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.model.ApplicationPriority;

import java.util.List;

public interface ApplicationPriorityService {

    int hardDeleteByApplicationInfoId(Long applicationInfoId);

    Long createUpdateApplicationPriority(ApplicationPriorityDto dto, Long applicationInfoId);

    Long deleteApplicationPriorityFile(Long id, String fileKey);
    void setExpiredAndSendNotifiction();
    Long deleteApplicationPriority(Long id);


    Long updatePriorityDocs(Long id, ApplicationPriorityDocsDto applicationPriorityDocsDto);
    Long updatePriorityDocsAndCompleteTask(Long id, ApplicationPriorityDocsDto applicationPriorityDocsDto);

    Long getPriorityDocumentsAllowanceDays();

    void setAboutExpiredAndSendNotifiction();

    List<Long> createUpdateApplicationPriority(ApplicationPriorityBulkDto applicationPriorityBulkDto, Long applicationInfoId);
    List <ApplicationPriorityListDto> listApplicationPriorites (Long applicationId);
    Long updatePriorityStatusAndComment(Long id, ApplicationPriorityUpdateStatusAndCommentsDto updateStatuscommentDto);

    ApplicationPriority findById(Long id);

    void softDeleteByAppId(Long applicationInfoId);
    
    List<ApplicationPriorityLightResponseDto> setPrioritiesDetailsIfValid(ApplicationInfoDto applicationInfoDto);
    public Boolean doesApplicationHasPrioritiesThatHaveNoActionTaken(Long applicationId);

    Boolean checkApplicationPrioritiesProvideDocLater(Long appId);
    Boolean checkApplicationPriorities(Long appId);
    Boolean checkApplicationPrioritiesDocuments(Long appId);

    Long createUpdateApplicationPriorityWithComplete(ApplicationPriorityDto dto, Long applicationInfoId);

    List<ApplicationPriorityListDto> getPrioritiesThatHaveNotPriorityDocument(Long appId);

    ApplicationPriorityListDto getApplicationPriorityById(Long priorityId);


    void completeApplicantPriorityDocument(Long applicationId , String categoryCode);
}
