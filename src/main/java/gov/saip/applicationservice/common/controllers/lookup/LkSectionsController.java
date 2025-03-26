package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.LkSectionDto;
import gov.saip.applicationservice.common.model.LkSection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/sections", "/internal-calling/sections"})

public class LkSectionsController extends BaseLkpController<LkSection, LkSectionDto, Integer> {

}
