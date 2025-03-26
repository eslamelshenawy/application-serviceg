package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.lookup.LkResultDocumentTypeDto;
import gov.saip.applicationservice.common.model.LkResultDocumentType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/result-document-type", "/internal-calling/result-document-type"})

public class LkResultDocumentTypeController extends BaseLkpController<LkResultDocumentType, LkResultDocumentTypeDto, Integer> {


}
