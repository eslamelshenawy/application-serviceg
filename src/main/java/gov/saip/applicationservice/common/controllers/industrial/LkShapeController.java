package gov.saip.applicationservice.common.controllers.industrial;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.industrial.LkShapeDto;
import gov.saip.applicationservice.common.model.industrial.LkShapeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( {"/se/v1/shape","/internal-calling/shape"})
@RequiredArgsConstructor
@Slf4j
public class LkShapeController extends BaseLkpController<LkShapeType, LkShapeDto, Long> {

}
