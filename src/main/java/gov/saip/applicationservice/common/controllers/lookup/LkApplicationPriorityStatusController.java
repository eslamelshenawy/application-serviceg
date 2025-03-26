package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationPriorityStatusDto;
import gov.saip.applicationservice.common.mapper.lookup.LkApplicationPriorityStatusMapper;
import gov.saip.applicationservice.common.model.LkApplicationPriorityStatus;
import gov.saip.applicationservice.common.service.lookup.LkApplicationPriorityService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/application-priority-status", "/internal-calling/application-priority-status"})
@RequiredArgsConstructor
@Slf4j
@Getter
public class LkApplicationPriorityStatusController extends BaseController<LkApplicationPriorityStatus, LkApplicationPriorityStatusDto, Long> {

    private final LkApplicationPriorityStatusMapper mapper;
    private final LkApplicationPriorityService service;
}
