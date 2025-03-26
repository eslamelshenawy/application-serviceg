package gov.saip.applicationservice.common.controllers;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PetitionRequestNationalStageDto;
import gov.saip.applicationservice.common.dto.opposition.OppositionRequestDto;
import gov.saip.applicationservice.common.mapper.PetitionRequestNationalStageMapper;
import gov.saip.applicationservice.common.mapper.opposition.OppositionRequestMapper;
import gov.saip.applicationservice.common.model.PetitionRequestNationalStage;
import gov.saip.applicationservice.common.model.opposition.OppositionRequest;
import gov.saip.applicationservice.common.service.PetitionRequestNationalStageService;
import gov.saip.applicationservice.common.service.opposition.OppositionRequestService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/kc/support-service/petition-request-national-stage","/internal-calling/support-service/petition-request-national-stage"})
@AllArgsConstructor
public class PetitionRequestNationalStageController extends BaseController<PetitionRequestNationalStage, PetitionRequestNationalStageDto, Long> {


    private final PetitionRequestNationalStageMapper petitionRequestNationalStageMapper;
    private final PetitionRequestNationalStageService petitionRequestNationalStageService;
    @Override
    protected BaseService<PetitionRequestNationalStage, Long> getService() {
        return petitionRequestNationalStageService;
    }

    @Override
    protected BaseMapper<PetitionRequestNationalStage, PetitionRequestNationalStageDto> getMapper() {
        return petitionRequestNationalStageMapper;
    }

    @GetMapping("/service/{id}")
    public ApiResponse<PetitionRequestNationalStageDto> getPetitionRequestNationalStageByApplicationSupportServiceId(@PathVariable(name = "id") Long applicationSupportServiceId) {
        return ApiResponse.ok(petitionRequestNationalStageMapper.map(petitionRequestNationalStageService.findById(applicationSupportServiceId)));
    }


    @GetMapping("/check-request-number/{requestNumber}")
    public ApiResponse<Boolean> checkPetitionRequestNumberToUseInPct(@PathVariable String requestNumber){
        return ApiResponse.ok(petitionRequestNationalStageService.checkPetitionRequestNumberToUseInPct(requestNumber));
    }


}
