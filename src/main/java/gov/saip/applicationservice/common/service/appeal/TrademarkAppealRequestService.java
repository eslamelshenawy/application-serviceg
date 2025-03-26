package gov.saip.applicationservice.common.service.appeal;

import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.appeal.TrademarkAppealRequestDto;
import gov.saip.applicationservice.common.enums.support_services_enums.TrademarkAppealRequestType;
import gov.saip.applicationservice.common.model.appeal.TrademarkAppealRequest;
import gov.saip.applicationservice.common.service.SupportServiceRequestService;

public interface TrademarkAppealRequestService extends SupportServiceRequestService<TrademarkAppealRequest> {

    void updateDepartmentReply(Long id, String reply);
    void updateFinalDecision(Long id, String notes);

    TrademarkAppealRequestType getTrademarkAppealRequestType(Long applicationId);

    void handleApplicantTaskTimeout(Long id);
    TrademarkAppealRequestDto findDetailsByById(Long aLong);
    
    RequestTasksDto getOpenedAppealTaskInMainProcess(Long rowId, Long parentService);

}
