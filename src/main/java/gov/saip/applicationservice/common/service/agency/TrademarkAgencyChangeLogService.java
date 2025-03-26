package gov.saip.applicationservice.common.service.agency;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyStatusChangeLog;

import java.util.List;

public interface TrademarkAgencyChangeLogService {
    List<TrademarkAgencyStatusChangeLog> getByTrademarkAgencyId(Long id);

    TrademarkAgencyStatusChangeLog insert(TrademarkAgencyStatusChangeLog entity);
}
