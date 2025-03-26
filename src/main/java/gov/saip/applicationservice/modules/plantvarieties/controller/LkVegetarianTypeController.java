package gov.saip.applicationservice.modules.plantvarieties.controller;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.LkVegetarianTypeDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.mapper.LkVegetarianTypeMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.LkVegetarianTypes;
import gov.saip.applicationservice.modules.plantvarieties.repository.LkVegetarianTypeRepository;
import gov.saip.applicationservice.modules.plantvarieties.service.LkVegetarianTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/lk-vegetarian-type", "/internal-calling/lk-vegetarian-type"})
@RequiredArgsConstructor
@Slf4j
public class LkVegetarianTypeController extends BaseController<LkVegetarianTypes, LkVegetarianTypeDto,Long> {

    private final LkVegetarianTypeMapper lkVegetarianTypeMapper;
    private final LkVegetarianTypeService lkVegetarianTypeService;
    private final LkVegetarianTypeRepository lkVegetarianTypeRepository;
    @Override
    protected BaseService<LkVegetarianTypes, Long> getService() {
        return  lkVegetarianTypeService;
    }

    @Override
    protected BaseMapper<LkVegetarianTypes, LkVegetarianTypeDto> getMapper() {
        return lkVegetarianTypeMapper;
    }


    @GetMapping("/category/page")
    public ApiResponse<PaginationDto> getAllPaginatedVegetarianTypes(@RequestParam(required = false, defaultValue = "0") int page,
                                                                     @RequestParam(required = false, defaultValue = "10") int limit ,
                                                                     @RequestParam(value = "isActive",required = false) Boolean isActive ,
                                                                     @RequestParam(value = "search",required = false) String search){
        return ApiResponse.ok(lkVegetarianTypeService.getAllPaginatedVegetarianTypes(search ,isActive,page, limit));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Find by id", description = "to retrieve element by id")
    public ApiResponse<?> deleteById(@PathVariable(name = "id") Long id) {
        lkVegetarianTypeService.softDeleteById(id);
        return ApiResponse.ok(null);
    }
    @GetMapping("/with-properties")
    public ApiResponse<List<LkVegetarianTypeDto>> getAllVegetarianTypesThatHaveOnlyPropertiesAndOptions(@RequestParam(value = "excellence",required = false) PVExcellence excellence){
        List<LkVegetarianTypeDto> lkVegetarianTypeDtos = lkVegetarianTypeMapper.map(lkVegetarianTypeService.getAllVegetarianTypesThatHaveOnlyPropertiesAndOptions(excellence));
        return ApiResponse.ok(lkVegetarianTypeDtos);
    }

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String responseMessage = lkVegetarianTypeService.processExcelFile(file);
        HttpStatus status = responseMessage.equals("File uploaded and processed successfully.") ?
                HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(responseMessage);
    }


}
