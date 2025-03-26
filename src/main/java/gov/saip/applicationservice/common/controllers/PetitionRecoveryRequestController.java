package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationInfoDto;
import gov.saip.applicationservice.common.dto.PetitionRecoveryRequestDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.PetitionRecoveryRequestMapper;
import gov.saip.applicationservice.common.model.PetitionRecoveryRequest;
import gov.saip.applicationservice.common.service.PetitionRecoveryRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/support-service/petition-recovery", "/internal-calling/support-service/petition-recovery"})
@RequiredArgsConstructor
@Slf4j
public class PetitionRecoveryRequestController extends BaseController<PetitionRecoveryRequest, PetitionRecoveryRequestDto, Long> {
    
    private final PetitionRecoveryRequestService petitionRecoveryRequestService;
    private final PetitionRecoveryRequestMapper petitionRecoveryRequestMapper;

    @Override
    protected BaseService<PetitionRecoveryRequest, Long> getService() {
        return petitionRecoveryRequestService;
    }

    @Override
    protected BaseMapper<PetitionRecoveryRequest, PetitionRecoveryRequestDto> getMapper() {
        return petitionRecoveryRequestMapper;
    }

    @GetMapping("/{appId}/application")
    public ApiResponse<List<PetitionRecoveryRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(petitionRecoveryRequestMapper.map(petitionRecoveryRequestService.getAllByApplicationId(appId, SupportServiceType.PETITION_RECOVERY)));
    }
    
    @GetMapping("/service/{id}")
    public ApiResponse<PetitionRecoveryRequestDto> PetitionRecoveryRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(petitionRecoveryRequestMapper.map(petitionRecoveryRequestService.findById(id)));
    }

    @GetMapping("/main-application/{id}")
    public ApiResponse<ApplicationInfoDto> getMainApplicationInfo(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(petitionRecoveryRequestService.getMainApplicationInfo(id));
    }


}
