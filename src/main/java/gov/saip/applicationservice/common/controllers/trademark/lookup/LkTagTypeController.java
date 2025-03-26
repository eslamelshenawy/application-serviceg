package gov.saip.applicationservice.common.controllers.trademark.lookup;


import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.trademark.LkTagTypeDescDto;
import gov.saip.applicationservice.common.model.trademark.LkTagTypeDesc;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping({"kc/tag-type", "/internal-calling/tag-type"})
@RequiredArgsConstructor
public class LkTagTypeController extends BaseLkpController<LkTagTypeDesc, LkTagTypeDescDto, Integer> {

}
