package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.common.dto.lookup.LKNexuoUserDto;
import gov.saip.applicationservice.common.mapper.lookup.LkNexuoUserMapper;
import gov.saip.applicationservice.common.model.LkNexuoUser;
import gov.saip.applicationservice.common.service.lookup.LKNexuoUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/nexuo-user", "/internal-calling/nexuo-user"})
@RequiredArgsConstructor
@Getter
public class LkNexuoUserController extends BaseController<LkNexuoUser, LKNexuoUserDto, Long> {

    private final LKNexuoUserService service;
    private final LkNexuoUserMapper mapper;

}
