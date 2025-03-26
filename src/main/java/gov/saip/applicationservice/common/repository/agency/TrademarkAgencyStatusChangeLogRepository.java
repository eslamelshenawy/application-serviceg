package gov.saip.applicationservice.common.repository.agency;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyStatusChangeLog;
import gov.saip.applicationservice.common.model.supportService.SupportServiceStatusChangeLog;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TrademarkAgencyStatusChangeLogRepository extends BaseRepository<TrademarkAgencyStatusChangeLog, Long> {

    List<TrademarkAgencyStatusChangeLog> findByTrademarkAgencyRequestId(Long id);
}
