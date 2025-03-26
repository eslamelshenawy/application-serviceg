package gov.saip.applicationservice.base.controller;

import java.util.List;
import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.model.BaseLkEntity;
import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

@MappedSuperclass
public abstract class BaseLkpController<T extends BaseLkEntity<ID>, DTO extends BaseLkpEntityDto<ID>, ID extends Number> {

	@Autowired
	private BaseLkServiceImpl<T, ID> baseService;

	@Autowired
	private BaseMapper<T, DTO> baseMapper;

	@GetMapping("")
	@Operation(summary = "Find All", description = "to retrieve list of all data")
	public ApiResponse findAll() {
		List<DTO> dtos = baseMapper.map(baseService.findAll());
		return ApiResponse.ok(dtos);
	}

	@GetMapping("/page")
	@Operation(summary = "Find All Pageable", description = "parameters is  1-pag int, optional, default value is 0"
			+ ", 2-limit int, optional, default value is 10 , 3-sortableColumn string, optional, default value is id")
	public ApiResponse<PaginationDto> findAllPaging(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                    @RequestParam(required = false, defaultValue = "id") String sortableColumn) {
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortableColumn));
		Page<DTO> pageDto = baseService.findAll(pageable).map(t -> baseMapper.map(t));
		return ApiResponse.ok(PaginationDto
                .builder()
                        .content(pageDto.getContent())
                        .totalElements(pageDto.getTotalElements())
                        .totalPages(pageDto.getTotalPages())
                .build());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Find by id", description = "to retrieve element by id")
	public ApiResponse<DTO> findById(@PathVariable ID id) {
		return ApiResponse.ok(baseMapper.map(baseService.findById(id)));
	}

	@PostMapping("")
	@Operation(summary = "Insert", description = "to adding one element by json object")
	public ApiResponse<ID> insert(@Valid @RequestBody DTO dto) {
		T entity = baseMapper.unMap(dto);
		T result = baseService.insert(entity);
		DTO dtos = baseMapper.map(baseService.findById(result.getId()));
		return ApiResponse.ok(dtos.getId());
	}

	@PutMapping("")
	@Operation(summary = "Update", description = "to updating one element by json object")
	public ApiResponse<?> update(@Valid @RequestBody DTO dto) {
		T entity = baseMapper.unMap(dto);
		T result = baseService.update(entity);
		DTO dtos = baseMapper.map(baseService.findById(result.getId()));
		return ApiResponse.ok(null);
	}

    @DeleteMapping("/{id}")
    @Operation(summary = "Find by id", description = "to retrieve element by id")
    public ApiResponse<?> deleteById(@PathVariable ID id) {
        baseService.deleteById(id);
        return ApiResponse.ok(null);
    }

}
