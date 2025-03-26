package gov.saip.applicationservice.modules.plantvarieties.controller;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPlantDetailsDto;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPlantDetails;
import gov.saip.applicationservice.modules.plantvarieties.service.LKPlantDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping( value = {"/kc/lk-plant-details","/internal-calling/lk-plant-details"})
public class LKPlantDetailsController extends BaseLkpController<LKPlantDetails, LKPlantDetailsDto,Integer> {

    private final LKPlantDetailsService lkPlantDetailsService;


    @GetMapping("/code")
    public ApiResponse<List<LKPlantDetails>> findByMainCode(@RequestParam("mainCode")String mainCode){
        return ApiResponse.ok(lkPlantDetailsService.findByMainCode(mainCode));
    }


}
