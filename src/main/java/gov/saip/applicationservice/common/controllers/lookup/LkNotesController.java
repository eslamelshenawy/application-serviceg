package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.lookup.LkNotesDto;
import gov.saip.applicationservice.common.enums.NotesStepEnum;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.mapper.lookup.LkNotesMapper;
import gov.saip.applicationservice.common.model.LkNotes;
import gov.saip.applicationservice.common.service.lookup.LkNotesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/kc/notes", "/internal-calling/notes"})
@Slf4j
@RequiredArgsConstructor
public class LkNotesController {

    private final LkNotesService lkNotesService;
    private final LkNotesMapper lkNotesMapper;

    @GetMapping("")
    public ApiResponse findAllByCategoryAndStep(@RequestParam(required = false) String categoryCode,
                                                          @RequestParam(required = false) String sectionCode,
                                                          @RequestParam(required = false) String attributeCode,
                                                          @RequestParam(required = false) NotesTypeEnum notesType,
                                                          @RequestParam(required = false) NotesStepEnum notesStep) {
        return ApiResponse.ok(lkNotesService.getNotes(categoryCode, sectionCode, attributeCode,notesType,notesStep));
    }



    @GetMapping("/all")
    @Operation(summary = "Find All", description = "to retrieve list of all data")
    public ApiResponse findAll() {
        List<LkNotesDto> dtos = lkNotesMapper.map(lkNotesService.findAll());
        return ApiResponse.ok(dtos);
    }

    @GetMapping("/page")
    @Operation(summary = "Find All Pageable", description = "parameters is  1-pag int, optional, default value is 0"
            + ", 2-limit int, optional, default value is 10 , 3-sortableColumn string, optional, default value is id")
    public ApiResponse<PaginationDto> findAllPaging(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                    @RequestParam(required = false, defaultValue = "id") String sortableColumn) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortableColumn));
        Page<LkNotesDto> pageDto = lkNotesService.findAll(pageable).map(t -> lkNotesMapper.map(t));
        return ApiResponse.ok(PaginationDto
                .builder()
                .content(pageDto.getContent())
                .totalElements(pageDto.getTotalElements())
                .totalPages(pageDto.getTotalPages())
                .build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find by id", description = "to retrieve element by id")
    public ApiResponse<LkNotesDto> findById(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(lkNotesMapper.map(lkNotesService.findById(id)));
    }

    @PostMapping("")
    @Operation(summary = "Insert", description = "to adding one element by json object")
    public ApiResponse<Long> insert(@RequestBody LkNotesDto dto) {
        LkNotes entity = lkNotesMapper.unMap(dto);
        LkNotes result = lkNotesService.insert(entity);
        return ApiResponse.ok(result.getId());
    }

    @PutMapping("")
    @Operation(summary = "Update", description = "to updating one element by json object")
    public ApiResponse<?> update(@RequestBody LkNotesDto dto) {
        LkNotes entity = lkNotesMapper.unMap(dto);
        LkNotes result = lkNotesService.update(entity);
        LkNotesDto dtos = lkNotesMapper.map(lkNotesService.findById(result.getId()));
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete by id", description = "to delete element by id")
    public ApiResponse<?> deleteById(@PathVariable(name = "id") Long id) {
        lkNotesService.softDeleteById(id);
        return ApiResponse.ok("SUCCESS");
    }


    @GetMapping("/category/page")
    public  ApiResponse<PaginationDto> getAllPaginatedNotes(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                            @RequestParam(required = false, defaultValue = "id") String sortableColumn,
                                                            @RequestParam(required = false , value = "categoryId")Long categoryId,
                                                            @RequestParam(required = false) NotesStepEnum notesStep,
                                                            @RequestParam(required = false)String sectionCode,
                                                            @RequestParam(required = false)String attributeCode,
                                                            @RequestParam(required = false)String search,
                                                            @RequestParam(required = false)String noteCategoryCode){
        return ApiResponse.ok(lkNotesService.findAllPaginatedNotesByAppCategory(page, limit, sortableColumn, categoryId, notesStep, sectionCode, attributeCode, noteCategoryCode , search));
    }

}
