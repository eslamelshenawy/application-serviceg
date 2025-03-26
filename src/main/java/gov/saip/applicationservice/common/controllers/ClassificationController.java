package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.service.ClassificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController()
@RequestMapping(value = {"kc/classification", "/internal-calling/classification"})
@RequiredArgsConstructor
public class ClassificationController{

    private final ClassificationService classificationService;
    private final ClassificationMapper classificationMapper;

    @GetMapping("/category/page")
    public ApiResponse<PaginationDto> getClassifications(@RequestParam(required = false, defaultValue = "0") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int limit,
                                                         @RequestParam(required = false, value = "search") String search,
                                                         @RequestParam(required = false) Integer versionId,
                                                         @RequestParam(required = false) Long unitId,
                                                         @RequestParam(required = false) String saipCode,
                                                         @RequestParam(value = "categoryId", required = false) Long categoryId) {
        return ApiResponse.ok(classificationService.getAllClassifications(page, limit, search, versionId, saipCode, categoryId , unitId));
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse <List<ClassificationDto>> findByCategory (@PathVariable Long categoryId){
        return ApiResponse.ok(classificationService.findByCategoryId(categoryId));
    }
    @GetMapping("/category/units")
    public ApiResponse <List<ClassificationDto>> findByCategory (@RequestParam String categorySaipCode,@RequestParam Long unitId){
        return ApiResponse.ok(classificationService.findByUnitIdAndCategory(categorySaipCode,unitId));
    }

    @GetMapping("/category")
    public ApiResponse <List<ClassificationDto>> findByCategorySaipCode (@RequestParam(name = "category") String categorySaipCode){
        return ApiResponse.ok(classificationService.findBySaipCode(categorySaipCode));
    }

    @GetMapping("/all")
    public ApiResponse<List<ClassificationLightDto>> getAll(@RequestParam(name = "saipCode", required = false) String saipCode,
                                                            @RequestParam(name = "versionId", required = false) Integer versionId) {
        return ApiResponse.ok(classificationService.getAllClassifications(saipCode, versionId ));
    }

    @GetMapping("")
    @Operation(summary = "Find All Pageable", description = "parameters is  1-pag int, optional, default value is 0"
            + ", 2-limit int, optional, default value is 10 , 3-sortableColumn string, optional, default value is id")
    public ApiResponse<PaginationDto> findAllPaging(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                    @RequestParam(required = false, defaultValue = "id") String sortableColumn) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortableColumn));
        Page<ClassificationDto> pageDto = classificationService.findAll(pageable).map(t -> classificationMapper.map(t));
        return ApiResponse.ok(PaginationDto
                .builder()
                .content(pageDto.getContent())
                .totalElements(pageDto.getTotalElements())
                .totalPages(pageDto.getTotalPages())
                .build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find by id", description = "to retrieve element by id")
    public ApiResponse<ClassificationDto> findById(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(classificationMapper.map(classificationService.findById(id)));
    }

    @PostMapping("")
    @Operation(summary = "Insert", description = "to adding one element by json object")
    public ApiResponse<Long> insert(@Valid @RequestBody ClassificationDto dto) {
        return ApiResponse.ok(classificationService.addClassification(dto));
    }

    @PutMapping("")
    @Operation(summary = "Update", description = "to updating one element by json object")
    public ApiResponse<?> update(@Valid @RequestBody ClassificationDto dto) {
        return ApiResponse.ok(classificationService.updateClassification(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Find by id", description = "to retrieve element by id")
    public ApiResponse<?> deleteById(@PathVariable(name = "id") Long id) {
        classificationService.deleteById(id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/list-classification/{id}/application")
    public ApiResponse<List<ListApplicationClassificationDto>> listApplicationClassification(@PathVariable(value = "id") Long id
    ) {
        List<ListApplicationClassificationDto> result = classificationService.listApplicationClassification(id);
//        List<ListApplicationClassificationDto> listApplicationClassificationDtos = classificationMapper.mapClassificationToListApplicationClassificationDto(result);
        return ApiResponse.ok(result);
    }
    @GetMapping("/by-unit")
    public ApiResponse<List<ClassificationLightDto>> getAllByUnit(@RequestParam(required = false, value = "unitId") Long unitId) {
        return ApiResponse.ok(classificationService.getClassificationsByUnit(unitId));
    }

}
