package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.lookup.LkDatabaseDto;
import gov.saip.applicationservice.common.model.LkDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/database", "/internal-calling/database"})
@RequiredArgsConstructor
@Slf4j
public class LkDatabaseController extends BaseLkpController<LkDatabase, LkDatabaseDto, Integer> {

}
