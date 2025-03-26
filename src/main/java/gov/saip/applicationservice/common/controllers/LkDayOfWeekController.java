package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.LKSupportServicesDto;
import gov.saip.applicationservice.common.dto.lookup.LkDayOfWeekDto;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.model.LkDayOfWeek;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/day-of-week", "/internal-calling/day-of-week"})
@RequiredArgsConstructor
@Slf4j
public class LkDayOfWeekController extends BaseLkpController<LkDayOfWeek, LkDayOfWeekDto, Integer> {

}
