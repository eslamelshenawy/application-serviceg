package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.supportService.license.LkLicencePurposeDto;
import gov.saip.applicationservice.common.model.supportService.LkLicencePurpose;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/kc/licence-purpose", "/internal-calling/licence-purpose"})
@Slf4j
@RequiredArgsConstructor
public class LkLicencePurposeController extends BaseLkpController<LkLicencePurpose, LkLicencePurposeDto, Long> {
}
