package gov.saip.applicationservice.modules.plantvarieties.controller;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.modules.plantvarieties.dto.FillingRequestInOtherCountryDto;
import gov.saip.applicationservice.modules.plantvarieties.mapper.FillingRequestInOtherCountryMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.FillingRequestInOtherCountry;
import gov.saip.applicationservice.modules.plantvarieties.service.FillingRequestInOtherCountryPlantVarietyService;
import gov.saip.applicationservice.modules.plantvarieties.service.PlantVarietyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/plant-variety-details/filling-request-other-country", "/internal-calling/plant-variety-details/filling-request-other-country"})
@RequiredArgsConstructor
@Slf4j
public class FillingRequestInOtherCountryController {
    private final FillingRequestInOtherCountryPlantVarietyService fillingRequestInOtherCountryPlantVarietyService;
    private final FillingRequestInOtherCountryMapper fillingRequestInOtherCountryMapper;
    private final PlantVarietyService plantVarietyService;
    @PostMapping
    public ApiResponse<Long> save(@RequestBody FillingRequestInOtherCountryDto dto) {
        Long plantVarietyId = plantVarietyService.getPlantVarietyId(dto.getApplication());
        dto.setPlantVarietyDetailsId(plantVarietyId);
        FillingRequestInOtherCountry fillingRequestInOtherCountry = fillingRequestInOtherCountryMapper.unMap(dto);
        return ApiResponse.ok(fillingRequestInOtherCountryPlantVarietyService.insert(fillingRequestInOtherCountry).getId());
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Long> softDeleteDocumentById(@PathVariable Long id) {
        return ApiResponse.ok(fillingRequestInOtherCountryPlantVarietyService.softFillingRequestInOtherCountryById(id));
    }

    @GetMapping("/{applicationId}")
    public ApiResponse<List<FillingRequestInOtherCountryDto>> findApplicationDusTestDocumentsByApplicationId(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(fillingRequestInOtherCountryPlantVarietyService.findAllByPlantDetailsId(applicationId));
    }
}
