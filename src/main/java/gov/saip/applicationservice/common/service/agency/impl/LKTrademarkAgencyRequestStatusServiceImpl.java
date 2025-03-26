package gov.saip.applicationservice.common.service.agency.impl;


import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.model.agency.LKTrademarkAgencyRequestStatus;
import gov.saip.applicationservice.common.repository.agency.LKTrademarkAgencyRequestStatusRepository;
import gov.saip.applicationservice.common.service.agency.LKTrademarkAgencyRequestStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@AllArgsConstructor
@Transactional
public class LKTrademarkAgencyRequestStatusServiceImpl extends BaseLkServiceImpl<LKTrademarkAgencyRequestStatus, Integer> implements LKTrademarkAgencyRequestStatusService {
    private final LKTrademarkAgencyRequestStatusRepository lkTrademarkAgencyRequestStatusRepository;

}
