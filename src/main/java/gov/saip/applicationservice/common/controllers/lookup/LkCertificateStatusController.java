package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.LkCertificateStatusDto;
import gov.saip.applicationservice.common.model.LkCertificateStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/certificate-status", "/internal-calling/certificate-status"})
@RequiredArgsConstructor
public class LkCertificateStatusController extends BaseLkpController<LkCertificateStatus, LkCertificateStatusDto, Integer> {

}