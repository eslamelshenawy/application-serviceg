package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.common.dto.LkCertificateTypeDto;
import gov.saip.applicationservice.common.model.LkCertificateType;

import java.util.List;

public interface LkCertificateTypeService extends BaseLkService<LkCertificateType, Integer> {

    List<LkCertificateTypeDto> findCertificateTypesByCategoryId(Long categoryId);
}
