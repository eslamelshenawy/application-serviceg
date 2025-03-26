package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationServicesDto;
import gov.saip.applicationservice.common.mapper.lookup.LkApplicationServiceMapper;
import gov.saip.applicationservice.common.model.LkApplicationService;
import gov.saip.applicationservice.common.service.lookup.LKApplicationServiceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/application-service", "/internal-calling/application-service"})
@RequiredArgsConstructor
@Slf4j
@Getter
public class LkApplicationServiceController extends BaseController<LkApplicationService, LkApplicationServicesDto, Long> {

    private final LKApplicationServiceService service;
    private final LkApplicationServiceMapper mapper;
}
