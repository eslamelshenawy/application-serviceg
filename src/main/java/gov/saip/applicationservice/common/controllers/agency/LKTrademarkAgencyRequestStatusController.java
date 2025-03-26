package gov.saip.applicationservice.common.controllers.agency;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.agency.LKTrademarkAgencyRequestStatusDto;
import gov.saip.applicationservice.common.model.agency.LKTrademarkAgencyRequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = {"/kc/lk-agency-status", "/internal-calling/lk-agency-status"})
@RequiredArgsConstructor
public class LKTrademarkAgencyRequestStatusController extends BaseLkpController<LKTrademarkAgencyRequestStatus, LKTrademarkAgencyRequestStatusDto, Integer> {




}
