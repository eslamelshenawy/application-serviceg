package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;

import java.util.List;

public interface LKSupportServiceRequestStatusService extends BaseLkService<LKSupportServiceRequestStatus, Integer> {
    Integer findIdByCode(SupportServiceRequestStatusEnum supportServiceRequestStatusEnum);

    List<LkSupportServiceRequestStatusDto> findAllSupportServiceRequestStatus();

    LKSupportServiceRequestStatus getStatusByCode(String code);

    LKSupportServiceRequestStatus getStatusByCodeAndNameEn(String code, String nameEn);


}
