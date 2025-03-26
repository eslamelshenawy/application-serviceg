package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.RevokeByCourtOrderInternalDto;
import gov.saip.applicationservice.common.dto.RevokeByCourtOrderRequestDto;
import gov.saip.applicationservice.common.model.RevokeByCourtOrder;

public interface RevokeByCourtOrderService extends SupportServiceRequestService<RevokeByCourtOrder> {

    void updateRevokeByCourtOrderRequestWithInternalData(RevokeByCourtOrderInternalDto revokeByCourtOrderInternalDto);
    RevokeByCourtOrderRequestDto findByServiceId(Long id);
}
