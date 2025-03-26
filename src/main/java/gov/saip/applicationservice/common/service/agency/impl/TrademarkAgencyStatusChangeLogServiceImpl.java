package gov.saip.applicationservice.common.service.agency.impl;


import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyStatusChangeLog;
import gov.saip.applicationservice.common.repository.agency.TrademarkAgencyStatusChangeLogRepository;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TrademarkAgencyStatusChangeLogServiceImpl  implements TrademarkAgencyChangeLogService {

    private final TrademarkAgencyStatusChangeLogRepository trademarkAgencyStatusChangeLogRepository;

    @Override
    public TrademarkAgencyStatusChangeLog insert(TrademarkAgencyStatusChangeLog entity) {
        return trademarkAgencyStatusChangeLogRepository.save(entity);
    }

    @Override
    public List<TrademarkAgencyStatusChangeLog> getByTrademarkAgencyId(Long appId) {
        return trademarkAgencyStatusChangeLogRepository.findByTrademarkAgencyRequestId(appId);
    }

}
