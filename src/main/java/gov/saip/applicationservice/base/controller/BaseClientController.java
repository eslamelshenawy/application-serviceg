package gov.saip.applicationservice.base.controller;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.base.service.BaseClientService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;


public abstract class BaseClientController<DTO extends BaseDto<ID>, ID extends Serializable> {

	@Autowired
	private BaseClientService<DTO, ID> baseClientService;


	@GetMapping
	@Operation(summary = "Find All", description = "to retrieve list of all data")
	public ApiResponse<List<DTO>> findAll() {
		return ApiResponse.ok(baseClientService.findAll());
	}

	@GetMapping("/page")
	@Operation(summary = "Find All Pageable", description = "parameters is  1-pag int, optional, default value is 0"
			+ ", 2-limit int, optional, default value is 10 , 3-sortableColumn string, optional, default value is id")
	public ApiResponse<PaginationDto<List<DTO>>> findAllPaging(@RequestParam(value = "page", defaultValue = "1") Integer page,
														 @RequestParam(value = "limit", defaultValue = "10") Integer limit,
														 @RequestParam(required = false, defaultValue = "id") String sortableColumn) {
		return ApiResponse.ok(baseClientService.findAllPaging(page, limit, sortableColumn));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Find by id", description = "to retrieve element by id")
	public ApiResponse<DTO> findById(@PathVariable ID id) {
		return ApiResponse.ok(baseClientService.findById(id));
	}

	@PostMapping
	@Operation(summary = "Insert", description = "to adding one element by json object")
	public ApiResponse<ID> insert(@Valid @RequestBody DTO dto) {
		return ApiResponse.ok(baseClientService.insert(dto));
	}

	@PutMapping("")
	@Operation(summary = "Update", description = "to updating one element by json object")
	public ApiResponse<ID> update(@Valid @RequestBody DTO dto) {
		return ApiResponse.ok(baseClientService.update(dto));
	}

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete by id", description = "to delete element by id")
    public ApiResponse<?> deleteById(@PathVariable ID id) {
		baseClientService.deleteById(id);
        return ApiResponse.ok(null);
    }

}
