package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LkTaskEqmItemDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.mapper.LkTaskEqmItemMapper;
import gov.saip.applicationservice.common.model.LkTaskEqmItem;
import gov.saip.applicationservice.common.service.LkTaskEqmItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
    @RequestMapping(value = {"/kc/task-eqm-item", "/internal-calling/task-eqm-item"})
@RequiredArgsConstructor
@Slf4j
public class LkTaskEqmItemController extends BaseLkpController<LkTaskEqmItem, LkTaskEqmItemDto, Integer> {

    private final LkTaskEqmItemService taskEqmItemService;
    private final LkTaskEqmItemMapper taskEqmItemMapper;

    @GetMapping("/category")
    public ApiResponse<List<LkTaskEqmItemDto>> findByTypeCode(@RequestParam(value = "categoryCode", required = false) String saipCode) {
        List<LkTaskEqmItem> res = taskEqmItemService.findByTypeCode(saipCode);
        return ApiResponse.ok(taskEqmItemMapper.map(res));
    }

    @GetMapping("/code")
    public ApiResponse<LkTaskEqmItemDto> findByCode(@RequestParam(value = "code") String code) {
        LkTaskEqmItem res = taskEqmItemService.findByCode(code);
        return ApiResponse.ok(taskEqmItemMapper.map(res));
    }

    @PutMapping("/update")
    public ApiResponse<LkTaskEqmItemDto> updateDto(@RequestBody LkTaskEqmItemDto dto) {
        LkTaskEqmItem mappedEntity = taskEqmItemMapper.unMap(dto);
        LkTaskEqmItem res = taskEqmItemService.updateDto(mappedEntity);
        return ApiResponse.ok(taskEqmItemMapper.map(res));
    }

    @Override
    @PutMapping("")
    @Operation(summary = "Update", description = "to updating one element by json object")
    public ApiResponse<?> update(@Valid @RequestBody LkTaskEqmItemDto dto) {
        LkTaskEqmItem entity = taskEqmItemMapper.unMap(dto);
        LkTaskEqmItem result = taskEqmItemService.updateDto(entity);
        LkTaskEqmItemDto dtos = taskEqmItemMapper.map(taskEqmItemService.findById(result.getId()));
        return ApiResponse.ok(dtos.getId());
    }


    @GetMapping("/category/page")
    public ApiResponse<PaginationDto> getAllTaskEqmItemsWithSearch(@RequestParam(required = false, defaultValue = "0") int page,
                                                                   @RequestParam(required = false, defaultValue = "10") int limit,
                                                                   @RequestParam(value = "search" , required = false) String search){
        return ApiResponse.ok(taskEqmItemService.getAllPaginatedLKTaskEqm(search, page, limit));
    }

}
