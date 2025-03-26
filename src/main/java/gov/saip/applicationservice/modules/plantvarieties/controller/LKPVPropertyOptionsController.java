package gov.saip.applicationservice.modules.plantvarieties.controller;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyOptionsDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import gov.saip.applicationservice.modules.plantvarieties.mapper.LKPVPropertyOptionsMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVPropertyOptions;
import gov.saip.applicationservice.modules.plantvarieties.service.LKPVPropertyOptionsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping(value = {"/kc/lk-pv-property-options", "/internal-calling/lk-pv-property-options"})
@RequiredArgsConstructor
public class LKPVPropertyOptionsController extends BaseController<LKPVPropertyOptions, LKPVPropertyOptionsDto,Long> {

    private final LKPVPropertyOptionsService lkpvPropertyOptionsService;
    private final LKPVPropertyOptionsMapper lkpvPropertyOptionsMapper;
    @Override
    protected BaseService<LKPVPropertyOptions, Long> getService() {
        return lkpvPropertyOptionsService;
    }



    @Override
    protected BaseMapper<LKPVPropertyOptions, LKPVPropertyOptionsDto> getMapper() {
        return lkpvPropertyOptionsMapper;
    }


    @GetMapping("/all/page")
    public ApiResponse<PaginationDto> getAllPaginatedPVPropertyOptions(@RequestParam(required = false, defaultValue = "0") int page,
                                                                               @RequestParam(required = false, defaultValue = "10") int limit,
                                                                               @RequestParam(required = false, value = "lkPVPropertyId") Long lkPVPropertyId,
                                                                               @RequestParam(required = false, value = "type") PVPropertyType type,
                                                                               @RequestParam(required = false , value = "excellence") PVExcellence excellence,
                                                                               @RequestParam(required = false , value = "lkVegetarianTypeId") Long lkVegetarianTypeId,
                                                                               @RequestParam(value = "isActive",required = false) Boolean isActive,
                                                                               @RequestParam(required = false , value = "language") String language,
                                                                               @RequestParam(required = false, value = "search") String search) {
        return ApiResponse.ok(lkpvPropertyOptionsService.getAllPaginatedPVPropertyOptions(page , limit,lkPVPropertyId,type,excellence,lkVegetarianTypeId,isActive,language,search));
    }

    @GetMapping("/all")
    public ApiResponse<List<LKPVPropertyOptions>> getAllPVPropertyOptions(
            @RequestParam(required = false, value = "lkPVPropertyId") Long lkPVPropertyId,
            @RequestParam(required = false, value = "type") PVPropertyType type,
            @RequestParam(required = false, value = "excellence") PVExcellence excellence,
            @RequestParam(required = false, value = "search") String search) {
        return ApiResponse.ok(lkpvPropertyOptionsService.getAllPVPropertyOptions(lkPVPropertyId, type, excellence, search));
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "delete by id", description = "to soft delete element by id")
    public ApiResponse<?> deleteById(@PathVariable(name = "id") Long id) {
        lkpvPropertyOptionsService.softDeleteById(id);
        return ApiResponse.ok(null);
    }
    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "lkPVPropertyId" , required = false) Long lkPVPropertyId) {
        String responseMessage = lkpvPropertyOptionsService.processExcelFile(file,lkPVPropertyId);
        HttpStatus status = responseMessage.equals("File uploaded and processed successfully.") ?
                HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(responseMessage);
    }
}
