package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LkClassificationVersionDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.model.LkClassificationVersion;
import gov.saip.applicationservice.common.service.LkClassificationVersionService;
import gov.saip.applicationservice.common.service.lookup.LkClassificationUnitService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/classification-version", "/internal-calling/classification-version"})
@RequiredArgsConstructor
@Slf4j
public class LkClassificationVersionController extends BaseLkpController<LkClassificationVersion, LkClassificationVersionDto, Integer> {

    private final LkClassificationVersionService lkClassificationVersionService;
    private final LkClassificationUnitService lkClassificationUnitService;

    @GetMapping("/category/page")
    public ApiResponse<PaginationDto> findAllClassificationVersions(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                                    @RequestParam(value = "sortableColumn", required = false, defaultValue = "id") String sortableColumn,
                                                                    @RequestParam(value = "search", required = false) String search,
                                                                    @RequestParam(required = false , value = "categoryId",name = "categoryId")Long categoryId) {
        return ApiResponse.ok(lkClassificationVersionService.findAllClassificationVersionsBySearch(page, limit, sortableColumn, search,categoryId));
    }

    @GetMapping("/category")
    public ApiResponse<List<LkClassificationVersionDto>> findAllClassificationVersionsByCategory(@RequestParam(value = "categoryId") Long categoryId) {
        return ApiResponse.ok(lkClassificationVersionService.findAllClassificationVersionsByCategory(categoryId));
    }

    @GetMapping("/lastVersion/category/{categoryId}")
    public ApiResponse<LkClassificationVersionDto> findLatestClassificationVersionsWithClassificationByCategory(@PathVariable(name = "categoryId") Long categoryId) {
        return ApiResponse.ok(lkClassificationVersionService.findLatestClassificationVersionsWithClassificationByCategory(categoryId));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file , @RequestParam("categoryId") Long categoryId) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a valid Excel file.", HttpStatus.BAD_REQUEST);
        }
        lkClassificationVersionService.reedLocarnoSheet(file , categoryId);
        return new ResponseEntity<>("SECCESS", HttpStatus.OK);
    }

    @PostMapping("/saveLocarno")
    @Operation(summary = "saveLocarno", description = "to adding one element by json object")
    public ApiResponse<Integer> saveLocarno(@RequestBody LkClassificationVersionDto dto) {
        Integer result = lkClassificationVersionService.saveLocarno(dto);
        return ApiResponse.ok(result);
    }

    @DeleteMapping("/delete-Locarno/{id}")
    @Operation(summary = "delete-Locarno", description = "to delete one element by id")
    public ApiResponse<?> deleteLocarno(@PathVariable Integer id) {
        lkClassificationVersionService.deleteLocarno(id);
        return ApiResponse.ok("Success");
    }

}
