package gov.saip.applicationservice.common.controllers.veena;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.veena.ApplicationVeenaClassificationDto;
import gov.saip.applicationservice.common.dto.veena.ApplicationVeenaClassificationRequestDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaClassificationDto;
import gov.saip.applicationservice.common.mapper.veena.ApplicationVeenaClassificationMapper;
import gov.saip.applicationservice.common.model.veena.ApplicationVeenaClassification;
import gov.saip.applicationservice.common.service.veena.ApplicationVeenaClassificationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/application-veena-classification", "/internal-calling/application-veena-classification"})
@RequiredArgsConstructor
@Slf4j
@Getter
public class ApplicationVeenaClassificationController extends BaseController<ApplicationVeenaClassification, ApplicationVeenaClassificationRequestDto, Long> {

    private final ApplicationVeenaClassificationService service;
    private final ApplicationVeenaClassificationMapper mapper;



    @DeleteMapping("/application/{appId}")
    public ApiResponse<Long> deleteAllVeenaClassificationsByAppId(@PathVariable("appId") Long appId) {
        service.deleteAllVeenaClassificationsByAppId(appId);
        return ApiResponse.ok(appId);
    }
    @GetMapping("/application/{appId}")
    public ApiResponse<List<ApplicationVeenaClassificationRequestDto>> getVeenaClassificationsByAppId(@PathVariable("appId") Long appId) {
        return ApiResponse.ok(service.findByApplicationId(appId));
    }
}
