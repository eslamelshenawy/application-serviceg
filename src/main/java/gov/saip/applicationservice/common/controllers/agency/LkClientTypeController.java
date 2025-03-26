package gov.saip.applicationservice.common.controllers.agency;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.agency.LkClientTypeDto;
import gov.saip.applicationservice.common.mapper.agency.LkClientTypeMapper;
import gov.saip.applicationservice.common.model.agency.LkClientType;
import gov.saip.applicationservice.common.service.agency.LkClientTypeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/internal-calling/client-type","/kc/client-type"})
@Slf4j
@RequiredArgsConstructor
@Getter
public class LkClientTypeController extends BaseController<LkClientType, LkClientTypeDto,Integer> {
    private final LkClientTypeService service;
    private final LkClientTypeMapper mapper;
}
