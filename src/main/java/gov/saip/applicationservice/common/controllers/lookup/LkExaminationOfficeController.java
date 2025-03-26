package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.lookup.LkExaminationOfficeDto;
import gov.saip.applicationservice.common.model.LkExaminationOffice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/examination-office", "/internal-calling/examination-office"})
@RequiredArgsConstructor
@Slf4j
public class LkExaminationOfficeController extends BaseLkpController<LkExaminationOffice, LkExaminationOfficeDto, Integer> {

}
