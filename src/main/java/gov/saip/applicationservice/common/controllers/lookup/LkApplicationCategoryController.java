package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryLightDto;
import gov.saip.applicationservice.common.mapper.LkApplicationCategoryMapper;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/application-category", "/internal-calling/application-category"})
@RequiredArgsConstructor
@Slf4j
public class LkApplicationCategoryController extends BaseController<LkApplicationCategory, LKApplicationCategoryDto, Long> {

    private final LkApplicationCategoryService lkApplicationCategoryService;
    private final LkApplicationCategoryMapper lkApplicationCategoryMapper;

    @Override
    protected BaseService<LkApplicationCategory, Long> getService() {
        return  lkApplicationCategoryService;
    }

    @Override
    protected BaseMapper<LkApplicationCategory, LKApplicationCategoryDto> getMapper() {
        return lkApplicationCategoryMapper;
    }
    @Override
    @GetMapping("")
    public ApiResponse<List<LKApplicationCategoryDto>> findAll() {
        return ApiResponse.ok(lkApplicationCategoryService.findAllActiveCategories());
    }
    
    @GetMapping("/categories-by-codes")
    public ApiResponse<List<LKApplicationCategoryLightDto>> getCategoriesByCodes(@RequestParam(name = "codes") List<String> codes) {
        return ApiResponse.ok(lkApplicationCategoryService.getCategoriesByCodes(codes));
    }

    @GetMapping("/saip-code")
    public ApiResponse<LKApplicationCategoryLightDto> findBySaipCode(@RequestParam(name = "saipCode") String saipCode) {
        return ApiResponse.ok(lkApplicationCategoryMapper.mapEntityToLightDto(
                lkApplicationCategoryService.findBySaipCode(saipCode))
        );
    }

}
