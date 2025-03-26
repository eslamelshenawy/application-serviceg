package gov.saip.applicationservice.common.controllers;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.SupportServicesTypeDecisionsDto;
import gov.saip.applicationservice.common.dto.SupportServicesTypeDecisionsResponse;
import gov.saip.applicationservice.common.mapper.SupportServiceTypeDecisionsMapper;
import gov.saip.applicationservice.common.model.SupportServicesTypeDecisions;
import gov.saip.applicationservice.common.service.SupportServiceTypeDecisionsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/kc/support-service-decision", "/internal-calling/support-service-decision"})
@AllArgsConstructor
public class SupportServiceTypeDecisionsController extends BaseController<SupportServicesTypeDecisions , SupportServicesTypeDecisionsDto , Long> {


    private final SupportServiceTypeDecisionsService supportServiceTypeDecisionsService;
    private final SupportServiceTypeDecisionsMapper decisionsMapper;

    @Override
    protected BaseService<SupportServicesTypeDecisions, Long> getService() {
        return  supportServiceTypeDecisionsService;
    }

    @Override
    protected BaseMapper<SupportServicesTypeDecisions, SupportServicesTypeDecisionsDto> getMapper() {
        return decisionsMapper;
    }

    @GetMapping("/decisions/{serviceId}")
    public ApiResponse<SupportServicesTypeDecisionsResponse> getAllDecisionsByServiceId(
            @PathVariable(name = "serviceId") Long serviceId) {

        SupportServicesTypeDecisions lastDecisionsForLoggedIn = supportServiceTypeDecisionsService.getLastDecisionsForLoggedIn(serviceId);
        return ApiResponse.ok(decisionsMapper.mapToSupportServicesTypeDecisionsResponse(lastDecisionsForLoggedIn));
    }


    @PostMapping("/withNoLogic")
    public ApiResponse<Long> insertWithNoOtherApplyingLogic(@Valid @RequestBody SupportServicesTypeDecisionsDto dto) {
        return ApiResponse.ok(supportServiceTypeDecisionsService.insertWithNoOtherApplyingLogic(dto).getId());
    }
    @PutMapping("/document")
    public void addDocumentToDecision(@RequestParam("serviceId") Long serviceId,@RequestParam("documentId")Long documentId){
        supportServiceTypeDecisionsService.addDocumentToDecision(serviceId,documentId);
    }

}
