package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.common.dto.LKDocumentTypeDto;
import gov.saip.applicationservice.common.mapper.lookup.LkDocumentTypeMapper;
import gov.saip.applicationservice.common.model.LkDocumentType;
import gov.saip.applicationservice.common.service.lookup.LKDocumentTypeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/document-type", "/internal-calling/document-type"})
@RequiredArgsConstructor
@Slf4j
@Getter
public class LkDocumentTypeController extends BaseController<LkDocumentType, LKDocumentTypeDto, Long> {

    private final LKDocumentTypeService service;
    private final LkDocumentTypeMapper mapper;
}
