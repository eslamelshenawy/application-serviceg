package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.supportService.license.LkLicenceTypeDto;
import gov.saip.applicationservice.common.model.supportService.LkLicenceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/kc/licenceType", "/internal-calling/licenceType"})
@Slf4j
@RequiredArgsConstructor
public class LkLicenceTypeController extends BaseLkpController<LkLicenceType, LkLicenceTypeDto, Long> {
}
