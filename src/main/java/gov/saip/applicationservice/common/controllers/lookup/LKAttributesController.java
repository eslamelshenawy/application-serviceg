package gov.saip.applicationservice.common.controllers.lookup;


import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.LkAttributeDto;
import gov.saip.applicationservice.common.model.LkAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"kc/attributes" , "internal-calling/attributes"})
public class LKAttributesController extends BaseLkpController <LkAttribute , LkAttributeDto , Integer> {
}
