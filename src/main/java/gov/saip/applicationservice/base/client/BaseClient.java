package gov.saip.applicationservice.base.client;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public interface BaseClient<DTO extends BaseDto<ID>, ID extends Serializable> {

    @GetMapping
    public ApiResponse<List<DTO>> findAll();
    @GetMapping("/page")
    public ApiResponse<PaginationDto<List<DTO>>> findAllPaging(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                         @RequestParam(value = "sortableColumn", required = false, defaultValue = "id") String sortableColumn);

    @GetMapping("/{id}")
    public ApiResponse<DTO> findById(@PathVariable(name = "id") ID id);

    @PostMapping
    public ApiResponse<ID> insert(@Valid @RequestBody DTO dto);

    @PutMapping
    public ApiResponse<ID> update(@Valid @RequestBody DTO dto);

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteById(@PathVariable(name = "id") ID id);

}
