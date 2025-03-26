package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSupportServicesTypeDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.dto.search.SearchDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceHelperInfoDto;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.ValidationType;
import gov.saip.applicationservice.common.mapper.ApplicationSupportServicesTypeMapper;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.PetitionRequestNationalStageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/support-service/previous-requests", "/kc/support-services", "/internal-calling/support-service/previous-requests"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationSupportServicesTypeController extends BaseController<ApplicationSupportServicesType, ApplicationSupportServicesTypeDto, Long> {

    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final ApplicationSupportServicesTypeMapper applicationSupportServicesTypeMapper;
    private final PetitionRequestNationalStageService petitionRequestNationalStageService;

    @Override
    protected BaseService<ApplicationSupportServicesType, Long> getService() {
        return applicationSupportServicesTypeService;
    }

    @Override
    protected BaseMapper<ApplicationSupportServicesType, ApplicationSupportServicesTypeDto> getMapper() {
        return applicationSupportServicesTypeMapper;
    }
   @GetMapping("/{appId}/application")
    public ApiResponse<List<ApplicationSupportServicesTypeDto>> getAllSupportServicesByApplication(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationSupportServicesTypeMapper.map(applicationSupportServicesTypeService.getAllByApplicationId(appId)));
    }

    @PostMapping("/renewal-fee/create")
    public ApiResponse<Long> insertRenewalFeesRequest(@RequestBody ApplicationSupportServicesTypeDto dto) {
        ApplicationSupportServicesType supportServicesType = applicationSupportServicesTypeMapper.unMap(dto);
        return ApiResponse.ok(applicationSupportServicesTypeService.insertRenewalFeesRequest(supportServicesType));
    }

    @GetMapping("/filter")
    public ApiResponse<PaginationDto> listApplicationsByApplicationCategoryAndUserId(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "query", required = false) String query,
            @ModelAttribute SearchDto searchDto,
            @RequestParam(value = "isInternal", defaultValue = "false", required = false) boolean isInternal) {
        return ApiResponse.ok(applicationSupportServicesTypeService.getPreviousRequestsByFilter(page, limit, query, searchDto, isInternal));
    }

    @PutMapping("/{id}/status/{newStatusId}")
    public String updateRequestStatusById(@PathVariable(name = "id") Long id,
                                          @PathVariable(name = "newStatusId") Integer newStatusId) {
        applicationSupportServicesTypeService.updateRequestStatusById(id, newStatusId);
        return "SUCCESS";
    }

    @PutMapping("/{id}/status/code/{newStatusCode}")
    public String updateRequestStatusByCode(@PathVariable(name = "id") Long id,
                                          @PathVariable(name = "newStatusCode") SupportServiceRequestStatusEnum newStatusCode) {
        applicationSupportServicesTypeService.updateRequestStatusByCode(id, newStatusCode);
        return "SUCCESS";
    }

    @GetMapping("/application/{id}/code/{serviceCode}")
    public ApiResponse<Long> getLastSupportServiceRequestServiceId(@PathVariable(name = "id") Long id,
                                                                   @PathVariable(name = "serviceCode") SupportServiceType serviceCode) {
        return ApiResponse.ok(applicationSupportServicesTypeService.getLastSupportServiceRequestServiceId(id, serviceCode));
    }

    @PutMapping("/initiate-examination/{id}")
    public ApiResponse<Void> initiateExamination(@PathVariable(name = "id") Long id){
        applicationSupportServicesTypeService.initiateExamination(id);
        return ApiResponse.noContent();
    }


    @GetMapping("/{supportServiceId}/requestNumber")
    public ApiResponse<String> getSupportServicesRequestNumber(@PathVariable(name = "supportServiceId") Long supportServiceId) {
        return ApiResponse.ok(applicationSupportServicesTypeService.getSupportServicesRequestNumber(supportServiceId));
    }

    @GetMapping("/licenced-exists/{appId}")
    public ApiResponse<Boolean> applicationSupportServicesTypeLicencedExists(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationSupportServicesTypeService.applicationSupportServicesTypeLicencedExists(appId));
    }
    @GetMapping("/is-authorized/{serviceId}")
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public void authorizeSupportServiceCustomer(@PathVariable(name = "serviceId") Long serviceId){
    }
    @GetMapping("/{supportServiceId}/status")
    public LkSupportServiceRequestStatusDto getSupportServiceStatus(@PathVariable(name = "supportServiceId") Long supportServiceId){
        return applicationSupportServicesTypeService.getSupportServiceStatus(supportServiceId);
    }

    @GetMapping("/{serviceId}/status-code")
    ApiResponse<String> getSupportServiceStatusCode(@PathVariable(name = "serviceId") Long serviceId){
        return ApiResponse.ok(applicationSupportServicesTypeService.getSupportServiceStatusCode(serviceId));
    }

    @GetMapping("/application/{applicationId}/processRequestId")
    ApiResponse<Long> getPetitionRequestNationalStageSupportServicesProcessRequestId(@PathVariable(name = "applicationId") Long applicationId) {
        return ApiResponse.ok(petitionRequestNationalStageService.getPetitionRequestNationalStageSupportServicesProcessRequestId(applicationId));
    }

    @GetMapping("/service/{serviceId}/customer-data")
    ApiResponse<SupportServiceHelperInfoDto> getCreatedByCustomerCodeAndApplicationIdById(@PathVariable(name = "serviceId") Long serviceId) {
        return ApiResponse.ok(applicationSupportServicesTypeService.getCreatedByCustomerCodeAndApplicationIdById(serviceId));
    }
}