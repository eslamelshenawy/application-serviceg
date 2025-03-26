package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusDto;
import gov.saip.applicationservice.common.mapper.lookup.LkApplicationStatusMapper;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.service.lookup.LkApplicationStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static gov.saip.applicationservice.util.Constants.DEFAULT_PAGE_NUMBER;
import static gov.saip.applicationservice.util.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping(value = {"/kc/application-status", "/internal-calling/application-status"})
@RequiredArgsConstructor
@Slf4j
public class LkApplicationStatusController extends BaseController<LkApplicationStatus, LkApplicationStatusDto, Long> {

    private final LkApplicationStatusMapper lkApplicationStatusMapper;
    private final LkApplicationStatusService lkApplicationStatusService;
    @Override
    protected BaseService<LkApplicationStatus, Long> getService() {
        return  lkApplicationStatusService;
    }

    @Override
    protected BaseMapper<LkApplicationStatus, LkApplicationStatusDto> getMapper() {
        return lkApplicationStatusMapper;
    }

    @GetMapping("/category")
    public ApiResponse<List<LkApplicationStatusDto>> findAll(@RequestParam(name = "category", required = false) String category) {

        return ApiResponse.ok(lkApplicationStatusService.getStatusGrouped(category));
    }
    @GetMapping("/category/internal")
    public ApiResponse<List<LkApplicationStatusDto>> findAllStatusCategoryGroup(@RequestParam(name = "category", required = false) String category) {

        return ApiResponse.ok(lkApplicationStatusService.getStatusGroupedInternal(category));
    }

    @GetMapping("/category/page")
    public ApiResponse<PaginationDto> findAllApplicationStatusByAppCategory(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
                                                                            @RequestParam(value = "limit", defaultValue = DEFAULT_PAGE_SIZE) Integer limit,
                                                                            @RequestParam(required = false, defaultValue = "id") String sortableColumn,
                                                                            @RequestParam(value = "categoryId", required = false) Long applicationCategoryId,
                                                                            @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return ApiResponse.ok(lkApplicationStatusService.findAllApplicationStatusByAppCategory(page, limit, sortableColumn, applicationCategoryId, search));
    }

    @GetMapping("/category/by-code")
    public ApiResponse<LkApplicationStatusDto> findByCodeAndApplicationCategory(@RequestParam(name = "code", required = false) String code,
                                                                                @RequestParam(name = "categoryId", required = false) Long categoryId) {
        LkApplicationStatus statusByCategory = lkApplicationStatusService.findByCodeAndApplicationCategory(code, categoryId);
        LkApplicationStatusDto map = lkApplicationStatusMapper.map(statusByCategory);
        return ApiResponse.ok(map);
    }
    @GetMapping("/by-code")
    public ApiResponse<LkApplicationStatusDto> findByCode(@RequestParam(name = "code") String code){
        LkApplicationStatus statusByCategory = lkApplicationStatusService.getStatusByCode(code);
        return ApiResponse.ok(lkApplicationStatusMapper.map(statusByCategory));
    }

    @GetMapping("/by-code-appId")
    ApiResponse<LkApplicationStatusDto> findByCodeAndApplicationId(@RequestParam(name = "code") String code,
                                                                   @RequestParam(name = "appId") Long appId) {
        LkApplicationStatus statusByCategory = lkApplicationStatusService.getStatusByCodeAndApplicationId(code, appId);
        return ApiResponse.ok(lkApplicationStatusMapper.map(statusByCategory));
    }
}
