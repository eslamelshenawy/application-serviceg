package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.DocumentsTemplateDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.mapper.DocumentsTemplateMapper;
import gov.saip.applicationservice.common.model.DocumentsTemplate;
import gov.saip.applicationservice.common.service.DocumentsTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"kc/documentsTemplate", "/internal-calling/documentsTemplate"})
public class DocumentsTemplateController {

    private final DocumentsTemplateService documentsTemplateService;
    private final DocumentsTemplateMapper documentsTemplateMapper;
    

    @GetMapping("/{saipCode}")
    public ApiResponse<List<DocumentsTemplateDto>> findDocumentTemplates(@PathVariable String saipCode) {
        return ApiResponse.ok(documentsTemplateService.findDocumentsTemplates(saipCode));

    }


    @GetMapping("")
    @Operation(summary = "Find All", description = "to retrieve list of all data")
    public ApiResponse findAll() {
        List<DocumentsTemplateDto> dtos = documentsTemplateMapper.map(documentsTemplateService.findAll());
        return ApiResponse.ok(dtos);
    }

    @GetMapping("/page")
    @Operation(summary = "Find All Pageable", description = "parameters is  1-pag int, optional, default value is 0"
            + ", 2-limit int, optional, default value is 10 , 3-sortableColumn string, optional, default value is id")
    public ApiResponse<PaginationDto> findAllPaging(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                    @RequestParam(required = false, defaultValue = "id") String sortableColumn) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortableColumn));
        Page<DocumentsTemplateDto> pageDto = documentsTemplateService.findAll(pageable).map(t -> documentsTemplateMapper.map(t));
        return ApiResponse.ok(PaginationDto
                .builder()
                .content(pageDto.getContent())
                .totalElements(pageDto.getTotalElements())
                .totalPages(pageDto.getTotalPages())
                .build());
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Find by id", description = "to retrieve element by id")
    public ApiResponse<DocumentsTemplateDto> findById(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(documentsTemplateMapper.map(documentsTemplateService.findById(id)));
    }

    @PostMapping("")
    @Operation(summary = "Insert", description = "to adding one element by json object")
    public ApiResponse<Long> insert(@Valid @RequestBody DocumentsTemplateDto dto) {
        DocumentsTemplate entity = documentsTemplateMapper.unMap(dto);
        DocumentsTemplate result = documentsTemplateService.insert(entity);
        DocumentsTemplateDto dtos = documentsTemplateMapper.map(documentsTemplateService.findById(result.getId()));
        return ApiResponse.ok(dtos.getId());
    }

    @PutMapping("")
    @Operation(summary = "Update", description = "to updating one element by json object")
    public ApiResponse<?> update(@Valid @RequestBody DocumentsTemplateDto dto) {
        DocumentsTemplate entity = documentsTemplateMapper.unMap(dto);
        DocumentsTemplate result = documentsTemplateService.update(entity);
        DocumentsTemplateDto dtos = documentsTemplateMapper.map(documentsTemplateService.findById(result.getId()));
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Find by id", description = "to retrieve element by id")
    public ApiResponse<?> deleteById(@PathVariable(name = "id") Long id) {
        documentsTemplateService.deleteById(id);
        return ApiResponse.ok(null);
    }

}