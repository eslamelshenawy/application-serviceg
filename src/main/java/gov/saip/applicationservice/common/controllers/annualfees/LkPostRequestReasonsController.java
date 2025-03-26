package gov.saip.applicationservice.common.controllers.annualfees;


import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.LkAttributeDto;
import gov.saip.applicationservice.common.dto.annualfees.LkPostRequestReasonsDto;
import gov.saip.applicationservice.common.model.LkAttribute;
import gov.saip.applicationservice.common.model.annual_fees.LkPostRequestReasons;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"kc/annual-fees/post-reason" , "internal-calling/annual-fees/post-reason"})
public class LkPostRequestReasonsController extends BaseLkpController <LkPostRequestReasons, LkPostRequestReasonsDto, Long>  {
}
