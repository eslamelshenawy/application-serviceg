package gov.saip.applicationservice.base.controller;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static gov.saip.applicationservice.util.Constants.DEFAULT_PAGE_NUMBER;
import static gov.saip.applicationservice.util.Constants.DEFAULT_PAGE_SIZE;

@MappedSuperclass
public abstract class BaseController<T extends BaseEntity<ID>, DTO extends BaseDto<ID>, ID extends Number> {

	protected abstract BaseService<T, ID> getService();

	protected abstract BaseMapper<T, DTO> getMapper();

	@GetMapping("")
	@Operation(summary = "Find All", description = "to retrieve list of all data")
	public ApiResponse findAll() {
		List<DTO> dtos = getMapper().map(getService().findAll());
		return ApiResponse.ok(dtos);
	}

	@GetMapping("/page")
	@Operation(summary = "Find All Pageable", description = "parameters is  1-pag int, optional, default value is 0"
			+ ", 2-limit int, optional, default value is 10 , 3-sortableColumn string, optional, default value is id")
	public ApiResponse<PaginationDto> findAllPaging(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
													@RequestParam(value = "limit", defaultValue = DEFAULT_PAGE_SIZE) Integer limit,
													@RequestParam(required = false, defaultValue = "id") String sortableColumn) {
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortableColumn));
		Page<DTO> pageDto = getService().findAll(pageable).map(t -> getMapper().map(t));
		return ApiResponse.ok(PaginationDto
				.builder()
				.content(pageDto.getContent())
				.totalElements(pageDto.getTotalElements())
				.totalPages(pageDto.getTotalPages())
				.build());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Find by id", description = "to retrieve element by id")
	public ApiResponse<DTO> findById(@PathVariable(name = "id") ID id) {
		return ApiResponse.ok(getMapper().map(getService().findById(id)));
	}

	@PostMapping("")
	@Operation(summary = "Insert", description = "to adding one element by json object")
	public ApiResponse<ID> insert(@Valid @RequestBody DTO dto) {
		T entity = getMapper().unMap(dto);
		T result = getService().insert(entity);
		DTO dtos = Objects.nonNull(result)?getMapper().map(getService().findById(result.getId())):null;
		return ApiResponse.ok(Objects.nonNull(dtos)?dtos.getId():null);
	}

	@PutMapping("")
	@Operation(summary = "Update", description = "to updating one element by json object")
	public ApiResponse<?> update(@Valid @RequestBody DTO dto) {
		T entity = getMapper().unMap(dto);
		T result = getService().update(entity);
		return ApiResponse.ok(result == null ? null : result.getId());
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Find by id", description = "to retrieve element by id")
	public ApiResponse<?> deleteById(@PathVariable(name = "id") ID id) {
		getService().deleteById(id);
		return ApiResponse.ok(null);
	}

}
