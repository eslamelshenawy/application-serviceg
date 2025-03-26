package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.lookup.LkAcceleratedTypeDto;
import gov.saip.applicationservice.common.mapper.LkAcceleratedTypeMapper;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import gov.saip.applicationservice.common.service.lookup.LkAcceleratedTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/accelerated-types", "/internal-calling/accelerated-types"})
@Slf4j
@RequiredArgsConstructor

public class LkAcceleratedTypeController extends BaseController<LkAcceleratedType, LkAcceleratedTypeDto, Long> {
    private final LkAcceleratedTypeService acceleratedTypeService;
    private final LkAcceleratedTypeMapper lkAcceleratedTypeMapper;
    @Override
    protected BaseService<LkAcceleratedType, Long> getService() {
        return  acceleratedTypeService;
    }

    @Override
    protected BaseMapper<LkAcceleratedType, LkAcceleratedTypeDto> getMapper() {
        return lkAcceleratedTypeMapper;
    }
    public List<LkAcceleratedTypeDto> getAllAcceleratedTypes(@PathVariable String appCategoryDescEn) {
        return acceleratedTypeService.filterByApplicationCategory(appCategoryDescEn);
    }
}
