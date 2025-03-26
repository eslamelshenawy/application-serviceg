package gov.saip.applicationservice.modules.plantvarieties.controller;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import gov.saip.applicationservice.modules.plantvarieties.mapper.LKPVPropertyMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVProperty;
import gov.saip.applicationservice.modules.plantvarieties.service.LKPVPropertyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/lk-pv-property", "/internal-calling/lk-pv-property"})
@RequiredArgsConstructor
@Slf4j
public class LKPVPropertyController extends BaseController<LKPVProperty, LKPVPropertyDto,Long> {

    private final LKPVPropertyMapper LKPVPropertyMapper;
    private final LKPVPropertyService LKPVPropertyService;
    @Override
    protected BaseService<LKPVProperty, Long> getService() {
        return LKPVPropertyService;
    }

    @Override
    protected BaseMapper<LKPVProperty, LKPVPropertyDto> getMapper() {
        return LKPVPropertyMapper;
    }


    @GetMapping("/all/page")
    public ApiResponse<PaginationDto> getAllPaginatedPVProperties(@RequestParam(required = false, defaultValue = "0") int page,
                                                                  @RequestParam(required = false, defaultValue = "10") int limit,
                                                                  @RequestParam(required = false,value = "search" ) String search,
                                                                  @RequestParam(required = false,value = "lkVegetarianTypeId") Long lkVegetarianTypeId,
                                                                  @RequestParam(required = false,value = "type") PVPropertyType type,
                                                                  @RequestParam(required = false,value = "excellence")PVExcellence excellence,
                                                                  @RequestParam(value = "isActive",required = false) Boolean isActive,
                                                                  @RequestParam(required = false,value = "language")String language) {
        return ApiResponse.ok(LKPVPropertyService.getAllPaginatedPvProperties(page , limit , search,lkVegetarianTypeId,type,excellence,isActive,language));
    }


    @GetMapping("/with-options/all/page")
    public ApiResponse<PaginationDto> getAllPaginatedPVPropertiesThatHaveOptionsOnly(@RequestParam(required = false, defaultValue = "0") int page,
                                                                                     @RequestParam(required = false, defaultValue = "10") int limit,
                                                                                     @RequestParam(required = false,value = "lkVegetarianTypeId") Long lkVegetarianTypeId,
                                                                                     @RequestParam(required = false,value = "type") PVPropertyType type) {
        return ApiResponse.ok(LKPVPropertyService.getAllPaginatedPvPropertiesThatHaveOptionsOnly(page , limit , lkVegetarianTypeId,type));
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Delete by id", description = "to delete element by id")
    public ApiResponse<?> deleteById(@PathVariable(name = "id") Long id) {
        LKPVPropertyService.softDeleteById(id);
        return ApiResponse.ok(null);
    }


    @GetMapping("/all")
    public ApiResponse<List<LKPVPropertyDto>> getAllPVPropertiesWithoutPaging(@RequestParam(value = "lkVegetarianTypeId" , required = false) Long lkVegetarianTypeId,
                                                                              @RequestParam(value = "type",required = false) PVPropertyType type,
                                                                              @RequestParam(value = "excellence",required = false) PVExcellence excellence) {
        return ApiResponse.ok(LKPVPropertyService.getAllPvPropertiesWithoutPaging(lkVegetarianTypeId,type,excellence));
    }


    @GetMapping("/with-options/all")
    public ApiResponse<List<LKPVPropertyDto>> getAllPVPropertiesThatHaveOptionsOnlyWithoutPaging(@RequestParam(value = "lkVegetarianTypeId" , required = false) Long lkVegetarianTypeId,
                                                                                                 @RequestParam (value = "excellence",required = false) PVExcellence excellence,
                                                                                                 @RequestParam(value = "type",required = false) PVPropertyType type) {
        return ApiResponse.ok(LKPVPropertyService.getAllPvPropertiesThatHaveOptionsOnlyWithoutPaging(lkVegetarianTypeId,excellence,type));
    }


    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam(value = "lkVegetarianTypeId" , required = false) Long lkVegetarianTypeId,
                                              @RequestParam (value = "excellence",required = false) PVExcellence excellence,
                                              @RequestParam(required = false,value = "type") PVPropertyType type) {
        String responseMessage = LKPVPropertyService.processExcelFile(file,lkVegetarianTypeId,excellence,type);
        HttpStatus status = responseMessage.equals("File uploaded and processed successfully.") ?
                HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(responseMessage);
    }

}
