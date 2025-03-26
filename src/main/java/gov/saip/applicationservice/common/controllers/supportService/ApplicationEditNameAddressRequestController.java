package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.common.dto.supportService.ApplicationEditNameAddressRequestDto;
import gov.saip.applicationservice.common.mapper.supportService.ApplicationEditNameAddressRequestMapper;
import gov.saip.applicationservice.common.model.supportService.application_edit_name_address.ApplicationEditNameAddressRequest;
import gov.saip.applicationservice.common.service.supportService.ApplicationEditNameAddressRequestService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping({"/kc/support-service/application-edit-name-address", "/internal-calling/support-service/application-edit-name-address"})
@RequiredArgsConstructor
@Getter
public class ApplicationEditNameAddressRequestController extends BaseController<ApplicationEditNameAddressRequest, ApplicationEditNameAddressRequestDto, Long> {

    private final ApplicationEditNameAddressRequestService service;
    private final ApplicationEditNameAddressRequestMapper mapper;
}
