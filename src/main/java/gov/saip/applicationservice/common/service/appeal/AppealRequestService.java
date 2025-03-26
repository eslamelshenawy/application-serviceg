package gov.saip.applicationservice.common.service.appeal;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.appeal.AppealDetailsDto;
import gov.saip.applicationservice.common.dto.appeal.AppealRequestDto;
import gov.saip.applicationservice.common.dto.appeal.AppealSupportServiceRequestDto;
import gov.saip.applicationservice.common.model.appeal.AppealRequest;
import gov.saip.applicationservice.common.service.SupportServiceRequestService;

public interface AppealRequestService extends SupportServiceRequestService<AppealRequest> {

    Long addCheckerDecision(AppealRequestDto dto);

    Long addOfficialLetter(AppealRequestDto dto);

    Long addAppealCommitteeDecision(AppealRequestDto dto);


    //List<ApplicationInfo> getApplicationByAppealId(Long id);
    AppealDetailsDto getTradeMarkAppealDetails(Long appealId);
    Long updateAppealRequest(AppealRequestDto appealRequestDto , String taskId);
    void processAppealRequestPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id);
}
