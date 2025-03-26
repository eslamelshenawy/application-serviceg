package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LkRegionsDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.mapper.LkRegionsMapper;
import gov.saip.applicationservice.common.model.LkRegions;
import gov.saip.applicationservice.common.service.lookup.LkRegionsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/kc/regions", "/internal-calling/regions"})
@Slf4j
@RequiredArgsConstructor
public class LkRegionsController extends BaseLkpController<LkRegions, LkRegionsDto, Long> {
}
