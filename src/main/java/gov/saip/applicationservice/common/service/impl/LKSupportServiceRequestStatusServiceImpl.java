package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.mapper.lookup.LkSupportServiceRequestStatusMapper;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.repository.LKSupportServiceRequestStatusRepository;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LKSupportServiceRequestStatusServiceImpl extends BaseLkServiceImpl<LKSupportServiceRequestStatus, Integer> implements LKSupportServiceRequestStatusService {

    private final LKSupportServiceRequestStatusRepository requestStatusRepository;
    private final LkSupportServiceRequestStatusMapper lkSupportServiceRequestStatusMapper;

    @Override
    public Integer findIdByCode(SupportServiceRequestStatusEnum supportServiceRequestStatusEnum) {
        return requestStatusRepository.findIdByCode(supportServiceRequestStatusEnum.name());
    }

    @Override
    public List<LkSupportServiceRequestStatusDto> findAllSupportServiceRequestStatus() {
        return lkSupportServiceRequestStatusMapper.map(requestStatusRepository.findAll());
    }

    @Override
    public LKSupportServiceRequestStatus getStatusByCode(String code) {
        return requestStatusRepository.getStatusByCode(code);
    }

    @Override
    public LKSupportServiceRequestStatus getStatusByCodeAndNameEn(String code, String nameEn) {
        return requestStatusRepository.getStatusByCodeAndNameEn(code, nameEn);
    }
}
