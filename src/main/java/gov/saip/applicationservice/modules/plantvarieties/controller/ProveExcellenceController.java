package gov.saip.applicationservice.modules.plantvarieties.controller;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.PlantDetailsTestingDifferenceExcellenceDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceLightDto;
import gov.saip.applicationservice.modules.plantvarieties.mapper.ProveExcellenceMapper;
import gov.saip.applicationservice.modules.plantvarieties.service.ProveExcellenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/plant-variety-details/prove-excellence", "/internal-calling/plant-variety-details/prove-excellence"})
@RequiredArgsConstructor
@Slf4j
public class ProveExcellenceController {

    private final ProveExcellenceService proveExcellenceService;

    @PostMapping
    public ApiResponse<Long> save(@RequestBody ProveExcellenceDto dto) {
        return ApiResponse.ok(proveExcellenceService.saveProveExcellence(dto));
    }
    @PutMapping("/update")
    public ApiResponse<Long> update(@RequestBody ProveExcellenceDto proveExcellenceDto) {
        return ApiResponse.ok(proveExcellenceService.updateProveExcellenceWithApplication(proveExcellenceDto));
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Long> softDeleteProveExcellenceById(@PathVariable Long id) {
        return ApiResponse.ok(proveExcellenceService.softDeleteProveExcellenceById(id));
    }
    @GetMapping("/application/{applicationId}")
    public ApiResponse<List<ProveExcellenceLightDto>> findApplicationProveExcellenceByApplicationId(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(proveExcellenceService.findApplicationProveExcellenceByApplicationId(applicationId));
    }
    @GetMapping("/page/{appId}")
    public ApiResponse<PaginationDto<List<ProveExcellenceLightDto>>> listApplications(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @PathVariable(value = "appId") Long appId,
            @RequestParam(value = "lkVegetarianTypesId") Long lkVegetarianTypesId,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        PaginationDto<List<ProveExcellenceLightDto>> applicationListByApplicationCategoryAndUserId =
                proveExcellenceService.listProveExcellenceApplication(page, limit,appId,lkVegetarianTypesId, sortDirection);
        return ApiResponse.ok(applicationListByApplicationCategoryAndUserId);
    }
    @GetMapping("/plant-details/{applicationId}")
    public ApiResponse<PlantDetailsTestingDifferenceExcellenceDto> findProveExcellenceByPlantDetailsId(@PathVariable("applicationId") Long applicationId,
                                                                                                       @RequestParam(value = "lkVegetarianTypesId") Long lkVegetarianTypesId) {
        return ApiResponse.ok(proveExcellenceService.findProveExcellenceByPlantDetailsId(applicationId,lkVegetarianTypesId));
    }

}
