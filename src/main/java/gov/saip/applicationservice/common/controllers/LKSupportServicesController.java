package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LKSupportServicesDto;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.LKSupportServicesMapper;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.service.LKSupportServicesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/support-service/requests", "/internal-calling/support-service/requests"})
@RequiredArgsConstructor
@Slf4j
public class LKSupportServicesController extends BaseController<LKSupportServices, LKSupportServicesDto, Long> {

    private final LKSupportServicesService lkSupportServicesService;
    private final LKSupportServicesMapper lkSupportServicesMapper;
    @Override
    protected BaseService<LKSupportServices, Long> getService() {
        return lkSupportServicesService;
    }

    @Override
    protected BaseMapper<LKSupportServices, LKSupportServicesDto> getMapper() {
        return lkSupportServicesMapper;
    }

    @GetMapping("/specific-category")
    public ApiResponse<List<LKSupportServicesDto>> findSupportServicesByApplicationCategoryId(@RequestParam(value = "category-id" , required = false) Long categoryId){
        List<LKSupportServices> supportServices = lkSupportServicesService.findServicesByCategoryId(categoryId);
        return ApiResponse.ok(lkSupportServicesMapper.map(supportServices));
    }
    
    
    @GetMapping("/{id}/statuses")
    public ApiResponse<List<LkSupportServiceRequestStatusDto>> getSupportServiceTypeStatuses(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(lkSupportServicesService.getSupportServiceTypeStatuses(id));
    }

    @GetMapping("/type/{code}")
    public ApiResponse<LKSupportServicesDto> getSupportServiceTypeStatusesByCode(@PathVariable(name = "code") SupportServiceType code) {
        return ApiResponse.ok(lkSupportServicesMapper.map(lkSupportServicesService.findByCode(code)));
    }

}
