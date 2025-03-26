package gov.saip.applicationservice.common.service.supportService.impl;

import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.model.supportService.LkLicencePurpose;
import gov.saip.applicationservice.common.model.supportService.LkLicenceType;
import gov.saip.applicationservice.common.service.supportService.LkLicencePurposeService;
import gov.saip.applicationservice.common.service.supportService.LkLicenceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LkLicenceTypeServiceImpl extends BaseLkServiceImpl<LkLicenceType,Long> implements LkLicenceTypeService {
}
