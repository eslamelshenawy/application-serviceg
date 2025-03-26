package gov.saip.applicationservice.common.controllers.trademark.lookup;


import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.trademark.LkMarkTypeDto;
import gov.saip.applicationservice.common.model.trademark.LkMarkType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping({"kc/mark-type", "/internal-calling/mark-type"})
@RequiredArgsConstructor
public class LkMarkTypeController extends BaseLkpController<LkMarkType, LkMarkTypeDto, Integer> {

}
