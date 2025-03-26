package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.ChangeOwnershipRequestDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestDto;
import gov.saip.applicationservice.common.model.ChangeOwnershipRequest;

public interface ChangeOwnershipRequestService extends SupportServiceRequestService<ChangeOwnershipRequest> {
    ChangeOwnershipRequestDto getChangeOwnershipRequestByApplicationSupportServiceId(Long applicationSupportServicesId);

    void processApprovedChangeOwnershipRequest(Long id);

    TrademarkAgencyRequestDto getTrademarkAgencyRequestByRequestNumber(String requestNumber, Long applicationId);
}
