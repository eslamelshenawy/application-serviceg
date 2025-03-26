package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.lookup.LkStepsDto;
import gov.saip.applicationservice.common.model.LkStep;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/step", "/internal-calling/step"})

public class LkStepController extends BaseLkpController<LkStep, LkStepsDto, Integer> {

}
