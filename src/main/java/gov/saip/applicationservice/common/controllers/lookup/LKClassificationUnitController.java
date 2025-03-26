package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.mapper.LkClassificationUnitMapper;
import gov.saip.applicationservice.common.model.LkClassificationUnit;
import gov.saip.applicationservice.common.service.lookup.LkClassificationUnitService;
import lombok.RequiredArgsConstructor;import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController()
@RequestMapping(value = {"kc/classification-unit", "/internal-calling/classification-unit"})
@RequiredArgsConstructor
public class LKClassificationUnitController extends BaseController<LkClassificationUnit, LkClassificationUnitDto, Long> {

    private final LkClassificationUnitService lkClassificationUnitService;
    private final LkClassificationUnitMapper lkClassificationUnitMapper;


    protected BaseService<LkClassificationUnit, Long> getService() {
        return  lkClassificationUnitService;
    }

    @Override
    protected BaseMapper<LkClassificationUnit, LkClassificationUnitDto> getMapper() {
        return lkClassificationUnitMapper;
    }

    @GetMapping("/all")
    public ApiResponse<List<ClassificationUnitLightDto>> getAllClassificationUnits() {
        return ApiResponse.ok(lkClassificationUnitService.getAllClassificationUnits());
    }

    @GetMapping("/category")
    public ApiResponse<List<LkClassificationUnitDto>>getCategoryUnits(@RequestParam List<String> categories){
        return ApiResponse.ok(lkClassificationUnitService.getCategoryUnits(categories));
    }

    @GetMapping("/category/page")
    public ApiResponse<PaginationDto> getAllPaging(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "10") int limit,
                                             @RequestParam(required = false , value = "search") String search,
                                             @RequestParam(required = false , value = "categoryId")Long categoryId,
                                             @RequestParam(required = false , value = "versionId")Integer versionId){
        return ApiResponse.ok(lkClassificationUnitService.filter(page, limit, search , categoryId , versionId));
    }

    @GetMapping("/category-locarno/page")
    public ApiResponse<PaginationDto> getIndustrialCategoryWithLastLocarnoVersion(@RequestParam(required = false, defaultValue = "0") int page,
                                                   @RequestParam(required = false, defaultValue = "10") int limit,
                                                   @RequestParam(required = false , value = "search") String search,
                                                   @RequestParam(required = false , value = "categoryId")Long categoryId){
        return ApiResponse.ok(lkClassificationUnitService.getIndustrialCategoryWithLastLocarnoVersion(page, limit, search , categoryId ));
    }

    @GetMapping("/version/{versionId}")
    public ApiResponse<List<LkClassificationUnitDto>> findByVersion(@PathVariable("versionId") Integer versionId){
        return ApiResponse.ok(lkClassificationUnitService.findByVersion(versionId));
    }




    @Override
    @PostMapping("")
    public ApiResponse<Long> insert(@RequestBody LkClassificationUnitDto dto){
        return ApiResponse.ok(lkClassificationUnitService.insert(dto));
    }


    @Override
    @PutMapping("")
    public ApiResponse<Long> update(@RequestBody LkClassificationUnitDto dto) {
        return ApiResponse.ok(lkClassificationUnitService.update(dto));
    }





    @GetMapping("/with-classification-ids/page")
    public ApiResponse<PaginationDto> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                                       @RequestParam(required = false, defaultValue = "10") int limit){
        return ApiResponse.ok(lkClassificationUnitService.getAllClassificationUnitsWithClassificationIds(page , limit));
    }

    @DeleteMapping("/{id}/soft-deleted")
    public ApiResponse<?> softDeleteById(@PathVariable Long id) {
        lkClassificationUnitService.softDeleteById(id);
        return ApiResponse.ok("SUCCESS");
    }


}
